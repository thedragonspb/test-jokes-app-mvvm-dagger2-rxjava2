package thedragonspb.testjokesapp.joke.domain.model

import android.os.Parcel
import android.os.Parcelable
import thedragonspb.testjokesapp.categories.domain.model.Category

data class Joke(
    val id: String,
    val text: String,
    val category: Category?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        id = parcel.readString() ?: "",
        text = parcel.readString() ?: "",
        category = parcel.readParcelable(Category::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(category, flags)
        parcel.writeString(id)
        parcel.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Joke> {
        override fun createFromParcel(parcel: Parcel): Joke {
            return Joke(parcel)
        }

        override fun newArray(size: Int): Array<Joke?> {
            return arrayOfNulls(size)
        }
    }
}