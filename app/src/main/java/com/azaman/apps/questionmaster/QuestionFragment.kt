package com.azaman.apps.questionmaster

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.fragment.app.activityViewModels
import com.azaman.apps.questionmaster.QuestionViewModel.QuestionViewModel
import com.azaman.apps.questionmaster.databinding.FragmentQuestionBinding
import com.azaman.apps.questionmaster.item.Option
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionFragment : Fragment() {

    private val questionViewModelViewModel: QuestionViewModel by activityViewModels()
    private lateinit var binding: FragmentQuestionBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       bindQuestion()
    }

    private fun bindQuestion() {
        var currentQuestion= arguments?.getInt(QUESTION_ID)
            ?.let {
                questionViewModelViewModel.getQuestion(it)
            }

        questionViewModelViewModel.currentQuesion.value?.let {
            binding.tvQuestion.text=it.question_english
            when(it.input_type){
                "radio"-> bindRadio(it.option)
                "option"-> bindOption(it.option)
                "number"-> bindNumber(it.option)
                else -> {

                }

            }
        }


    }

    private fun bindNumber(option: List<Option>) {
        binding.et.visibility=View.VISIBLE
    }

    private fun bindRadio(option: List<Option>) {

        for ( op in option ) {
            binding.radioGroup.addView(createRadio(op))
        }

        binding.radioGroup.visibility=View.VISIBLE

    }

    private fun bindOption(option: List<Option>) {

        for ( op in option ) {
            binding.options.addView(createCheckBox(op))
        }

        binding.options.visibility=View.VISIBLE

    }

    companion object {

        const val QUESTION_ID="questionId"
        @JvmStatic
        fun newInstance(questionId: Int) =
            QuestionFragment().apply {
                arguments = Bundle().apply {
                    putInt(QUESTION_ID, questionId)
                }
            }
    }


   private fun createCheckBox(option: Option):CheckBox{

        val radioButton2 = CheckBox(context)
        radioButton2.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        radioButton2.text=option.option_value
        radioButton2.tag=option
        radioButton2.id=option.id
        return radioButton2;
    }

    private fun createRadio(option: Option):RadioButton{

        val radioButton2 = RadioButton(context)
        radioButton2.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        radioButton2.text=option.option_value
        radioButton2.tag=option
        radioButton2.id=option.id
        return radioButton2;
    }
}