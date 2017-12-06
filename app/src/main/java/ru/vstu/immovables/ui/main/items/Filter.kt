package ru.vstu.immovables.ui.main.items

import android.os.Parcel
import android.os.Parcelable
import com.avito.konveyor.blueprint.Item

sealed class Filter : Item, Parcelable {
    class Chooser(
            override val id: Long,
            val name: String,
            val description: String,
            val list: List<String>,
            var choosenField: String = ""
    ): Filter() {

        constructor(parcel: Parcel) : this(
                parcel.readLong(),
                parcel.readString(),
                parcel.readString(),
                listOf<String>().apply { parcel.readStringList(this) },
                parcel.readString()
        )

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest?.writeLong(id)
            dest?.writeString(name)
            dest?.writeString(description)
            dest?.writeStringList(list)
            dest?.writeString(choosenField)
        }

        override fun describeContents(): Int = 0

        companion object CREATOR : Parcelable.Creator<Chooser> {
            override fun createFromParcel(parcel: Parcel): Chooser {
                return Chooser(parcel)
            }

            override fun newArray(size: Int): Array<Chooser?> {
                return arrayOfNulls(size)
            }
        }

    }

    class Date(
            override val id: Long,
            val name: String,
            val description: String
    ): Filter() {
        constructor(parcel: Parcel) : this(
                parcel.readLong(),
                parcel.readString(),
                parcel.readString()
        )

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest?.writeLong(id)
            dest?.writeString(name)
            dest?.writeString(description)
        }

        override fun describeContents(): Int = 0

        companion object CREATOR : Parcelable.Creator<Date> {
            override fun createFromParcel(parcel: Parcel): Date {
                return Date(parcel)
            }

            override fun newArray(size: Int): Array<Date?> {
                return arrayOfNulls(size)
            }
        }
    }

    class Input(
            override val id: Long,
            val name: String,
            val description: String
    ): Filter() {
        constructor(parcel: Parcel) : this(
                parcel.readLong(),
                parcel.readString(),
                parcel.readString()
        )

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest?.writeLong(id)
            dest?.writeString(name)
            dest?.writeString(description)
        }

        override fun describeContents(): Int = 0

        companion object CREATOR : Parcelable.Creator<Input> {
            override fun createFromParcel(parcel: Parcel): Input {
                return Input(parcel)
            }

            override fun newArray(size: Int): Array<Input?> {
                return arrayOfNulls(size)
            }
        }
    }

    class Range(
            override val id: Long,
            val name: String,
            val description: String
    ): Filter() {
        constructor(parcel: Parcel) : this(
                parcel.readLong(),
                parcel.readString(),
                parcel.readString()
        )

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest?.writeLong(id)
            dest?.writeString(name)
            dest?.writeString(description)
        }

        override fun describeContents(): Int = 0

        companion object CREATOR : Parcelable.Creator<Range> {
            override fun createFromParcel(parcel: Parcel): Range {
                return Range(parcel)
            }

            override fun newArray(size: Int): Array<Range?> {
                return arrayOfNulls(size)
            }
        }
    }
}