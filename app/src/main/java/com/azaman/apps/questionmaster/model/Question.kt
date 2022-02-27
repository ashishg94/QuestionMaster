package com.azaman.apps.questionmaster.model

class Question(
    val id: Int,
    val image: String,
    val income_finish_flag: String,
    val input_type: String,
    val is_mandatory: String,
    val next_question_id: String,
    val on_skip_next_question: String,
    val option: List<Option>,
    val question_category: String,
    val question_english: String,
    val question_group: String,
    val question_gujarati: String,
    val question_hindi: String,
    val question_kannada: String,
    val question_marathi: String,
    val question_telugu: String,
    val status: String
)

data class Option(
    val id: Int,
    val option_value: String,
    val question_id: String,
    val next_question_id: String,
    val product_id: String,
    val property_type: String,
    val property_question_id: String,
    val image: String,
    val is_option: String,
    var status: Int,
    val created_by: String,
    val updated_by: String,
    val created_at: String,
    val updated_at: String,
    var isSelected: Boolean = false
) {

}