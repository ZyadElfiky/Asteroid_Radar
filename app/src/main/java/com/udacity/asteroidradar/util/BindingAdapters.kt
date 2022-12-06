package com.udacity.asteroidradar.util
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.adapter.AsteroidAdapter
import com.udacity.asteroidradar.api.AsteroidApiStatus
import com.udacity.asteroidradar.local.entities.Asteroid
import com.udacity.asteroidradar.network.PictureOfDay

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("showImageOfDay")
fun showImageOfDay(img: ImageView, model: PictureOfDay?) {
    if (model != null && model.mediaType == "image") Picasso.with(img.context).load(model.url)
        .error(R.drawable.placeholder_picture_of_day).into(img)
    else img.setImageResource(R.drawable.placeholder_picture_of_day)
}


@BindingAdapter("visibilityOfProgressBar")
fun setVisibilityOfProgressBar(progressBar: ProgressBar, state: AsteroidApiStatus?) {
    progressBar.visibility = when (state) {
        AsteroidApiStatus.DONE -> View.GONE
        else -> View.VISIBLE
    }
}
@BindingAdapter("AsteroidApiStatusPhoto")
fun bindStatus(statusImageView: ImageView, status: AsteroidApiStatus?) {
    when (status) {
        AsteroidApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        AsteroidApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_broken_image)
        }
        AsteroidApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        else -> {statusImageView.visibility = View.GONE}
    }
}