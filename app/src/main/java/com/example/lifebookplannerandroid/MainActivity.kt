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
            createNewEventView.visibility = View.VISIBLE
        }

        saveButton.setOnClickListener {
            // Here you handle the click event of the button
            // You can extract information from your views and save it
            val eventName = findViewById<EditText>(R.id.eventNameEntry).text.toString()
            val date = findViewById<EditText>(R.id.eventDateEntry).text.toString()
            val time = findViewById<EditText>(R.id.eventTimeEntry).text.toString()
            val location = findViewById<EditText>(R.id.eventLocationEntry).text.toString()

            // Create a new LifeEvent instance with the extracted information
            val newEvent = LifeEvent(eventName, date, time, location)

            // Call your function to save the new event
            eventList.saveNewEvent(newEvent)


            findViewById<EditText>(R.id.eventNameEntry).setText("")
            findViewById<EditText>(R.id.eventDateEntry).setText("")
            findViewById<EditText>(R.id.eventTimeEntry).setText("")
            findViewById<EditText>(R.id.eventLocationEntry).setText("")
            
            createNewEventView.visibility = View.GONE
        }

    }

    companion object {
        val FILE_NAME = "lifeBook.json"
    }


}