package kg.tutorialapp.weatherproject.storage

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kg.tutorialapp.weatherproject.models.CurrentForeCast
import kg.tutorialapp.weatherproject.models.ForeCast

class ModelsConverter {
//    перевод данных в JSON
    fun fromCurrentForeCastToJson(forecast: ForeCast):String =
        Gson().toJson(forecast)

    //перевод данных из JSON
    fun fromJsonToCurrentForeCast(json: String): ForeCast =
        Gson().fromJson(json, object: TypeToken<CurrentForeCast>() {}.type)
}