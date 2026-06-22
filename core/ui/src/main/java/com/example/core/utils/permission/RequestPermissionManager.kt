package com.example.core.utils.permission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

sealed class PermissionDialogState {
    data object Hidden : PermissionDialogState()
    data class Rationale(val permissions: List<String>) : PermissionDialogState()
    data class Settings(val permissions: List<String>) : PermissionDialogState()
}

@Stable
class PermissionManager(
    private val permissions: List<String>,
    private val context: Context,
    private val activity: Activity?
) {
    var dialogState by mutableStateOf<PermissionDialogState>(PermissionDialogState.Hidden)
        internal set

    internal var currentAction: () -> Unit = {}
    internal var launcher: ActivityResultLauncher<Array<String>>? = null

    fun triggerAction() {
        val needToRequest = permissions.filter {
            ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
        }

        if (needToRequest.isEmpty()) {
            currentAction()
        } else {
            val shouldShowRationale = needToRequest.any { permission ->
                activity?.let { ActivityCompat.shouldShowRequestPermissionRationale(it, permission) } == true
            }
            if (shouldShowRationale) {
                dialogState = PermissionDialogState.Rationale(needToRequest)
            } else {
                launcher?.launch(needToRequest.toTypedArray())
            }
        }
    }

    fun onConfirmDialog() {
        when (val state = dialogState) {
            is PermissionDialogState.Rationale -> {
                dialogState = PermissionDialogState.Hidden
                launcher?.launch(state.permissions.toTypedArray())
            }
            is PermissionDialogState.Settings -> {
                dialogState = PermissionDialogState.Hidden
                 context.openAppSystemSettings()
            }
            PermissionDialogState.Hidden -> {}
        }
    }

    fun onDismissDialog() {
        dialogState = PermissionDialogState.Hidden
    }
}

@Composable
fun rememberPermissionManager(
    permissions: List<String>,
    action: () -> Unit
): PermissionManager {
    val context = LocalContext.current
    val activity = LocalActivity.current
    val currentAction by rememberUpdatedState(action)
    val manager = remember(permissions, context, activity) {
        PermissionManager(permissions, context, activity)
    }
    manager.currentAction = currentAction
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val deniedList = result.filter { !it.value }.keys.toList()
        if (deniedList.isEmpty()) {
            manager.currentAction()
        } else {
            val shouldShowRationale = deniedList.any { permission ->
                activity?.let { ActivityCompat.shouldShowRequestPermissionRationale(it, permission) } == true
            }
            manager.dialogState = if (shouldShowRationale) {
                PermissionDialogState.Hidden
            } else {
                PermissionDialogState.Settings(deniedList)
            }
        }
    }
    manager.launcher = launcher
    return manager
}