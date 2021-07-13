package kg.tutorialapp.weatherproject

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.Observable
import io.reactivex.Observer
import kg.tutorialapp.weatherproject.models.CurrentForeCast
import kg.tutorialapp.weatherproject.models.ForeCast
import kg.tutorialapp.weatherproject.models.Weather
import kg.tutorialapp.weatherproject.storage.ForeCastDatabase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

//создаем глобальную переменную, будет создаваться инстанс базы данных
    private val db by lazy {
        ForeCastDatabase.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setup()
    }

    private fun setup() {
//        при нажатии на кнопку insert добавить в базу новые данные
        btn_insert.setOnClickListener {
            db.forecastDao()
                .insert(getForecastFromInput())
//                    указываем на каком потоке отправляем запрос
                .subscribeOn(Schedulers.io())
//                    и получаем ответ с главного потока
                .observeOn(AndroidSchedulers.mainThread())
//                    так как Complitable тогда нужно обязательно указать
//                    subscrube  чтобы программа знала, что нужно обязательно что то вернуть
                .subscribe { }
        }
//для обновления обязательно нужно указывать id обьекта
        btn_update.setOnClickListener {
            db.forecastDao()
                .update(getForecastFromInput())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { }
        }
//удаление тоже только по id
        btn_delete.setOnClickListener {
            db.forecastDao()
                .delete(getForecastFromInput())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { }

        }
//        кнопка чтобы достать все и получить список
        btn_query_get_all.setOnClickListener {
            db.forecastDao()
                .getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                    {
                        var text = ""
//         проходимся по списку и весь текст переводим в строку it.toString только для 4 полей для нащей базы
                        it.forEach {
                            text += it.toString()
                        }
//                        в Textview все элементы добавили в это поле  переведя в строку
                        tv_forecast_list.text = text
                    },
                    {
                    })
        }

// чтобы достать элемент по  id
        btn_query_id.setOnClickListener {
            db.forecastDao()
//                    9L- это id
                .getById(9L)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                    {
                        tv_forecast_list.text = it.toString()
                    },
                    {
                    })
        }

        btn_query.setOnClickListener {
            db.forecastDao()
                .deleteAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                    {
                        tv_forecast_list.text = null
                    },
                    {
                    })
        }
    }
//функция для получения данных из базы и  возвращаем все поля по id
    private fun getForecastFromInput(): ForeCast{
        val id = et_id.text?.toString().takeIf { !it.isNullOrEmpty() }?.toLong()
        val lat = et_lat.text?.toString().takeIf { !it.isNullOrEmpty() }?.toDouble()
        val long = et_long.text?.toString().takeIf { !it.isNullOrEmpty() }?.toDouble()
        val description = et_description?.text.toString()
        val current = CurrentForeCast(weather = listOf(Weather(description = description)))

        return ForeCast(id = id, lat = lat, lon = long, current = current)
    }

    @SuppressLint("CheckResult")
    private fun makeRxCall() {
        WeatherClient.weatherApi.fetchWeather()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ }, {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            })
    }
}