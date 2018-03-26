package gsd.multazam.cataloguemovie.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceFragmentCompat;

import gsd.multazam.cataloguemovie.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends PreferenceFragmentCompat {

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }
}
