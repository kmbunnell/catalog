package com.bonehill.catalog.ui.photoImport

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ImportScreenVM @Inject constructor(): ViewModel() {
    var txtList = mutableStateOf<List<String>>(listOf())
    fun processImage(img:InputImage) {

        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val lst = mutableListOf<String>()
        recognizer.process(img)
            .addOnSuccessListener { text->
                text.textBlocks.forEach{ block->
                    lst.add(block.text)
                }
                txtList.value=lst.distinct()

            }
            .addOnFailureListener { e -> // Task failed with an exception
                e.printStackTrace()
            }
    }

}