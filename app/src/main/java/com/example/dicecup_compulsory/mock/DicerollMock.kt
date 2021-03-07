package com.example.dicecup_compulsory.mock

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.dicecup_compulsory.models.BEDiceRoll
import java.time.LocalDateTime

class DicerollMock {


    @RequiresApi(Build.VERSION_CODES.O)
    private val rolls = generateRolls()

    @RequiresApi(Build.VERSION_CODES.O)
    private fun generateRolls(): ArrayList<BEDiceRoll> {
        val rollList = ArrayList<BEDiceRoll>()

        rollList.add(BEDiceRoll(LocalDateTime.now().toString(), intArrayOf(3, 4, 5)))
        rollList.add(BEDiceRoll(LocalDateTime.now().toString(), intArrayOf(2)))
        rollList.add(BEDiceRoll(LocalDateTime.now().toString(), intArrayOf(5, 6, 3, 2, 1)))
        rollList.add(BEDiceRoll(LocalDateTime.now().toString(), intArrayOf(3, 4, 1, 1)))
        rollList.add(BEDiceRoll(LocalDateTime.now().toString(), intArrayOf(3, 5, 5, 6)))

        return rollList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAllRolls(): ArrayList<BEDiceRoll> = rolls



}