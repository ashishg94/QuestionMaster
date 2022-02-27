package com.azaman.apps.questionmaster.api

import com.azaman.apps.questionmaster.item.QuestionMasterItem
import retrofit2.http.GET
import retrofit2.http.Query


    interface ApiService {
        @GET("api/v2/customerQuestionnaireMaster")
        suspend fun getQuestionMasterItem(): List<QuestionMasterItem>
}
