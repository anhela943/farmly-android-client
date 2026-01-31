package com.example.proba.navigation

object MainRoutes {
    const val Home = "home"
    const val Explore = "explore"
    const val Favorite = "favorite"
    const val Message = "message"
    const val Profile = "profile"
    const val Product = "product"
    const val MessageChat = "message_chat/{chatId}"
    const val ProfileProducer = "profile_producer"
    const val EditProduct = "edit_product"
    const val ProfileCreate = "profile_create"
    const val ProductAdd = "product_add"
    const val ProductEdit = "product_edit"

    fun messageChatRoute(chatId: String) = "message_chat/$chatId"
}
