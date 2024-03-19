package com.example.lifebookplannerandroid

class LifeEvent  (eventName: String, eventDate: String, eventTime: String, eventLocation: String) {
    var eventName: String = eventName
        get() = field
        set(value) { field = value }
    var eventDate: String = eventDate
        get() = field
        set(value) { field = value }
    var eventTime: String = eventTime
        get() = field
        set(value) { field = value }
    var eventLocation: String = eventLocation
        get() = field
        set(value) { field = value }



}

// properties -> info
// name of event, time/date, explanation, location
// save to list