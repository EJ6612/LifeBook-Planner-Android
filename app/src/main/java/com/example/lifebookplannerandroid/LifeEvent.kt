package com.example.lifebookplannerandroid

import android.widget.EditText

class LifeEvent  (eventName: String, eventDate: String, eventDetails: String, eventLocation: String) {
    var eventName: String = ""
        get() = field
        set(value) { field = value }
    var eventDate: String = ""
        get() = field
        set(value) { field = value }
    var eventDetails: String = ""
        get() = field
        set(value) { field = value }
    var eventLocation: String = ""
        get() = field
        set(value) { field = value }



}

// properties -> info
// name of event, time/date, explanation, location
// save to list