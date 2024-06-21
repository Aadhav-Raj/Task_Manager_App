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
         list= mutableListOf<DataModel>()

        databaseHelper=DatabaseHelper(applicationContext)

        /*val dataList=list.map{
            dataModel ->  """
                ${dataModel.head}
                ${dataModel.desc}
                """.trimIndent()
        }

         */


        arrayAdapter= CustomAdapter(this, R.layout.list_item, list)
        binding.list.adapter=arrayAdapter
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
                addItem(DataModel(ed1_v,ed2_v))
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

    private fun addItem(datamodel: DataModel) {
        val s:String
        s="""
            ${datamodel.head}
            ${datamodel.desc}
        """.trimIndent()



        //list.add(datamodel)

        arrayAdapter.notifyDataSetChanged()


    }
}