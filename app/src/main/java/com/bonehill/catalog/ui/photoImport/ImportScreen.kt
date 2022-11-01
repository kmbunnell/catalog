package com.bonehill.catalog.ui.photoImport

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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


@Composable
fun ImportScreen( viewModel:ImportScreenVM = hiltViewModel()){

    Surface(
        color= MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ){
        val bookList by remember { viewModel.txtList }
        val context = LocalContext.current
        val pickMedia =
            rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                // Callback is invoked after the user selects a media item or closes the
                // photo picker.
                uri?.let {
                    Log.d("PhotoPicker", "Selected URI: $uri")
                    viewModel.processImage( InputImage.fromFilePath(context, uri))
                }
            }

        Column() {
            Spacer(modifier = Modifier.height(20.dp))
            Button( onClick = { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }){
                Text(text = "Pick Image")
            }
            TextList(bookList)
        }

    }
}



@Composable
fun TextList(lst:List<String>)
{
    LazyColumn(contentPadding = PaddingValues( 16.dp)){

        items(lst.size){

            Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically

            )
            {
               Text(text = lst[it]) 
               
            }
        }
    }

}