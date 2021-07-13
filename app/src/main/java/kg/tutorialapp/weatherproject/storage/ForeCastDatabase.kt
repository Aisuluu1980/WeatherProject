package kg.tutorialapp.weatherproject.storage

import android.content.Context
import androidx.room.*
import kg.tutorialapp.weatherproject.models.ForeCast


//Room сам создает базу за нас, entities-таблица, пока одна таблица,
//version-версия, не хотим чтобы история сохранялась exportSxchema= false
@Database(
    entities = [ForeCast::class],
    version = 0,
    exportSchema = false
)
@TypeConverters(ModelsConverter::class, CollectionsConverter::class)
abstract class ForeCastDatabase: RoomDatabase() {
    abstract fun forecastDao(): ForeCastDao


    companion object{
//        singleton достаточно одну базу
        private const val DB_NAME = "forecastDB"
        private var DB: ForeCastDatabase? = null
// нам нужен контекст чтобы создать базу
        fun getInstance(context: Context): ForeCastDatabase{
//    если null то делаем чтобы не был null
            if (DB == null){
                DB = Room.databaseBuilder(
                    context,
                    ForeCastDatabase::class.java,
                    DB_NAME
                )
//       обязательно если новая версия, то все обнуляем и заново создается
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return DB!!
        }
    }
}