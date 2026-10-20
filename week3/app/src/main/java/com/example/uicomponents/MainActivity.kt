package com.example.uicomponents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

object Routes {
    const val WELCOME = "welcome"
    const val LIST = "list"
    const val TEXT = "text"
    const val IMAGE = "image"
    const val TEXTFIELD = "textfield"
    const val PASSWORD = "password"
    const val COLUMN = "column"
    const val ROW = "row"
    const val ALL = "all"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface { AppNavigation() }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = Routes.WELCOME) {
        composable(Routes.WELCOME) {
            WelcomeScreen(onReady = {
                nav.navigate(Routes.LIST) { popUpTo(Routes.WELCOME) { inclusive = true } }
            })
        }
        composable(Routes.LIST) { ComponentsListScreen(onItemClick = { nav.navigate(it) }) }
        composable(Routes.TEXT) { TextDetailScreen(onBack = { nav.popBackStack() }) }
        composable(Routes.IMAGE) { ImageDetailScreen(onBack = { nav.popBackStack() }) }
        composable(Routes.TEXTFIELD) { TextFieldDetailScreen(onBack = { nav.popBackStack() }) }
        composable(Routes.PASSWORD) { PasswordFieldDetailScreen(onBack = { nav.popBackStack() }) }
        composable(Routes.COLUMN) { ColumnLayoutScreen(onBack = { nav.popBackStack() }) }
        composable(Routes.ROW) { RowLayoutScreen(onBack = { nav.popBackStack() }) }
        composable(Routes.ALL) { AllComponentsScreen(onBack = { nav.popBackStack() }) }
    }
}