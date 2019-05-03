package com.plantflashcards.plantflashcards.service

import com.plantflashcards.plantflashcards.dao.NetworkingDAO
import com.plantflashcards.plantflashcards.dto.Plant
import org.json.JSONObject

/**
 * Business functions required to process plants.
 * Created by ucint on 8/26/2017.
 */

class PlantService {

    fun parsePlantsFromJSONData(difficulty: String?) : List<Plant>? {
        // declare return type
        var allPlants : ArrayList<Plant> = ArrayList<Plant>()

        // open a network connection to our JSON data feed: http://plantplaces.com/perl/mobile/flashcard.pl
        var networkDAO = NetworkingDAO()
        var plantJSONData = networkDAO.request("http://plantplaces.com/perl/mobile/flashcard.pl")

        // initial parse.
        var root = JSONObject(plantJSONData)
        var plantJSONArray = root.getJSONArray("values")

        // iterate over the JSON array.
        var i:Int = 0

        while (i < plantJSONArray.length()) {
            // create a new plant object
            var plant = Plant()

            // parse the raw data into a set of Plant objects.
            var plantJSON = plantJSONArray.getJSONObject(i)

            // assigning the values from JSON to our Plant object.
            plant.guid = plantJSON.getInt("plant")
            plant.genus = plantJSON.getString("genus")
            plant.species = plantJSON.getString("species")
            plant.cultivar = plantJSON.getString("cultivar")
            plant.common = plantJSON.getString("common")

            // add the plant objects to a collection.
            allPlants.add(plant)
            i++
        }

        // return the collection.
        return allPlants
    }

}