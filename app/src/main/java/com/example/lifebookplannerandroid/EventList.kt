package com.example.lifebookplannerandroid

import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import com.example.lifebookplannerandroid.MainActivity


class EventList(private val filesDir: File) {
    var taskList: MutableList<LifeEvent> = mutableListOf()

    companion object {
        val FILE_NAME = "lifeBook.json"
    }

    private fun loadEvents(): List<LifeEvent> {
        // Return a default or empty list of events for now
        return emptyList()
    }

    fun displayEvents(events: List<LifeEvent>) {
        // Implement your logic to display events here
        for (event in events) {
            println(event) // Replace this with your actual display logic
        }
    }


    fun saveNewEvent(event: LifeEvent) {
        try {
            taskList.add(event)

            val json = Gson().toJson(taskList)
            FileWriter(File(filesDir, MainActivity.FILE_NAME)).use { it.write(json) }

            println("Event saved successfully")
        } catch (e: Exception) {
            println("Problem saving event: ${e.message}")
        }
    }


    fun loadCurrentEvents(v: View): List<LifeEvent> {
        val file = File(filesDir, MainActivity.FILE_NAME)
        if (!file.exists()) {
            return emptyList()
        }

        return try {
            val reader = FileReader(file)
            val eventType = object : TypeToken<List<LifeEvent>>() {}.type
            Gson().fromJson(reader, eventType)
        } catch (e: Exception) {
            println("Error loading events: ${e.message}")
            emptyList()
        }
    }


}