package com.example.dicecup_compulsory.models

import android.os.Parcelable
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

class BEDiceRoll ( var date: String, var result: IntArray) : Serializable {}