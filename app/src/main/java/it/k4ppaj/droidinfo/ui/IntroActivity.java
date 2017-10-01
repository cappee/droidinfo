package it.k4ppaj.droidinfo.ui;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import it.k4ppaj.droidinfo.R;

public class IntroActivity extends AppIntro {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("DroidInfo", MODE_PRIVATE);

        String stringFontType = "Roboto/Roboto-Regular.ttf";

        //Use this: getResources().getColor(intColorChoose)
        int intColorBG = R.color.colorPrimaryDark;

        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.AppName), stringFontType, getResources().getString(R.string.FirstIntroDescription), stringFontType, R.drawable.ic_smartphone_white_24dp, getResources().getColor(intColorBG)));

        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.SecondIntroTitle), stringFontType, getResources().getString(R.string.SecondIntroDescription), stringFontType, R.drawable.ic_live_help_white_24dp, getResources().getColor(intColorBG)));

        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.ThirdIntroTitle), stringFontType, getResources().getString(R.string.ThirdIntroDescription), stringFontType, R.drawable.ic_person_pin_white_24dp, getResources().getColor(intColorBG)));

        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.FourthIntroTitle), stringFontType, getResources().getString(R.string.FourthIntroDescription), stringFontType, R.drawable.ic_developer_mode_white_24dp, getResources().getColor(intColorBG)));

        askForPermissions(new String[] { Manifest.permission.READ_PHONE_STATE }, 4);

        setBarColor(getResources().getColor(R.color.colorPrimaryDark));

        showSkipButton(true);
        showPagerIndicator(true);

        setImageNextButton(getResources().getDrawable(R.drawable.ic_arrow_forward_white_24dp));

        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        sharedPreferences.edit().putBoolean("FIRST_RUN", false).apply();
        startActivity(new Intent(IntroActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        sharedPreferences.edit().putBoolean("FIRST_RUN", false).apply();
        startActivity(new Intent(IntroActivity.this, MainActivity.class));
        finish();
    }
}
