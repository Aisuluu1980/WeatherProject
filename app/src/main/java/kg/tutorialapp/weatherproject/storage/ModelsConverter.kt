package kg.tutorialapp.weatherproject.storage

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kg.tutorialapp.weatherproject.models.CurrentForeCast
import kg.tutorialapp.weatherproject.models.ForeCast

class ModelsConverter {

//    TypeConverter нужен чтобы Room понял чтобы он пользовался этими функциями,
//    и все нужно сделать null(?), чтобы если пустые поля в базу попадали тоже
    @TypeConverter
//    перевод данных в JSON
    fun fromCurrentForeCastToJson(forecast: CurrentForeCast?):String? =
        Gson().toJson(forecast)
    @TypeConverter
    //перевод данных из JSON
    fun fromJsonToCurrentForeCast(json: String?): CurrentForeCast? =
        Gson().fromJson(json, object: TypeToken<CurrentForeCast>() {}.type)
}