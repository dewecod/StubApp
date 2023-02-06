package stavka.stavki.games.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import stavka.stavki.games.databinding.ActivityWebBinding

class WebActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra("url").toString()

        if (url.isNotEmpty()) {
            binding.web.webViewClient = WebViewClient()
            val webSettings = binding.web.settings

            if (savedInstanceState != null) {
                binding.web.restoreState(savedInstanceState)
            } else {
                binding.web.loadUrl(url)
            }

            val cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            webSettings.javaScriptEnabled = true
            webSettings.loadWithOverviewMode = true
            webSettings.useWideViewPort = true
            webSettings.domStorageEnabled = true
            webSettings.databaseEnabled = true
            webSettings.setSupportZoom(false)
            webSettings.allowFileAccess = true
            webSettings.allowContentAccess = true
            webSettings.loadWithOverviewMode = true
            webSettings.useWideViewPort = true
            webSettings.javaScriptCanOpenWindowsAutomatically = true
        }
    }
}