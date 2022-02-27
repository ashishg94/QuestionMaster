package com.azaman.apps.questionmaster.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azaman.apps.questionmaster.model.Question
import com.azaman.apps.questionmaster.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(private val questionRepository: QuestionRepository) :
    ViewModel() {

    private val allQuestions: MutableLiveData<List<Question>> = MutableLiveData()
    val currentQuesion: MutableLiveData<Question> = MutableLiveData<Question>()
    val nextQuestion: MutableLiveData<Question?> = MutableLiveData<Question?>(null)
    val previousQestion: MutableLiveData<Question?> = MutableLiveData<Question?>(null)

    init {
        questionRepository.getQuestionMasterItem().onEach {
            allQuestions.value = it
            previousQestion.value == null
            currentQuesion.value = it[0]
            assignNextQuestion()
        }.launchIn(viewModelScope)
    }

    private fun assignNextQuestion() {
        var nextQ: Question? = null
        currentQuesion.value?.next_question_id?.let {
            if (it.isEmpty()) {
                val indexOf = allQuestions.value?.indexOf(
                    currentQuesion.value
                )
                val size = allQuestions.value?.size ?: 0
                if (indexOf != null && indexOf > -1 && indexOf < size - 1) {
                    nextQ = allQuestions.value?.get(indexOf + 1)

                }
            } else {
                nextQ = getQuestion(it.toInt())
            }
        }
        nextQuestion.value = nextQ

    }

    private fun assignPrevious() {
        var preQ: Question? = null
        currentQuesion.value?.id?.let { questionId ->
            allQuestions.value?.forEach {
                if (it.next_question_id.isNotEmpty() && questionId == it.next_question_id.toInt()) {
                    preQ = it
                }
            }
        }
        if (preQ == null) {
            val indexOf = allQuestions.value?.indexOf(
                currentQuesion.value
            )
            if (indexOf != null && indexOf > 0) {
                preQ = allQuestions.value?.get(indexOf - 1)
            }
        }
        previousQestion.value = preQ
    }


    fun getQuestion(questionId: Int): Question? {
        allQuestions.value?.forEach {
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
