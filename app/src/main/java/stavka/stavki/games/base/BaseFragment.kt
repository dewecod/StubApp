package stavka.stavki.games.base

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import stavka.stavki.games.ui.MainActivity

abstract class BaseFragment : Fragment() {
    lateinit var mFragmentNavigation: FragmentNavigation
    lateinit var activity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)

        activity = requireContext() as MainActivity
        if (context is FragmentNavigation) {
            mFragmentNavigation = context
        }
    }

    /****************************************************************************************************
     ** FragNav
     ****************************************************************************************************/
    interface FragmentNavigation {
        fun clearStack()
        fun pushFragment(fragment: Fragment, sharedElementList: List<Pair<View, String>>? = null)
        fun navigateBack()
        fun navigateBack(i: Int)
        fun navigateBack(z: Boolean)
        fun navigateTo(fragment: Fragment)
    }
}