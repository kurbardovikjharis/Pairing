package com.haris.home.data.dtos

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ItemDto(
    val name: String?,
    @Json(name = "cook_time_minutes") val cookTimeMinutes: Double?,
    @Json(name = "total_time_minutes") val totalTimeMinutes: Double?,
    @Json(name = "canonical_id") val canonicalId: String?,
    @Json(name = "video_url") val videoUrl: String?,
    @Json(name = "original_video_url") val originalVideoUrl: String?,
    @Json(name = "thumbnail_url") val thumbnailUrl: String?,
    val description: String?,
)