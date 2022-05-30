package com.example.fooddelivery.model

import android.os.Parcel
import android.os.Parcelable

data class RestaurantDishesList (var name : String, var price:String, var size:String, var image:String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!)
    {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(price)
        parcel.writeString(size)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RestaurantDishesList> {
        override fun createFromParcel(parcel: Parcel): RestaurantDishesList {
            return RestaurantDishesList(parcel)
        }

        override fun newArray(size: Int): Array<RestaurantDishesList?> {
            return arrayOfNulls(size)
        }
    }
}