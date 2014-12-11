package com.example.statapalpha;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.*;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.statapalpha.R;

public class NewGameActivity extends Activity {
	EditText title;
	TextView team1, team2;
	SqliteHelper db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newgame);
		//ListViews
		team1 = (TextView) findViewById(R.id.textView4);
		team2 = (TextView) findViewById(R.id.textView5);
		title = (EditText) findViewById(R.id.editText1);
		db = new SqliteHelper(this.getApplicationContext());
		populateListViews();
		registerClickCallback();
		//Database TextField Initialization
		//EditText editName = (EditText)findViewById(R.id.editText1);
		//Button btnConfirm = (Button)findViewById(R.id.button2);
		SQLiteDatabase db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
		
	}

	/*
	 * 
	 * Database Functions
	 * 
	 */
	public void confirm() {
		String t1, t2, gTitle;
		t1 = team1.toString();
		t2 = team2.toString();
		gTitle = title.toString();
		
		Intent intent = new Intent(this, CourtActivity.class);
    	intent.putExtra("TEAM1", t1);
    	intent.putExtra("TEAM2", t2);
    	intent.putExtra("GAME_TITLE", gTitle);
    	startActivity(intent);
		
	}
	
	
	
	
	/*
	 * 
	 * ListView Stuff
	 * 
	 */
	private void populateListViews() {
		// THIS HERE WILL POPULATE BOTH TEAM LIST VIEWS
    	//Create list of items
		Cursor cursor = db.getTeams();
		
		ArrayList<String> values = new ArrayList<String>();
		if (cursor != null && cursor.getCount() != 0) {
		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {

		        values.add(cursor.getString(cursor.getColumnIndex("Team_Names")));

		        cursor.moveToNext();
		    }
		}    	
    	//Build Adapter
    	ArrayAdapter<String> t1adapter = new ArrayAdapter<String>(
    			this,					// Context
    			R.layout.teamlistviews,		// Layout to use
    			values);				// Items to be displayed
    	ArrayAdapter<String> t2adapter = new ArrayAdapter<String>(
    			this,					// Context
    			R.layout.teamlistviews,		// Layout to use
    			values);		
    	//Configure the List View
    	ListView t1list = (ListView) findViewById(R.id.listView1);
    	t1list.setAdapter(t1adapter);
    	ListView t2list = (ListView) findViewById(R.id.listView2);
    	t2list.setAdapter(t2adapter);
	}
    private void registerClickCallback() {
    	//This uses the List View and adds a listener to check for clicks/taps on different
    	//list view items. It will then display a message telling you which one you have selected.
    	ListView t1list = (ListView) findViewById(R.id.listView1);
    	ListView t2list = (ListView) findViewById(R.id.listView2);
    	t1list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {
				//team 1
					TextView textView = (TextView) viewClicked;
					
					//Changing team name
			    	TextView t1 = (TextView) findViewById(R.id.textView4);
					String team1 = textView.getText().toString();
					t1.setText(team1);
					//Toast message
					String message = "You Selected " + textView.getText().toString() + " for Team 1";
					Toast.makeText(NewGameActivity.this, message, Toast.LENGTH_SHORT).show();
			}
			
		});
    	t2list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {
				//team 2
					TextView textView = (TextView) viewClicked;
					//Changing Team name
					TextView t2 = (TextView) findViewById(R.id.textView5);
					String team2 = textView.getText().toString();
					t2.setText(team2);
					//Toast message
					String message = "You Selected " + textView.getText().toString() + " for Team 2";
					Toast.makeText(NewGameActivity.this, message, Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
