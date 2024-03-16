package com.example.entrega_primera;
import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import com.example.entrega_primera.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey);
    }
}