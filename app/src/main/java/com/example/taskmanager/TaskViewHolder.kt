package com.example.taskmanager

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class TaskViewHolder(
    private val containerView : View,
    private val onClickListener: OnClickListener
):ViewHolder(containerView) {


    private val TaskHead:TextView by lazy {
        containerView.findViewById(R.id.listhead)

    }

    private val Taskdesc:TextView by lazy {
        containerView.findViewById(R.id.listdesc)

    }


    fun bindData(dataModel: DataModel){
        containerView.setOnClickListener { onClickListener.onClick(dataModel) }
        TaskHead.text=dataModel.head
        Taskdesc.text=dataModel.desc
    }

    interface OnClickListener {
        fun onClick(dataModel: DataModel)
    }
}

