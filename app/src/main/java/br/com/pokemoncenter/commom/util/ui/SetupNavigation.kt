package br.com.pokemoncenter.commom.util.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.MenuItem
import androidx.core.app.ActivityOptionsCompat
import androidx.drawerlayout.widget.DrawerLayout
import br.com.pokemon_center.R
import br.com.pokemoncenter.ui.views.FavoritesActivity
import br.com.pokemoncenter.ui.views.GenChoiceActivity
import br.com.pokemoncenter.ui.views.MainActivity
import br.com.pokemoncenter.ui.views.NaturesActivity
import br.com.pokemoncenter.ui.views.RegionChoiceActivity
import br.com.pokemoncenter.ui.views.TypesActivity
import com.google.android.material.navigation.NavigationView

fun Activity.setupNavigationView(nv: NavigationView, dl: DrawerLayout) {
    nv.setNavigationItemSelectedListener { menuItem ->
        val targetActivity = when (menuItem.itemId) {
            R.id.types_item -> TypesActivity::class.java
            R.id.natures_item -> NaturesActivity::class.java
            R.id.generations_item -> GenChoiceActivity::class.java
            R.id.favorites_item -> FavoritesActivity::class.java
            R.id.pokedex_item -> RegionChoiceActivity::class.java
            else -> null
        }
        targetActivity?.let {
            if (this::class.java != it) {
                startActivity(Intent(this, it))
            }
        }
        menuItem.isChecked = true
        dl.close()
        true
    }
    val currentMenuItemId = when (this) {
        is TypesActivity -> R.id.types_item
        is NaturesActivity -> R.id.natures_item
        is GenChoiceActivity -> R.id.generations_item
        is FavoritesActivity -> R.id.favorites_item
        is RegionChoiceActivity -> R.id.pokedex_item
        else -> null
    }
    currentMenuItemId?.let { nv.setCheckedItem(it) }
}

fun Activity.menuItemClick(item: MenuItem?, dl: DrawerLayout): Boolean {
    return when (item!!.itemId) {
        R.id.menu_home -> {
            val intent = Intent(this, MainActivity::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                // for newer versions
                val options = ActivityOptionsCompat.makeCustomAnimation(
                    this, R.anim.slide_in_left, R.anim.slide_out_right
                )
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent, options.toBundle())
            } else {
                // for older versions
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
            true
        }

        R.id.menu_drawer -> {
            dl.open()
            true
        }

        else -> false
    }
}
