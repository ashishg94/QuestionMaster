package com.azaman.apps.questionmaster.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.azaman.apps.questionmaster.R
import com.azaman.apps.questionmaster.databinding.ActivityMainBinding
import com.azaman.apps.questionmaster.viewmodel.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val questionViewModelViewModel: QuestionViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel=questionViewModelViewModel
        binding.lifecycleOwner=this
        questionViewModelViewModel.currentQuesion.observe(this) {
            setQuestionFragment(it.id)

        }
    }

    private fun setQuestionFragment(questionId: Int) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.container, QuestionFragment.newInstance(questionId))
        }
    }
}