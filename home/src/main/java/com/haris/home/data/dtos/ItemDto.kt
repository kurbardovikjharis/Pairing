package com.haris.home.data.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemDto(
    val name: String?,
    @SerialName("cook_time_minutes") val cookTimeMinutes: Double?,
    @SerialName("total_time_minutes") val totalTimeMinutes: Double?,
    @SerialName("canonical_id") val canonicalId: String?,
    @SerialName("video_url") val videoUrl: String?,
    @SerialName("original_video_url") val originalVideoUrl: String?,
    @SerialName("thumbnail_url") val thumbnailUrl: String?,
    val description: String?,
)