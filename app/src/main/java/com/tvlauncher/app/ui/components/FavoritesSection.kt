package com.tvlauncher.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tvlauncher.app.viewmodel.AppInfo

@Composable
fun FavoritesSection(favorites: List<AppInfo>, onAppClick: (AppInfo) -> Unit) {
    if (favorites.isEmpty()) return
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Избранное",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        androidx.compose.foundation.lazy.LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(favorites) { app ->
                AppCard(app = app, onClick = { onAppClick(app) })
            }
        }
    }
}
