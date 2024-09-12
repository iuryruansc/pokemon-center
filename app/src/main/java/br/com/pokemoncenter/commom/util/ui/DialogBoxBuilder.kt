package br.com.pokemoncenter.commom.util.ui

import android.app.Activity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun showErrorDialog(activity: Activity, errorMessage: String) {
    MaterialAlertDialogBuilder(activity)
        .setTitle("Error Occurred")
        .setMessage(errorMessage)
        .setCancelable(false)
        .setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
            activity.finish()
        }
        .show()
}
