package kg.tutorialapp.weatherproject

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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

        makeRxCall()

        db.forecastDao().getAll().observe(this, {
            tv_forecast_list.text = it?.toString()
        })
    }
//задаем результат и показываем в одном Textview
    @SuppressLint("CheckResult")
    private fun makeRxCall() {
        WeatherClient.weatherApi.fetchWeather()
            .subscribeOn(Schedulers.io())
            .map {
                db.forecastDao().deleteAll()
                db.forecastDao().insert(it)
                it
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ }, {
//                в случае ошибки показывать тост
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            })
    }
}