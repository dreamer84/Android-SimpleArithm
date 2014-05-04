package com.example.simplearithm;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SettingsActivityForTablet extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Display the fragment as the main content.
		getFragmentManager()
				.beginTransaction()
				.replace(android.R.id.content, new SettingsFragment())
				.commit();
	}
}
