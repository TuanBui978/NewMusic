package com.example.core.utils.permission

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable

@Composable
fun rememberPermissionAction(
    permissions: List<String>,
    rationaleContent: @Composable (List<String>) -> PermissionDialogContent = { PermissionDialogDefaults.rationaleContent(it) },
    settingsContent: @Composable (List<String>) -> PermissionDialogContent = { PermissionDialogDefaults.settingsContent(it) },
    action: () -> Unit
): () -> Unit {
    val manager = rememberPermissionManager(
        permissions = permissions,
        action = action
    )
    PermissionDialogUI(
        manager = manager,
        rationaleContent = rationaleContent,
        settingsContent = settingsContent
    )
    return manager::triggerAction
}


fun Context.openAppSystemSettings() {
    startActivity(Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        data = Uri.fromParts("package", packageName, null)
    })
}
