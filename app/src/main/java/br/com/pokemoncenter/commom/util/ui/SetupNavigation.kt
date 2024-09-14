package br.com.pokemoncenter.commom.util.ui

import android.app.Activity
import android.content.Intent
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import br.com.pokemon_center.R
import br.com.pokemoncenter.ui.views.GenerationsChoiceActivity
import br.com.pokemoncenter.ui.views.MainActivity
import br.com.pokemoncenter.ui.views.NaturesActivity
import br.com.pokemoncenter.ui.views.TypesActivity
import com.google.android.material.navigation.NavigationView

fun Activity.setupNavigationView(nv: NavigationView, dl: DrawerLayout) {
    nv.setNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.types_item -> {
                if (this !is TypesActivity) {
                    val intent = Intent(this, TypesActivity::class.java)
                    startActivity(intent)
                }
            }

            R.id.natures_item -> {
                if (this !is NaturesActivity) {
                    val intent = Intent(this, NaturesActivity::class.java)
                    startActivity(intent)
                }
            }

            R.id.generations_item -> {
                if (this !is GenerationsChoiceActivity) {
                    val intent = Intent(this, GenerationsChoiceActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        menuItem.isChecked = true
        dl.close()
        true
    }
    val currentMenuItemId = when (this) {
        is TypesActivity -> R.id.types_item
        is NaturesActivity -> R.id.natures_item
        is GenerationsChoiceActivity -> R.id.generations_item
        else -> null
    }
    currentMenuItemId?.let { nv.setCheckedItem(it) }
}

fun Activity.menuItemClick(item: MenuItem?, dl: DrawerLayout): Boolean {
    return when (item!!.itemId) {
        R.id.menu_home -> {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            true
        }

        R.id.menu_drawer -> {
            dl.open()
            true
        }

        else -> false
    }
}
