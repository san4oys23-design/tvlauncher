package com.tvlauncher.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.hilt.navigation.compose.hiltViewModel
import com.tvlauncher.app.ui.components.TopBar
import com.tvlauncher.app.ui.components.AppRows
import com.tvlauncher.app.ui.components.FavoritesSection
import com.tvlauncher.app.ui.theme.TVLauncherTheme
import com.tvlauncher.app.viewmodel.LauncherViewModel

@Composable
fun MainScreen(viewModel: LauncherViewModel = hiltViewModel()) {
    val apps by viewModel.apps.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    val recent by viewModel.recentApps.collectAsState()
    
    TVLauncherTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopBar()
            FavoritesSection(favorites = favorites, onAppClick = { viewModel.launchApp(it) })
            AppRows(
                apps = apps,
                recent = recent,
                onAppClick = { viewModel.launchApp(it) },
                onFavoriteToggle = { viewModel.toggleFavorite(it) }
            )
        }
    }
}
