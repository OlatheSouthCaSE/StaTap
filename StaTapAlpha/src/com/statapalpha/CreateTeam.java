package com.statapalpha;

import java.util.ArrayList;

import com.example.statapalpha.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View.OnKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
//import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.database.Cursor;
//ignore
public class CreateTeam extends Activity {
	EditText editTeam;
	SqliteHelper db;
	ListView lv;
	//TEST
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_team);
		//Test
		editTeam = (EditText)findViewById(R.id.editTeam);
		editTeam.setOnKeyListener(new OnKeyListener() {
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		        // If the event is a key-down event on the "enter" button
		        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
		            (keyCode == KeyEvent.KEYCODE_ENTER)) {
		          // Perform action on key press
		        	create(v);
		          return true;
		        }
		        return false;
		    }
		});
		lv = (ListView)findViewById(R.id.listView1);
		//End
		//Database Text Box Initialization
		db = new SqliteHelper(this.getApplicationContext());
		//Button create = (Button)findViewById(R.id.create);


		populateListViews();
		registerClickCallback();
		registerForContextMenu(lv);
		//DB
	}
	public void onBackPressed() {
		//DO NOTHING
		Intent intent = new Intent(this, HomeScreen.class);
		finish();
		startActivity(intent);
	}
	public void create(View view) {
		
		
		if(editTeam.getText().toString().trim().length()==0) {
			String errormessage = "Error: Team name cannot be blank";
			Toast.makeText(CreateTeam.this, errormessage, Toast.LENGTH_SHORT).show();
		      return;
		}
		
		String Team_Name = editTeam.getText().toString();
		if (Team_Name.matches("[a-zA-Z ]*")) {
		String message = "Team " + editTeam.getText().toString() + " was added to the database";
		editTeam.setText("");
		db.addTeam(Team_Name);
		
		Toast.makeText(CreateTeam.this, message, Toast.LENGTH_SHORT).show();
		populateListViews();
		} else {
			Toast.makeText(CreateTeam.this, "Invalid characters in Team Name", Toast.LENGTH_SHORT).show();
		}
	}
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    	super.onCreateContextMenu(menu, v, menuInfo);
    	if (v.getId()==R.id.listView1) {
    		MenuInflater inflater = getMenuInflater();
    		inflater.inflate(R.menu.home_context_menu, menu);
    	}
}
	
	/*
	 * 
	 * ListViews
	 * 
	 */
	//long press
	
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.delete:
          // remove stuff here
            	//TOOK THIS OFF FOR NOW. CAN'T DELETE TEAMS BECAUSE IT FUCKS WITH THE REST
            Integer Position = ((AdapterView.AdapterContextMenuInfo)info).position;
            Object obj = lv.getItemAtPosition(Position);
            String teamname2 = obj.toString();
            deleteTeam(teamname2);
            return true;
            default:
                  return super.onContextItemSelected(item);
        }
  }
    public void deleteTeam(String teamname2) {
    	String teamname;
    	teamname = teamname2;
    	db.delTeam(teamname);
    	populateListViews();
    }
    //single tap
    private void registerClickCallback() {
    	ListView list = (ListView) findViewById(R.id.listView1);
    	list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    		
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {
					
					TextView textView = (TextView) viewClicked; 
					//Toast message
					String teamname = textView.getText().toString();
					editTeam(teamname, null);
			}
		});
   	}
    public void editTeam(String teamname, View view) {
    	Intent intent = new Intent(this, EditTeam.class);
    	intent.putExtra("TEAM_NAME", teamname);
    	startActivity(intent);
    }
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
    			R.layout.teamlistviews,	// Layout to use
    			values);				// Items to be displayed		
    	//Configure the List View
    	ListView t1list = (ListView) findViewById(R.id.listView1);
    	t1list.setAdapter(t1adapter);

    	cursor.close();
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_team, menu);
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
