
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CalendarModel {
	private GregorianCalendar cal;
	ArrayList<ChangeListener> lis;
	private CalendarEvent events;
	private ArrayList<Event> event = new ArrayList<Event>();

	/**
	 * Constructs the class CalendarModel with the object of GregorianCalendar given
	 * @param c - object of GregorianCalendar class
	 */
	CalendarModel(GregorianCalendar c){
		cal = c;
		lis = new ArrayList<ChangeListener>();
		events = new CalendarEvent();
	}

	/**
	 * Update the changes of the calendar
	 */
	public void update(){
		for(ChangeListener l : lis){
			l.stateChanged(new ChangeEvent(this));
		}
		
	}
	
	/**
	 * Gets a detail event with start time and date of the event
	 * @param d3 - start time of the event
	 * @return time and date of the event
	 */
	public String getEventDetail(String d3)
	{
		String date = "";
		if(cal.get(Calendar.MONTH) < 8)
			date += "0" + (cal.get(Calendar.MONTH)+1) + "/";
		else
			date += (cal.get(Calendar.MONTH)+1) + "/";
		
		if(cal.get(Calendar.DAY_OF_MONTH) < 10)
			date += "0" + cal.get(Calendar.DAY_OF_MONTH) + "/";
		else
			date += cal.get(Calendar.DAY_OF_MONTH) + "/";
		
		date += Integer.toString(cal.get(Calendar.YEAR));

		return events.getEvent(d3, date);
	}

	/**
	 * Gets next day of the month of the calendar
	 */
	public void nextDay(){
		cal.add(Calendar.DAY_OF_MONTH,1);
		update();
	}
	
	/**
	 * Gets previous day of the month of the calendar
	 */
	public void prevDay(){
		cal.add(Calendar.DAY_OF_MONTH,-1);
		update();
	}
	
	/**
	 * Gets next month of the calendar
	 */
	public void nextMonth(){
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		update();
	}

	/**
	 * Gets previous month of the calendar
	 */
	public void prevMonth(){
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		update();
	}

	/**
	 * Sets day calendar and update that day
	 * @param d - day of the month
	 */
	public void setDay(int d){
		cal.set(Calendar.DAY_OF_MONTH, d);
		update();
	}

	/**
	 * Attach the state of change in the calendar
	 * @param c
	 */
	public void attach(ChangeListener c){
		lis.add(c);
	}

	/**
	 * Returns date of calendar
	 * @return - date 
	 */
	public GregorianCalendar getCalendar(){
		return cal;
	}

	/**
	 * Add events and update events
	 * @param e - event 
	 */
	public void addEvent(Event e){
		events.add(e);
		update();
	}
	
	public JTextArea setData(JTextArea[] btn) throws IOException {
		for (int i = 0; i< 24; i++) {
			if (event.get(i).getTitle() == null) {
				btn[i].setText(" ");
			} else {
				btn[i].setText(event.get(i).getTitle() + "\n");
			}
			
			
		}
		return null;
		
	
		
	}

	/**
	 * Save events to calendar
	 */
	public void save(){
		events.saveEvent();
	}
	
	/** Returns toString methods of event
	 * @return - title, date of events
	 * 
	 */
	public String getEvent() {
		return events.getMyEvent();
	}
	
	/**
	 * Loads input events with specific named event, time, and date events
	 */
	public void load(){
		FileInputStream fileIn;
		ObjectInputStream input;
		CalendarEvent tempList = new CalendarEvent();
		try
	    {
		   fileIn = new FileInputStream("event.txt");
	       input = new ObjectInputStream(fileIn);
	       
	       
	       int num = (int) input.readObject();
		   
	       for(int i = 0; i < num; i++)
	       { 
	    	  tempList.add((Event) input.readObject());
	       }
	       
	       //tempList.add((Event) input.readObject());
	    
	       events = tempList;

	       update();
	       input.close();
		   fileIn.close();
	    }catch(IOException i)
	    {
	       return;
	    }catch(ClassNotFoundException c)
	    {
	      
	       c.printStackTrace();
	       return;
	    }
	}

}






















































