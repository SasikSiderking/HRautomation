package com.example.hrautomation.presentation.view.social.filter

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.hrautomation.presentation.model.social.DatePickerDialogResult
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        val datePickerDialogResult = DatePickerDialogResult(year, month + 1, day)
        val bundle = Bundle()
        bundle.putSerializable(RESULT_KEY, datePickerDialogResult)
        setFragmentResult(REQUEST_KEY, bundle)
        dismiss()
    }

    companion object {
        const val REQUEST_KEY = "PICKED_DATE"

        const val RESULT_KEY = "RESULT_KEY"
    }

}