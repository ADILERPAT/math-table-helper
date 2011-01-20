package info.bendtsen.tableHelper;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class TableHelperMain extends Activity {
	private Spinner tableSpinner = null;
	ArrayAdapter<Integer> adapter = null;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initTableSpinner();
		initStartButton();

	}
	private void initStartButton(){
		Button button = (Button)findViewById(R.id.StartButton);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startPressActivity((Integer)tableSpinner.getSelectedItem());
				
			}
		});
	}
	private void initTableSpinner() {
		tableSpinner= (Spinner) findViewById(R.id.TableSpinner);
		List<Integer> items = new ArrayList<Integer>();
		for (int i = 1; i <= 10; i++) {
			items.add(i);

		}
		adapter = new ArrayAdapter<Integer>(this,
				android.R.layout.simple_spinner_item, items);

		tableSpinner.setAdapter(adapter);

	}
	private void startPressActivity(int table){
		Intent intent = new Intent(this, PressActivity.class);
		intent.putExtra("TABLE", table);
		startActivity(intent);
		
	}
}