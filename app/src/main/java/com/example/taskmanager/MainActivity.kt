package com.example.taskmanager

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.icu.util.UniversalTimeScale.toLong
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText

import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.taskmanager.databinding.ActivityMainBinding
import okhttp3.internal.notifyAll
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private val retrofit by lazy {
        Retrofit.Builder()
            //.baseUrl("https://2107-117-253-86-114.ngrok-free.app")
            .baseUrl("http://192.168.137.1:8000/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }


    private val theTaskApiService by lazy {
        retrofit.create(TheTaskApiService::class.java)
    }

    private lateinit var binding: ActivityMainBinding

    private val taskAdapter by lazy {
        TaskAdapter(layoutInflater,
            object : TaskAdapter.OnClickListener {
                override fun onItemClick(dataModel: DataModel) {
                    showSelectionDialog(dataModel)
                }
            }, binding.recycleView
        )
    }

    /* private val recyclerView:RecyclerView by lazy {
        findViewById(R.id.recycle_view)

    }*/


    //lateinit var array:ArrayList<String>
    private lateinit var list: MutableList<DataModel>
    //private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    //lateinit var  arrayAdapter:CustomAdapter
    lateinit var databaseHelper: DatabaseHelper
    //lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    val swipeToDeleteCallback=SwipeToDeleteCallback()
    //@SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setSupportActionBar(binding.toolbar)

        actionBarDrawerToggle=ActionBarDrawerToggle(this,
            binding.drawerLayout,
            R.string.nav_open,
            R.string.nav_close)
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        //setSupportActionBar(binding.toolbar)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        //arrary= ArrayList()
        //list= mutableListOf<DataModel>()
        list = mutableListOf()

        binding.recycleView.adapter = taskAdapter
        binding.recycleView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.recycleView)
        readData()
        //taskAdapter.setData(list)


        //databaseHelper=DatabaseHelper(applicationContext)
        //val list2:List<DataModel>
        //list=databaseHelper.readData()


        /*val dataList=list.map{
            dataModel ->  """
                ${dataModel.head}
                ${dataModel.desc}
                """.trimIndent()
        }

         */

        /*
        arrayAdapter= CustomAdapter(this,
            R.layout.list_item,
            list)


 */
        /*binding.list.adapter=arrayAdapter
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

         */

        binding.refreshLayout.setOnRefreshListener {
            readData()
        }
        binding.floatingButton.setOnClickListener { view ->
            /*Snackbar.make(view,"task Added",Snackbar.LENGTH_LONG)
                .setAction("Undo",null)
                .show()
             */
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            builder.setTitle("Add Task")
            val dialog = inflater.inflate(R.layout.task, null)
            val ed1 = dialog.findViewById<EditText>(R.id.heading)
            val ed2 = dialog.findViewById<EditText>(R.id.desc)
            builder.setView(dialog)

            builder.setPositiveButton("Add") {

                    dialog, _ ->
                val ed1_v = ed1.text.toString()
                val ed2_v = ed2.text.toString()
                val postData=SendData(ed1_v,ed2_v)
                //if(postData!=0)
                    //taskAdapter.addItem(DataModel(postData.toLong(),ed1_v,ed2_v))
                    //taskAdapter.notifyItemInserted(taskAdapter.itemCount)
                    //taskAdapter.notifyAll()
                    //taskAdapter.notifyDataSetChanged()
                //val insert = databaseHelper.insertData(ed1_v, ed2_v)
                //if (insert != -1L)
                 //   taskAdapter.addItem(DataModel(insert, ed1_v, ed2_v))
                //addItem(ed1_v,ed2_v)
                //Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
                //Toast.makeText(this,"Task Cancelled",Toast.LENGTH_SHORT).show()

            }
            builder.show()


        }

    }



    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_drawer,menu)
        return super.onCreateOptionsMenu(menu)
    }*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        Toast.makeText(this,item.title,Toast.LENGTH_SHORT)
        return if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            true}
    else{
            super.onOptionsItemSelected(item)

        }
    }

    fun REDO(id: Long, head: String, desc: String) {
        list.add(DataModel(id, head, desc))
        //arrayAdapter.notifyDataSetChanged()
        //list.clear()
        //list=databaseHelper.readDataLast()
    }

    /*
    private fun addItem(head:String,desc:String) {

        //list.add(datamodel)
        val insert=databaseHelper.insertData(head,desc)
        println("inser == $insert")
        if (insert!=-1L)
        {
            REDO(insert,head,desc)

        }



     */


    private fun showSelectionDialog(dataModel: DataModel) {
        AlertDialog.Builder(this)
            .setTitle("Task")
            .setMessage(" ${dataModel.head}")
            .setPositiveButton("OK") { _, _ -> }
            .show()
    }

    fun readData(){
        binding.refreshLayout.isRefreshing = true

    val call = theTaskApiService.ListTask(1)
    call.enqueue(
    object : Callback<List<DataModel>> {
        override fun onFailure(call: Call<List<DataModel>>, t: Throwable) {

            Toast.makeText(applicationContext, "process Failed ", Toast.LENGTH_SHORT).show()
            binding.refreshLayout.isRefreshing = false

            Log.e("MainActivity", "onFailure: ", t)
        }

        override fun onResponse(

        call: Call<List<DataModel>>,
            response: Response<List<DataModel>>
        ) {
            binding.refreshLayout.isRefreshing = false

            var s: String = ""
            if (response.isSuccessful) {
                val taskResult = response.body()
                list.clear()
                taskResult?.let {
                    for (task in it) {
                        Log.d(
                            "MainActivity",
                            "Task Head: ${task.head}, Description: ${task.desc}"
                        )
                        list.add(DataModel(task.id, task.head, task.desc))

                    }

                    taskAdapter.setData(list)
                }


                if (taskResult != null) {
                    Log.w("Success", "Jaichuthom Maara")
                }

                // val task=TaskResultData?.firstOrNull()?.head?:"No url"
                Toast.makeText(
                    applicationContext,
                    " ID : ${taskResult?.get(0)?.id}Task : ${taskResult?.get(0)?.head} \nDesc : ${taskResult?.get(0)?.desc}  ",
                    Toast.LENGTH_LONG
                ).show()
                //Log.d("MainActivity", "onResponse: ${s}")
            } else {
                Toast.makeText(applicationContext, "Response not ok ", Toast.LENGTH_LONG).show()
                Log.d("MainActivity", "onResponse: Response not successful")

            }

        }
    })


}

    fun SendData(head: String,desc: String):Int
    {
        var res:Boolean=false
        var id:Int=0
        val call=theTaskApiService.AddTask(head,desc)
        call.enqueue(
            object :Callback<Map<String,Int>>{
                override fun onFailure(call: Call<Map<String,Int>>, t: Throwable) {
                    Toast.makeText(applicationContext,"POST FAILED",Toast.LENGTH_LONG).show()
                    Log.e("MainActivity","Post Faled",t)
                id=0
                //res=false
                }

                override fun onResponse(call: Call<Map<String,Int>>, response: Response<Map<String,Int>>) {
                    if(response.isSuccessful){
                        val postResult=response.body()

                        postResult.let {
                             id=it?.get("id")?:0

                            //it["id"]
                        }
                        taskAdapter.addItem(DataModel(id.toLong(),head,desc))

                       // Toast.makeText(applicationContext,"POST Sucessfull",Toast.LENGTH_LONG).show()
                        //res= true
                    }
                   else{
                        Toast.makeText(applicationContext," RESPONSE FAILED",Toast.LENGTH_LONG).show()
                        //res=false
                   }
                }
            }

        )
        return id
    }

    fun Delete(task:DataModel)
    {
        val id=task.id
        val call=theTaskApiService.DeleteTask(id.toInt())
        call.enqueue(
            object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(applicationContext,"Delete Failed",Toast.LENGTH_SHORT).show()

                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful){
                        if (response.code()==200) {
                            Toast.makeText(
                                applicationContext,
                                "ID : ${id} Deleted ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        )
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
            val removedItem =taskAdapter.removeItem(direction,position)
            if(direction==ItemTouchHelper.LEFT){
                if (removedItem != null) {
                    taskAdapter.show(removedItem,position,"Task deleted")
                    Delete(removedItem)
                }
            }
            else
            {
                if (removedItem != null) {
                    taskAdapter.show(removedItem,position,"Task Completed")
                    //Delete(removedItem.id)
                }
            }



        }
    }


}