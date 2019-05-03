package com.plantflashcards.plantflashcards

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by ucint on 8/26/2017.
 */

class HighScoreSynchronizer : BroadcastReceiver() {

    var power = false;

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(Intent.ACTION_POWER_CONNECTED)) {
            power = true
            synchronize()
        } else if (intent?.action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
            power = false
            synchronize()
        }


    }

    private fun synchronize() {
        if (power) {
            // upload our high scores to the server.
            var i = 1 + 1
        }
    }

}
