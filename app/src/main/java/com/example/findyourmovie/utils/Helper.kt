package com.example.findyourmovie.utils

import android.R
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log


object Helper {
    private const val TAG = "Helper"

    fun getMetaData(context: Context, name: String?): String? {
        try {
            val ai = context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA
            )
            val bundle = ai.metaData
            return bundle.getString(name)
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "Unable to load meta-data: " + e.message)
        }
        return null
    }
}