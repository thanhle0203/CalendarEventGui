
import java.io.Serializable;

public class Event implements Serializable{
	private String title;
	public String dates;
	String sTime;
	String eTime;
	
	/**
	 * Constructs the class Events with title, dates, start time, end time of the event given
	 * @param t - title of event
	 * @param dates - date of event
	 * @param sTime - start time of event
	 * @param eTime - end time of event
	 */
	Event(String title, String dates, String sTime, String eTime){
		this.title = title;
		this.dates = dates;
		this.sTime= sTime;
		this.eTime = eTime;
		MONTHS[] M = MONTHS.values();
		DAYS[] D = DAYS.values();
	}
	
	/**
	 * Sets date of event
	 */
	
	public void setDate(String d){
		dates = d;
	}
	
	/**
	 * Returns date of event
	 * @return date of event
	 */
	public String getDate(){
		return dates;
	}

	/**
	 * Return title of event
	 * @return title of event
	 */
	public String getTitle(){
		return title;
	}

	/**
	 * Sets start time of event
	 * @param t - start time of event
	 */
	public void setSTime(String t){
		sTime = t;
		
	}

	/**
	 * Gets start time of event
	 * @return start time of event
	 */
	public String getSTime(){
		
		return sTime;
	}
	
	/**
	 * Gets end time of event
	 * @return end time of event
	 */
	public String getETime(){
		
    	return eTime;
	}
	
	/**
	 * Sets end time of event
	 * @param t - end time of event
	 */
	public void setETime(String t){
		eTime = t;
	}
	
	/**
	 * Returns toString of event
	 */
	public String toString(){
		return getTitle() + getSTime() + getETime() + getDate();
	}

}
