package com.example.uts.model

data class ProductResponse(
    val products: List<Product> = emptyList(),
    val total: Int = 0,
    val skip: Int = 0,
    val limit: Int = 0
)

data class Product(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val price: Double = 0.0,
    val stock: Int = 0,
    val availabilityStatus: String = "",
    val tags: List<String> = emptyList(),
    val dimensions: Dimensions = Dimensions(),
    val reviews: List<Review> = emptyList(),
    val rating: Double = 0.0,
    val images: List<String> = emptyList(),
    val thumbnail: String = ""
)

data class Dimensions(
    val width: Double = 0.0,
    val height: Double = 0.0,
    val depth: Double = 0.0
)

data class Review(
    val rating: Int = 0,
    val comment: String = "",
    val date: String = "",
    val reviewerName: String = "",
    val reviewerEmail: String = ""
)
