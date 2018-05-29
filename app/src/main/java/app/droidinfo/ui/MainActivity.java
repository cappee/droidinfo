package app.droidinfo.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import app.droidinfo.R;
import app.droidinfo.fragment.AndroidFragment;
import app.droidinfo.fragment.BatteryFragment;
import app.droidinfo.fragment.DeviceFragment;
import app.droidinfo.fragment.DisplayFragment;
import app.droidinfo.fragment.SensorFragment;
import app.droidinfo.fragment.SoCFragment;
import app.droidinfo.fragment.TelephonyFragment;

@SuppressWarnings("static-access")
public class MainActivity extends AppCompatActivity {

    private int[] intTabIcons = new int[] { R.drawable.ic_android_white_24dp, R.drawable.ic_memory_white_24dp, R.drawable.ic_smartphone_white_24dp, R.drawable.ic_display_white_24dp, R.drawable.ic_battery_charging_full_white_24dp, R.drawable.ic_sim_card_white_24dp, R.drawable.ic_screen_rotation_white_24dp, R.drawable.ic_root_white_24dp};

    private String[] stringTitleToolbar = new String[] {};

    private String IS_REFRESHED = "IS_REFRESHED";
    private String POSITION_SELECTED_TAB = "POSITION_SELECTED_TAB";

    private ViewPager viewPager;
    private TabLayout tabLayout;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        //Window window = getWindow();
        //window.setNavigationBarColor(getResources().getColor(android.R.color.background_dark));

        stringTitleToolbar = new String[] { "Android", getString(R.string.SoC), getString(R.string.Device), getString(R.string.Display), getString(R.string.Battery), getString(R.string.Telephony), getString(R.string.Sensor), getString(R.string.Root) };

        sharedPreferences = getSharedPreferences("DroidInfo", MODE_PRIVATE);

        viewPager = findViewById(R.id.viewPager);
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

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons(tabLayout);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.tabSelectedIconColor);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.tabUnselectedIconColor);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        if (sharedPreferences.getBoolean(IS_REFRESHED, false)) {
            tabLayout.getTabAt(sharedPreferences.getInt(POSITION_SELECTED_TAB, 1)).select();
            sharedPreferences.edit().putBoolean(IS_REFRESHED, false).apply();
        }
    }

    private void setupTabIcons(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setIcon(intTabIcons[0]);
        tabLayout.getTabAt(1).setIcon(intTabIcons[1]);
        tabLayout.getTabAt(2).setIcon(intTabIcons[2]);
        tabLayout.getTabAt(3).setIcon(intTabIcons[3]);
        tabLayout.getTabAt(4).setIcon(intTabIcons[4]);
        tabLayout.getTabAt(5).setIcon(intTabIcons[5]);
        tabLayout.getTabAt(6).setIcon(intTabIcons[6]);
        tabLayout.getTabAt(7).setIcon(intTabIcons[7]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new AndroidFragment(), "Android");
        viewPagerAdapter.addFragment(new SoCFragment(), "SoC");
        viewPagerAdapter.addFragment(new DeviceFragment(), "Device");
        viewPagerAdapter.addFragment(new DisplayFragment(), "Display");
        viewPagerAdapter.addFragment(new BatteryFragment(), "Battery");
        viewPagerAdapter.addFragment(new TelephonyFragment(), "Telephony");
        viewPagerAdapter.addFragment(new SensorFragment(), "Sensor");
        viewPagerAdapter.addFragment(new SensorFragment(), "Root");
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    protected void onResume() {
        // setupViewPager(viewPager);
        // tabLayout.setupWithViewPager(viewPager);
        super.onResume();
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
        if (id == R.id.MenuRefresh) {
            sharedPreferences.edit().putBoolean(IS_REFRESHED, true).putInt(POSITION_SELECTED_TAB, tabLayout.getSelectedTabPosition()).apply();
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } /*else if (id == R.id.MenuBenchmark) {
            startActivity(new Intent(MainActivity.this, BenchmarkActivity.class));
            finish();
            return true;
        }*/ else if (id == R.id.MenuSettings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> stringList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager fragmentManager) {
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

        void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            stringList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
            //return stringList.get(position)
        }

    }
}
