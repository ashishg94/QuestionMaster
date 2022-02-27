package com.azaman.apps.questionmaster.QuestionViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azaman.apps.questionmaster.Repository.QuestionRepository
import com.azaman.apps.questionmaster.item.QuestionMasterItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(private val questionRepository: QuestionRepository) :
    ViewModel() {

    init {
        questionRepository.getQuestionMasterItem().onEach {
            _allQuestions.value = it
            previousQestion.value == null
            currentQuesion.value = it[0]
            assignNextQuestion()
        }.launchIn(viewModelScope)
    }

    private fun assignNextQuestion() {
        currentQuesion.value?.next_question_id?.let {
            if (it.isEmpty()) {
                val indexOf = _allQuestions.value?.indexOf(
                    currentQuesion.value
                )
                val size = _allQuestions.value?.size?:0
                if (indexOf != null && indexOf > -1 && indexOf<size-1) {
                    nextQuestion.value = _allQuestions.value?.get(indexOf + 1)
                }
                else
                {
                    nextQuestion.value=null
                }
            }
            else {
                nextQuestion.value = getQuestion(it.toInt())
            }
        }
    }

    private fun assignPrevious() {
        var preQ: QuestionMasterItem? = null
        currentQuesion.value?.id?.let { questionId ->
            _allQuestions.value?.forEach {
                if (it.next_question_id.isNotEmpty() && questionId == it.next_question_id.toInt()) {
                    preQ = it
                }
            }
        }
        if (preQ == null) {

            val indexOf = _allQuestions.value?.indexOf(
                currentQuesion.value
            )
            if (indexOf != null && indexOf > 0) {

                preQ = _allQuestions.value?.get(indexOf - 1)


            }
        }
        previousQestion.value = preQ
    }


    private val _allQuestions: MutableLiveData<List<QuestionMasterItem>> = MutableLiveData()

    val currentQuesion: MutableLiveData<QuestionMasterItem> = MutableLiveData<QuestionMasterItem>()
    val nextQuestion: MutableLiveData<QuestionMasterItem?> = MutableLiveData<QuestionMasterItem?>(null)
    val previousQestion: MutableLiveData<QuestionMasterItem?> = MutableLiveData<QuestionMasterItem?>(null)

    fun getQuestion(questionId: Int): QuestionMasterItem? {
        _allQuestions.value?.forEach {
            if (it.id == questionId) {
                return it
            }
        }
        return null
    }

    fun onNext() {
        previousQestion.value = currentQuesion.value
        currentQuesion.value = nextQuestion.value
        assignNextQuestion()
    }

    fun onPrevious() {
        nextQuestion.value = currentQuesion.value
        currentQuesion.value = previousQestion.value
        assignPrevious()
    }


}
