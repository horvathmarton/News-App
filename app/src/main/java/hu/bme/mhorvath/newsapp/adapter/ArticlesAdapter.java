package hu.bme.mhorvath.newsapp.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.bme.mhorvath.newsapp.R;
import hu.bme.mhorvath.newsapp.model.Article;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    private List<Article> articles = new ArrayList<>();

    @Override
    public ArticlesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ArticlesAdapter.ViewHolder holder, int position) {
        final Article article = articles.get(position);

        holder.tvTitle.setText(article.title);
        holder.tvDescription.setText(article.description);

        Picasso.with(holder.ivImage.getContext()).load(article.urlToImage).into(holder.ivImage);

        holder.cvArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.url));
                holder.cvArticle.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void addItems(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cvArticle)
        CardView cvArticle;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.ivImage)
        ImageView ivImage;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
