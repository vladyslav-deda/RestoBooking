package com.project.domain.model

data class Comment(
    val author: String = "",
    val commentText: String? = null,
    val rating: Int = 0,
    val dateAdded: Long = 0L
)
