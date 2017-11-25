package hu.bme.mhorvath.newsapp.interfaces;

public interface OnSourceChangedListener {

    void onSourceAdded(String source);

    void onSourceRemoved(int position);

    void onAllSourceRemoved();

}
