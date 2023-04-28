package io.thorib.domain.periodictable.dto

import com.google.gson.annotations.SerializedName
import io.thorib.domain.periodictable.PeriodicTableDomain


/**
 * Periodic table d t o
 *
 * @property elementDTOS
 * @constructor Create empty Periodic table d t o
 */
data class PeriodicTableDTO(
    @SerializedName("elements")
    val elementDTOS : List<ElementDTO>
) : PeriodicTableDomain.DTO {



    /**
     * Element d t o
     *
     * @property name
     * @property symbol
     * @property atomicNumber
     * @constructor Create empty Element d t o
     */
    data class ElementDTO(
        @SerializedName("name")
        val name : String ,
        @SerializedName("symbol")
        val symbol : String ,
        @SerializedName("number")
        val atomicNumber : Int
    )
}


