package com.example.hrautomation.presentation.view.social.filter

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.DatePicker
import androidx.core.os.bundleOf
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
        val resultBundle = Bundle()
        resultBundle.putSerializable(RESULT_KEY, datePickerDialogResult)
        setFragmentResult(requireArguments().getString(REQUEST_KEY)!!, resultBundle)
        dismiss()
    }

    override fun onCancel(dialog: DialogInterface) {
        val resultBundle = Bundle()
        setFragmentResult(requireArguments().getString(REQUEST_KEY)!!, resultBundle)
        super.onCancel(dialog)
    }

    companion object {

        const val TAG = "PICK_DATE"

        const val REQUEST_KEY = "PICKED_DATE"

        const val RESULT_KEY = "RESULT_KEY"

        const val FROM_REQUEST = "fromDate"

        const val TO_REQUEST = "toDate"

        fun newInstance(request: String): DatePickerFragment {
            return DatePickerFragment().apply {
                arguments = bundleOf(
                    REQUEST_KEY to request
                )
            }
        }
    }

}