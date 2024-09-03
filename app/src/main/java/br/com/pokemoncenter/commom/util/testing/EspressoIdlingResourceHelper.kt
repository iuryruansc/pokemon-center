package br.com.pokemoncenter.commom.util.testing

import br.com.pokemoncenter.commom.idling.ApiIdlingResource

class EspressoIdlingResourceHelper : IdlingResourceHelper {
    override fun increment() = ApiIdlingResource.increment()

    override fun decrement() = ApiIdlingResource.decrement()
}
