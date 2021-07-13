package kg.tutorialapp.weatherproject.storage

import androidx.room.*
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.Database
import kg.tutorialapp.weatherproject.models.ForeCast


//Room сам создает базу за нас, entities-таблица, пока одна таблица,
//version-версия, не хотим чтобы история сохранялась exportSxchema= false
@Database(
    entities = [ForeCast::class],
//    верия базы должна быть как минимум 1
    version = 2,
    exportSchema = false
)
@TypeConverters(ModelsConverter::class, CollectionsConverter::class)
abstract class ForeCastDatabase: RoomDatabase() {
    abstract fun forecastDao(): ForeCastDao

    companion object{
        const val DB_NAME = "forecastDb"
    }
}