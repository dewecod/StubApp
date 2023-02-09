package stavka.stavki.games.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import stavka.stavki.games.R
import stavka.stavki.games.base.BaseActivity
import stavka.stavki.games.base.BaseFragment
import stavka.stavki.games.databinding.ActivityMainBinding
import stavka.stavki.games.model.Config
import stavka.stavki.games.util.Constant.Companion.API_ARTICLES_COUNT
import stavka.stavki.games.util.Constant.Companion.API_KEY
import stavka.stavki.games.util.Constant.Companion.API_RESULT_TYPE
import kotlin.system.exitProcess


class MainActivity : BaseActivity(), BaseFragment.FragmentNavigation,
    FragNavController.TransactionListener, FragNavController.RootFragmentListener {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModel()

    private var doubleBackToExitPressedOnce = false

    private val fragNavController: FragNavController = FragNavController(supportFragmentManager, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFragNav(savedInstanceState)

        val apiConfig = Config(
            apiKey = API_KEY,
            resultType = API_RESULT_TYPE,
            articlesCount = API_ARTICLES_COUNT,
            keyword = listOf("sport", "football", "basketball"),
            ignoreKeyword = listOf(
                "die", "kill", "dead", "sex", "fatal", "threat", "terrible", "polytics", "dynamite", "war", "bad", "fatal"
            ),
        )
        mainViewModel.loadIndex(apiConfig)
    }

    private fun initFragNav(savedInstanceState: Bundle?) {
        fragNavController.apply {
            rootFragmentListener = this@MainActivity
            createEager = true
            fragmentHideStrategy = FragNavController.HIDE
        }
        fragNavController.initialize(0, savedInstanceState)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount <= 0 || !supportFragmentManager.popBackStackImmediate()) {
            if (!this.fragNavController.isRootFragment) {
                if (this.fragNavController.popFragment(
                        FragNavTransactionOptions.newBuilder().build()
                    )
                )
                    return
            } else {
                setupBackPressedDispatcher()
            }
        }
    }

    private fun setupBackPressedDispatcher() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity()
            exitProcess(0)
        }
        doubleBackToExitPressedOnce = true
        Toast.makeText(applicationContext, "Tap back button in order to exit", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> fragNavController.popFragment()
        }
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        fragNavController.onSaveInstanceState(outState)
    }

    /****************************************************************************************************
     **  FragNav (BaseFragment interface implementation)
     ****************************************************************************************************/
    override fun clearStack() = fragNavController.clearStack()
    override fun pushFragment(fragment: Fragment, sharedElementList: List<Pair<View, String>>?) {
        val options = FragNavTransactionOptions.newBuilder()
        sharedElementList?.let {
            it.forEach { pair ->
                options.addSharedElement(pair)
            }
        }
        fragNavController.pushFragment(fragment, options.build())
    }

    override fun navigateBack() = this.onBackPressed()

    override fun navigateBack(i: Int) {
        if (!fragNavController.isRootFragment) {
            fragNavController.popFragments(i, FragNavTransactionOptions.newBuilder().build())
        }
    }

    override fun navigateBack(z: Boolean) {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        }
    }

    override fun navigateTo(fragment: Fragment) = fragNavController.pushFragment(fragment, FragNavTransactionOptions.newBuilder().build())

    override val numberOfRootFragments: Int = 1

    override fun getRootFragment(index: Int): Fragment {
        when (index) {
            0 -> return HomeFragment()
        }
        throw IllegalStateException("Need to send an index that we know")
    }

    override fun onFragmentTransaction(
        fragment: Fragment?,
        transactionType: FragNavController.TransactionType
    ) {
        supportActionBar?.setDisplayHomeAsUpEnabled(fragNavController.isRootFragment.not())
    }

    override fun onTabTransaction(fragment: Fragment?, index: Int) {
        supportActionBar?.setDisplayHomeAsUpEnabled(fragNavController.isRootFragment.not())
    }
}