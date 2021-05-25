import java.io.IOException;
import java.util.GregorianCalendar;

/**
 * Enums Month class of the Calendar
 * @author thanh
 *
 */

/**
 * Enums Day class of the Calendar
 * @author thanh
 *
 */
enum MONTHS
{
	Jan, Feb, March, Apr, May, June, July, Aug, Sep, Oct, Nov, Dec;
}
enum DAYS
{
	Sun, Mon, Tue, Wed, Thur, Fri, Sat;
}

public class SimpleCalendarTester {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		GregorianCalendar cal = new GregorianCalendar(); //Today date
		
		CalendarModel model = new CalendarModel(cal);
		
		CalendarFrame view = new CalendarFrame(model);
		
		model.attach(view);
	}

}
