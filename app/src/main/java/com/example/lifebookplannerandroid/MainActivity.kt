package com.example.lifebookplannerandroid

//import EventList
//import LifeEvent
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        //RecyclerView Code
        recyclerView = findViewById(R.id.eventCardsRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val eventCardsData = listOf( //[insert a for loop or something to insert all event cards]
            EventCardsItem("NAME", "10:00", "MARCH 19 2024", "UR MOMS HOUSE")
             )
        //EventCardsItem()
        val adapter = EventCardsAdapter(eventCardsData)
        recyclerView.adapter = adapter
        //End of RecyclerView Code


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