package com.bonehill.catalog.ui.photoImport

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.util.Log
import android.widget.ImageView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.mlkit.vision.common.InputImage
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.asImageBitmap


@Composable
fun ImportScreen(viewModel: ImportScreenVM = hiltViewModel()) {
    val context = LocalContext.current
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {

        //val bookList by remember { viewModel.txtList }
        Column(
            modifier = Modifier.padding(
                horizontal = 8.dp,
                vertical = 8.dp
            )
        ) {

            when (val state = viewModel.uiState.value) {
                ImportPhotoState.Ready -> {
                    val pickMedia =
                        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                            // Callback is invoked after the user selects a media item or closes the
                            // photo picker.
                            uri?.let {
                                Log.d("PhotoPicker", "Selected URI: $uri")
                                try {
                                    Log.d("PhotoPicker", "Attempting decode image to bitmap")
                                    // viewModel.findImageText(InputImage.fromFilePath(context, uri))
                                    viewModel.findImageText(
                                        ImageDecoder.decodeBitmap(
                                            ImageDecoder
                                                .createSource(context.contentResolver, uri)
                                        )
                                    )
                                } catch (ex: java.lang.Exception) {
                                    Log.d("PhotoPicker", "Failed decode image to bitmap")
                                    viewModel.setError("Error decoding image: ${ex.localizedMessage}")
                                }
                            }
                        }

                    Button(onClick = {
                        pickMedia.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }) {
                        Text(text = "Pick Image")
                    }
                }

                is ImportPhotoState.ProcessedImage -> {

                    Image(bitmap = state.bitmap.asImageBitmap(),
                        contentDescription = "Selected Image",
                    modifier = Modifier.fillMaxSize())


                }

                is ImportPhotoState.Error -> {
                    Text(text = state.message)
                }

            }


        }

    }
}


@Composable
fun TextList(lst: List<String>) {
    LazyColumn(contentPadding = PaddingValues(16.dp)) {

        items(lst.size) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            )
            {
                Text(text = lst[it])

            }
        }
    }

}