package com.example.dz13mytrainingsession

import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Activity_Sport : AppCompatActivity() {

    private lateinit var toolbarSport:Toolbar

    val exercises = ExerciseDB.exercise
    private var exerciseIndex = 0
    private var selectedExercise = 0
    private lateinit var currentExercise: Exercise

    private lateinit var timer: CountDownTimer

    private lateinit var nameExerciseTV: TextView
    private lateinit var startTrainingBTN: Button
    private lateinit var descriptionExerciseTV: TextView
    private lateinit var timerTV: TextView
    private lateinit var nextExerciseBTN: Button
    private lateinit var imageExerciseIV: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sport)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Тулбар
        toolbarSport = findViewById(R.id.toolbarSport)
        setSupportActionBar(toolbarSport)
        title = "Фитнес"
        toolbarSport.subtitle = "Тренировка"
        toolbarSport.setLogo(R.drawable.sport)

        //Привязываем кнопки и тексты
        nameExerciseTV = findViewById(R.id.nameExerciseTV)
        startTrainingBTN = findViewById(R.id.startTrainingBTN)
        descriptionExerciseTV = findViewById(R.id.descriptionExerciseTV)
        timerTV = findViewById(R.id.timerTV)
        nextExerciseBTN = findViewById(R.id.nextExerciseBTN)
        imageExerciseIV = findViewById(R.id.imageExerciseIV)

        //получаем интент
        val exercise = intent.getStringExtra("exercise")
        //находит нужное упражнение из базы данны
        for (i in exercises.indices) {
            if (exercises[i].name.uppercase() == exercise!!.uppercase()) {
                selectedExercise = i
            }
        }

        //вывод на экран названия тренировки и ее описание
        nameExerciseTV.text = exercises[selectedExercise].name
        descriptionExerciseTV.text = exercises[selectedExercise].description
        timerTV.text = formatTime((exercises[selectedExercise].durationInSeconds).toInt())

        //Проверка на наличие в БД упражнения
        if (selectedExercise == 0) {
            imageExerciseIV.setImageResource(exercises[selectedExercise].gifImage)
            startTrainingBTN.isEnabled = false
            timerTV.text = ""
        }

        //кнопка старт
        startTrainingBTN.setOnClickListener {
            startWorkout()
        }

        //Кнопка Следующее упражнение
        nextExerciseBTN.setOnClickListener {
            nextExercise()
        }

    }

    private fun startWorkout() {
        exerciseIndex = selectedExercise
        startTrainingBTN.isEnabled = false
        startTrainingBTN.text = "Процесс тренировки"
        startExercise()
    }

    //Следующее упражнение
    private fun nextExercise() {
        timer.cancel()
        finish()
    }

    //Старт упражнения
    private fun startExercise() {
        nextExerciseBTN.isEnabled = false
        currentExercise = exercises[exerciseIndex]
        nameExerciseTV.text = currentExercise.name
        descriptionExerciseTV.text = currentExercise.description
        imageExerciseIV.setImageResource(exercises[exerciseIndex].gifImage)
        timerTV.text = formatTime(currentExercise.durationInSeconds)
        timer = object : CountDownTimer(
            currentExercise.durationInSeconds * 1000L,
            1000
        ) {
            override fun onTick(millisUntilFinished: Long) {
                timerTV.text = formatTime((millisUntilFinished / 1000).toInt())
            }
            override fun onFinish() {
                timerTV.text = "0:00"
                imageExerciseIV.visibility = View.VISIBLE
                nextExerciseBTN.isEnabled = true
                startTrainingBTN.text = "Начать тренировку заново"
                startTrainingBTN.isEnabled = true
                imageExerciseIV.setImageResource(0)
            }
        }.start()
    }

    //Формат времмени
    private fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }

    //Инициализация Меню
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.infoMenuMain -> {
                Toast.makeText(
                    applicationContext, "Автор Ефремов О.В. Создан 1.12.2024",
                    Toast.LENGTH_LONG
                ).show()
            }

            R.id.exitMenuMain -> {
                Toast.makeText(
                    applicationContext, "Работа приложения завершена",
                    Toast.LENGTH_LONG
                ).show()
                finishAffinity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}