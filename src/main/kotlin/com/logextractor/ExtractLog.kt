package com.logextractor

import java.io.*
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeParseException
import java.util.*


class ExtractLog(fromTime: String, endTime: String) {

    private val begin: Date = Date.from(Instant.parse(fromTime))
    private val end: Date = Date.from(Instant.parse(endTime))

    fun processLogFile(fileLocation: String) {

        try {
            val file = Paths.get(fileLocation).toFile()
            val logEntryBuffer: StringBuilder = java.lang.StringBuilder()
            val reader = BufferedReader(FileReader(file))
            var line: String?
            var counter = 0
            while (reader.readLine().also { line = it } != null) {
                /** Counter to check if the log file is empty*/
                counter++
                line = line!!.trim { it <= ' ' }
                val splitTime = line!!.split(",")[0]
                if (checkLogTime(splitTime)) {
                    logEntryBuffer.append(line + "\n")
                }
            }
            when {
                logEntryBuffer.isNotEmpty() -> {
                    println(logEntryBuffer)
                }
                counter == 0 -> {
                    println("Empty log file. Please provide a valid log file.")
                }
                else -> {
                        println("No entry in the log file matched the provided time range.")
                }
            }

            reader.close()
        }
        catch (ex : FileNotFoundException){
                println("Location of the file not found. Please provide valid file.")
        }
    }

    /** This method checks if the specified line with date falls in the provided time range*/
    private fun checkLogTime(logTime: String): Boolean {
        val logDate: Date = Date.from(Instant.parse(logTime))
        return logDate in begin..end
    }

    /**This method is just for creating a log file for testing*/
    private fun createLogFiles() {
        val dt = "1980-01-31T20:12:38.1234Z" // Start date
        val c = Calendar.getInstance()
        c.time = Date.from(SimpleDateFormat("yyyy-MM-dd").parse(dt).toInstant())

        val path = File("/home/abhimanyu/outputs/output.txt")
        val fileWriter = FileWriter(path)
        fileWriter.write("${c.time.toInstant()}, Some Field, Other Field, And so on, Till new line,...\n")
        for (i in 1..100000) {
            fileWriter.append("${c.time.toInstant()}, Some Field, Other Field, And so on, Till new line,...\n")
            c.add(Calendar.DATE, 1)
        }
        fileWriter.close()
    }
}

fun main() {
    try {
        val startTime = System.currentTimeMillis()
        val fileLocation = System.getProperty("i")
        val fromTime = System.getProperty("f")
        val endTime = System.getProperty("t")

        /** Input validations*/
        if (fileLocation == null || fromTime == null || endTime == null){
            println("Provide all the necessary arguments to the jar.")
            return
        }
        ExtractLog(fromTime, endTime).processLogFile(fileLocation)
        print(System.currentTimeMillis()- startTime)
    }
    catch (ex: DateTimeParseException){
            println("One of the date arguments provided is not in valid ISO 8601 format.")
    }

}