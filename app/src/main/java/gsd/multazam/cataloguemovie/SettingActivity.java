package gsd.multazam.cataloguemovie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import gsd.multazam.cataloguemovie.util.AlarmReceiver;
import gsd.multazam.cataloguemovie.util.SchedulerTask;

public class SettingActivity extends AppCompatActivity {
    private AlarmReceiver alarmReceiver = new AlarmReceiver();
    private SchedulerTask schedulerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefFragment()).commit();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @SuppressLint("ValidFragment")
    public class PrefFragment extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

        String rdaily = getResources().getString(R.string.key_reminder_daily);
        String rup = getResources().getString(R.string.key_reminder_upcoming);
        String klocale = getResources().getString(R.string.key_setting_locale);

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            findPreference(rdaily).setOnPreferenceChangeListener(this);
            findPreference(rup).setOnPreferenceChangeListener(this);
            findPreference(klocale).setOnPreferenceClickListener(this);
            schedulerTask = new SchedulerTask(getActivity());
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {
            String key = preference.getKey();
            boolean isOn = (boolean) o;
            if (key.equals(rdaily)) {
                if (isOn) {
                    alarmReceiver.setRepeatingAlarm(getActivity(), AlarmReceiver.TYPE_REPEATING, "07:00", "Saved");
                } else {
                    alarmReceiver.cancelAlarm(getActivity(), AlarmReceiver.TYPE_REPEATING);
                }
                Toast.makeText(SettingActivity.this, "OK", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (key.equals(rup)) {
                if (isOn) {
                    schedulerTask.createPeriodicTask();
                } else schedulerTask.cancelPeriodicTask();

                Toast.makeText(SettingActivity.this, "OK", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            String key = preference.getKey();
            if (key.equals(klocale)) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                return true;
            }
            return false;
        }
    }
}
