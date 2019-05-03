package com.plantflashcards.plantflashcards.dto

/**
 * Created by ucint on 7/24/2017.
 */

class Plant(var guid: Int, var genus : String, var species : String, var cultivar : String, var common : String, var height : Int = 0) {
    constructor() : this(0,"","","","") {

    }

    override fun toString(): String {
        return "$genus $species $cultivar $common"
    }

}