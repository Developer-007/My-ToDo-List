package com.example.mytodolist.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodolist.R
import com.example.mytodolist.data.repository.DbRepositoryImpl
import com.example.mytodolist.databinding.ActivityMainBinding
import com.example.mytodolist.domain.usecases.CloseDbUseCase
import com.example.mytodolist.domain.usecases.DeleteFromDbUseCase
import com.example.mytodolist.domain.usecases.InsertToDbUseCase
import com.example.mytodolist.domain.usecases.OpenDbUseCase
import com.example.mytodolist.domain.usecases.ReadDbDataUseCase
import com.example.mytodolist.domain.usecases.UpdateUseCase
import com.example.mytodolist.presentation.adapter.DbAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding
    private val repository= DbRepositoryImpl(this)
    private val openDbUseCase=OpenDbUseCase(repository)
    private val closeDbUseCase= CloseDbUseCase(repository)
    private val readDbDataUseCase=ReadDbDataUseCase(repository)
    private val deleteFromDbUseCase=DeleteFromDbUseCase(repository)
    private val dbAdapter= DbAdapter(ArrayList(), this@MainActivity)
    private var job: Job?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        mainBinding=ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        init()
        initSearchView()
    }

    override fun onResume() {
        openDbUseCase.execute()
        fillAdapter("")
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        closeDbUseCase.execute()
    }

    private fun fillAdapter(text: String){
        job?.cancel()
        job= CoroutineScope(Dispatchers.IO).launch{
            val list = readDbDataUseCase.execute(text)
            runOnUiThread {
                dbAdapter.updateAdapter(list)
            }
        }
    }

    fun init(){
        mainBinding.rcView.layoutManager= LinearLayoutManager(this)
        val swapHelper=getSwapMg()
        swapHelper.attachToRecyclerView(mainBinding.rcView)
        mainBinding.rcView.adapter=dbAdapter
    }

    private fun getSwapMg(): ItemTouchHelper {
        return ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                dbAdapter.removeItem(viewHolder.adapterPosition, deleteFromDbUseCase)
            }
        })
    }

    fun createNew(view: View) {
        val i= Intent(this@MainActivity, EditActivity::class.java)
        startActivity(i)
    }

    private fun initSearchView(){
        mainBinding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                fillAdapter(text!!)
                return true
            }
        })
    }
}