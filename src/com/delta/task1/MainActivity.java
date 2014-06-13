package com.delta.task1;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;

public class MainActivity extends ActionBarActivity 
{
	TextView tvTime, tv1, tv2, tv3, tv4, tv5;
	Button start, pause, reset;
	long startTime = 0;
	long updatedTime = 0;
	long timeWhenPaused = 0;
	long timeAtTheMoment = 0;
	Handler handlerForTimer = new Handler(); // handler for executing the runnable
	long sec = 0, min = 0;
	long hour = 0;
	String newhour = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main); // for setting up the xml file

		initialise(); // function to reference the xml elements in java

		start.setOnClickListener(new View.OnClickListener() 
		{

			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				startTime = SystemClock.uptimeMillis(); // fixed value until reset is clicked. 
				handlerForTimer.postDelayed(Timer, 0); // to start the runnable timer immediately

			}
		});

		pause.setOnClickListener(new View.OnClickListener() 
		{

			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				timeWhenPaused += timeAtTheMoment; //storing the time when paused so that the display can be continued when the timer is started again, after pausing it
				handlerForTimer.removeCallbacks(Timer);
			}
		});

		reset.setOnClickListener(new View.OnClickListener() 
		{

			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stud
				displayList(); // to display the recorded time in list below the buttons
				startTime = 0L; // Reinitializing all variables to zero as timer is being reset
				updatedTime = 0L;
				timeWhenPaused = 0L;
				timeAtTheMoment = 0L;
				tvTime.setText("00:00:00");
				sec = 0;
				min = 0;
				hour = 0;
				handlerForTimer.removeCallbacks(Timer); // so ensure normal functioning of app when reset is clicked after start (skipping the pause button)
			}
		});

	}

	private Runnable Timer = new Runnable() 
	{
		public void run() 
		{
			timeAtTheMoment = SystemClock.uptimeMillis() - startTime; // difference gives the number of milliseconds recorded
			updatedTime = timeAtTheMoment + timeWhenPaused; // in case the timer was paused in between, updates time appropriately

			sec = updatedTime / 1000; // to obtain in seconds, minutes and hours
			min = sec / 60;
			sec = sec % 60;
			hour = min / 60;

			if (hour > -1 && hour < 10) // to display as "00:00:00" instead of "0:00:00"
				newhour = "0" + hour;
			else
				newhour = "" + hour;

			tvTime.setText("" + newhour + ":" + String.format("%02d", min)+ ":" + String.format("%02d", sec));// display updated(recorded) time

			handlerForTimer.postDelayed(this, 0);// calling the runnable from within the runnable
		}

	};

	private void displayList() 
	{
		// TODO Auto-generated method stub
		if (hour > -1 && hour < 10) // to display as "00:00:00" instead of "0:00:00"
			newhour = "0" + hour;
		else
			newhour = "" + hour;

		tv5.setText(tv4.getText()); //to make each new entry to the top of the list and keep moving the previous entry (if any) down
		tv4.setText(tv3.getText());
		tv3.setText(tv2.getText());
		tv2.setText(tv1.getText());
		tv1.setText("" + newhour + ":" + String.format("%02d", min) + ":"+ String.format("%02d", sec));

	}

	private void initialise() //  // function to reference the xml elements in java 
	{
		// TODO Auto-generated method stub
		tvTime = (TextView) findViewById(R.id.tvTime);
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
		tv5 = (TextView) findViewById(R.id.tv5);
		start = (Button) findViewById(R.id.bStart);
		pause = (Button) findViewById(R.id.bPause);
		reset = (Button) findViewById(R.id.bReset);
	}

}
