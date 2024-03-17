package com.example.entrega_primera;
import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import com.example.entrega_primera.R;

import java.util.Locale;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey);

        ListPreference languagePreference = findPreference("language_preference");
        if (languagePreference != null) {
            SharedPreferences preferences = getActivity().getSharedPreferences("my_preferences", MODE_PRIVATE);
            String language = preferences.getString("language_preference", "en");
            languagePreference.setValue(language);
            languagePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    String selectedLanguage = (String) newValue;

                    SharedPreferences preferences = getActivity().getSharedPreferences("my_preferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("language_preference", selectedLanguage);
                    editor.apply();


                    return true;
                }
            });
        } else {
            System.out.println("No se ha encontrado la preferencia");
        }

    }
}