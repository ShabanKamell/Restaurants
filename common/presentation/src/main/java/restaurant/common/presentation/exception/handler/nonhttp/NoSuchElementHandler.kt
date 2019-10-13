package restaurant.common.presentation.exception.handler.nonhttp

import restaurants.common.core.R
import java.util.*

class NoSuchElementHandler : NonHttpExceptionHandler() {

    override fun supportedThrowables(): List<Class<*>> {
        return listOf<Class<*>>(NoSuchElementException::class.java)
    }

    override fun handle() {
        presenter.view.showErrorInFlashBar(R.string.no_data_entered_yet)
    }
}
