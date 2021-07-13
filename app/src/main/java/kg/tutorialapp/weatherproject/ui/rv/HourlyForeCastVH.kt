package kg.tutorialapp.weatherproject.ui.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.tutorialapp.weatherproject.R
import kg.tutorialapp.weatherproject.format
import kg.tutorialapp.weatherproject.models.Constants
import kg.tutorialapp.weatherproject.models.HourlyForeCast
import kotlinx.android.synthetic.main.item_hourly_forecast.view.*

import kotlin.math.roundToInt

class HourlyForeCastVH(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(item: HourlyForeCast){
        itemView.run {
            tv_time.text = item.date.format("HH:mm")
            item.probability?.let {
                tv_precipitation.text = "${(it * 100).roundToInt()} %"
            }
            tv_temp.text = item.temp?.roundToInt()?.toString()

            Glide.with(context)
                .load("${Constants.iconUri}${item.weather?.get(0)?.icon}${Constants.iconFormat}")
                .into(iv_weather_icon)
        }
    }
    companion object{
        fun create(parent: ViewGroup): HourlyForeCastVH{
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_hourly_forecast, parent, false)

            return HourlyForeCastVH(view)
        }
    }
}