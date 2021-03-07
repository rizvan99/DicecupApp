package com.example.dicecup_compulsory.gui

import android.content.Intent
import android.content.res.Configuration
import android.icu.util.CurrencyAmount
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.dicecup_compulsory.R
import com.example.dicecup_compulsory.models.BEDiceRoll
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MyActivity"
    private val REQUEST_CODE_ANSWER = 12

    private val generateRandom = Random()
    private val listOfRolls = ArrayList<BEDiceRoll>()

    private val diceId = intArrayOf(0, R.drawable.dice1,
            R.drawable.dice2,
            R.drawable.dice3,
            R.drawable.dice4,
            R.drawable.dice5,
            R.drawable.dice6)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnRoll.setOnClickListener { _ -> onClickRoll() }

        val orientation = this.resources.configuration.orientation
        val message = if (orientation == Configuration.ORIENTATION_PORTRAIT) "Portrait" else "Landscape"
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

        if (savedInstanceState != null) {
            Log.e("xyz", "Saved state is not null")
            val history = savedInstanceState.getSerializable("history") as ArrayList<BEDiceRoll>
            history.forEach { item -> listOfRolls.add(item)}
            val amountOfDices = savedInstanceState.getSerializable("amount") as Int

            setupDiceBoard(amountOfDices)

        }

        // Setting up spinner
        val languages = resources.getStringArray(R.array.diceAmount)
        if (spnAmount != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
            spnAmount.adapter = adapter
        }
        // Selected spinner item
        spnAmount.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@MainActivity, "Re-select amount", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View?, position: Int, id: Long) {

                Toast.makeText(this@MainActivity,
                    getString(R.string.selected_item) + " " +
                            "" + languages[position], Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onClickHistory(view: View) {
        val intent = Intent(this, HistoryActivity::class.java)
        intent.putExtra("rollHistory", listOfRolls as Serializable)
        startActivityForResult(intent, REQUEST_CODE_ANSWER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ANSWER) {
            if (resultCode == 2) {
                listOfRolls.clear()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onClickRoll() {
        val amountOfDices = getAmountOfDices()
        var amount: Array<Int>
        val e1 = generateRandom.nextInt(6) + 1
        val e2 = generateRandom.nextInt(6) + 1
        val e3 = generateRandom.nextInt(6) + 1
        val e4 = generateRandom.nextInt(6) + 1
        val e5 = generateRandom.nextInt(6) + 1
        val e6 = generateRandom.nextInt(6) + 1

        amount = arrayOf(e1, e2, e3, e4, e5, e6)

        updateDicesWith(amount)
        setupDiceBoard(amountOfDices)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateDicesWith(array: Array<Int>) {
        imgDice1.setImageResource( diceId[array[0]] )
        imgDice2.setImageResource( diceId[array[1]] )
        imgDice3.setImageResource( diceId[array[2]] )
        imgDice4.setImageResource( diceId[array[3]] )
        imgDice5.setImageResource( diceId[array[4]] )
        imgDice6.setImageResource( diceId[array[5]] )

        when (getAmountOfDices()) {
            1 -> {
                val rolled = listOf(array[0])
                listOfRolls.add(BEDiceRoll(LocalDateTime.now().toString(), rolled.toIntArray()))
                Log.e("xyz", "You rolled 1 dice and got " + array[0])
            }
            2 -> {
                val rolled = listOf(array[0], array[1])
                listOfRolls.add(BEDiceRoll(LocalDateTime.now().toString(), rolled.toIntArray()))
            }
            3 -> {
                val rolled = listOf(array[0], array[1], array[2])
                listOfRolls.add(BEDiceRoll(LocalDateTime.now().toString(), rolled.toIntArray()))
                Log.e("xyz", "You rolled 3 dices and got " + array[0] + ", " + array[1] + " and " + array[2])
            }
            4 -> {
                val rolled = listOf(array[0], array[1], array[2], array[3])
                listOfRolls.add(BEDiceRoll(LocalDateTime.now().toString(), rolled.toIntArray()))
                Log.e("xyz", "You rolled 4 dices and got " + array[0] + ", " + array[1] + ", " + array[2] + " and " + array[3])
            }
            5 -> {
                val rolled = listOf(array[0], array[1], array[2], array[3], array[4])
                listOfRolls.add(BEDiceRoll(LocalDateTime.now().toString(), rolled.toIntArray()))
                Log.e("xyz", "You rolled 5 dices and got " + array[0] + ", " + array[1] + ", " + array[2] + ", " + array[3] + " and " + array[4])
            }
            6 -> {
                val rolled = listOf(array[0], array[1], array[2], array[3], array[4], array[5])
                listOfRolls.add(BEDiceRoll(LocalDateTime.now().toString(), rolled.toIntArray()))
                Log.e("xyz", "You rolled 6 dices and got " + array[0] + ", " + array[1] + ", " + array[2] + ", " + array[3] + ", " + array[4] + " and " + array[5])
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "History saved")
        val output = listOfRolls
        val amount = getAmountOfDices()
        outState.putSerializable("history", output)
        outState.putSerializable("amount", amount)
    }


    private fun getAmountOfDices(): Int {
        val amount = spnAmount.selectedItem.toString();
        return amount.toInt()
    }

    private fun setupDiceBoard(amount: Int) {
        when (amount) {
            1 -> {
                imgDice1.isVisible = true
                imgDice2.isVisible = false
                imgDice3.isVisible = false
                imgDice4.isVisible = false
                imgDice5.isVisible = false
                imgDice6.isVisible = false }
            2 -> {
                imgDice1.isVisible = true
                imgDice2.isVisible = true
                imgDice3.isVisible = false
                imgDice4.isVisible = false
                imgDice5.isVisible = false
                imgDice6.isVisible = false }
            3 -> {
                imgDice1.isVisible = true
                imgDice2.isVisible = true
                imgDice3.isVisible = true
                imgDice4.isVisible = false
                imgDice5.isVisible = false
                imgDice6.isVisible = false }
            4 -> {
                imgDice1.isVisible = true
                imgDice2.isVisible = true
                imgDice3.isVisible = true
                imgDice4.isVisible = true
                imgDice5.isVisible = false
                imgDice6.isVisible = false }
            5 -> {
                imgDice1.isVisible = true
                imgDice2.isVisible = true
                imgDice3.isVisible = true
                imgDice4.isVisible = true
                imgDice5.isVisible = true
                imgDice6.isVisible = false }
            6 -> {
                imgDice1.isVisible = true
                imgDice2.isVisible = true
                imgDice3.isVisible = true
                imgDice4.isVisible = true
                imgDice5.isVisible = true
                imgDice6.isVisible = true }
        }
    }



}