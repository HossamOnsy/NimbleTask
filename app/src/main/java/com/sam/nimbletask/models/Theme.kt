package com.sam.nimbletask.models


import com.squareup.moshi.Json

data class Theme(
    @Json(name = "color_active")
    val colorActive: String?,
    @Json(name = "color_answer_inactive")
    val colorAnswerInactive: String?,
    @Json(name = "color_answer_normal")
    val colorAnswerNormal: String?,
    @Json(name = "color_inactive")
    val colorInactive: String?,
    @Json(name = "color_question")
    val colorQuestion: String?
)