package com.project.domain.model

data class Comment(
    val id: String? = null,
    val author: String = "",
    val commentText: String? = null,
    val rating: Int = 0,
    val dateAdded: Long = 0L,
    val textOfReplyToComment: String? = null,
    val dateOfReply: Long? = null,
)
