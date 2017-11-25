package hu.bme.mhorvath.newsapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.nisrulz.sensey.Sensey;

import org.joda.time.DateTime;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.bme.mhorvath.newsapp.adapter.ScreenSliderPagerAdapter;
import hu.bme.mhorvath.newsapp.fragment.AddSourceDialogFragment;
import hu.bme.mhorvath.newsapp.interfaces.OnSourceChangedListener;
import hu.bme.mhorvath.newsapp.notification.Receiver;
import hu.bme.mhorvath.newsapp.transformer.ZoomOutPageTransformer;

public class MainActivity extends AppCompatActivity implements OnSourceChangedListener {

    @BindView(R.id.pager)
    ViewPager pager;
    private ScreenSliderPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        pagerAdapter = new ScreenSliderPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setPageTransformer(true, new ZoomOutPageTransformer());

        initNotification();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Sensey.getInstance().init(this);
    }

    @Override
    protected void onStop() {
        Sensey.getInstance().stop();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_source:
                new AddSourceDialogFragment().show(getSupportFragmentManager(),
                        AddSourceDialogFragment.TAG);
                return true;
            case R.id.action_delete_source:
                onSourceRemoved(pager.getCurrentItem());
                return true;
            case R.id.action_delete_all_source:
                onAllSourceRemoved();
                return true;
            default:
                return true;
        }
    }

    @Override
    public void onSourceAdded(String source) {
        pagerAdapter.addSource(source);
    }

    @Override
    public void onSourceRemoved(int position) {
        pagerAdapter.removeSource(position);
    }

    @Override
    public void onAllSourceRemoved() {
        pagerAdapter.removeAllSources();
    }

    private void initNotification() {

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 3, intent,
                0);

        DateTime when = new DateTime().withHourOfDay(7).withMinuteOfHour(0).withSecondOfMinute(0);

        am.setRepeating(AlarmManager.RTC_WAKEUP, when.getMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

    }

}
