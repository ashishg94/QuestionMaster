package com.azaman.apps.questionmaster.api

import com.azaman.apps.questionmaster.model.Question
import javax.inject.Inject

//TODO Need to implement authentication
class ApiServiceImpl @Inject constructor(private val apiService: ApiService){
    suspend fun getQuestionMasterItem() : List<Question> =
        apiService.getQuestionMasterItem()

}
