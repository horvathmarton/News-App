package hu.bme.mhorvath.newsapp.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import hu.bme.mhorvath.newsapp.fragment.ArticlesFragmet;

public class ScreenSliderPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> sources = new ArrayList<>();

    public ScreenSliderPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(ArticlesFragmet.KEY_SOURCE_NAME, sources.get(position));
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
        sources.add(name);
        notifyDataSetChanged();
    }

    public void removeSource(int position) {
        sources.remove(position);
        notifyDataSetChanged();
    }

    public void removeAllSources() {
        sources.clear();
        notifyDataSetChanged();
    }
}
