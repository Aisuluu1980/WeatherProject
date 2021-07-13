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
import kg.tutorialapp.weatherproject.models.ForeCast
import kg.tutorialapp.weatherproject.storage.ForeCastDatabase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setup()
    }

    private fun setup() {
        btn_start.setOnClickListener {
            makeRxCall()
        }
        btn_show_toast.setOnClickListener {
            Toast.makeText(this, "Hello", Toast.LENGTH_LONG).show()
//       педаем контекст и вставляем в Dao
            ForeCastDatabase.getInstance(applicationContext).forecastDao().insert(ForeCast(lat = 21341.000))
        }
    }
    @SuppressLint("CheckResult")
    private fun makeRxCall() {
        WeatherClient.weatherApi.fetchWeather()
//                на io потоке отправляем запрос на сервер, на глвном потоке(mainThread) нельзя отправлять запрос
            .subscribeOn(Schedulers.io())
//                на главном потоке получим данные и отправим на Textview
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
//                с потока получим данные, то и получим текст
                textView.text = it.current?.temp.toString()
                textView2.text = it.current?.weather!![0].description
            }, {
//                если выйдет ошибка, показать тост
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            })
    }
    //just, create, fromCallable(), fromIterable() -создание observable
    //disposable, compositeDisposable, clear(), dispose()
    //map, flatmap, zip

    private fun doSomeWork() {
//        создание Observable
        val observable = Observable.create<String>  { emitter->
            Log.d(TAG, "${Thread.currentThread().name} starting emitting")
//            имитация тяжелой работы
            Thread.sleep(3000)
//            onNext выдаст сообщение
            emitter.onNext("Hello")
            Thread.sleep(1000)
            emitter.onNext("Bishkek")
            emitter.onComplete()
        }
//        переменная что будет ожидать
        val observer = object: Observer<String> {
            override fun onSubscribe(d: Disposable) {
            }
            override fun onNext(t: String) {
                Log.d(TAG, "${Thread.currentThread().name} onNext{} $t")
            }
            override fun onError(e: Throwable) {
            }
            override fun onComplete() {
            }
        }
        observable
//                специальный поток для тяжелого
            .subscribeOn(Schedulers.computation())
            .map {
//                посмотреть на каком потоке все происходит
                Log.d(TAG, "${Thread.currentThread().name} starting mapping")
                it.toUpperCase()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }
    companion object{
        const val TAG = "Rx"
    }
}


