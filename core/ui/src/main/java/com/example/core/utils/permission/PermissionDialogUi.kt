package com.example.core.utils.permission

import android.Manifest
import android.os.Build
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.core.ui.R


object PermissionDialogDefaults {
    enum class PermissionGroup(
        @field:StringRes val rationaleTitleId: Int,
        @field:StringRes val rationaleMessageId: Int,
        @field:StringRes val settingsTitleId: Int,
        @field:StringRes val settingsMessageId: Int
    ) {
        MULTIPLE(
            R.string.permission_rationale_title_multiple,
            R.string.permission_rationale_message_multiple,
            R.string.permission_settings_title_multiple,
            R.string.permission_settings_message_multiple
        ),
        CAMERA(
            R.string.permission_rationale_title_camera,
            R.string.permission_rationale_message_camera,
            R.string.permission_settings_title_camera,
            R.string.permission_settings_message_camera
        ),
        MIC(
            R.string.permission_rationale_title_mic,
            R.string.permission_rationale_message_mic,
            R.string.permission_settings_title_mic,
            R.string.permission_settings_message_mic
        ),
        LOCATION(
            R.string.permission_rationale_title_location,
            R.string.permission_rationale_message_location,
            R.string.permission_settings_title_location,
            R.string.permission_settings_message_location
        ),
        MUSIC(
            R.string.permission_rationale_title_music,
            R.string.permission_rationale_message_music,
            R.string.permission_settings_title_music,
            R.string.permission_settings_message_music
        ),
        STORAGE(
            R.string.permission_rationale_title_storage,
            R.string.permission_rationale_message_storage,
            R.string.permission_settings_title_storage,
            R.string.permission_settings_message_storage
        ),
        DEFAULT(
            R.string.permission_rationale_title_default,
            R.string.permission_rationale_message_default,
            R.string.permission_settings_title_default,
            R.string.permission_settings_message_default
        )
    }

    private fun getPermissionGroup(deniedPermissions: List<String>): PermissionGroup {
        return when {
            deniedPermissions.size > 1 -> PermissionGroup.MULTIPLE
            deniedPermissions.contains(Manifest.permission.CAMERA) -> PermissionGroup.CAMERA
            deniedPermissions.contains(Manifest.permission.RECORD_AUDIO) -> PermissionGroup.MIC
            isLocationPermission(deniedPermissions) -> PermissionGroup.LOCATION
            isMusicPermission(deniedPermissions) -> PermissionGroup.MUSIC
            isStoragePermission(deniedPermissions) -> PermissionGroup.STORAGE
            else -> PermissionGroup.DEFAULT
        }
    }

    @Composable
    fun rationaleContent(deniedPermissions: List<String>): PermissionDialogContent {
        val group = getPermissionGroup(deniedPermissions)
        return PermissionDialogContent(
            title = stringResource(id = group.rationaleTitleId),
            message = stringResource(id = group.rationaleMessageId),
            positiveText = stringResource(id = R.string.action_grant_permission),
            negativeText = stringResource(id = R.string.action_cancel)
        )
    }

    @Composable
    fun settingsContent(deniedPermissions: List<String>): PermissionDialogContent {
        val group = getPermissionGroup(deniedPermissions)
        return PermissionDialogContent(
            title = stringResource(id = group.settingsTitleId),
            message = stringResource(id = group.settingsMessageId),
            positiveText = stringResource(id = R.string.action_open_settings),
            negativeText = stringResource(id = R.string.action_cancel)
        )
    }

    private fun isMusicPermission(permissions: List<String>): Boolean {
        return permissions.any {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && it == Manifest.permission.READ_MEDIA_AUDIO
        }
    }

    private fun isLocationPermission(permissions: List<String>): Boolean {
        return permissions.any {
            it == Manifest.permission.ACCESS_FINE_LOCATION ||
                    it == Manifest.permission.ACCESS_COARSE_LOCATION
        }
    }

    private fun isStoragePermission(permissions: List<String>): Boolean {
        return permissions.any {
            it == Manifest.permission.READ_EXTERNAL_STORAGE ||
                    it == Manifest.permission.WRITE_EXTERNAL_STORAGE ||
                    (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                            (it == Manifest.permission.READ_MEDIA_IMAGES || it == Manifest.permission.READ_MEDIA_VIDEO)
                            )
        }
    }
}

data class PermissionDialogContent(
    val title: String,
    val message: String,
    val positiveText: String,
    val negativeText: String
)

@Composable
fun ModernPermissionDialog(
    content: PermissionDialogContent,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = content.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = content.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        Text(
                            text = content.negativeText,
                            style = MaterialTheme.typography.labelLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Button(
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    ) {
                        Text(
                            text = content.positiveText,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun PermissionDialogUI(
    manager: PermissionManager,
    rationaleContent: @Composable (List<String>) -> PermissionDialogContent = { PermissionDialogDefaults.rationaleContent(it) },
    settingsContent: @Composable (List<String>) -> PermissionDialogContent = { PermissionDialogDefaults.settingsContent(it) }
) {
    when (val state = manager.dialogState) {
        is PermissionDialogState.Rationale -> {
            ModernPermissionDialog(
                content = rationaleContent(state.permissions),
                onConfirm = manager::onConfirmDialog,
                onDismiss = manager::onDismissDialog
            )
        }
        is PermissionDialogState.Settings -> {
            ModernPermissionDialog(
                content = settingsContent(state.permissions),
                onConfirm = manager::onConfirmDialog,
                onDismiss = manager::onDismissDialog
            )
        }
        PermissionDialogState.Hidden -> {
        }
    }
}