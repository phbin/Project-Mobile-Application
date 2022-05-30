package com.example.fooddelivery.model

import android.os.Parcel
import android.os.Parcelable
import com.google.type.DateTime

data class PromotionClass (var description:String, var expiryDate:String, var name:String,var value:String): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(expiryDate)
        parcel.writeString(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PromotionClass> {
        override fun createFromParcel(parcel: Parcel): PromotionClass {
            return PromotionClass(parcel)
        }

        override fun newArray(size: Int): Array<PromotionClass?> {
            return arrayOfNulls(size)
        }
    }
}
