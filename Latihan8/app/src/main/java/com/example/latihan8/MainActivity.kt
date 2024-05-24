package com.example.latihan8

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.Application
import androidx.room.Room


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    class MyApp : Application() {
        companion object {
            lateinit var database: AppDatabase
        }

        override fun onCreate() {
            super.onCreate()
            database = Room.databaseBuilder(
                this, AppDatabase::class.java, "my_database")
                .build()

            // Isi data dummy ke dalam database
            Thread {
                val userDao = database.userDao()
                userDao.insertUser(User(
                    username = "John Doe", email = "john@example.com"))
                userDao.insertUser(User(
                    username = "Jane Smith", email = "jane@example.com"))
                userDao.insertUser(User(
                    username = "Mike Johnson", email = "mike@example.com"))
            }.start()
        }
    }

}