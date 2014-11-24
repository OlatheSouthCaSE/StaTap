package com.example.statapalpha;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class SqliteHelper extends SQLiteOpenHelper {
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "StaTap";
    private static final String TEAM_NAMES = "Team_Names";
    private static final String TEAM_NUM = "Team_Nbr";
    private static final String JERSEY_NUM = "Jersey_nbr";
    private static final String LAST_NAME = "last_name";
    private static final String FIRST_NAME = "first_name";
    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
    	// Create Team Table
		db.execSQL("CREATE TABLE IF NOT EXISTS teamlist(id INTEGER PRIMARY KEY AUTOINCREMENT,Team_Names TEXT UNIQUE);");

		// Create player table
		db.execSQL("CREATE TABLE IF NOT EXISTS player(Team_nbr INTEGER, Jersey_nbr INTEGER UNIQUE PRIMARY KEY, first_name TEXT, last_name TEXT);");
		
		
		//Create Games Table
		db.execSQL("CREATE TABLE IF NOT EXISTS games(id INTEGER PRIMARY KEY AUTOINCREMENT, team1 INTEGER, team2 INTEGER, game_name TEXT)");
		
		// Create stats table
		db.execSQL("CREATE TABLE IF NOT EXISTS stats(play_id INTEGER PRIMARY KEY AUTOINCREMENT, game_id INTEGER, Jersey_nbr INTEGER, team_nbr INTEGER, " +
				"half_nbr INTEGER, action TEXT, x_coord INTEGER, y_coord INTEGER);");
    }
    public Cursor getTeams() {
    	SQLiteDatabase db = this.getWritableDatabase();
    	String[] projection = { "Team_Names" };
    	return db.query("teamlist",projection, null, null, null, null, null, null);
    	
    }
    public Cursor getPlayers() {
    	SQLiteDatabase db = this.getWritableDatabase();
    	String[] projection = { "Team_Names" };
    	return db.query("teamlist",projection, null, null, null, null, null, null);
    	
    }
    public void addPlayer(Integer teamnum, Integer jerseynum, String firstname, String lastname){

    	// 1. get reference to writable DB
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	// 2. create ContentValues to add key "column"/value
    	ContentValues values = new ContentValues();
    	values.put(TEAM_NUM, teamnum);
    	values.put(JERSEY_NUM, jerseynum);
    	values.put(FIRST_NAME, firstname);
    	values.put(LAST_NAME, lastname.toString()); // get title 

    	// 3. insert
    	db.insert("player", // table name
    	null, //nullColumnHack
    	values); // key/value -> keys = column names/ values = column values

    	// 4. close
    	db.close(); 
    }
    public void addTeam(String teamname){

    	// 1. get reference to writable DB
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	// 2. create ContentValues to add key "column"/value
    	ContentValues values = new ContentValues();
    	values.put(TEAM_NAMES, teamname.toString()); // get title 

    	// 3. insert
    	db.insert("teamlist", // table name
    	null, //nullColumnHack
    	values); // key/value -> keys = column names/ values = column values

    	// 4. close
    	db.close(); 
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
 
        // create fresh books table
        this.onCreate(db);
    }
    
    // For getting player info
    public Cursor getPlayerNumber() {
    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	String[] projection = { "stat_nbr" };
    	return db.query("player",projection, null, null, null, null, null, null);
    }
    
    // For updating player stats
    public void recordPlay(int player, String action, CourtActivity.position position, int playNumber) {
    	
    	// 1. get reference to writable DB
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	// 2. create ContentValues to add key "column"/value
    	ContentValues values = new ContentValues();
    	values.put("jersey_nbr", player); // get title 
    	values.put("action", action);
    	values.put("x_coord", position.x);
    	values.put("y_coord", position.y);
    	values.put("stat_nbr", playNumber);
    	
    	// 3. insert
    	db.insert("stats", // table name
    	null, //nullColumnHack
    	values); // key/value -> keys = column names/ values = column values
    	
    	
    	// 4. close
    	db.close(); 
    }
}