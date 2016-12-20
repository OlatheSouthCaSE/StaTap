#StaTap

##Introduction

Hello! Thanks for coming to check out our application!

This application was created as a senior project by 6/10 of Olathe South High School's C.A.S.E. program's 2015 Seniors.

Since our application was built with only one use in mind, we ran into a few issues with letting others be able to use it. Specifically, the application is tough to test/emulate without use of a 10.1" tablet at 2560x1600. These are all things that would have been fixed and been accounted for given more time.

Thus, this application stands to serve mainly as a learning experience for us as Seniors, by taking our skills we learned throughout High School and attempting an ambitious project.

Our program took the idea of recording basketball stats using an application. This would eliminate the need for a pen-and-paper score/playbook to record real-time game stats. This program can be compared to the likes of Hudl, but at the time, there was none like it. Hudl is mainly for tagging game footage afterward aswell, while ours is used for ingame functionality and also post-game.

##Functionality - Pictures to be added later.

The Program starts by displaying the home screen. Users may then choose a previous saved game, or start a new one by selecting two teams. Teams can be added with options of Number and names for each player.

When a game instance is started/opened, a basketball court appears centered and taking up a majority of the tablet screen. Players ingame are displayed as buttons, and can be substituted. Users may then select a player as active and then select an action e.g. steal, assist, foul. There is a running game clock that can be started, stopped, reset, and is mainly used for recording times that actions occurred. For recording shots, there is only a shot made/missed button. This is because, as a user, you are able to select an active player and spot on the court to record the shot attempt/result. This would then be used later to shot a heatmap of shot attempts/makes.

##Remarks

The program worked remarkably well for its purpose. We did run into many complications and issues, but many of those could easily be fixed now with the knowledge that we have gained.

The main complication was in-app memory management. After a certain time elapsed of running the application, it would usually crash, but this could have easily been fixed by noting proper allocations and deallocations of dynamic memory.

The other most notable complication was usability. Due to the hectic nature of a basketball game, it can be very tough to keep track of plays/actions going on e.g. a string of missed shots/rebounds by multiple players of a team in the span of 5-10 seconds. Due to this and the current functionality of our application, operating the application really required two people.

<br><br><br><br><br><br>
#Archived
*Remaining to-do list has now been archived as the program is no longer in production.*

##Bugs
<ul>
	<li>Fixed all current bugs.</li>
</ul>





##Assignments

###Home Screen
#####~~Refresh Button~~ <strong>DONE!</strong>
<ul>
	<li>End activity when moving to Edit Teams</li>
	<li>Remove Button</li>
</ul>
===================
###New Game Screen
#####~~Team < 5 Players~~ <strong>DONE!</strong>
<ul>
	<li>SQL Statement to check if there are less than 5 players</li>
	<li>Display message saying team cannot be used with less than 5 Players</li>
</ul>
===================
###Court Screen
#####~~Refresh Function into separate parts~~ <strong>DONE!</strong>
<ul>
	<li>Make a different refresh for each function</li>
</ul>
#####Running Clock - PAUL
<ul>
	<li>Find a clock function</li>
	<li>Add clock display in text</li>
	<li>Pause-Resume Buttons</li>
</ul>
#####Settings Screen (In-Game)
<ul>
	<li>Time Settings
	<ul>
		<li>Change Max Time per Quarter</li>
		<li>Format?</li>
	</ul>
	</li>
</ul>
#####Plotting Points for FG/FGA - LOGAN
<ul>
	<li>Draw Dots for every Coord</li>
	<li>4 Different Colors?</li>
	<li>X's and O's?</li>
</ul>

===============		
###Stat Screen
#####~~Stat Format - Damian~~ DONE!
<ul>
	<li>Change Font sizes and colors to distinguish between stats</li>
	<li>Clean up stats screen</li>
</ul>

================





##Long-Term Goals

#####Web Integration
Only attempt if accomplish all other essential things
<ul>
	<li>Acquire domain, or use existing</li>
	<li>Design Web App for viewing game using dummy data</li>
	<li>Develop table framework for layouts</li>
	<li>Implement uploading games to website</li>
	<li>Ability to edit/delete existing games online</li>
</ul>
