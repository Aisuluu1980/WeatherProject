package kg.tutorialapp.weatherproject.storage

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kg.tutorialapp.weatherproject.models.DailyForeCast
import kg.tutorialapp.weatherproject.models.HourlyForeCast

class CollectionsConverter {
    @TypeConverter
//    из списка перевод в String получает список и назад ждет String
    fun fromHourlyForeCastListToJson(list: List<HourlyForeCast>?): String? =
        Gson().toJson(list)

    @TypeConverter
//    чтобы доставать достаем String из JSON и передаем список list
    fun fromJsonToHourlyForeCastList(json: String?): List<HourlyForeCast>? =
        Gson().fromJson(json, object : TypeToken<List<HourlyForeCast>>() {}.type)

    @TypeConverter
    fun fromDailyForeCastListToJson(list: List<DailyForeCast>?): String? =
        Gson().toJson(list)

    @TypeConverter
    fun fromJsonToDailyForeCastList(json: String?): List<DailyForeCast>? =
        Gson().fromJson(json, object : TypeToken<List<DailyForeCast>>() {}.type)
}