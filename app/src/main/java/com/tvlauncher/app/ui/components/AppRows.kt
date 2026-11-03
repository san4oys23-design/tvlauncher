package com.tvlauncher.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import android.widget.ImageView
import com.tvlauncher.app.viewmodel.AppInfo

@Composable
fun AppRows(
    apps: List<AppInfo>,
    recent: List<AppInfo>,
    onAppClick: (AppInfo) -> Unit,
    onFavoriteToggle: (AppInfo) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        if (recent.isNotEmpty()) {
            Text(
                text = "Недавние",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyAppRow(items = recent, onAppClick = onAppClick)
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        Text(
            text = "Все приложения",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyAppRow(items = apps, onAppClick = onAppClick)
    }
}

@Composable
fun LazyAppRow(items: List<AppInfo>, onAppClick: (AppInfo) -> Unit) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items) { app ->
            AppCard(app = app, onClick = { onAppClick(app) })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppCard(app: AppInfo, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.size(120.dp, 100.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AndroidView(
                factory = { context ->
                    ImageView(context).apply {
                        setImageDrawable(app.icon)
                    }
                },
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = app.label,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1
            )
        }
    }
}
