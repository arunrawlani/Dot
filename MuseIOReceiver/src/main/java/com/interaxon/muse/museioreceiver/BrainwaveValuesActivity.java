package com.interaxon.muse.museioreceiver;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.interaxon.muse.museioreceiver.MuseIOReceiver.MuseConfig;
import com.interaxon.muse.museioreceiver.MuseIOReceiver.MuseDataListener;

import java.io.IOException;

/**
 * This activity is meant to display the alpha, beta, theta and delta values
 * from only one headband.
 */
public class BrainwaveValuesActivity extends Activity implements
		MuseDataListener {

	private MuseIOReceiver museReceiver;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.main);

		this.museReceiver = new MuseIOReceiver(5005, true);
		this.museReceiver.registerMuseDataListener(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		try {
			this.museReceiver.connect();
			Log.i("connection established","connection established");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		this.museReceiver.disconnect();
	}

	@Override
	public void receiveMuseElementsAlpha(MuseConfig config, final float[] alpha) {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				((TextView) BrainwaveValuesActivity.this
						.findViewById(R.id.alpha_ch1)).setText(String.format(
						"%.2f", alpha[0]));
				((TextView) BrainwaveValuesActivity.this
						.findViewById(R.id.alpha_ch2)).setText(String.format(
						"%.2f", alpha[1]));
				((TextView) BrainwaveValuesActivity.this
						.findViewById(R.id.alpha_ch3)).setText(String.format(
						"%.2f", alpha[2]));
				((TextView) BrainwaveValuesActivity.this
						.findViewById(R.id.alpha_ch4)).setText(String.format(
						"%.2f", alpha[3]));
			}
		});
	}

	@Override
	public void receiveMuseElementsBeta(MuseConfig config, final float[] beta) {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				((TextView) BrainwaveValuesActivity.this
						.findViewById(R.id.beta_ch1)).setText(String.format(
						"%.2f", beta[0]));
				((TextView) BrainwaveValuesActivity.this
						.findViewById(R.id.beta_ch2)).setText(String.format(
						"%.2f", beta[1]));
				((TextView) BrainwaveValuesActivity.this
						.findViewById(R.id.beta_ch3)).setText(String.format(
						"%.2f", beta[2]));
				((TextView) BrainwaveValuesActivity.this
						.findViewById(R.id.beta_ch4)).setText(String.format(
						"%.2f", beta[3]));
			}
		});
	}

	@Override
	public void receiveMuseElementsTheta(MuseConfig config, final float[] theta) {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				((TextView) BrainwaveValuesActivity.this
						.findViewById(R.id.theta_ch1)).setText(String.format(
						"%.2f", theta[0]));
				((TextView) BrainwaveValuesActivity.this
						.findViewById(R.id.theta_ch2)).setText(String.format(
						"%.2f", theta[1]));
				((TextView) BrainwaveValuesActivity.this
						.findViewById(R.id.theta_ch3)).setText(String.format(
						"%.2f", theta[2]));
				((TextView) BrainwaveValuesActivity.this
						.findViewById(R.id.theta_ch4)).setText(String.format(
						"%.2f", theta[3]));
			}
		});
	}

	@Override
	public void receiveMuseElementsDelta(MuseConfig config, final float[] delta) {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				((TextView) BrainwaveValuesActivity.this
						.findViewById(R.id.delta_ch1)).setText(String.format(
						"%.2f", delta[0]));
				((TextView) BrainwaveValuesActivity.this
						.findViewById(R.id.delta_ch2)).setText(String.format(
						"%.2f", delta[1]));
				((TextView) BrainwaveValuesActivity.this
						.findViewById(R.id.delta_ch3)).setText(String.format(
						"%.2f", delta[2]));
				((TextView) BrainwaveValuesActivity.this
						.findViewById(R.id.delta_ch4)).setText(String.format(
						"%.2f", delta[3]));
			}
		});
	}

	@Override
	public void receiveMuseEeg(MuseConfig config, float[] eeg) {
		// Do nothing
	}

	@Override
	public void receiveMuseAccel(MuseConfig config, float[] accel) {
		// Do nothing		
	}

	@Override
	public void receiveMuseBattery(MuseConfig config, int[] battery) {
		// Do nothing
	}

}
