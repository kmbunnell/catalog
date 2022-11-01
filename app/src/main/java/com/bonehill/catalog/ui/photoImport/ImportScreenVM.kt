package com.bonehill.catalog.ui.photoImport

import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ImportScreenVM @Inject constructor(): ViewModel() {

    fun processImage(img:InputImage) {

        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(img)
            .addOnSuccessListener { texts ->
                val t=texts
                val lst = texts.textBlocks
            }
            .addOnFailureListener { e -> // Task failed with an exception
                e.printStackTrace()
            }

    }



}