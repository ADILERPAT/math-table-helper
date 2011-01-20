package info.bendtsen.tableHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PressActivity extends Activity {
	private List<Integer> numbers = new ArrayList<Integer>();

	class CounterTask extends AsyncTask<Integer, Integer, Void> {

		@Override
		protected Void doInBackground(Integer... params) {

			for (Integer i : numbers) {
				try {
					if (i % params[0] == 0) {
						missed.add(String.valueOf(i));
					}
					publishProgress(i);
					Thread.sleep(750);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			Toast.makeText(getApplicationContext(), "Errors: " + error.size(),
					Toast.LENGTH_SHORT).show();
			Toast.makeText(getApplicationContext(), "Missed: " + missed.size(),
					Toast.LENGTH_SHORT).show();
			if(!SHUFFLE.equals(action)){
				next.setVisibility(View.VISIBLE);
			}
			number.setVisibility(View.GONE);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			number.setTextColor(Color.BLACK);
			number.setText(String.valueOf(values[0]));

		}

	}

	private Button number;

	private boolean started = false;
	private List<String> missed = new ArrayList<String>();
	private List<String> error = new ArrayList<String>();
	private static final String SHUFFLE = "SHUFFLE";
	private static final String REVERSE = "REVERSE";
	private Button next = null;
	private String action = null;
	private int table = 0;
	private void init() {
		started = false;
		number.setText(R.string.press_number);
		number.setTextColor(Color.BLACK);
		number.setVisibility(View.VISIBLE);
		missed = new ArrayList<String>();
		error = new ArrayList<String>();
		numbers = new ArrayList<Integer>();
		for (int i = 1; i <= table * 10 + 1; i++) {
			numbers.add(i);
		}

		if (SHUFFLE.equals(action)) {
			Collections.shuffle(numbers);
			next.setVisibility(View.GONE);
			TextView tv = (TextView) findViewById(R.id.press_heading);
			String txt = getText(R.string.press_heading).toString();
			txt += "(" + this.getText(R.string.random) + ")";
			tv.setText(txt);
		} else if (REVERSE.equals(action)) {
			Collections.reverse(numbers);
			TextView tv = (TextView) findViewById(R.id.press_heading);
			String txt = tv.getText().toString();
			txt += "(" + this.getText(R.string.reverse) + ")";
			tv.setText(txt);
		}
		if (REVERSE.equals(action) || !SHUFFLE.equals(action)) {
			next.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getApplicationContext(),
							PressActivity.class);
					if (REVERSE.equals(action)) {
						action = SHUFFLE;
					} else {
						action = REVERSE;
					}
					next.setVisibility(View.GONE);
					init();
					

				}
			});
		}
		number.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!started) {
					started = true;
					new CounterTask().execute(table);

				} else {

					String val = number.getText().toString();
					missed.remove(val);
					int iVal = Integer.valueOf(val);
					if (iVal % table != 0) {
						number.setTextColor(Color.RED);
						error.add(val);

					}

				}

			}
		});

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.press);
		number = (Button) findViewById(R.id.press_number);
		next = (Button) findViewById(R.id.press_next);
		next.setVisibility(View.GONE);
		table = getIntent().getExtras().getInt("TABLE");
	
		init();
	}

}
