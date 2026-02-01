package com.example.proba.model

import com.example.proba.data.model.response.ProductListItem

data class ProductUi(
    val id: String,
    val name: String,
    val price: Double,
    val producer: String,
    val producerReview: Double?,
    val city: String,
    val imageUrl: String,
    val producerImageUrl: String? = null
) {
    companion object {
        fun fromApi(item: ProductListItem): ProductUi {
            return ProductUi(
                id = item.id,
                name = item.name,
                price = item.price,
                producer = item.producer.fullName,
                producerReview = item.producer.overallReview.overallReview,
                city = item.producer.city,
                imageUrl = item.imageUrl,
                producerImageUrl = item.producer.imageUrl
            )
        }
    }
}
