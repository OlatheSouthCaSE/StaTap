package com.statapalpha;
 
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
@SuppressLint("DefaultLocale") 
public class SqliteHelper extends SQLiteOpenHelper {
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "StaTap";
    private static final String TEAM_NAMES = "Team_Names";
    private static final String GAME_TITLES = "game_name";
    private static final String JERSEY_NUM = "Jersey_num";
    private static final String LAST_NAME = "last_name";
    private static final String FIRST_NAME = "first_name";
    private static final String TEAM_1 = "team1";
    private static final String TEAM_2 = "team2";
    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
    	// Create Teamlist Table
    	
		db.execSQL("CREATE TABLE IF NOT EXISTS teams(id INTEGER PRIMARY KEY AUTOINCREMENT,Team_Names TEXT UNIQUE);");
		
		//Create Games Table
		db.execSQL("CREATE TABLE IF NOT EXISTS games(id INTEGER PRIMARY KEY AUTOINCREMENT, game_name TEXT, team1 TEXT, team2 TEXT, q1T INTEGER, q2T INTEGER, q3T INTEGER, q4T INTEGER)");
		
		
		
    }
    @SuppressLint("DefaultLocale") public void delGame(String gamenamen, String team1n, String team2n) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	String command1, command2, gamename;
    	gamename = gamenamen.replaceAll(" ", "_").toLowerCase();
    	command1 = "DELETE FROM games WHERE game_name = '"+gamename+"'";
    	command2 = "DROP TABLE IF EXISTS "+gamename;
    	db.execSQL(command1);
    	db.execSQL(command2);
    	
    }
    public void updateQuarterTime(String gamename, long seconds, int quarter) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	int time = (int) seconds;
    	String command = "UPDATE games SET q" + quarter + "T=" + time + " WHERE game_name = '"+gamename+"'";
    	db.execSQL(command);
    }
    public int getGames() {
    	SQLiteDatabase db = this.getReadableDatabase();
    	int games = 0;
    	String command = "SELECT count(*) from games";
    	Cursor cursor = db.rawQuery(command, null);
    	if(cursor.moveToFirst()) {
    		games = cursor.getInt(0);
    	}
    	cursor.close();
    	return games;
    }
    public String getGameTitle(int gid) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	onCreate(db);
    	String command, title;
    	title = "Didn't work";
    	Cursor cursor;
    	command = "SELECT game_name FROM games WHERE id = "+gid;
    	cursor = db.rawQuery(command,  null);
    	if(cursor.moveToFirst()) {
    		title = cursor.getString(0);
    	}
    	cursor.close();
    	return title;
    }
    public Cursor getQuarterTimes(String gamename) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	onCreate(db);
    	Cursor cursor;
    	String command = "SELECT q1T, q2T, q3T, q4T FROM games WHERE game_name = '"+gamename+"'";
    	cursor = db.rawQuery(command, null);
    	return cursor;
    }
    public int duplicateGame(String gamename) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	int number = 0;
    	String command = "SELECT count(*) FROM games WHERE game_name = '"+gamename+"'";
    	Cursor cursor = db.rawQuery(command, null);
    	if(cursor.moveToFirst()) {
    		number = cursor.getInt(0);
    	}
    	cursor.close();
    	return number;
    }
    @SuppressLint("DefaultLocale") public int countPlayers(String teamname) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	int players = 0;
    	String teamLower = teamname.replaceAll(" ", "_").toLowerCase();
    	String command = "SELECT count(*) FROM "+teamLower;
    	Cursor cursor = db.rawQuery(command, null);
    	if(cursor.moveToFirst()) {
    		players = cursor.getInt(0);
    	}
    	cursor.close();
    	return players;
    }
    public String getGameT1(int gid) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	onCreate(db);
    	String command, title;
    	title = "Didn't work";
    	Cursor cursor;
    	command = "SELECT team1 FROM games WHERE id = "+gid;
    	cursor = db.rawQuery(command,  null);
    	if(cursor.moveToFirst()) {
    		title = cursor.getString(0);
    	}
    	cursor.close();
    	return title;
    }
    public String getGameT2(int gid) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	onCreate(db);
    	String command, title;
    	title = "Didn't work";
    	Cursor cursor;
    	command = "SELECT team2 FROM games WHERE id = "+gid;
    	cursor = db.rawQuery(command,  null);
    	if(cursor.moveToFirst()) {
    		title = cursor.getString(0);
    	}
    	cursor.close();
    	return title;
    }

    public int getStats(String action, Integer jnum, String teamname, String tablename) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	String command;
    	Cursor cursor;
    	int number = 99;
    	command = "SELECT count(action) FROM "+tablename+" WHERE Jersey_num = "+jnum+" AND action = '"+action+"' AND team_name = '"+teamname+"'";
    	cursor = db.rawQuery(command, null);
    	if(cursor.moveToFirst()){
    		number = cursor.getInt(0);
    	}
    	cursor.close();
    	return number;
    }
    
    public Cursor getTeams() {
    	SQLiteDatabase db = this.getWritableDatabase();
    	String[] projection = { "Team_Names" };
    	return db.query("teams",projection, null, null, null, null, null, null);
    }
    public Cursor getGameIDs() {
    	SQLiteDatabase db = this.getWritableDatabase();
    	onCreate(db);
    	String[] projection = { "id" };
    	return db.query("games",projection, null, null, null, null, null, null);
    }
    public Cursor getPlayerJNums(String teamname) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	String[] projection = { "jersey_num" };
    	return db.query(teamname,projection, null, null, null, null, null, null);
    	
    }
    public String getPlayerFName(Integer jnum, String teamname) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor;
    	String fname ="";
    	cursor = db.rawQuery("SELECT first_name FROM "+teamname+" WHERE jersey_num = "+jnum, null);
    	if(cursor.moveToFirst()){
    		fname = cursor.getString(0);
    	}
    	cursor.close();
    	return fname;
    }
    public String getPlayerLName(Integer jnum, String teamname) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor;
    	String lname ="";
    	cursor = db.rawQuery("SELECT last_name FROM "+teamname+" WHERE jersey_num = "+jnum, null);
    	if(cursor.moveToFirst()){
    		lname = cursor.getString(0);
    	}
    	cursor.close();
    	return lname;
    }
    public void addPlayer(String teamname, Integer jerseynum, String firstname, String lastname){

    	// 1. get reference to writable DB
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	// 2. create ContentValues to add key "column"/value
    	ContentValues values = new ContentValues();
    	values.put(JERSEY_NUM, jerseynum);
    	values.put(FIRST_NAME, firstname.toString());
    	values.put(LAST_NAME, lastname.toString()); // get title 

    	// 3. insert
    	db.insert(teamname, // table name
    	null, //nullColumnHack
    	values); // key/value -> keys = column names/ values = column values

    	// 4. close
    	
    	db.close(); 
    }
    @SuppressLint("DefaultLocale") public Cursor getJNums(String teamname) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	String teamname2;
    	teamname2 = teamname.replaceAll(" ", "_").toLowerCase();
    	String command = "SELECT jersey_num FROM "+teamname2;
    	return db.rawQuery(command, null);
    }
    public int getFouls(Integer jnum, String teamname, String tablename) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor;
    	String command1;
    	int Fouls = 0;
    	command1 = "SELECT count(action) FROM "+tablename+" WHERE Jersey_num = "+jnum+" AND action = 'FC' AND team_name = '"+teamname+"'";
    	cursor = db.rawQuery(command1,  null);
    	if(cursor.moveToFirst()){
    	    Fouls = cursor.getInt(0);
    	}
    	cursor.close();
    	return Fouls;
    }
    public int getTPA(Integer jnum, String teamname, String tablename) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	int number = 0, FTH = 0, FTM = 0;
    	String command1, command2;
    	Cursor cursor1, cursor2;
    	command1 = "SELECT count(*) FROM "+tablename+" WHERE Jersey_num = "+jnum+" AND action = 'F3H' AND team_name = '"+teamname+"'";
    	command2 = "SELECT count(*) FROM "+tablename+" WHERE Jersey_num = "+jnum+" AND action = 'F3M' AND team_name = '"+teamname+"'";
    	cursor1 = db.rawQuery(command1, null);
    	cursor2 = db.rawQuery(command2, null);
    	if(cursor1.moveToFirst()){
    	    FTH = cursor1.getInt(0);
    	}
    	if(cursor2.moveToFirst()){
    	    FTM = cursor2.getInt(0);
    	}
    	cursor1.close();
    	cursor2.close();
    	number = FTH+FTM;
    	return number;
    }
    public int getFGA(Integer jnum, String teamname, String tablename) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	int number = 0, FTH = 0, FTM = 0;
    	String command1, command2;
    	Cursor cursor1, cursor2;
    	command1 = "SELECT count(*) FROM "+tablename+" WHERE Jersey_num = "+jnum+" AND action = 'F2H' AND team_name = '"+teamname+"'";
    	command2 = "SELECT count(*) FROM "+tablename+" WHERE Jersey_num = "+jnum+" AND action = 'F2M' AND team_name = '"+teamname+"'";
    	cursor1 = db.rawQuery(command1, null);
    	cursor2 = db.rawQuery(command2, null);
    	if(cursor1.moveToFirst()){
    	    FTH = cursor1.getInt(0);
    	}
    	if(cursor2.moveToFirst()){
    	    FTM = cursor2.getInt(0);
    	}
    	cursor1.close();
    	cursor2.close();
    	number = FTH+FTM;
    	return number;
    }
    public int getFTA(Integer jnum, String teamname, String tablename) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	int number = 0, FTH = 0, FTM = 0;
    	String command1, command2;
    	Cursor cursor1, cursor2;
    	command1 = "SELECT count(*) FROM "+tablename+" WHERE Jersey_num = "+jnum+" AND action = 'FTH' AND team_name = '"+teamname+"'";
    	command2 = "SELECT count(*) FROM "+tablename+" WHERE Jersey_num = "+jnum+" AND action = 'FTM' AND team_name = '"+teamname+"'";
    	cursor1 = db.rawQuery(command1, null);
    	cursor2 = db.rawQuery(command2, null);
    	if(cursor1.moveToFirst()){
    	    FTH = cursor1.getInt(0);
    	}
    	if(cursor2.moveToFirst()){
    	    FTM = cursor2.getInt(0);
    	}
    	cursor1.close();
    	cursor2.close();
    	number = FTH+FTM;
    	return number;
    }
    public int getPoints(Integer jnum, String teamname, String tablename) {
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	int twopt, threept, points;
    	int rawtwopt = 0;
    	int rawthreept = 0;
    	int rawft = 0;
    	String command1, command2, command3;
    	Cursor cursor1, cursor2, cursor3;
    	command1 = "SELECT count(*) FROM "+tablename+" WHERE Jersey_num = "+jnum+" AND action = 'F2H' AND team_name = '"+teamname+"'";
    	command2 = "SELECT count(*) FROM "+tablename+" WHERE Jersey_num = "+jnum+" AND action = 'F3H' AND team_name = '"+teamname+"'";
    	command3 = "SELECT count(*) FROM "+tablename+" WHERE Jersey_num = "+jnum+" AND action = 'FTH' AND team_name = '"+teamname+"'";
    	cursor1 = db.rawQuery(command1, null);
    	cursor2 = db.rawQuery(command2, null);
    	cursor3 = db.rawQuery(command3, null);
    	if(cursor1.moveToFirst()){
    	    rawtwopt = cursor1.getInt(0);
    	}
    	if(cursor2.moveToFirst()){
    	    rawthreept = cursor2.getInt(0);
    	}
    	if(cursor3.moveToFirst()){
    	    rawft = cursor3.getInt(0);
    	}
    	twopt = (rawtwopt * 2);
    	threept = (rawthreept * 3);
    	points = (twopt + threept + rawft);
    	cursor1.close();
    	cursor2.close();
    	cursor3.close();
    	return points;
    }
    public void addTeam(String teamname){

    	// 1. get reference to writable DB
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	// 2. create ContentValues to add key "column"/value
    	ContentValues values = new ContentValues();
    	values.put(TEAM_NAMES, teamname.toString()); // get title 
    	String teamname2;
    	teamname2 = teamname.replaceAll(" ", "_").toLowerCase();
    	db.execSQL("CREATE TABLE IF NOT EXISTS "+teamname2+"(jersey_num INTEGER PRIMARY KEY UNIQUE, first_name TEXT, last_name TEXT)");
    	// 3. insert
    	db.insert("teams", // table name
    	null, //nullColumnHack
    	values); // key/value -> keys = column names/ values = column values
    	
    	// 4. close
    	db.close(); 
    }
    public void createGame(String gamename, String team1, String team2, int time) {
    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	onCreate(db);
    	ContentValues values = new ContentValues();
    	values.put(GAME_TITLES, gamename);
    	values.put(TEAM_1, team1);
    	values.put(TEAM_2, team2);
    	values.put("q1T", time);
    	values.put("q2T", time);
    	values.put("q3T", time);
    	values.put("q4T", time);
    	db.insert("games", // table name
    	    	null, //nullColumnHack
    	    	values);
    	db.close();  
    }
    public void delTeam(String teamname) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	String teamname2;
    	teamname2 = teamname.replaceAll(" ",  "_").toLowerCase();
    	db.execSQL("DROP TABLE IF EXISTS "+teamname2);
    	db.execSQL("DELETE FROM teams WHERE Team_Names = '"+teamname+"'");
    	db.execSQL("DELETE FROM games WHERE team1 = '"+teamname+"'");
    	db.execSQL("DELETE FROM games WHERE team2 = '"+teamname+"'");
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
    	String[] projection = { "stat_num" };
    	return db.query("player",projection, null, null, null, null, null, null);
    }
    
    // For updating player stats
    public int getGameId() {
    	SQLiteDatabase db = this.getWritableDatabase();    	
    	onCreate(db);
    	Cursor cursor;
    	int gameId = 9;
    	cursor = db.rawQuery("SELECT count(*) FROM games", null);
    	if(cursor.moveToFirst()){
    	    gameId = cursor.getInt(0);
    	}
    	cursor.close();
    	return gameId;
    }
    public void delPlayer(int jnum, String teamname) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.execSQL("DELETE FROM "+teamname+" WHERE jersey_num = "+jnum);
    	db.close();  
    }
    public void createStatTable(String gamename) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.execSQL("CREATE TABLE IF NOT EXISTS "+gamename+"(play_id INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT, game_id INTEGER, Jersey_num INTEGER, team_name TEXT, " +
				"half_num INTEGER, action TEXT, x_coord INTEGER, y_coord INTEGER, time INTEGER)");
    	db.close();  
    }
    public Cursor grabPlays(String gamename) {
    	Cursor cursor;
    	SQLiteDatabase db = this.getWritableDatabase();
    	String command = "SELECT play_id, Jersey_num, action, team_name, time FROM "+gamename+" ORDER BY play_id DESC";
    	cursor = db.rawQuery(command,  null);
    	return cursor;
    }
    public void delPlay(String playNum, String gamename) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	int play = Integer.parseInt(playNum);
    	String command = "DELETE FROM "+gamename+" WHERE play_id = "+play;
    	db.execSQL(command);
    	db.close();
    }
    public void recordPlay(int player, String team, String action, CourtActivity.position position, String table, long time) {
    	
    	// 1. get reference to writable DB
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	
    	onCreate(db);
    	// 2. create ContentValues to add key "column"/value
    	ContentValues values = new ContentValues();
    	int seconds = (int) time;
    	values.put("jersey_num", player); // get title 
    	values.put("action", action);
    	values.put("x_coord", position.x);
    	values.put("y_coord", position.y);
    	values.put("team_name", team);
    	values.put("time", seconds);
    	
    	
    	// 3. insert
    	db.insert(table, // table name
    	null, //nullColumnHack
    	values); // key/value -> keys = column names/ values = column values
    	
    	
    	// 4. close
    	db.close();  
    }
    public int countPlays(String tablename) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor;
    	String command;
    	int plays = 0;
    	command = "SELECT count(*) from "+tablename;
    	cursor = db.rawQuery(command, null);
    	if(cursor.moveToFirst()){
    	    plays = cursor.getInt(0);
    	}
    	cursor.close();
    	return plays;
    }
    public void undoPlay(String tablename, int play) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	int playd = (play-1);
    	db.execSQL("DELETE FROM "+tablename+" WHERE play_id = "+playd);

    	db.close();
    }
    public String grabUndoAction(String tablename, int play) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	String action = "None";
    	int playd = (play-1);
    	String command = "SELECT action FROM "+tablename+" WHERE play_id = "+playd;
    	Cursor cursor = db.rawQuery(command, null);
    	if(cursor.moveToFirst()){
    	    action = cursor.getString(0);
    	}
    	cursor.close();
    	return action;
    }
    public String grabTeamName(String tablename, int play) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	String team = "None";
    	int playd = (play-1);
    	String command = "SELECT team_name FROM "+tablename+" WHERE play_id = "+playd;
    	Cursor cursor = db.rawQuery(command, null);
    	if(cursor.moveToFirst()){
    	    team = cursor.getString(0);
    	}
    	cursor.close();
    	return team;
    }
    public int grabUndoJNum(String tablename, int play) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	int jnum = -1;
    	int playd = (play-1);
    	String command = "SELECT Jersey_num FROM "+tablename+" WHERE play_id = "+playd;
    	Cursor cursor = db.rawQuery(command, null);
    	if(cursor.moveToFirst()){
    	    jnum = cursor.getInt(0);
    	}
    	cursor.close();
    	return jnum;
    }
    public int grabScore(String tablename, String teamname) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	int score = 0, score1 = 0, score2 = 0, score3 = 0;
    	String command1 = "SELECT count(*) FROM "+tablename+" WHERE team_name = '"+teamname+"' AND action = 'FTH'";
    	String command2 = "SELECT count(*) FROM "+tablename+" WHERE team_name = '"+teamname+"' AND action = 'F2H'";
    	String command3 = "SELECT count(*) FROM "+tablename+" WHERE team_name = '"+teamname+"' AND action = 'F3H'";
    	Cursor cursor = db.rawQuery(command1, null);
    	if(cursor.moveToFirst()){
    	    score1 = cursor.getInt(0);
    	}
    	cursor = db.rawQuery(command2, null);
    	if(cursor.moveToFirst()){
    	    score2 = cursor.getInt(0);
    	}
    	cursor = db.rawQuery(command3, null);
    	if(cursor.moveToFirst()){
    	    score3 = cursor.getInt(0);
    	}
    	cursor.close();
    	score = (score1 + (score2*2) + (score3*3));
    	return score;
    }

}
