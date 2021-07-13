package kg.tutorialapp.weatherproject


import java.text.SimpleDateFormat
import java.util.*

fun Long?.format(pattern: String? = "dd/MM/yyyy"): String{
//    если не null то возвращаем все это
    this?.let {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
//для форматирования даты, так как приходит в миллисекундах умножаем на 1000
        return sdf.format(Date(this * 1000))
    }
//    если null, то возвращает  пустую строку
    return ""
}