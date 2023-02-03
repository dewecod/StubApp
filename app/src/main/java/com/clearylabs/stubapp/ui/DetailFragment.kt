package com.clearylabs.stubapp.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.clearylabs.stubapp.R
import com.clearylabs.stubapp.base.BaseFragment
import com.clearylabs.stubapp.databinding.FragmentDetailBinding
import com.clearylabs.stubapp.model.Article

class DetailFragment : BaseFragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = this.arguments
        if (bundle != null) {
            val article: Article = bundle.getParcelable("article")!!
            updateUI(article)
        }
    }

    private fun updateUI(article: Article) {
        binding.layoutToolbar.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.layoutToolbar.toolbar.setNavigationOnClickListener { mFragmentNavigation.navigateBack() }
        binding.layoutToolbar.toolbar.title = article.title

        binding.textTitle.text = article.title

        Glide.with(activity)
            .load(article.image)
            .into(binding.imageArticle)

        // Update only post body other attributes are update above (updateUI)
        binding.textBody.setBackgroundColor(Color.TRANSPARENT)
        binding.textBody.settings.textZoom = (binding.textBody.settings.textZoom * 1)
        binding.textBody.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        binding.textBody.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

        article.body?.let {
            val html = "<html><body style='margin:0;padding:0;'>${it}</body></html>"
            binding.textBody.loadData(html, "text/html; charset=utf-8", "utf-8")
        }
    }
}