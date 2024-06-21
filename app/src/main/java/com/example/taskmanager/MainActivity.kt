package com.example.taskmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.taskmanager.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var array:ArrayList<String>
lateinit var list:MutableList<DataModel>
lateinit var  arrayAdapter:CustomAdapter
lateinit var  databaseHelper:DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //arrary= ArrayList()
         //list= mutableListOf<DataModel>()


        databaseHelper=DatabaseHelper(applicationContext)
        //val list2:List<DataModel>
        list=databaseHelper.readData()
        /*val dataList=list.map{
            dataModel ->  """
                ${dataModel.head}
                ${dataModel.desc}
                """.trimIndent()
        }

         */


        arrayAdapter= CustomAdapter(this,
            R.layout.list_item,
            list)

        binding.list.adapter=arrayAdapter
        binding.list.setOnItemClickListener{adapterView,view,postion, id ->

            val task=adapterView.getItemAtPosition(postion) as DataModel
            val builder2=AlertDialog.Builder(this)
            val inflater2=layoutInflater
            //val dialog2=inflater2.inflate(R.layout.display,null)
            //builder2.setPositiveButtonIcon(@drawable.)

            builder2.setTitle(task.head)
/*            Toast.makeText(this,
                """The Task : ${task.head} 
                   Desciption: ${task.desc}
            """.trimMargin(),Toast.LENGTH_SHORT).show()*/
        }
        binding.floatingButton.setOnClickListener{view ->
            /*Snackbar.make(view,"task Added",Snackbar.LENGTH_LONG)
                .setAction("Undo",null)
                .show()
             */
            val builder= AlertDialog.Builder(this)
            val inflater=layoutInflater
            builder.setTitle("Add Task")
            val dialog=inflater.inflate(R.layout.task,null)
            val ed1=dialog.findViewById<EditText>(R.id.heading)
            val ed2=dialog.findViewById<EditText>(R.id.desc)
            builder.setView(dialog)

            builder.setPositiveButton("Add"){

                dialog,_ ->
                val ed1_v=ed1.text.toString()
                val ed2_v=ed2.text.toString()
                addItem(ed1_v,ed2_v)
                Toast.makeText(this,"Task Added",Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Cancel"){
                dialog,_ ->
                dialog.dismiss()
                //Toast.makeText(this,"Task Cancelled",Toast.LENGTH_SHORT).show()

            }
            builder.show()




        }

    }
    fun REDO(id:Long,head:String,desc:String)
    {
        list.add(DataModel(id,head,desc))
        arrayAdapter.notifyDataSetChanged()
        //list.clear()
        //list=databaseHelper.readDataLast()
    }

    private fun addItem(head:String,desc:String) {

        //list.add(datamodel)
        val insert=databaseHelper.insertData(head,desc)
        if (insert!=-1L)
        {
            REDO(insert,head,desc)

        }



    }
}