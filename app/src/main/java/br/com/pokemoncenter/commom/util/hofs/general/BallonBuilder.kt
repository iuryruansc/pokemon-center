package br.com.pokemoncenter.commom.util.hofs.general

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import br.com.pokemon_center.R
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec

fun createBalloon(context: Context, lifecycleOwner: LifecycleOwner, text: String, color: Int): Balloon {
    return Balloon.Builder(context)
        .setWidthRatio(1.0f)
        .setHeight(BalloonSizeSpec.WRAP)
        .setText(text)
        .setTextColorResource(R.color.md_theme_light_onPrimary)
        .setTextSize(15f)
        .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
        .setArrowSize(10)
        .setArrowPosition(0.5f)
        .setPadding(12)
        .setCornerRadius(8f)
        .setBackgroundColorResource(color)
        .setBalloonAnimation(BalloonAnimation.ELASTIC)
        .setLifecycleOwner(lifecycleOwner)
        .build()
}
