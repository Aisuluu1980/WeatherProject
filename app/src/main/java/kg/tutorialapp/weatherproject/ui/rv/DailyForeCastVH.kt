package kg.tutorialapp.weatherproject.ui.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.tutorialapp.weatherproject.R
import kg.tutorialapp.weatherproject.format
import kg.tutorialapp.weatherproject.models.Constants
import kg.tutorialapp.weatherproject.models.DailyForeCast
import kotlinx.android.synthetic.main.item_daily_forecast.view.*
import kotlin.math.roundToInt


class DailyForeCastVH (itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(item: DailyForeCast){
        itemView.run {
            tv_weekday.text = item.date.format("dd/MM")
            item.probability?.let {
                tv_precipitation.text = "${(it * 100).roundToInt()} %"
            }
            tv_temp_max.text = item.temp?.max?.roundToInt()?.toString()
            tv_temp_min.text = item.temp?.min?.roundToInt()?.toString()

            Glide.with(context)
                .load("${Constants.iconUri}${item.weather?.get(0)?.icon}${Constants.iconFormat}")
                .into(iv_weather_icon)
        }
    }
    companion object{
        fun create(parent: ViewGroup): DailyForeCastVH{
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_daily_forecast, parent, false)

            return DailyForeCastVH(view)
        }
    }
}