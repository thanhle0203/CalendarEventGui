
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class CalendarEvent implements Serializable {
	static Event newEvent;
	private static final long ID = 1L;
	static Map<String, ArrayList<Event>> eventList = new HashMap<String, ArrayList<Event>>();
	ArrayList<Event> events = new ArrayList<Event>();
	public static int eventsNum = 0;
	
	/**
	 * Add events which have title, date, start time, end time to the calendar
	 * @param e - event created by a user
	 */
	public static void add(Event e) {
		eventsNum++;
		ArrayList<Event> events = new ArrayList<Event>();
		events.add(e);
	
		newEvent = new Event(e.getTitle(), e.getDate(),e.getSTime(),e.getETime());
	
		try( FileWriter eventdata = new FileWriter("event.txt",true)) {
			
			 eventdata.write(eventsNum+"," + newEvent.getTitle() + "," + newEvent.getDate() + ","+newEvent.getSTime()+ ","+ newEvent.getETime()+"\n");
			 eventdata.close();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}	
		
	}
	
	/**
	 * Gets event with input time and date 
	 * @param time of event
	 * @param date of event
	 * @return time and date of event
	 */
	public String getEvent(String time, String date)
	{
		
		if(eventList.containsKey(date))
		{
			
			for(Event e: eventList.get(date))
			{
				
				if(e.sTime.equals(time))
				{
					
					return e.getTitle();
				}
			}
		}
		return "";
	}
	
	/**
	 * Save events to the txt. file
	 */
	public void saveEvent(){
		try
	      {
			
	        FileOutputStream fileOut = new FileOutputStream("event.txt");
	        ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        
	        out.writeObject(eventsNum);
	        
	        for(String s : eventList.keySet())
	 		{
	 			for(Event e : eventList.get(s))
	 			{
	 				out.writeObject(e);
	 			}
	 		}
	         out.close();
	         fileOut.close();	         
	         
	         System.out.printf("Events saved event.txt");
	         
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	/**
	 * Return number of events
	 * @return number of events
	 */
	public int getEventsNum(){
		return eventsNum;
	}
	
	/**
	 * Return details of events
	 */
	public String getMyEvent() {
		 for(String s : eventList.keySet())
	 		{
	 			for(Event e : eventList.get(s))
	 			{
	 				return e.toString();
	 			}
	 		}
		
		return null;
	}

}
































