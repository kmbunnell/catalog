package com.bonehill.catalog.ui.photoImport

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import java.security.MessageDigest
import javax.inject.Inject


@HiltViewModel
class ImportScreenVM @Inject constructor() : ViewModel() {


    private val _currentState = mutableStateOf<ImportPhotoState>(ImportPhotoState.Ready)
    val uiState: State<ImportPhotoState>
        get() = _currentState

    fun findImageText(img: InputImage, chosenImage: Bitmap) {

        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val lst = mutableListOf<String>()

        recognizer.process(img)
            .addOnSuccessListener { text ->
                lst.add(text.text)
                text.textBlocks
                /*  text.textBlocks.forEach{ block->
                      lst.add(block.text)
                  }*/

                _currentState.value = ImportPhotoState.ProcessedImage(text, chosenImage, text.textBlocks)
                var w = chosenImage.width
                var h = chosenImage.height

            }
            .addOnFailureListener { e -> // Task failed with an exception
                e.printStackTrace()
                _currentState.value =
                    ImportPhotoState.Error("Failed to find text : ${e.localizedMessage}")
            }
    }

   /* this is returning 0 text when I hand it the bitmap.  Need to revisit.
   fun findImageText(chosenImage: Bitmap) {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(InputImage.fromBitmap(chosenImage, 90))
            .addOnSuccessListener { text ->
                _currentState.value = ImportPhotoState.ProcessedImage(text, chosenImage)
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                _currentState.value =
                    ImportPhotoState.Error("Failed to find text : ${e.localizedMessage}")
            }
    }*/

    fun setError(msg: String) {
        _currentState.value = ImportPhotoState.Error(msg)
    }

}