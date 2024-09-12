package br.com.pokemoncenter.commom.util.hofs.general

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import br.com.pokemon_center.R
import br.com.pokemoncenter.commom.Constants
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec

fun createBalloon(context: Context, lifecycleOwner: LifecycleOwner, text: String, color: Int): Balloon {
    return Balloon.Builder(context)
        .setWidthRatio(Constants.BALLOON_WIDTH_RATIO)
        .setHeight(BalloonSizeSpec.WRAP)
        .setText(text)
        .setTextColorResource(R.color.md_theme_light_onPrimary)
        .setTextSize(Constants.BALLOON_TEXT_SIZE)
        .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
        .setArrowSize(Constants.BALLOON_ARROW_SIZE)
        .setArrowPosition(Constants.BALLOON_ARROW_POSITION)
        .setPadding(Constants.BALLOON_PADDING)
        .setCornerRadius(Constants.BALLOON_CORNER_RADIUS)
        .setBackgroundColorResource(color)
        .setBalloonAnimation(BalloonAnimation.ELASTIC)
        .setLifecycleOwner(lifecycleOwner)
        .build()
}
