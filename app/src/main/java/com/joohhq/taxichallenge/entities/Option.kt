package com.joohhq.taxichallenge.entities

data class Option(
    val description: String,
    val id: Int,
    val name: String,
    val review: Review,
    val value: Double,
    val vehicle: String
)