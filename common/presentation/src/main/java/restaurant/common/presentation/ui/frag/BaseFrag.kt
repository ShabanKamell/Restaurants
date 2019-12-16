package restaurant.common.presentation.ui.frag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import restaurant.common.presentation.R
import restaurant.common.presentation.ui.activity.BaseActivity
import restaurant.common.presentation.ui.view.ViewInterface
import restaurant.common.presentation.ui.vm.BaseViewModel
import restaurants.common.core.util.CrashlyticsUtil

abstract class BaseFrag<VM: BaseViewModel> : Fragment(), ViewInterface {

    abstract val vm: VM

    abstract var layoutId: Int
    open var swipeRefreshLayoutId: Int = 0
    protected open fun doOnViewCreated() {}
    protected fun doOnResume() {}

    open var hasBackNavigation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            vm.viewInterface = this

        } catch (e: Exception) {
            CrashlyticsUtil.logAndPrint(e)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            doOnViewCreated()
            setupSwipeRefresh()
            enableBackNavigation()
        } catch (e: Exception) {
            CrashlyticsUtil.logAndPrint(e)
        }

    }

    private fun setupSwipeRefresh() {
        if (swipeRefreshLayoutId != 0) {
            findViewById<SwipeRefreshLayout>(swipeRefreshLayoutId).setOnRefreshListener { this.onSwipeRefresh() }
        }
        else {
            findViewById<SwipeRefreshLayout>(swipeRefreshLayoutId).isEnabled = false
        }
    }

    /**
     * Designed to be overridden in Fragments that implement [HasSwipeRefresh]
     */
    protected open fun onSwipeRefresh() {}


    override fun onResume() {
        super.onResume()
        try {
            doOnResume()
        } catch (e: Exception) {
            CrashlyticsUtil.logAndPrint(e)
        }
    }

    override fun activity(): BaseActivity? {
        return activity as? BaseActivity
    }

    private fun enableBackNavigation() {
        if (!hasBackNavigation || view == null) return

        val backButton: View = view!!.findViewById(R.id.btnBack)
                ?: throw IllegalStateException("back button not found!")

        backButton.visibility = View.VISIBLE
        backButton.setOnClickListener { activity()?.onBackPressed() }
    }

    fun <T : View> findViewById(@IdRes id: Int): T {
        return activity!!.findViewById(id)
    }
}