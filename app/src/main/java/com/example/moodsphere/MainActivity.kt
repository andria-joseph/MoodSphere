package com.example.moodsphere

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val calendar = Calendar.getInstance()
    private val formatter = SimpleDateFormat("MMM. dd, yyyy", Locale.US)
    private var select = 3
    private val moodsList: MutableList<MoodItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var seeker = findViewById<SeekBar>(R.id.seekerBar)
        var emojis = findViewById<ImageView>(R.id.emojiIV)
        var date = findViewById<TextView>(R.id.enterDateTV)
        val setMood = findViewById<Button>(R.id.button3)
        val details = findViewById<EditText>(R.id.addStuffEV)
        val arrow = findViewById<ImageView>(R.id.nextarrowIV)

        arrow.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        val recyclerView : RecyclerView = findViewById(R.id.someMoodsRv)
        val adaptMood = MoodAdapter(moodsList)
        recyclerView.adapter = adaptMood
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch(Dispatchers.IO) {
            //Retrieve all mood entries
            MoodDatabase.getDatabase(this@MainActivity).moodDao().getAllEntries()
                .collect { allEntriesFromDB ->
                    //Update the entries list on the main thread
                    withContext(Dispatchers.Main) {
                        moodsList.clear()
                        moodsList.addAll(allEntriesFromDB)
                        adaptMood.notifyDataSetChanged()
                    }
                }
        }


        setMood.setOnClickListener {
            val moodItem = MoodItem(select = select, date =date.text.toString(), details = details.text.toString())

            //Launch a coroutine to insert the new mood entry and then retrieve all entries
            lifecycleScope.launch(Dispatchers.IO){
                //Insert new mood entry
                MoodDatabase.getDatabase(this@MainActivity).moodDao().insert(moodItem)

                //Retrieve all mood entries
                MoodDatabase.getDatabase(this@MainActivity).moodDao().getAllEntries().collect{
                    allEntries ->
                    //Update the entries list on the main thread
                    withContext(Dispatchers.Main){
                        moodsList.clear()
                        moodsList.addAll(allEntries)
                        adaptMood.notifyDataSetChanged()
                    }
                    adaptMood.notifyDataSetChanged()
                    details.text.clear()
                }
            }
        }


        date.setOnClickListener {
            DatePickerDialog(
                this@MainActivity,
               DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->  
                   //Update the TextView with the selected date
                   calendar.set(year, month, dayOfMonth)
                   date.text = formatter.format(calendar.time)
               },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


        seeker.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener
        {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean)
            {
                sadHappy(progress, seeker, emojis)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?)
            {
                return
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                return
            }
        })

    }


    private fun sadHappy(progress:Int, seeker: SeekBar, emojis:ImageView) {

        if (progress == 0) {
            emojis.setImageResource(R.drawable.depressed_emoji)
            seeker.performHapticFeedback(4)
            select = 0
        }

        else if (progress == 1) {
            emojis.setImageResource(R.drawable.sad_emoji)
            seeker.performHapticFeedback(4)
            select = 1
        }

        else if(progress == 2){
            emojis.setImageResource(R.drawable.angry_emoji)
            seeker.performHapticFeedback(4)
            select = 2
        }

        else if(progress == 3){
            emojis.setImageResource(R.drawable.tired_emoji)
            seeker.performHapticFeedback(4)
            select = 3
        }

        else if(progress == 4){
            emojis.setImageResource(R.drawable.neutral_emoji)
            seeker.performHapticFeedback(4)
            select = 4
        }

        else if(progress == 5){
            emojis.setImageResource(R.drawable.happy_emoji)
            seeker.performHapticFeedback(4)
            select = 5
        }

        else if(progress == 6){
            emojis.setImageResource(R.drawable.ecstatic_emoji)
            seeker.performHapticFeedback(4)
            select = 6
        }
    }


}