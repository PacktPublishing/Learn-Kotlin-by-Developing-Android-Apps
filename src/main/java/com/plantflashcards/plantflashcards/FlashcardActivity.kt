package com.plantflashcards.plantflashcards

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.plantflashcards.plantflashcards.dto.Photo
import com.plantflashcards.plantflashcards.dto.Plant
import com.plantflashcards.plantflashcards.service.PlantService

class FlashcardActivity : AppCompatActivity() {

    val CAMERA_ACTIVITY_REQUEST = 10
    var imageView: ImageView? = null
    var button1: Button? = null
    var button2: Button? = null
    var button3: Button? = null
    var button4: Button? = null
    var correctAnswer = 0
    var status : TextView? = null
    var allPlants : List<Plant> = ArrayList<Plant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcard)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            var getPlantsTask = GetPlantsTask()
            getPlantsTask.execute("")

            button1?.setBackgroundColor(Color.LTGRAY)
            button2?.setBackgroundColor(Color.LTGRAY)
            button3?.setBackgroundColor(Color.LTGRAY)
            button4?.setBackgroundColor(Color.LTGRAY)
        }

        imageView = findViewById(R.id.imageView) as ImageView
        button1 = findViewById(R.id.button1) as Button
        button2 = findViewById(R.id.button2) as Button
        button3 = findViewById(R.id.button3) as Button
        button4 = findViewById(R.id.button4) as Button
        status = findViewById(R.id.txtStatus) as TextView
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_flashcard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun onButton1Clicked(v: View) {
        evaluate(0)

    }


    fun onButton2Clicked(v: View) {
        evaluate(1)
    }

    fun onButton3Clicked(v: View) {
        evaluate(2)
    }

    fun onButton4Clicked(v: View) {
        evaluate(3)
    }

    private fun evaluate(guess: Int) {
        when(correctAnswer) {
            0 -> button1?.setBackgroundColor(Color.GREEN)
            1 -> button2?.setBackgroundColor(Color.GREEN)
            2 -> button3?.setBackgroundColor(Color.GREEN)
            3 -> button4?.setBackgroundColor(Color.GREEN)
        }
        if (guess == correctAnswer) {
            status?.setText("Correct!")
        } else {
            status?.setText("Incorrect. The correct plant is: " + allPlants.get(correctAnswer).toString())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_ACTIVITY_REQUEST) {
                // I'm hearing back from the camera.

                var image = data?.getExtras()?.get("data") as Bitmap
                var i = 1 + 1
                imageView?.setImageBitmap(image);
            }
        }

    }

    inner class GetPlantsTask : AsyncTask<String, Int, List<Plant>?>() {

        /**
         * Has access to the user interface thread, and the components of the
         * encolsing activity.
         * In this method, we can update the screen based on the data retrieved in
         * the background thread.
         */
        override fun onPostExecute(result: List<Plant>?) {
            super.onPostExecute(result)
            var numberResults = result?.size ?: 0
            if (numberResults > 3) {
                // assign one of the selected plants to each of the buttons.
                button1?.text = (result?.get(0).toString())
                button2?.text = (result?.get(1).toString())
                button3?.text = (result?.get(2).toString())
                button4?.text = result?.get(3).toString()

                correctAnswer = (Math.random() * 4).toInt()

                // TODO now that we have chosen which plant is going to be the correct plant,
                // we need to fetch the image and show it.

                allPlants = result!!;

            }

        }

        /**
         * this method runs on a separate thread, so it can perform networking operations
         * without impacting our User Interface experience.
         * @param p0: an idication of the plant types for which we want flashcards.
         * @return A collection of populated Plant objects, where those plants were populated from our JSON data stream.
         */
        override fun doInBackground(vararg p0: String?): List<Plant>? {
            // get the difficulty from the incoming parameter as the first argument of the array.
            var difficulty = p0[0]

            // create an object of PlantService
            var plantService = PlantService()

            // invoke another function.
            return plantService.parsePlantsFromJSONData(difficulty)

        }

    }



}
