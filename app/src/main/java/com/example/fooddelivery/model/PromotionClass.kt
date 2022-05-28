package com.example.fooddelivery.model

import android.os.Parcel
import android.os.Parcelable
import com.google.type.DateTime

data class PromotionClass (var code:String,var description:String, var expiryDate:String, var name:String,var value:String, var image:String): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(code)
        parcel.writeString(description)
        parcel.writeString(expiryDate)
        parcel.writeString(value)
        parcel.writeString(image)
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
