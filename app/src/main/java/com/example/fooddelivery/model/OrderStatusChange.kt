package com.example.fooddelivery.model

data class OrderStatusChange(
    var date : String,
    var deliveryFee : String,
    var idCustomer : String,
    var idPromotion : String,
    var idRestaurant : String,
    var idShipper : String,
    var latCus : String,
    var longCus : String,
    var quantity : String,
    var status : String,
    var total : String,
    var distance : String)
