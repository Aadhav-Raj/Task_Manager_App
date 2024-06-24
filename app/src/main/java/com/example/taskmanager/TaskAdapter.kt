package com.example.taskmanager

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class TaskAdapter(
    private val layoutInflater: LayoutInflater,
    private val onClickListener: OnClickListener,
    private val rootView: View
    ):RecyclerView.Adapter<TaskViewHolder>()

{

    val swipeToDeleteCallback=SwipeToDeleteCallback()
    private val TaskData= mutableListOf<DataModel>()

    fun setData(TaskData:List<DataModel>){
        this.TaskData.clear()
        this.TaskData.addAll(TaskData)
        notifyDataSetChanged()
    }

    fun addItem( item:DataModel) {
        TaskData.add(item)
        notifyItemInserted(TaskData.size+1)
    }

    fun removeItem(direction: Int,position: Int){
        if (direction==ItemTouchHelper.LEFT) {

            val delete = TaskData.get(position)

            TaskData.removeAt(position)
            notifyItemRemoved(position)
            show(delete, position,"Task Deleted ")
        }
        else{
            val delete = TaskData.get(position)

            TaskData.removeAt(position)
            notifyItemRemoved(position)
            show(delete, position,"Task Completed")
        }
    }

    fun show(delete:DataModel,position: Int,text:String) {
        Snackbar.make(
            rootView,
            text,//"Task deleted ",
            Snackbar.LENGTH_LONG
        )
            .setAction("Undo") { v ->
                TaskData.add(position, delete)
                notifyItemInserted(position)
            }
            .show()
    }
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): TaskViewHolder {

        val view=layoutInflater.inflate(R.layout.list_item,
            parent,
            false)
        return TaskViewHolder(view,
            object : TaskViewHolder.OnClickListener{
            override fun onClick(dataModel: DataModel) {
                onClickListener.onItemClick(dataModel)
            }
        })
    }

    override fun getItemCount(): Int {

        return  TaskData.size
    }


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        holder.bindData(TaskData[position])
    }

    interface OnClickListener {
        fun onItemClick(dataModel: DataModel )
    }

    inner class SwipeToDeleteCallback:
            ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean =false

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        )=  if(viewHolder is TaskViewHolder){
            makeMovementFlags(
                ItemTouchHelper.ACTION_STATE_IDLE,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) or makeMovementFlags(
                ItemTouchHelper.ACTION_STATE_SWIPE,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

            )
        }
        else{
            0
        }
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val removedItem =removeItem(direction,position)


        }
            }

    }




