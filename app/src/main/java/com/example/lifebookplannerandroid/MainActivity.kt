package com.example.lifebookplannerandroid

//import EventList
//import LifeEvent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        val saveButton = findViewById<Button>(R.id.saveEventButton)
        val createEventButton = findViewById<Button>(R.id.createNewEventButton)
        val eventList = EventList(filesDir)
        val createNewEventView = findViewById<LinearLayout>(R.id.newEventPopUpHome)

        createEventButton.setOnClickListener {
            createNewEventView.visibility = View.GONE
        }

        saveButton.setOnClickListener {
            // Here you handle the click event of the button
            // You can extract information from your views and save it
            val eventName = findViewById<EditText>(R.id.eventNameEntry)
            val date = findViewById<EditText>(R.id.eventDateEntry)
            val time = findViewById<EditText>(R.id.eventTimeEntry)
            val location = findViewById<EditText>(R.id.eventLocationEntry)

            // Create a new LifeEvent instance with the extracted information
            val newEvent = LifeEvent(eventName, date, time, location)

            // Call your function to save the new event
            eventList.taskList.add(newEvent)
            createNewEventView.visibility = View.GONE

            eventName.text.clear()
            date.text.clear()
            time.text.clear()
            location.text.clear()
        }

    }

    companion object {
        val FILE_NAME = "lifeBook.json"
    }


}