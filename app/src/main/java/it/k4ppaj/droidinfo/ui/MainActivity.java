package it.k4ppaj.droidinfo.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.k4ppaj.droidinfo.R;
import it.k4ppaj.droidinfo.fragment.AndroidFragment;
import it.k4ppaj.droidinfo.fragment.BatteryFragment;
import it.k4ppaj.droidinfo.fragment.DeviceFragment;
import it.k4ppaj.droidinfo.fragment.DisplayFragment;
import it.k4ppaj.droidinfo.fragment.SoCFragment;

@SuppressWarnings("static-access")
public class MainActivity extends AppCompatActivity {

    private int[] intTabIcons = new int[] { R.drawable.ic_android_white_24dp, R.drawable.ic_memory_white_24dp, R.drawable.ic_smartphone_white_24dp, R.drawable.ic_display_white_24dp, R.drawable.ic_battery_charging_full_white_24dp};

    private String[] stringTitleToolbar = new String[] {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        stringTitleToolbar = new String[] { "Android", "SoC", getString(R.string.Device), getString(R.string.Display), getString(R.string.Battery) };

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                toolbar.setTitle(stringTitleToolbar[position]);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons(tabLayout);
    }

    private void setupTabIcons(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setIcon(intTabIcons[0]);
        tabLayout.getTabAt(1).setIcon(intTabIcons[1]);
        tabLayout.getTabAt(2).setIcon(intTabIcons[2]);
        tabLayout.getTabAt(3).setIcon(intTabIcons[3]);
        tabLayout.getTabAt(4).setIcon(intTabIcons[4]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new AndroidFragment(), "Android");
        viewPagerAdapter.addFragment(new SoCFragment(), "SoC");
        viewPagerAdapter.addFragment(new DeviceFragment(), "Device");
        viewPagerAdapter.addFragment(new DisplayFragment(), "Display");
        viewPagerAdapter.addFragment(new BatteryFragment(), "Battery");
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> stringList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            stringList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
            //return stringList.get(position);
        }
    }
}
