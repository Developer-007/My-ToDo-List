package com.example.mytodolist.presentation

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mytodolist.R
import com.example.mytodolist.data.repository.DbRepositoryImpl
import com.example.mytodolist.databinding.ActivityEditBinding
import com.example.mytodolist.domain.usecases.CloseDbUseCase
import com.example.mytodolist.domain.usecases.DeleteFromDbUseCase
import com.example.mytodolist.domain.usecases.InsertToDbUseCase
import com.example.mytodolist.domain.usecases.OpenDbUseCase
import com.example.mytodolist.domain.usecases.ReadDbDataUseCase
import com.example.mytodolist.domain.usecases.UpdateUseCase
import com.example.mytodolist.presentation.adapter.DbAdapter
import com.example.mytodolist.presentation.adapter.intent_constats.IntentConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.Locale

class EditActivity : AppCompatActivity() {
    lateinit var editBinding: ActivityEditBinding
    private val repository= DbRepositoryImpl(this)
    private val openDbUseCase= OpenDbUseCase(repository)
    private val closeDbUseCase= CloseDbUseCase(repository)
    private val insertToDbUseCase= InsertToDbUseCase(repository)
    private val updateUseCase= UpdateUseCase(repository)
    private val readDbDataUseCase= ReadDbDataUseCase(repository)
    private val deleteFromDbUseCase= DeleteFromDbUseCase(repository)
    var id = 0
    var isEditState = false
    private var job: Job?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editBinding=ActivityEditBinding.inflate(layoutInflater)
        setContentView(editBinding.root)
        readData()
    }

    override fun onResume() {
        super.onResume()
        openDbUseCase.execute()
    }

    override fun onDestroy() {
        super.onDestroy()
        closeDbUseCase.execute()
    }

    fun save(view: View) {
        val myTitle = editBinding.etTitle.text.toString()
        val myDesc = editBinding.etDesc.text.toString()

        if (myTitle != "") {
            CoroutineScope(Dispatchers.IO).launch{
                if (isEditState) {
                    updateUseCase.execute(myTitle, myDesc, getCurrentTime(), id)
                } else {
                    insertToDbUseCase.execute(myTitle, myDesc, getCurrentTime())
                }
                finish()
            }
        }
    }

    fun readData() {
        val i = intent
        if (i != null) {
            if (i.getStringExtra(IntentConstants.I_TITLE_KEY) != null) {
                editBinding.apply {
                    etTitle.setText(i.getStringExtra(IntentConstants.I_TITLE_KEY))
                    isEditState = true
                    etDesc.setText(i.getStringExtra(IntentConstants.I_DESC_KEY))
                    id = i.getIntExtra(IntentConstants.I_ID_KEY, 0)
                }
            }
        }
    }

    private fun getCurrentTime(): String{
        val time = Calendar.getInstance().time
        val formatter= SimpleDateFormat("dd/MM/yy", Locale.getDefault())
        return formatter.format(time)
    }
}