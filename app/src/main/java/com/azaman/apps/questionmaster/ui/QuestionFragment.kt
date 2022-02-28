package com.azaman.apps.questionmaster.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.azaman.apps.questionmaster.databinding.FragmentQuestionBinding
import com.azaman.apps.questionmaster.model.Option
import com.azaman.apps.questionmaster.viewmodel.QuestionViewModel
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

        questionViewModelViewModel.currentQuesion.value?.let {
            binding.tvQuestion.text=it.question_english
            when(it.input_type){
                "radio"-> bindRadio(it.option)
                "option"-> bindOption(it.option)
                else -> {
                    bindNumber()
                }

            }
        }


    }

    private fun bindNumber() {
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
        @JvmStatic
        fun newInstance() =
            QuestionFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


   private fun createCheckBox(option: Option):CheckBox{

        val checkBox = CheckBox(context)
        checkBox.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        checkBox.text=option.option_value
        checkBox.tag=option
        checkBox.id=option.id
        checkBox.isChecked=option.isSelected
        checkBox.setOnClickListener {
            option.isSelected = !option.isSelected
        }
        return checkBox
    }

    private fun createRadio(option: Option):RadioButton{

        val button = RadioButton(context)
        button.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        button.text=option.option_value
        button.tag=option
        button.id=option.id
        button.isChecked=option.isSelected
        button.setOnClickListener {
            option.isSelected=!option.isSelected
        }
        return button
    }
}