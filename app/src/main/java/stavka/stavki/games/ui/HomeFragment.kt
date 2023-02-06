package stavka.stavki.games.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import stavka.stavki.games.R
import stavka.stavki.games.base.BaseFragment
import stavka.stavki.games.databinding.FragmentHomeBinding
import stavka.stavki.games.net.Resource
import stavka.stavki.games.util.Constant.Companion.PER_PAGE
import stavka.stavki.games.util.toGone
import stavka.stavki.games.util.toVisible
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by sharedViewModel()

    private val adapter by lazy { ArticleAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initRecyclerView()
        observe()
    }

    private fun initView() {
        binding.layoutToolbar.toolbar.title = resources.getString(R.string.article_list)
    }

    private fun initRecyclerView() {
        binding.recyclerArticle.adapter = adapter
        binding.recyclerArticle.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerArticle.setHasFixedSize(true)
        binding.recyclerArticle.setItemViewCacheSize(PER_PAGE)

        adapter.onItemClick = { article ->
            val bundle = Bundle()
            bundle.putParcelable("article", article)
            val detailFragment = DetailFragment()
            detailFragment.arguments = bundle
            mFragmentNavigation.navigateTo(detailFragment)
        }
    }

    private fun observe() {
        mainViewModel.indexResponse().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    binding.swipeAddress.isRefreshing = true
                }
                is Resource.Success -> {
                    binding.swipeAddress.isRefreshing = false
                    binding.recyclerArticle.toVisible()
                    if (response.data.articleList.results.isNotEmpty()) {
                        adapter.submitList(response.data.articleList.results.toMutableList())
                    }
                }
                is Resource.Error -> {
                    binding.swipeAddress.isRefreshing = false
                    adapter.submitList(mutableListOf())
                    Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show()

                }
                is Resource.Empty -> {
                    binding.swipeAddress.isRefreshing = false
                    adapter.submitList(mutableListOf())
                    binding.recyclerArticle.toGone()
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}