package android.training.notepad

import android.os.Build
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import org.threeten.bp.LocalDateTime

@Parcelize
data class Note(var name : String = "", var text : String = "", var dateNote : LocalDateTime ?= null, var filename : String = "") : Parcelable, Serializable




