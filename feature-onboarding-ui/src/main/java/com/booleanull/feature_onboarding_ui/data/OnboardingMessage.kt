package com.booleanull.feature_onboarding_ui.data

import android.os.Parcel
import android.os.Parcelable

data class OnboardingMessage(val title: String, val description: CharSequence) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description.toString())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OnboardingMessage> {
        override fun createFromParcel(parcel: Parcel): OnboardingMessage {
            return OnboardingMessage(parcel)
        }

        override fun newArray(size: Int): Array<OnboardingMessage?> {
            return arrayOfNulls(size)
        }
    }

}