package kg.tutorialapp.weatherproject.storage

import androidx.room.Dao
import androidx.room.Insert
import kg.tutorialapp.weatherproject.models.ForeCast


//для Room кроме Entuty нужен также интерфейс Dao
@Dao
interface ForeCastDao {
    @Insert
    fun insert(forecast: ForeCast)
}