package com.example.lifebookplannerandroid

//import EventList
//import LifeEvent
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val eventList = EventList(filesDir)
        eventList.loadCurrentEvents()

        //RecyclerView Code
        recyclerView = findViewById(R.id.eventCardsRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val eventCardItems = eventList.taskList.map {event ->
            EventCardsItem(event.eventName, event.eventTime, event.eventDate, event.eventLocation)
        }

        //EventCardsItem()
        val adapter = EventCardsAdapter(eventCardItems)
        recyclerView.adapter = adapter
        //End of RecyclerView Code

        val saveButton = findViewById<Button>(R.id.saveEventButton)
        val createEventButton = findViewById<Button>(R.id.createNewEventButton)
        val createNewEventView = findViewById<LinearLayout>(R.id.newEventPopUpHome)
        val backButtonTop = findViewById<Button>(R.id.eventPageBackTop)
        val backButtonBottom = findViewById<Button>(R.id.eventPageBackBottom)

        val eventNameField = findViewById<EditText>(R.id.eventNameEntry)
        val eventDateField = findViewById<EditText>(R.id.eventDateEntry)
        val eventTimeField = findViewById<EditText>(R.id.eventTimeEntry)
        val eventLocationField = findViewById<EditText>(R.id.eventLocationEntry)

        createEventButton.setOnClickListener {
            createNewEventView.visibility = View.VISIBLE
        }

        //Instead of the user typing in the time, bring in the android TimePicker
        eventTimeField.setOnClickListener {
            val newCalendar = Calendar.getInstance()

            val hour = newCalendar.get(Calendar.HOUR_OF_DAY)
            val minute = newCalendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                this,
                { view, hourOfDay, minute ->
                    eventTimeField.setText("$hourOfDay:$minute")
                },
                hour,
                minute,
                false
            )

            timePickerDialog.show()
        }

        //Instead of the user typing in the date, bring in the android DatePicker
        eventDateField.setOnClickListener {
            val newCalendar = Calendar.getInstance()

            val day = newCalendar.get(Calendar.DATE)
            val month = newCalendar.get(Calendar.MONTH)
            val year = newCalendar.get(Calendar.YEAR)

            val datePickerDialog = DatePickerDialog(
                this,
                { view, selectedYear, selectedMonth, selectedDay ->
                    eventDateField.setText("${selectedMonth + 1}/$selectedDay/$selectedYear")
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }

        saveButton.setOnClickListener {
            // Here you handle the click event of the button
            // You can extract information from your views and save it
            val eventName = eventNameField.text.toString()
            val eventDate = eventDateField.text.toString()
            val eventTime = eventTimeField.text.toString()
            val eventLocation = eventLocationField.text.toString()

            // Create a new LifeEvent instance with the extracted information
            val newEvent = LifeEvent(eventName, eventDate, eventTime, eventLocation)

            // Call function to save the new event
            eventList.saveNewEvent(newEvent)

            findViewById<EditText>(R.id.eventNameEntry).setText("")
            findViewById<EditText>(R.id.eventDateEntry).setText("")
            findViewById<EditText>(R.id.eventTimeEntry).setText("")
            findViewById<EditText>(R.id.eventLocationEntry).setText("")

            //This is the new stuff that makes it load on the save button: Alex
//            var newEvents: List<LifeEvent> = eventList.loadCurrentEvents()
            eventList.loadCurrentEvents()
            val newEventCardItems = eventList.taskList.map {event ->
                EventCardsItem(event.eventName, event.eventTime, event.eventDate, event.eventLocation)
            }
            val newAdapter = EventCardsAdapter(newEventCardItems)
            recyclerView.adapter = newAdapter
            // New stuff ends here

            createNewEventView.visibility = View.GONE
        }

        //when the user taps the "empty space" above the new event card
        backButtonTop.setOnClickListener {

            findViewById<EditText>(R.id.eventNameEntry).setText("")
            findViewById<EditText>(R.id.eventDateEntry).setText("")
            findViewById<EditText>(R.id.eventTimeEntry).setText("")
            findViewById<EditText>(R.id.eventLocationEntry).setText("")

            createNewEventView.visibility = View.GONE

        }

        //when the user taps the "empty space" below the new event card
        backButtonBottom.setOnClickListener {

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

//More RecyclerView Code
data class EventCardsItem(val nameId: String, val timeId: String, val dateId: String, val localeId: String)

class EventCardsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val eventName: TextView = itemView.findViewById<TextView>(R.id.eventCardName)
    val eventTime: TextView = itemView.findViewById(R.id.eventCardTime)
    val eventDate: TextView = itemView.findViewById(R.id.eventCardDate)
    val eventLocale: TextView = itemView.findViewById(R.id.eventCardLocation)
}

class EventCardsAdapter(private val data: List<EventCardsItem>) : RecyclerView.Adapter<EventCardsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventCardsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_card, parent, false)
        return EventCardsViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventCardsViewHolder, position: Int) {
        val item = data[position]
        holder.eventName.text = item.nameId
        holder.eventDate.text = item.dateId
        holder.eventTime.text = item.timeId
        holder.eventLocale.text = item.localeId
    }

    override fun getItemCount() = data.size

}