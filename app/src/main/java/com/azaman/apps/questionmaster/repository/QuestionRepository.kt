package com.azaman.apps.questionmaster.repository

import com.azaman.apps.questionmaster.api.ApiServiceImpl
import com.azaman.apps.questionmaster.model.Question
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class QuestionRepository @Inject constructor(private val apiServiceImpl: ApiServiceImpl) {

    fun getQuestionMasterItem(): Flow<List<Question>> = flow {
        val response = apiServiceImpl.getQuestionMasterItem()
        emit(response)
    }.flowOn(Dispatchers.IO)
        .conflate()
}