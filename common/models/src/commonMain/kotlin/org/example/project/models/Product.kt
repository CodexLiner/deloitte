package org.example.project.models

data class Product(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val price: Double = 0.0,
    val discountPercentage: Double = 0.0,
    val rating: Double = 0.0,
    val stock: Int = 0,
    val tags: List<String> = emptyList(),
    val brand: String = "",
    val sku: String = "",
    val weight: Double = 0.0,
    val dimensions: Dimensions = Dimensions(),
    val warrantyInformation: String = "",
    val shippingInformation: String = "",
    val availabilityStatus: String = "",
    val reviews: List<Review> = emptyList(),
    val returnPolicy: String = "",
    val minimumOrderQuantity: Int = 0,
    val meta: Meta = Meta(),
    val thumbnail: String = "",
    val images: List<String> = emptyList()
)

data class Dimensions(
    val width: Double = 0.0,
    val height: Double = 0.0,
    val depth: Double = 0.0
)

data class Review(
    val rating: Double = 0.0,
    val comment: String = "",
    val date: String = "",
    val reviewerName: String = "",
    val reviewerEmail: String = ""
)

data class Meta(
    val createdAt: String = "",
    val updatedAt: String = "",
    val barcode: String = "",
    val qrCode: String = ""
)