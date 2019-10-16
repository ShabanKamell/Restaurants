package restaurants.feature.restaurants

import com.sha.rxrequester.RequestInfo
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import restaurant.common.presentation.ui.vm.BaseViewModel
import restaurants.common.data.DataManager
import restaurants.common.data.model.Restaurant
import restaurants.common.core.util.disposeBy
import restaurants.common.data.model.toPresentation

val searchModule = module {
    viewModel { RestaurantsVm(get()) }
}

class RestaurantsVm(dataManager: DataManager) : BaseViewModel(dataManager) {

    fun restaurants(callback: (MutableList<Restaurant>) -> Unit) {
        val requestInfo = RequestInfo.Builder()
                .showLoading(true)
                .inlineErrorHandling { false }
                .build()
        requester.request(requestInfo) { dm.restaurantsRepo.all() }
                .subscribe {
                    callback(it.restaurants.toPresentation())
                }.disposeBy(disposable = disposables)
    }

}
