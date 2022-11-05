package com.example.filestoragedemo

import android.content.Context
import android.util.Log
import java.io.File

private const val LOG_TAG = "UserTextInputModel"

class UserTextInputModel(_context: Context)
{
    private var context = _context
    private val textFileName = "userInput.txt"

    private fun makeFile(): File {
        return File(this.context.filesDir, this.textFileName)
    }

    fun saveText(s: String) {
        val file = this.makeFile()
        Log.v(LOG_TAG, "Will save to: $file")

        // Make sure the file is gone
        if ( file.exists() ) {
            file.delete()
        }

        // Write the file
        file.writeText(s)
    }

    fun loadText(): String {
        val file = this.makeFile()

        var s = ""

        if ( file.exists() ) {
            s = file.readText()
        }

        return s
    }
}