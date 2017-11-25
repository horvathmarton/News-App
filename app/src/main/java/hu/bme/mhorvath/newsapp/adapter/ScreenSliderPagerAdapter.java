package hu.bme.mhorvath.newsapp.adapter;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import hu.bme.mhorvath.newsapp.fragment.ArticlesFragmet;
import hu.bme.mhorvath.newsapp.model.NewsData;
import hu.bme.mhorvath.newsapp.model.Source;
import hu.bme.mhorvath.newsapp.network.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScreenSliderPagerAdapter extends FragmentStatePagerAdapter {

    private List<Source> sources = new ArrayList<>();

    public ScreenSliderPagerAdapter(FragmentManager fm) {
        super(fm);
        sources = Source.listAll(Source.class);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(ArticlesFragmet.KEY_SOURCE_NAME, sources.get(position).getSource());
        ArticlesFragmet fragmet = new ArticlesFragmet();
        fragmet.setArguments(bundle);
        return fragmet;
    }

    @Override
    public int getCount() {
        return sources.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void addSource(String name) {
        Source source = new Source(name);
        sources.add(source);
        source.save();
        notifyDataSetChanged();
    }

    public void removeSource(int position) {
        Source source = sources.remove(position);
        source.delete();
        notifyDataSetChanged();
    }

    public void removeAllSources() {
        sources.clear();
        Source.deleteAll(Source.class);
        notifyDataSetChanged();
    }
}
