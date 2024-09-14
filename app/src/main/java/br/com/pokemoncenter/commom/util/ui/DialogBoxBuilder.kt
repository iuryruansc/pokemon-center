package br.com.pokemoncenter.commom.util.ui

import android.app.Activity
import androidx.appcompat.app.AlertDialog
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

fun showExitConfirmationDialog(activity: Activity) {
    AlertDialog.Builder(activity)
        .setTitle("Exit App")
        .setMessage("Do you want to exit the app?")
        .setCancelable(false)
        .setPositiveButton("Yes") { _, _ -> activity.finish() }
        .setNegativeButton("No") { _, _ -> }
        .show()
}
