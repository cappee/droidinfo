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
import it.k4ppaj.droidinfo.fragment.BatteryFragment;
import it.k4ppaj.droidinfo.fragment.DeviceFragment;
import it.k4ppaj.droidinfo.fragment.DisplayFragment;
import it.k4ppaj.droidinfo.fragment.SoCFragment;

@SuppressWarnings("static-access")
public class MainActivity extends AppCompatActivity {

    private int[] intTabIcons = new int[] { R.drawable.ic_memory_white_24dp, R.drawable.ic_smartphone_white_24dp, R.drawable.ic_display_white_24dp, R.drawable.ic_battery_charging_full_white_24dp};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons(tabLayout);

        final FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fabMain);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getRandomNumber() == 1 || getRandomNumber() == 3 || getRandomNumber() == 5 || getRandomNumber() == 7 || getRandomNumber() == 9) {
                    floatingActionButton.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward));
                } else {
                    floatingActionButton.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward));
                }
                //TODO: Refresh data
                Toast.makeText(MainActivity.this, R.string.Updating, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(8) + 1;
    }

    private void setupTabIcons(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setIcon(intTabIcons[0]);
        tabLayout.getTabAt(1).setIcon(intTabIcons[1]);
        tabLayout.getTabAt(2).setIcon(intTabIcons[2]);
        tabLayout.getTabAt(3).setIcon(intTabIcons[3]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
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
