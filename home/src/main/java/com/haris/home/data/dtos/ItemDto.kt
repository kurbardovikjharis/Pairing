package com.haris.home.data.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemDto(
    val name: String?,
    @SerialName("cook_time_minutes") val cook_time_minutes: Double?,
    @SerialName("total_time_minutes") val total_time_minutes: Double?,
    @SerialName("canonical_id") val canonical_id: String?,
    @SerialName("video_url") val video_url: String?,
    @SerialName("original_video_url") val original_video_url: String?,
    @SerialName("thumbnail_url") val thumbnail_url: String?,
    val description: String?,
)