package com.example.lifebookplannerandroid

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileReader
import java.io.FileWriter


class EventList(private val filesDir: File) {
    var taskList: MutableList<LifeEvent> = mutableListOf()

    companion object {
        val FILE_NAME = "lifeBook.json"
    }

    //adds taskList to a Json file
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


    // reads Json file into taskList
    fun loadCurrentEvents() {
        val file = File(filesDir, MainActivity.FILE_NAME)
        if (!file.exists()) {
            taskList = emptyList<LifeEvent>().toMutableList()
        }

        return try {
            val reader = FileReader(file)
            val eventType = object : TypeToken<List<LifeEvent>>() {}.type
            taskList = Gson().fromJson(reader, eventType)
        } catch (e: Exception) {
            println("Error loading events: ${e.message}")
            taskList = emptyList<LifeEvent>().toMutableList()
        }
    }


}