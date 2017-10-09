package it.k4ppaj.droidinfo.ui;

import android.content.Intent;
import android.net.Uri;
import android.preference.Preference;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.preference.PreferenceActivity;
import android.widget.Toast;

import it.k4ppaj.droidinfo.R;
import it.k4ppaj.droidinfo.adapter.HeadersAdapter;

public class SettingsActivity extends AppCompatActivity {

    String[] stringHeadersTitle = new String[] {};

    int[] intHeadersIcon = new int[] {
            R.drawable.ic_settings_black_24dp,
            R.drawable.ic_attach_money_black_24dp,
            R.drawable.ic_info_black_24dp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSettings);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                finish();
            }
        });

        stringHeadersTitle = new String[] {
                getString(R.string.General),
                getString(R.string.Donation),
                getString(R.string.Information)
        };

        ListView listView = (ListView) findViewById(R.id.listViewSettings);
        HeadersAdapter adapter = new HeadersAdapter(this, stringHeadersTitle, intHeadersIcon);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Toast.makeText(SettingsActivity.this, R.string.WorkInProgress, Toast.LENGTH_SHORT).show();
                        /*startActivity(new Intent(SettingsActivity.this, GeneralPreferenceFragment.class));
                        finish();*/
                        break;
                    case 1:
                        startActivity(new Intent(SettingsActivity.this, DonationPreferenceFragment.class));
                        finish();
                        break;
                    case 2:
                        startActivity(new Intent(SettingsActivity.this, InfoPreferenceFragment.class));
                        finish();
                        break;
                }
            }
        });
    }

    public static class GeneralPreferenceFragment extends PreferenceActivity {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefercence_general);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
        }

        @Override
        protected void onPostCreate(@Nullable Bundle savedInstanceState) {
            super.onPostCreate(savedInstanceState);

            LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
            Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.layout_toolbar, root, false);
            bar.setTitle(R.string.General);
            root.addView(bar, 0);
            bar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentBack = new Intent(GeneralPreferenceFragment.this, SettingsActivity.class);
                    startActivity(intentBack);
                    finish();
                }
            });
        }

        @Override
        public void onBackPressed() {
            startActivity(new Intent(GeneralPreferenceFragment.this, SettingsActivity.class));
            finish();
            super.onBackPressed();
        }
    }

    public static class DonationPreferenceFragment extends PreferenceActivity {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference_donation);

            Preference preferenceLinkk4ppaj = findPreference("linkk4ppaj");
            Preference preferenceLinkSimonePandolfi = findPreference("linkSimonePandolfi");

            preferenceLinkk4ppaj.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.me/k4ppaj"));
                    startActivity(browserIntent);
                    return false;
                }
            });
            preferenceLinkSimonePandolfi.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.me/SimonePandolfi"));
                    startActivity(browserIntent);
                    return false;
                }
            });
        }

        @Override
        protected void onPostCreate(@Nullable Bundle savedInstanceState) {
            super.onPostCreate(savedInstanceState);

            LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
            Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.layout_toolbar, root, false);
            bar.setTitle(R.string.Donation);
            root.addView(bar, 0);
            bar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentBack = new Intent(DonationPreferenceFragment.this, SettingsActivity.class);
                    startActivity(intentBack);
                    finish();
                }
            });
        }

        @Override
        public void onBackPressed() {
            startActivity(new Intent(DonationPreferenceFragment.this, SettingsActivity.class));
            finish();
            super.onBackPressed();
        }
    }

    public static class InfoPreferenceFragment extends PreferenceActivity {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference_information);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
        }

        @Override
        protected void onPostCreate(@Nullable Bundle savedInstanceState) {
            super.onPostCreate(savedInstanceState);

            LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
            Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.layout_toolbar, root, false);
            bar.setTitle(R.string.Information);
            root.addView(bar, 0);
            bar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentBack = new Intent(InfoPreferenceFragment.this, SettingsActivity.class);
                    startActivity(intentBack);
                    finish();
                }
            });
        }

        @Override
        public void onBackPressed() {
            startActivity(new Intent(InfoPreferenceFragment.this, SettingsActivity.class));
            finish();
            super.onBackPressed();
        }
    }
}
