package br.com.pokemoncenter.commom.idling

import androidx.test.espresso.idling.CountingIdlingResource

object ApiIdlingResource {
    private const val RESOURCE = "API_CALLS"
    private val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        countingIdlingResource.decrement()
    }

    fun getIdlingResource(): CountingIdlingResource {
        return countingIdlingResource
    }
}
