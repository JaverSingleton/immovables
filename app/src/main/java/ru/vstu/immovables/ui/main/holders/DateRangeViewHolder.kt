package ru.vstu.immovables.ui.main.holders

import android.app.DatePickerDialog
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.main.views.DateRangeView
import java.util.*

/**
 * Created by kkruchinin on 26.11.17.
 */
class DateRangeViewHolder(view: View) : BaseViewHolder(view), DateRangeView {

    private val title: TextView = view.findViewById(R.id.title)
    private val fromDate: TextView = view.findViewById(R.id.fromDate)
    private val toDate: TextView = view.findViewById(R.id.toDate)

    override fun setTitle(title: String) {
        this.title.text = title
    }

    override fun setDescription(description: String) {

    }

    override fun setFromDate(date: String, listener: () -> Unit) {
        fromDate.text = date
        fromDate.setOnClickListener { listener.invoke() }
    }

    override fun setToDate(date: String, listener: () -> Unit) {
        toDate.text = date
        toDate.setOnClickListener { listener.invoke() }
    }

    override fun openFromDatePicker() {
        showDatePickerDialog()
    }

    override fun openToDatePicker() {
        showDatePickerDialog()
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val dialog = DatePickerDialog(
                itemView.context,
                DatePickerDialog.OnDateSetListener { datePicker, year, month, day -> },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        )

        dialog.setCancelable(true)
        dialog.show()
    }
}