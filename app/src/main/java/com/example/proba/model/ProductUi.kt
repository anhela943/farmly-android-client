package com.example.proba.model

import androidx.annotation.DrawableRes

data class ProductUi(
    val id: String,
    val name: String,
    val price: Double,
    val producer: String,
    val producerReview: Double,
    val city: String,
    @DrawableRes val imageProducer: Int,
    @DrawableRes val imageProduct: Int
) {
    companion object {
        fun from(
            name: String,
            price: Double,
            producer: String,
            producerReview: Double,
            city: String,
            @DrawableRes imageProducer: Int,
            @DrawableRes imageProduct: Int
        ): ProductUi {
            val id = listOf(name, producer, price.toString(), city).joinToString("|")
            return ProductUi(
                id = id,
                name = name,
                price = price,
                producer = producer,
                producerReview = producerReview,
                city = city,
                imageProducer = imageProducer,
                imageProduct = imageProduct
            )
        }
    }
}
