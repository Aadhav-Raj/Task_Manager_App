package com.example.taskmanager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class CustomAdapter(context: Context,
                    private val resource: Int,
                    private val data: List<DataModel>) :
    ArrayAdapter<DataModel>(context, resource, data) {

    override fun getView(position: Int,
                         convertView: View?,
                         parent: ViewGroup): View {
        var view = convertView
        val viewHolder: ViewHolder

        if (view == null) {
            view = LayoutInflater.from(context).inflate(resource, parent, false)
            viewHolder = ViewHolder()
            viewHolder.txtHead = view.findViewById(R.id.listhead)
            viewHolder.txtDesc = view.findViewById(R.id.listdesc)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        val dataModel = getItem(position)

        viewHolder.txtHead?.text = dataModel?.head
        viewHolder.txtDesc?.text = dataModel?.desc

        return view!!
    }

    private class ViewHolder {
        var txtHead: TextView? = null
        var txtDesc: TextView? = null
    }
}
