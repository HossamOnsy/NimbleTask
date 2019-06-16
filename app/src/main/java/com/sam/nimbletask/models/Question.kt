package com.sam.nimbletask.models


import com.squareup.moshi.Json

data class Question(
    @Json(name = "answers")
    val answers: List<Any?>?,
    @Json(name = "correct_answer_id")
    val correctAnswerId: Any?,
    @Json(name = "cover_background_color")
    val coverBackgroundColor: Any?,
    @Json(name = "cover_image_opacity")
    val coverImageOpacity: Double?,
    @Json(name = "cover_image_url")
    val coverImageUrl: String?,
    @Json(name = "display_order")
    val displayOrder: Int?,
    @Json(name = "display_type")
    val displayType: String?,
    @Json(name = "facebook_profile")
    val facebookProfile: String?,
    @Json(name = "font_face")
    val fontFace: Any?,
    @Json(name = "font_size")
    val fontSize: Any?,
    @Json(name = "help_text")
    val helpText: Any?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "image_url")
    val imageUrl: Any?,
    @Json(name = "is_mandatory")
    val isMandatory: Boolean?,
    @Json(name = "is_shareable_on_facebook")
    val isShareableOnFacebook: Boolean?,
    @Json(name = "is_shareable_on_twitter")
    val isShareableOnTwitter: Boolean?,
    @Json(name = "pick")
    val pick: String?,
    @Json(name = "short_text")
    val shortText: String?,
    @Json(name = "tag_list")
    val tagList: String?,
    @Json(name = "text")
    val text: String?,
    @Json(name = "twitter_profile")
    val twitterProfile: Any?
)