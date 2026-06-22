package com.example.core.utils.permission

interface PermissionChecker {
    fun hasPermission(): Boolean
    fun getRequiredPermission(): List<String>
}