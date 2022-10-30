package com.bonehill.catalog.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bonehill.catalog.ui.catalogList.CatalogScreen
import com.bonehill.catalog.ui.photoImport.ImportScreen


@Composable
fun CatalogNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = PhotoImport.route,
        modifier = modifier
    ) {
        composable(route = PhotoImport.route) {
          ImportScreen()
        }
        composable(route = Catalog.route) {
            CatalogScreen()
        }
    }

}