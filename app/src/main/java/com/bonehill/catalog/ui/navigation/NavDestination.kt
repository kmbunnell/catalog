package com.bonehill.catalog.ui.navigation

interface NavDestination {
    val name: String
    val route: String
}

object PhotoImport : NavDestination {
    override val name = "Photo Import"
    override val route = "photo_import"
}

object Catalog : NavDestination {
    override val name = "Catalog"
    override val route = "catalog"
}