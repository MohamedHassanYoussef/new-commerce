package com.example.commerceapp.model


data class SmartCollectionList (
    val smartCollections: List<SmartCollection>
)

data class SmartCollection (
    val id: Long,
    val handle: String,
    val title: String,
    val updatedAt: String,
    val bodyHTML: String,
    val publishedAt: String,
    val sortOrder: SortOrder,
    val templateSuffix: Any? = null,
    val disjunctive: Boolean,
    val rules: List<Rule>,
    val publishedScope: PublishedScope1,
    val adminGraphqlAPIID: String,
    val image: Image1
)

data class Image1 (
    val createdAt: String,
    val alt: Any? = null,
    val width: Long,
    val height: Long,
    val src: String
)

enum class PublishedScope1 {
    Web
}

data class Rule (
    val column: Column,
    val relation: Relation,
    val condition: String
)

enum class Column {
    Title
}

enum class Relation {
    Contains
}

enum class SortOrder {
    BestSelling
}
