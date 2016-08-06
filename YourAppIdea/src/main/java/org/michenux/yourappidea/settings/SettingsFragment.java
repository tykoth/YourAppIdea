package org.michenux.yourappidea.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import org.michenux.yourappidea.BuildConfig;
import org.michenux.yourappidea.R;
import org.michenux.yourappidea.YourApplication;
import org.michenux.yourappidea.tutorial.sync.TutorialSyncHelper;

import javax.inject.Inject;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Inject
    TutorialSyncHelper mTutorialSyncHelper;

    @Override
    public void onCreatePreferences(Bundle bundle, String rootKey) {
        ((YourApplication) getActivity().getApplication()).inject(this);
        addPreferencesFromResource(R.xml.preferences);
        PreferenceManager preferenceManager = getPreferenceManager();
        preferenceManager.getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("notificationPref")) {
            if (sharedPreferences.getBoolean(key, true)) {
                if (BuildConfig.DEBUG) {
                    Log.d(YourApplication.LOG_TAG, "settings notificationPref changed: addPeriodicSync()");
                }
                mTutorialSyncHelper.enablePeriodicSync(this.getActivity());
            } else {
                if (BuildConfig.DEBUG) {
                    Log.d(YourApplication.LOG_TAG, "settings notificationPref changed: removePeriodicSync()");
                }
                mTutorialSyncHelper.removePeriodicSync();
            }
        }
    }
}
