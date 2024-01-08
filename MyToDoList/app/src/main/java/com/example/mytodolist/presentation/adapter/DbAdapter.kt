package com.example.mytodolist.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodolist.R
import com.example.mytodolist.data.model.TaskItem
import com.example.mytodolist.domain.usecases.DeleteFromDbUseCase
import com.example.mytodolist.presentation.EditActivity
import com.example.mytodolist.presentation.adapter.intent_constats.IntentConstants

class DbAdapter(listMain: ArrayList<TaskItem>, contextM: Context): RecyclerView.Adapter<DbAdapter.DbHolder>() {
    var listArray=listMain
    var context=contextM
    class DbHolder(itemView: View, contextV: Context) : RecyclerView.ViewHolder(itemView){
        val title=itemView.findViewById<TextView>(R.id.textTask)
        val time=itemView.findViewById<TextView>(R.id.textData)
        val context=contextV

        fun setData(item: TaskItem){
            title.text=item.title
            time.text=item.time

            itemView.setOnClickListener {
                val intent = Intent(context, EditActivity::class.java).apply{
                    putExtra(IntentConstants.I_TITLE_KEY, item.title)
                    putExtra(IntentConstants.I_DESC_KEY, item.desc)
                    putExtra(IntentConstants.I_ID_KEY, item.id)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DbHolder {
        val inflater= LayoutInflater.from(parent.context)
        return DbHolder(inflater.inflate(R.layout.task_item, parent, false), context)
    }

    override fun getItemCount(): Int {
        return listArray.size
    }

    override fun onBindViewHolder(holder: DbHolder, position: Int) {
        holder.setData(listArray[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(taskItems: List<TaskItem>){
        listArray.clear()
        listArray.addAll(taskItems)
        notifyDataSetChanged()
    }

    fun removeItem(pos: Int, deleteFromDbUseCase: DeleteFromDbUseCase){
        deleteFromDbUseCase.execute(listArray[pos].id.toString())
        listArray.removeAt(pos)
        notifyItemRangeChanged(0, listArray.size)
        notifyItemRemoved(pos)
    }
}