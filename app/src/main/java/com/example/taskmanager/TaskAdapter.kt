package com.example.taskmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class TaskAdapter(
    private val layoutInflater: LayoutInflater,
    //private val actionListener:ActionListerner,
    private val onClickListener: OnClickListener,
    private val rootView: View,
):RecyclerView.Adapter<TaskViewHolder>()

{

    //val swipeToDeleteCallback=SwipeToDeleteCallback()
    private val TaskData= mutableListOf<DataModel>()

    fun setData(TaskDatas:List<DataModel>){
        /*this.TaskData.addAll(TaskData)
        notifyDataSetChanged()*/
        TaskData.clear()
        TaskData.addAll(TaskDatas)
        notifyDataSetChanged()
    }

    fun addItem( item:DataModel) {
        TaskData.add(item)
        notifyItemInserted(TaskData.size+1)
    }

    fun removeItem(direction: Int,position: Int):DataModel?{
        val d=TaskData.removeAt(position)
        //show(d.id)
        notifyItemRemoved(position)
        return d




        /*if (direction==ItemTouchHelper.LEFT) {


            val d=TaskData.removeAt(position)
            //show(d.id)
            notifyItemRemoved(position)
            //show(delete, position,"Task Deleted ")

        }
        else{
            val delete = TaskData.get(position)

            TaskData.removeAt(position)
            notifyItemRemoved(position)
            //show(delete, position,"Task Completed")
        }*/
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
    interface OnDeleteListener{
        fun onDelete(id:Int)
    }



    }




