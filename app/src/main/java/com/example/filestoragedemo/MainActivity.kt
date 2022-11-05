package com.example.filestoragedemo

import android.Manifest
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import java.io.File

private const val LOG_TAG = "MyMainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var textInput: TextView
    private lateinit var saveButton: Button
    private lateinit var theImage: ImageView

    private val textInputModel: UserTextInputModel by lazy {
        UserTextInputModel(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            0
        )

        this.connectViews()
        this.setupCallbacks()

        this.loadTheText()

        this.loadAnImage()
    }

    private fun connectViews() {
        this.textInput = this.findViewById(R.id.text_input)
        this.saveButton = this.findViewById(R.id.save_button)
        this.theImage = this.findViewById(R.id.the_image)
    }

    private fun setupCallbacks() {
        this.saveButton.setOnClickListener {
            Log.v(LOG_TAG, "Save button was clicked")

            val t = this.textInput.text
            if (t != null) {
                this.saveTheText(t.toString())
            }
        }
    }

    private fun loadTheText() {
        this.textInput.text = this.textInputModel.loadText()
    }

    private fun saveTheText(s: String) {
        this.textInputModel.saveText(s)
    }

    private fun loadAnImage() {
        Log.v(LOG_TAG, "loadAnImage() - Begin")

        val storageRoot = Environment.getExternalStorageDirectory()
        val downloadsDir = File(storageRoot, "Download")

        // using !! will make it ignore that folder can be null
        for (f in downloadsDir.listFiles()!!) {
            Log.v(LOG_TAG, "Found a file of size ({${f.length()}): $f")

            if ( f.isFile ) {

                //
                val bytes = f.readBytes()
                val theBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

                this.theImage.setImageBitmap(theBitmap)
            }
        }
    }
}