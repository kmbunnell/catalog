package com.bonehill.catalog.ui.catalogList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CatalogScreen(
){

    Surface(
        color= MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ){
        Column() {
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Catalog")
        }

    }
}
