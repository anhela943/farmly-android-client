package com.example.proba.navigation

object MainRoutes {
    const val Home = "home"
    const val Explore = "explore"
    const val Favorite = "favorite"
    const val Message = "message"
    const val Profile = "profile"
    const val Product = "product/{productId}"
    const val MessageChat = "message_chat/{chatId}?initialMessage={initialMessage}"
    const val ProfileProducer = "profile_producer"
    const val EditProduct = "edit_product"
    const val ProfileCreate = "profile_create"
    const val ProductAdd = "product_add"
    const val ProductEdit = "product_edit"
    const val ReviewPage = "review_page"

    fun productRoute(productId: String) = "product/$productId"
    fun messageChatRoute(chatId: String, initialMessage: String? = null): String {
        return if (initialMessage != null) {
            "message_chat/$chatId?initialMessage=${java.net.URLEncoder.encode(initialMessage, "UTF-8")}"
        } else {
            "message_chat/$chatId"
        }
    }
}
