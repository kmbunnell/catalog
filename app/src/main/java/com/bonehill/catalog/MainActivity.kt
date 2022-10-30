package com.bonehill.catalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.bonehill.catalog.ui.theme.CatalogTheme

import com.bonehill.catalog.ui.navigation.BottomNavBar
import com.bonehill.catalog.ui.navigation.Catalog
import com.bonehill.catalog.ui.navigation.CatalogNavHost
import com.bonehill.catalog.ui.navigation.PhotoImport

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CatalogTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavBar(
                            items = listOf(
                                PhotoImport, Catalog
                            ),
                            navController = navController,
                            onItemClick = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                ) {
                    CatalogNavHost(navController=navController)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}