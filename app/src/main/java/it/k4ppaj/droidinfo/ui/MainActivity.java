package it.k4ppaj.droidinfo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.k4ppaj.droidinfo.R;
import it.k4ppaj.droidinfo.fragment.AndroidFragment;
import it.k4ppaj.droidinfo.fragment.BatteryFragment;
import it.k4ppaj.droidinfo.fragment.DeviceFragment;
import it.k4ppaj.droidinfo.fragment.DisplayFragment;
import it.k4ppaj.droidinfo.fragment.SoCFragment;
import it.k4ppaj.droidinfo.fragment.TelephonyFragment;

@SuppressWarnings("static-access")
public class MainActivity extends AppCompatActivity {

    private int[] intTabIcons = new int[] { R.drawable.ic_android_white_24dp, R.drawable.ic_memory_white_24dp, R.drawable.ic_smartphone_white_24dp, R.drawable.ic_display_white_24dp, R.drawable.ic_battery_charging_full_white_24dp, R.drawable.ic_sim_card_white_24dp };

    private String[] stringTitleToolbar = new String[] {};

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        Window window = getWindow();
        window.setNavigationBarColor(getResources().getColor(android.R.color.background_dark));

        stringTitleToolbar = new String[] { "Android", getString(R.string.SoC), getString(R.string.Device), getString(R.string.Display), getString(R.string.Battery), getString(R.string.Telephony) };

        viewPager = (ViewPager) findViewById(R.id.viewPager);
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

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons(tabLayout);
    }

    private void setupTabIcons(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setIcon(intTabIcons[0]);
        tabLayout.getTabAt(1).setIcon(intTabIcons[1]);
        tabLayout.getTabAt(2).setIcon(intTabIcons[2]);
        tabLayout.getTabAt(3).setIcon(intTabIcons[3]);
        tabLayout.getTabAt(4).setIcon(intTabIcons[4]);
        tabLayout.getTabAt(5).setIcon(intTabIcons[5]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new AndroidFragment(), "Android");
        viewPagerAdapter.addFragment(new SoCFragment(), "SoC");
        viewPagerAdapter.addFragment(new DeviceFragment(), "Device");
        viewPagerAdapter.addFragment(new DisplayFragment(), "Display");
        viewPagerAdapter.addFragment(new BatteryFragment(), "Battery");
        viewPagerAdapter.addFragment(new TelephonyFragment(), "Telephony");
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
            Toast.makeText(MainActivity.this, R.string.WorkInProgress, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.MenuBenchmark) {
            startActivity(new Intent(MainActivity.this, BenchmarkActivity.class));
            return true;
        } else if (id == R.id.MenuSettings) {
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
