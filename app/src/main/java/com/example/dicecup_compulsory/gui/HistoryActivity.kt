package com.example.dicecup_compulsory.gui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dicecup_compulsory.R
import com.example.dicecup_compulsory.mock.DicerollMock
import com.example.dicecup_compulsory.models.BEDiceRoll
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime
import java.util.ArrayList

class HistoryActivity : AppCompatActivity() {

    private var listOfRolls = ArrayList<BEDiceRoll>()
    private var isCleared: Boolean? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        val extras: Bundle = intent.extras!!

        btnClearHistory.setOnClickListener { _ -> onClickClearHistory() }

        val recycler = findViewById<RecyclerView>(R.id.recyclerView)
        recycler.layoutManager = LinearLayoutManager(this)

        if (extras != null) {
            val rollHistory = extras.getSerializable("rollHistory") as ArrayList<BEDiceRoll>
            listOfRolls = rollHistory
            isCleared = false
        }

        val diceadap = RecycleAdapter(listOfRolls)
        recycler.adapter = diceadap
    }

    /**
     * Send back result code 2, letting MainActivity know to clear the list
     * Setting content view after clearing to let it refresh real time
     */
    private fun onClickClearHistory() {
        val intent = Intent()
        setResult(2, intent)

        // Avoid spamming clear button to set content view
        if (isCleared == false) {
            setContentView(R.layout.activity_history)
            isCleared == true
        }
    }




}
