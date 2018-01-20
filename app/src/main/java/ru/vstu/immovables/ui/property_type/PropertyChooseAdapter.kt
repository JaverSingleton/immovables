package ru.vstu.immovables.ui.property_type

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.vstu.immovables.R
import ru.vstu.immovables.getColorCompat

class PropertyChooseAdapter(
        private val items: List<String>,
        private val selectedItem: Int,
        private val onChoose: (Int) -> Unit
) : RecyclerView.Adapter<PropertyChooseAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(items[position], position == selectedItem, { onChoose(items.indexOf(it)) })
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.view_property_item, parent, false)

        return ViewHolder(view)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val context = view.context
        private val chooseTitle: TextView = view.findViewById(R.id.choose_title)

        fun bind(title: String, isSelected: Boolean, callback: (String) -> Unit) {
            if (isSelected) {
                chooseTitle.setTextColor(context.getColorCompat(R.color.blue))
            }
            chooseTitle.text = title

            itemView.setOnClickListener { callback(title) }
        }
    }
}