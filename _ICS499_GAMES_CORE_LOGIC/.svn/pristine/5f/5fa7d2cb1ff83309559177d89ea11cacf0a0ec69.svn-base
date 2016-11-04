package core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.Timer;

/**
 * The ClockListener class represents a timer object.  
 * @author sean.ford
 *
 */
public class ClockListener implements ActionListener
{

	private int hours;
	private int minutes;
	private int seconds;
	private String hour;
	private String minute;
	private String second;
	private static final int N = 60;
	private String time = new String();
	private Timer t;

	/**
	 * Initializes a newly created ClockListener object so that it represents the a timer and formats a string equivalent
	 * of a stop watch formatted hours minutes seconds (hh:mm:ss).
	 */
	public ClockListener() {
		time = "" + new String("00:00:00");
		t = new Timer(1000, this);
	}
		
	/**
	 * Start timer in ClockListener
	 */
	public void start() {
		t.start();
	}
	
	/**
	 * Stop timer in ClockListener
	 */
	public void stop() {
		t.stop();
	}
	
	/**
	 * Restart timer if timer is not running in ClockListener
	 */
	public void restart() {
		if(!t.isRunning())
			t.restart();
	}
	
	/**
	 * Returns boolean value indicating whether or not the timer is running or not.
	 * @return
	 */
	public boolean isRunning() {		
		return t.isRunning();
	}
	
	/**
	 * Returns a string formated 00:00:00 i.e hours minutes seconds
	 * @return
	 */
	public String getTime() {
		return time;
	}
		
	@Override
	public void actionPerformed(ActionEvent e)
	{

		NumberFormat formatter = new DecimalFormat("00");
		if (seconds == N)
		{
			seconds = 00;
			minutes++;
		}

		if (minutes == N)
		{
			minutes = 00;
			hours++;
		}
		hour = formatter.format(hours);
		minute = formatter.format(minutes);
		second = formatter.format(seconds);
		time = hour + ":" + minute + ":" + second;
		seconds++;

	}
}
