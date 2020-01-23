package restaurant.common.presentation.ui.vm

import androidx.lifecycle.ViewModel
import com.sha.rxrequester.Presentable
import com.sha.rxrequester.RxRequester
import io.reactivex.disposables.CompositeDisposable
import restaurant.common.presentation.R
import restaurant.common.presentation.rx.*
import restaurant.common.presentation.ui.view.ViewInterface
import restaurants.common.data.DataManager

open class BaseViewModel(val dm: DataManager)
    : ViewModel() {

    lateinit var viewInterface: ViewInterface
    val disposables: CompositeDisposable = CompositeDisposable()
    var requester: RxRequester

    init { requester = setupRequester() }

    private fun setupRequester(): RxRequester {
        val presentable = object: Presentable {
            override fun showError(error: String) {
                viewInterface.showErrorInFlashBar(error)
            }

            override fun showError(error: Int) {
                viewInterface.showErrorInFlashBar(error)
            }

            override fun showLoading() {
                viewInterface.showLoadingDialog("")
            }

            override fun hideLoading() = viewInterface.dismissLoadingDialogs()

            override fun onHandleErrorFailed(throwable: Throwable) {
                viewInterface.showErrorInFlashBar(R.string.oops_something_went_wrong)
            }
        }

        return RxRequester.create(presentable) {
            httpHandlers = listOf(ServerErrorHandler())
            throwableHandlers = listOf(IoExceptionHandler(), NoSuchElementHandler(), OutOfMemoryErrorHandler())
            serverErrorContract = ErrorContract::class.java
        }
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}

