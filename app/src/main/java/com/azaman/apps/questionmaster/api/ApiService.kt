package com.azaman.apps.questionmaster.api

import com.azaman.apps.questionmaster.model.Question
import retrofit2.http.GET

interface ApiService {
    @GET("api/v2/customerQuestionnaireMaster")
    suspend fun getQuestionMasterItem(): List<Question>
}
