package com.example.findyourmovie.presentation.view

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import kotlin.system.exitProcess

class ExitDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage("Вы уверены, что хотите закрыть приложение?")
            .setCancelable(false)
            .setNegativeButton(android.R.string.no, null)
            .setPositiveButton(android.R.string.yes, DialogInterface.OnClickListener { _, _ ->
                activity?.finishAffinity()
            }).create()
    }
}