package com.bonehill.catalog.ui.photoImport

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntSize
import com.google.mlkit.vision.text.Text
import kotlin.math.roundToInt


@Composable
fun ImportScreen(viewModel: ImportScreenVM = hiltViewModel()) {
    val context = LocalContext.current
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {

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

                                    viewModel.findImageText(
                                        InputImage.fromFilePath(context, uri),
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

                  val w = state.bitmap.asImageBitmap().width
                    val h = state.bitmap.asImageBitmap().height
                    val img = state.bitmap.asImageBitmap()


                   /*Image(
                        bitmap = state.bitmap.asImageBitmap(),

                        contentDescription = "Selected Image",
                        modifier = Modifier.fillMaxSize().
                        pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    state.text
                                    var k = it.x

                                }
                            )
                        }
                    )*/
                    /*val customPainter = remember {
                        object : Painter() {

                            override val intrinsicSize: Size
                                get() = Size(img.width.toFloat(), img.height.toFloat())

                            override fun DrawScope.onDraw() {
                                drawImage(img)
                                drawLine(
                                    color = androidx.compose.ui.graphics.Color.Red,
                                    start = Offset(0f, 0f),
                                    end = Offset(img.width.toFloat(), img.height.toFloat()),
                                    strokeWidth = 5f
                                )
                            }
                        }
                    }
                    Image(painter = customPainter, contentDescription = null)*/
                   // TextList(lst = state.lst)

                    ImageCanvas(img, state.lst)

                }

                is ImportPhotoState.Error -> {
                    Text(text = state.message)
                }
            }
        }
    }
}

@Composable
fun ImageCanvas(imageBitmap: ImageBitmap, blocks:List<Text.TextBlock>) {

    Canvas(modifier = Modifier.fillMaxSize(

    )) {

        val canvasWidth = size.width.roundToInt()
        val canvasHeight = size.height.roundToInt()

        drawImage(
            image = imageBitmap,
            srcSize = IntSize(imageBitmap.width, imageBitmap.height),
            dstSize = IntSize(canvasWidth, canvasHeight),


        )

        blocks[3].boundingBox?.let{
            val size = Size(it.width().toFloat(), it.height().toFloat())
            blocks[3].cornerPoints
            drawRect(
                color = Blue,
                size = size,
                topLeft = Offset(it.left.toFloat() + 8, it.top.toFloat()),
                alpha = .50f

            )
        }

        /*blocks.forEach {
            drawRect()

        }*/

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