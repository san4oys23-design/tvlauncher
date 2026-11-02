package com.tvlauncher.app.viewmodel

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AppInfo(
    val packageName: String,
    val label: String,
    val icon: android.graphics.drawable.Drawable,
    val isSystem: Boolean,
    val isFavorite: Boolean = false
)

@HiltViewModel
class LauncherViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {
    
    private val _apps = MutableStateFlow<List<AppInfo>>(emptyList())
    val apps: StateFlow<List<AppInfo>> = _apps.asStateFlow()
    
    private val _favorites = MutableStateFlow<List<AppInfo>>(emptyList())
    val favorites: StateFlow<List<AppInfo>> = _favorites.asStateFlow()
    
    private val _recentApps = MutableStateFlow<List<AppInfo>>(emptyList())
    val recentApps: StateFlow<List<AppInfo>> = _recentApps.asStateFlow()
    
    private val packageManager = application.packageManager
    
    init {
        loadApps()
    }
    
    private fun loadApps() {
        viewModelScope.launch {
            val installedApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
                .filter { app ->
                    packageManager.getLaunchIntentForPackage(app.packageName) != null
                }
                .map { app ->
                    AppInfo(
                        packageName = app.packageName,
                        label = packageManager.getApplicationLabel(app).toString(),
                        icon = packageManager.getApplicationIcon(app),
                        isSystem = (app.flags and ApplicationInfo.FLAG_SYSTEM) != 0
                    )
                }
                .sortedBy { it.label }
            
            _apps.value = installedApps
        }
    }
    
    fun launchApp(app: AppInfo) {
        val intent = packageManager.getLaunchIntentForPackage(app.packageName)
        intent?.let {
            it.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            getApplication<Application>().startActivity(it)
        }
    }
    
    fun toggleFavorite(app: AppInfo) {
        // Simple toggle - in full implementation would save to DataStore
        val updated = _favorites.value.toMutableList()
        if (app in updated) {
            updated.remove(app)
        } else {
            updated.add(app)
        }
        _favorites.value = updated
    }
}
