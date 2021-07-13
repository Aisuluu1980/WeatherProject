package kg.tutorialapp.weatherproject.storage

import androidx.room.Dao
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import kg.tutorialapp.weatherproject.models.ForeCast


//для Room кроме Entuty нужен также интерфейс Dao
@Dao
interface ForeCastDao {
    @Insert
    fun insert(forecast: ForeCast): Completable
//    fun insert(forecast: List<ForeCast>): Completable  можно передавать список элементов для добавления, удаления и редактирования
//    вставляем(добавляю) в базу новый обьект , но нам не важен результат, поэтому Completable

//    update также ждет ForeCast
    @Update
    fun update(forecast: ForeCast): Completable

    // все что со знаком @ это аннотации. (insert, update, delete)
    @Delete
    fun delete(forecast: ForeCast): Completable

    //может делать все что угодно, getAll достает все, вернет Single список(list) из ForeCast
    @Query("select * from ForeCast")
    fun getAll(): Single<List<ForeCast>>

//достает обьект по его id по всем  полям , выводит данные id, остальное покажет как null
    @Query("select * from ForeCast where id = :id")
    fun getById(id: Long): Single<ForeCast>

//удаляет все , назад ничего не ждем Completable
    @Query("delete from ForeCast")
    fun deleteAll(): Completable
}