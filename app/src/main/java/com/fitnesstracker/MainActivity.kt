package com.fitnesstracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


// Data class for workouts
data class Workout(
    val type: String,
    val distance: Float,
    val duration: Int,
    val calories: Int,
    val intensity: Int
)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }