package com.example.dz13mytrainingsession

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var toolbarMain:Toolbar

    private lateinit var exercisesET: EditText
    private lateinit var startExercisesBTN: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Тулбар
        toolbarMain = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbarMain)
        title = "  Фитнес"
        toolbarMain.subtitle = "Вер1.Главная страница"
        toolbarMain.setLogo(R.drawable.sport)

        //Привязываем кнопки
        exercisesET = findViewById(R.id.exercisesET)
        startExercisesBTN = findViewById(R.id.startExercisesBTN)

        //Обработка кнопки Старт
        startExercisesBTN.setOnClickListener {
            if (exercisesET.text.isEmpty()) return@setOnClickListener

            val exercise = exercisesET.text.toString()

            val intent = Intent(this, Activity_Sport::class.java)
            intent.putExtra("exercise", exercise)
            startActivity(intent)

            exercisesET.text.clear()
        }

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