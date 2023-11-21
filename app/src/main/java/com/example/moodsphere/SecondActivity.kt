package com.example.moodsphere

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SecondActivity : AppCompatActivity() {

    private val moodsList: MutableList<MoodItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mood_list)

        val newMood = findViewById<ImageView>(R.id.addAMoodIV)

        val recyclerView: RecyclerView = findViewById(R.id.moodsRv)
        val adaptMood = MoodAdapter(moodsList)
        recyclerView.adapter = adaptMood
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch(Dispatchers.IO) {
            //Retrieve all mood entries
            MoodDatabase.getDatabase(this@SecondActivity).moodDao().getAllEntries()
                .collect { allEntriesFromDB ->
                    //Update the entries list on the main thread
                    withContext(Dispatchers.Main) {
                        moodsList.clear()
                        moodsList.addAll(allEntriesFromDB)
                        adaptMood.notifyDataSetChanged()
                    }
                }
        }

        newMood.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}