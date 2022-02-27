package com.azaman.apps.questionmaster

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import com.azaman.apps.questionmaster.QuestionViewModel.QuestionViewModel
import com.azaman.apps.questionmaster.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val questionViewModelViewModel: QuestionViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        questionViewModelViewModel.currentQuesion.observe(this, Observer {

            setQuestionFragment(it.id)

        })



        binding.nextButton.setOnClickListener {

           questionViewModelViewModel.onNext()

        }

        binding.preButton.setOnClickListener {

            questionViewModelViewModel.onPrevious()

        }

    }

    private fun setQuestionFragment(questionId: Int) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.container, QuestionFragment.newInstance(questionId))
        }
    }
}