package com.example.fooddelivery.model

import android.os.Parcel
import android.os.Parcelable

data class RestaurantMenuList(var menuName : String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(menuName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RestaurantMenuList> {
        override fun createFromParcel(parcel: Parcel): RestaurantMenuList {
            return RestaurantMenuList(parcel)
        }

        override fun newArray(size: Int): Array<RestaurantMenuList?> {
            return arrayOfNulls(size)
        }
    }
}