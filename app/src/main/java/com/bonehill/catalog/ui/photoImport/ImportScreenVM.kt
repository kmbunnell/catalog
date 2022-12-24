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
    var txtList = mutableStateOf<List<String>>(listOf())

    private val _currentState = mutableStateOf<ImportPhotoState>(ImportPhotoState.Ready)
    val uiState: State<ImportPhotoState>
        get() = _currentState

    fun findImageText(img: InputImage) {

        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val lst = mutableListOf<String>()

        recognizer.process(img)
            .addOnSuccessListener { text ->
                /*  text.textBlocks.forEach{ block->
                      lst.add(block.text)
                  }
                  txtList.value=lst.distinct()*/

            }
            .addOnFailureListener { e -> // Task failed with an exception
                e.printStackTrace()
                _currentState.value =
                    ImportPhotoState.Error("Failed to find text : ${e.localizedMessage}")
            }
    }

    fun findImageText(chosenImage: Bitmap) {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(InputImage.fromBitmap(chosenImage, 0))
            .addOnSuccessListener { text ->
                _currentState.value = ImportPhotoState.ProcessedImage(text, chosenImage)
            }
            .addOnFailureListener { e -> // Task failed with an exception
                e.printStackTrace()
                _currentState.value =
                    ImportPhotoState.Error("Failed to find text : ${e.localizedMessage}")
            }
    }

    fun setError(msg: String) {
        _currentState.value = ImportPhotoState.Error(msg)
    }

}