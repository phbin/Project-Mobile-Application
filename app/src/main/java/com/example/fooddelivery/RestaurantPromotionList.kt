package com.example.fooddelivery

import android.os.Parcel
import android.os.Parcelable

data class RestaurantPromotionList(var name : String, var expiry : String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(expiry)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RestaurantPromotionList> {
        override fun createFromParcel(parcel: Parcel): RestaurantPromotionList {
            return RestaurantPromotionList(parcel)
        }

        override fun newArray(size: Int): Array<RestaurantPromotionList?> {
            return arrayOfNulls(size)
        }
    }
}
