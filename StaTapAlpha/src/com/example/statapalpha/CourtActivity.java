package com.example.statapalpha;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CourtActivity extends Activity {

	// Player class to declare all players as and store their info
	public class player {
		public String firstName = "";
		public String lastName = "";
		public int number = 0;
		public int points = 0;
		public int fouls = 0;
		public int rebounds = 0;
		public int assists = 0;
		public int blocks = 0;
		public int steals = 0;
		public int turnovers = 0;
		public int twoPointMade = 0;
		public int twoPointMiss = 0;
		public int threePointMade = 0;
		public int threePointMiss = 0;
		public int freeThrowMade = 0;
		public int freeThrowMiss = 0;
		
		public void rebound() {
			rebounds++;
		}
		
		public void assist() {
			assists++;
		}
		
		public void block() {
			blocks++;
		}

		
		public void steal() {
			steals++;
		}
		
		public void turnover() {
			turnovers++;
		}
		
		public void foul() {
			fouls++;
		}
	}

	// Array that will hold all the players for both teams
	public player[] homePlayers = new player[25];
	public player[] awayPlayers = new player[25];
	
	int player = 0;
	
	void setPlayer(View v) {
		Button b = (Button)v;
		player = Integer.parseInt(b.getText().toString());
		String message = "You Clicked Player #" + Integer.toString(player);
		Toast.makeText(CourtActivity.this, message, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_court);
	}
}
