package com.bonehill.catalog.ui.photoImport

import android.graphics.Bitmap
import com.google.mlkit.vision.text.Text

sealed class ImportPhotoState{

    object Ready: ImportPhotoState()
    data class ProcessedImage(val text: Text,val  bitmap: Bitmap, val lst:List<String>):ImportPhotoState()
    data class Error (val message:String): ImportPhotoState()

}
