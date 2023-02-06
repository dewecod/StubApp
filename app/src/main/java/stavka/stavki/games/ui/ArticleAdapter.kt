package stavka.stavki.games.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import stavka.stavki.games.databinding.ItemArticleBinding
import stavka.stavki.games.model.Article

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.HomeViewHolder>() {
    private val mDiffer: AsyncListDiffer<Article> = AsyncListDiffer(this, DiffUtilCallback)

    var onItemClick: ((Article) -> Unit)? = null
    private var mList: MutableList<Article> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleAdapter.HomeViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int = mDiffer.currentList.size
    override fun onBindViewHolder(holder: ArticleAdapter.HomeViewHolder, position: Int) = holder.bind(mDiffer.currentList[position])

    inner class HomeViewHolder(private val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            if (mDiffer.currentList.isNotEmpty()) {
                val activity: MainActivity = binding.root.context as MainActivity
                binding.root.setOnClickListener { onItemClick?.invoke(article) }

                binding.textTitle.text = article.title

                Glide.with(activity)
                    .load(article.image)
                    .into(binding.imageArticle)
            }
        }
    }

    object DiffUtilCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.uri == newItem.uri
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    fun submitList(articleList: MutableList<Article>) {
        mList.clear()
        mList.addAll(articleList)
        mDiffer.submitList(articleList)
    }

    fun getList(): List<Article> = mList.toList()
}