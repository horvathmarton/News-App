package hu.bme.mhorvath.newsapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.bme.mhorvath.newsapp.R;
import hu.bme.mhorvath.newsapp.adapter.ArticlesAdapter;
import hu.bme.mhorvath.newsapp.model.Article;
import hu.bme.mhorvath.newsapp.model.NewsData;
import hu.bme.mhorvath.newsapp.network.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticlesFragmet extends Fragment {

    public static final String KEY_SOURCE_NAME = "KEY_SOURCE_NAME";

    @BindView(R.id.srlFragment)
    SwipeRefreshLayout srlFragment;
    @BindView(R.id.rvArticles)
    RecyclerView rvArticles;
    private RecyclerView.LayoutManager layoutManager;
    private ArticlesAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_articles, container,
                false);
        ButterKnife.bind(this, rootView);

        layoutManager = new LinearLayoutManager(getContext());
        adapter = new ArticlesAdapter();

        rvArticles.setHasFixedSize(true);
        rvArticles.setLayoutManager(layoutManager);
        rvArticles.setAdapter(adapter);

        refreshView();
        srlFragment.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshView();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshView();
    }

    private void refreshView() {

        String source = getArguments().getString(KEY_SOURCE_NAME);
        Call<NewsData> call = NetworkManager.getInstance().getNews(source);
        call.enqueue(new Callback<NewsData>() {
            @Override
            public void onResponse(@NonNull Call<NewsData> call, @NonNull Response<NewsData> response) {

                List<Article> articles = response.body().articles;
                adapter.addItems(articles);

            }

            @Override
            public void onFailure(@NonNull Call<NewsData> call, @NonNull Throwable t) {}
        });

        // TODO: Here comes some kind of refresh indication

        if (srlFragment.isRefreshing()) {
            srlFragment.setRefreshing(false);
        }
    }
}
