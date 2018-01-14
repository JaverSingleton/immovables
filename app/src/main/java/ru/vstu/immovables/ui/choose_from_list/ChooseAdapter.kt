package ru.vstu.immovables.ui.choose_from_list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.vstu.immovables.R

/**
 * Created by kkruchinin on 02.12.17.
 */
class ChooseAdapter(private val array: Array<String>, private val onChoose: (Int) -> Unit) : RecyclerView.Adapter<ChooseAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(array[position], { onChoose(array.indexOf(it)) })
    }

    override fun getItemCount(): Int = array.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.view_choose_item, parent, false)

        return ViewHolder(view)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val chooseTitle: TextView = view.findViewById(R.id.choose_title)

        fun bind(title: String, callback: (String) -> Unit) {
            chooseTitle.text = title

            itemView.setOnClickListener { callback(title) }
        }
    }
}