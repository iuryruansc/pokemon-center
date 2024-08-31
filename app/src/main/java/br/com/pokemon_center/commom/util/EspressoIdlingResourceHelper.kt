package br.com.pokemon_center.commom.util

import br.com.pokemon_center.commom.idling.ApiIdlingResource


class EspressoIdlingResourceHelper : IdlingResourceHelper {
    override fun increment() = ApiIdlingResource.increment()

    override fun decrement() = ApiIdlingResource.decrement()
}