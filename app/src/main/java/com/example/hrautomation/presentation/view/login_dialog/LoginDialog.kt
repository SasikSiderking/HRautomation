package com.example.hrautomation.presentation.view.login_dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.hrautomation.R

class LoginDialog: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.app_name))
            .setPositiveButton(getString(R.string.app_name)) { _, _ -> }
            .create()
    }
    companion object {
        const val TAG = "PurchaseConfirmationDialog"
    }
}