package com.fitnesstracker

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

// Data class for workouts
data class Workout(
    val type: String,
    val distance: Float,
    val duration: Int,
    val calories: Int,
    val intensity: Int
)

class MainActivity : AppCompatActivity() {

    private val workouts = mutableListOf<Workout>()
    private val gson = Gson()
    private val fileName = "workouts.json"
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        loadData()


        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)


        val typeRadioGroup: RadioGroup = findViewById(R.id.typeRadioGroup)
        val distanceInput: EditText = findViewById(R.id.distanceInput)
        val durationInput: EditText = findViewById(R.id.durationInput)
        val caloriesInput: EditText = findViewById(R.id.caloriesInput)
        val intensitySeekBar: SeekBar = findViewById(R.id.intensitySeekBar)
        val addButton: Button = findViewById(R.id.addButton)

        // Add workout button listener
        addButton.setOnClickListener {
            val selectedTypeId = typeRadioGroup.checkedRadioButtonId
            val type = when (selectedTypeId) {
                R.id.radioWalk -> "Spacer"
                R.id.radioRun -> "Bieg"
                R.id.radioStrength -> "Trening Siłowy"
                else -> ""
            }
            val distance = distanceInput.text.toString().toFloatOrNull() ?: 0f
            val duration = durationInput.text.toString().toIntOrNull() ?: 0
            val calories = caloriesInput.text.toString().toIntOrNull() ?: 0
            val intensity = intensitySeekBar.progress

            if (type.isNotEmpty() && distance > 0 && duration > 0 && calories > 0) {
                val workout = Workout(type, distance, duration, calories, intensity)
                workouts.add(workout)
                saveData()
                Toast.makeText(this, "Trening dodany!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Wypełnij dobrze wszystkie pola!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun showWorkoutDetails(workout: Workout) {
        AlertDialog.Builder(this)
            .setTitle("${workout.type} Szczegóły")
            .setMessage(
                "Dystans: ${workout.distance} km\n" +
                        "Czas trwania: ${workout.duration} min\n" +
                        "Kalorie: ${workout.calories}\n" +
                        "Intensywność: ${workout.intensity}/100"
            )
            .setPositiveButton("OK", null)
            .show()
    }


    private fun saveData() {
        val json = gson.toJson(workouts)
        val file = File(filesDir, fileName)
        file.writeText(json)
    }


    private fun loadData() {
        val file = File(filesDir, fileName)
        if (file.exists()) {
            val json = file.readText()
            val type = object : TypeToken<MutableList<Workout>>() {}.type
            workouts.clear()
            workouts.addAll(gson.fromJson(json, type))
        }
    }
}