package com.example.fitlife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private View view;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean isAppLockEnabled;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Get the shared preferences object
        sharedPreferences = getActivity().getSharedPreferences("FitLifePrefs", 0);

        // Initialize the editor
        editor = sharedPreferences.edit();

        // Check if app lock is enabled or not
        isAppLockEnabled = sharedPreferences.getBoolean("isAppLockEnabled", false);

        // Initialize the weight and height radio groups
        RadioGroup weightUnitRadioGroup = view.findViewById(R.id.weight_unit_radio_group);
        RadioGroup heightUnitRadioGroup = view.findViewById(R.id.height_unit_radio_group);

        // Set the weight unit radio button based on shared preferences
        if (sharedPreferences.getString("weightUnit", "kg").equals("kg")) {
            weightUnitRadioGroup.check(R.id.kg_radio_button);
        } else {
            weightUnitRadioGroup.check(R.id.lbs_radio_button);
        }

        // Set the height unit radio button based on shared preferences
        if (sharedPreferences.getString("heightUnit", "cm").equals("cm")) {
            heightUnitRadioGroup.check(R.id.cm_radio_button);
        } else {
            heightUnitRadioGroup.check(R.id.in_radio_button);
        }

        // Set the app lock button text based on shared preferences
        Button appLockButton = view.findViewById(R.id.app_lock_button);
        if (isAppLockEnabled) {
            appLockButton.setText("Disable App Lock");
        } else {
            appLockButton.setText("Enable App Lock");
        }

        // Set the click listener for the app lock button
        appLockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAppLockEnabled = !isAppLockEnabled;
                if (isAppLockEnabled) {
                    appLockButton.setText("Disable App Lock");
                    Toast.makeText(getActivity(), "App Lock Enabled", Toast.LENGTH_SHORT).show();
                } else {
                    appLockButton.setText("Enable App Lock");
                    Toast.makeText(getActivity(), "App Lock Disabled", Toast.LENGTH_SHORT).show();
                }
                // Save the app lock status to shared preferences
                editor.putBoolean("isAppLockEnabled", isAppLockEnabled);
                editor.apply();
            }
        });

        // Set the click listener for the logout button
        Button logoutButton = view.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the shared preferences
                editor.clear();
                editor.apply();
                // Launch the login activity
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

}