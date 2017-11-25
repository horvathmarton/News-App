package hu.bme.mhorvath.newsapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.ShakeDetector;

import org.joda.time.DateTime;

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
    @BindView(R.id.tvUpdate)
    TextView tvUpdate;
    private RecyclerView.LayoutManager layoutManager;
    private ArticlesAdapter adapter;
    private ShakeDetector.ShakeListener shakeListener;

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

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        shakeListener = new ShakeDetector.ShakeListener() {
            @Override
            public void onShakeDetected() {}

            @Override
            public void onShakeStopped() {
                refreshView();
            }
        };
        Sensey.getInstance().startShakeDetection(shakeListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshView();
    }

    @Override
    public void onStop() {
        Sensey.getInstance().stopShakeDetection(shakeListener);
        super.onStop();
    }

    public void refreshView() {

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

        DateTime now = new DateTime();
        StringBuilder sb = new StringBuilder("Last updated: ");
        sb.append(now.toLocalDate()).append(" ").append(now.toLocalTime());
        sb.setLength(sb.length() - 4); // To remove milliseconds
        tvUpdate.setText(sb.toString());

        if (srlFragment.isRefreshing()) {
            srlFragment.setRefreshing(false);
        }
    }
}
