package com.azaman.apps.questionmaster.api

import com.azaman.apps.questionmaster.item.QuestionMasterItem
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService: ApiService){

    suspend fun getQuestionMasterItem() : List<QuestionMasterItem> =
        apiService.getQuestionMasterItem()

}
