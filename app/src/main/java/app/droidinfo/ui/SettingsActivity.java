package app.droidinfo.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;

import app.droidinfo.R;
import app.droidinfo.adapter.HeadersAdapter;

public class SettingsActivity extends AppCompatActivity {

    /*private Activity context;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }*/

    String[] stringHeadersTitle = new String[] {};

    int[] intHeadersIcon = new int[] {
            R.drawable.ic_settings_white_24dp,
            R.drawable.ic_attach_money_white_24dp,
            R.drawable.ic_info_white_24dp,
            R.drawable.ic_bug_report_white_24dp
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Toolbar toolbar = findViewById(R.id.toolbarSettings);
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
                getString(R.string.Information),
                getString(R.string.Debug)
        };

        ListView listView = findViewById(R.id.listViewSettings);
        HeadersAdapter adapter = new HeadersAdapter(this, stringHeadersTitle, intHeadersIcon);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(SettingsActivity.this, GeneralPreferenceFragment.class));
                        finish();
                        break;
                    case 1:
                        startActivity(new Intent(SettingsActivity.this, DonationPreferenceFragment.class));
                        finish();
                        break;
                    case 2:
                        startActivity(new Intent(SettingsActivity.this, InfoPreferenceFragment.class));
                        finish();
                        break;
                    case 3:
                        startActivity(new Intent(SettingsActivity.this, DebugPreferenceFragment.class));
                        finish();
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
        finish();
        super.onBackPressed();
    }

    public static class GeneralPreferenceFragment extends PreferenceActivity {

        private String FONT = "FONT";
        private String CLICKONITEM = "CLICKONITEM";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference_general);

            final SharedPreferences sharedPreferences = getSharedPreferences("DroidInfo", MODE_PRIVATE);

            final Preference preferenceFont = findPreference("preferenceFont");
            String fontSummary = "";
            switch (sharedPreferences.getString(FONT, "Roboto")) {
                case "Roboto": fontSummary = "Roboto";
                    break;
                case "Google Sans": fontSummary = "Google Sans";
                    break;
                case "OpenDyslexic": fontSummary = "OpenDyslexic";
                    break;
            }
            preferenceFont.setSummary(sharedPreferences.getString(FONT, "Roboto"));

            final CharSequence[] charSequencesFont = new CharSequence[] {"Roboto", "Google Sans", "OpenDyslexic"};
            preferenceFont.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(final Preference preference) {
                    int itemChecked = 0;
                    switch (sharedPreferences.getString(FONT, "Roboto")) {
                        case "Roboto": itemChecked = 0;
                            break;
                        case "Google Sans": itemChecked = 1;
                            break;
                        case "OpenDyslexic": itemChecked = 2;
                            break;
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(GeneralPreferenceFragment.this);
                    builder.setSingleChoiceItems(charSequencesFont, itemChecked, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int checked) {
                            switch (checked) {
                                case 0: sharedPreferences.edit().putString(FONT, "Roboto").apply();
                                    preferenceFont.setSummary("Roboto");
                                    dialogInterface.dismiss();
                                    break;
                                case 1: sharedPreferences.edit().putString(FONT, "Google Sans").apply();
                                    preferenceFont.setSummary("Google Sans");
                                    dialogInterface.dismiss();
                                    break;
                                case 2: sharedPreferences.edit().putString(FONT, "OpenDyslexic").apply();
                                    preferenceFont.setSummary("OpenDyslexic");
                                    dialogInterface.dismiss();
                                    break;
                            }
                        }
                    });
                    builder.show();
                    return false;
                }
            });

            final SwitchPreference switchPreferenceMoreFeature = (SwitchPreference) findPreference("preferenceSwitchMoreFeature");
            final CheckBoxPreference checkboxPreferenceClickOnItem = (CheckBoxPreference) findPreference("checkboxPreferenceClickOnItem");

            switchPreferenceMoreFeature.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    if (switchPreferenceMoreFeature.isChecked()) {
                        sharedPreferences.edit()
                                .putBoolean(CLICKONITEM, checkboxPreferenceClickOnItem.isChecked())
                                .apply();
                    } else {
                        sharedPreferences.edit()
                                .putBoolean(CLICKONITEM, false)
                                .apply();
                    }
                    return false;
                }
            });

            checkboxPreferenceClickOnItem.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    sharedPreferences.edit().putBoolean(CLICKONITEM, checkboxPreferenceClickOnItem.isChecked()).apply();
                    return false;
                }
            });
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

            preferenceLinkk4ppaj.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.me/k4ppaj"));
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

            Preference preferenceAuthor = findPreference("preferenceAuthor");
            Preference preferenceVoteApplication = findPreference("preferenceVoteApplication");
            Preference preferencePackageName = findPreference("preferencePackageName");

            preferencePackageName.setSummary(getApplicationContext().getPackageName());

            preferenceAuthor.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(InfoPreferenceFragment.this);
                    builder.setMessage(R.string.OpenLinks);
                    builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/k4ppaj")));
                        }
                    });
                    builder.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                    return false;
                }
            });

            preferenceVoteApplication.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(InfoPreferenceFragment.this);
                    builder.setMessage(R.string.OpenLinks);
                    builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=it.droidinfo")));
                        }
                    });
                    builder.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                    return false;
                }
            });
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

    public static class DebugPreferenceFragment extends PreferenceActivity {

        private String USE_DEFAULT_INFORMATION = "USE_DEFAULT_INFORMATION";
        private boolean IS_UNLOCKED = false;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference_debug);

            final SharedPreferences sharedPreferences = getSharedPreferences("DroidInfo", MODE_PRIVATE);

            final SwitchPreference switchPreferenceDefaultInformation = (SwitchPreference) findPreference("preferenceDefaultInformation");
            Preference preferenceUnlockDebug = findPreference("preferenceUnlockDebug");

            switchPreferenceDefaultInformation.setChecked(sharedPreferences.getBoolean(USE_DEFAULT_INFORMATION, false));

            preferenceUnlockDebug.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    if (!IS_UNLOCKED) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DebugPreferenceFragment.this);
                        builder.setTitle(R.string.UnlockDebug);
                        builder.setMessage(R.string.UnlockDebugSummary);
                        View layoutView = getLayoutInflater().inflate(R.layout.layout_pin_dialog, null, true);
                        final EditText editTextPIN = layoutView.findViewById(R.id.editTextPIN);
                        builder.setView(layoutView);
                        builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String SHA256Hash = "";
                                try {
                                    URL url = new URL("https://droidinfo.app/debug/pin.dinfo");
                                    HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));

                                    String line = null;
                                    while ((line = bufferedReader.readLine()) != null)
                                        SHA256Hash = line;
                                    bufferedReader.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if (SHA256Hash.equals(computeSHAHash(editTextPIN.getText().toString()))) {
                                    switchPreferenceDefaultInformation.setEnabled(true);
                                    IS_UNLOCKED = true;
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(DebugPreferenceFragment.this, R.string.PINIsIncorrect, Toast.LENGTH_SHORT).show();
                                    switchPreferenceDefaultInformation.setEnabled(false);
                                    IS_UNLOCKED = false;
                                    dialog.dismiss();
                                }
                            }
                        });
                        builder.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    } else {
                        switchPreferenceDefaultInformation.setEnabled(false);
                        IS_UNLOCKED = false;
                        Toast.makeText(DebugPreferenceFragment.this, R.string.OptionsLocked, Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
            });

            switchPreferenceDefaultInformation.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    sharedPreferences
                            .edit()
                            .putBoolean(USE_DEFAULT_INFORMATION, switchPreferenceDefaultInformation.isChecked())
                            .apply();
                    return false;
                }
            });
        }

        private String computeSHAHash(String password) {
            MessageDigest messageDigest = null;
            try {
                messageDigest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            messageDigest.reset();
            return bin2hex(messageDigest.digest(password.getBytes()));
        }

        private String bin2hex(byte[] data) {
            return String.format("%0" + (data.length * 2) + "X", new BigInteger(1, data));
        }

        @Override
        protected void onPostCreate(@Nullable Bundle savedInstanceState) {
            super.onPostCreate(savedInstanceState);

            LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
            Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.layout_toolbar, root, false);
            bar.setTitle(R.string.Debug);
            root.addView(bar, 0);
            bar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentBack = new Intent(DebugPreferenceFragment.this, SettingsActivity.class);
                    startActivity(intentBack);
                    finish();
                }
            });
        }

        @Override
        public void onBackPressed() {
            startActivity(new Intent(DebugPreferenceFragment.this, SettingsActivity.class));
            finish();
            super.onBackPressed();
        }
    }
}
