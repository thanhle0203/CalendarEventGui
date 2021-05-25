
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class CalendarFrame extends JFrame implements ChangeListener{
	private static final long serialVersionUID = 1L;
	CalendarModel model;
	GregorianCalendar c;
	private Event event;
	private CalendarEvent calendarEvent;
	private CalendarFrame calendarFrame;
	private JTextArea eventList;
	
	private JPanel monthDisplay;
	private JScrollPane scrollEventDisplay;
	private JPanel eventDisplayPanel;
	
	MONTHS[] Months = MONTHS.values();
    DAYS[] Days = DAYS.values();
	

	static final int FRAME_WIDTH = 1000;
	static final int FRAME_HEIGHT = 600;
	
	/**
	 * Constructors of CalendarFrame class
	 * @param d - object of CalendarModel class
	 * @throws IOException 
	 */
	public CalendarFrame(CalendarModel d) throws IOException
	{
		setResizable(false);
		model = d;
	
		this.setSize(1000,436);
		getContentPane().setLayout(new BorderLayout());
		

		scrollEventDisplay = makeEventDisplay();
		monthDisplay = makeCalendarDisplay();
		
		JPanel controlPanel = new JPanel(new FlowLayout());
		JButton quitButton = new JButton("Quit");
		JButton createButton = new JButton("CREATE");
		createButton.setBackground(Color.RED);
		createButton.setForeground(Color.WHITE);
		JButton previousDayButton = new JButton("<");
		JButton nextDayButton = new JButton(">");
		
		
		quitButton.setBackground(Color.WHITE);
		quitButton.addActionListener((ActionListener) new
				ActionListener()
				{
					public void actionPerformed1(ActionEvent arg0)
					{
						model.save();
						close();
					}

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
		createButton.addActionListener(new
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						createEvent();
					}
				});
	
		previousDayButton.addActionListener(new
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						model.prevDay();
					}
				});
		nextDayButton.addActionListener(new
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						model.nextDay();
					}
				});

		controlPanel.add(createButton);
		controlPanel.add(previousDayButton);
		controlPanel.add(nextDayButton);
		controlPanel.add(quitButton);
		
		
		getContentPane().add(controlPanel,BorderLayout.NORTH);
		getContentPane().add(monthDisplay,BorderLayout.WEST);
		getContentPane().add(scrollEventDisplay, BorderLayout.CENTER);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	/**
	 * Close the file
	 */
	protected void close()
	{
		this.dispose();
	}

	/**
	 * Create the event of the Calendar
	 */
	public void createEvent()
	{
		String date = "";
		if(model.getCalendar().get(Calendar.MONTH) < 9)
			date += "0" +(model.getCalendar().get(Calendar.MONTH)+1) +"/";
		else
			date += (model.getCalendar().get(Calendar.MONTH)+1) +"/";
		
		if(model.getCalendar().get(Calendar.DAY_OF_MONTH) < 10)
			date += "0" + model.getCalendar().get(Calendar.DAY_OF_MONTH) + "/";
		else
			date += model.getCalendar().get(Calendar.DAY_OF_MONTH) + "/";
		
		date += model.getCalendar().get(Calendar.YEAR)+""; 
		

		
		JPanel createEventPanel = new JPanel(new BorderLayout());
		JTextField eventTitle = new JTextField("Untitled Event");
		JPanel startEndSavePanel = new JPanel(new FlowLayout());
		JLabel dateLabel = new JLabel(date);
		String datee= date;
		JTextField startTime = new JTextField("Start 00:00");
		JLabel toLabel = new JLabel("to");
		JTextField endTime = new JTextField("End 00:00");
		
		
		
		JButton saveButton = new JButton("Save");
		saveButton.setBackground(Color.WHITE);
		saveButton.addActionListener(new
			ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					int lines = 0;	
					String[]events;
					String line="";
				      try {
				    	  BufferedReader reader = new BufferedReader(new FileReader("event.txt")); 
				    	  while (reader.readLine() != null) lines++;
				    	  reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				       if (lines==0) {
				    	   CalendarEvent.add(new Event(eventTitle.getText(), datee, startTime.getText(), endTime.getText()));
							createEventPanel.setVisible(false);
			
							
				       }else {
				    	   try {
								for (int i =0 ; i<lines; i++){
								line = Files.readAllLines(Paths.get("event.txt")).get(i);
								 System.out.println(line);
								 events = line.split("\\s*[,]\\s*");
								 if (events.length>0) {
							     SimpleDateFormat sdformat = new SimpleDateFormat("yyyy/MM/dd");
								 Date date1 = sdformat.parse(events[2]);
								 Date date2 = sdformat.parse(datee);
								 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
								 	Date d1 = sdf.parse(events[3]);
								 	Date d2 = sdf.parse(events[4]); 
								 	Date d3 = sdf.parse(startTime.getText());
								 	Date d4 = sdf.parse(endTime.getText());
						
								   		if(date1.compareTo(date2)==0) {
								   			if(((d3.before(d2))&& (d2.after(d1)))||((d4.before(d2))&&(d4.after(d1)))){
								   			JOptionPane.showMessageDialog(createEventPanel,
								   				    "Warning, conflict time Error",
								   				    "WARNING ",
								   				    JOptionPane.WARNING_MESSAGE);
								   	 
								   			}else if (d3.after(d4)) {
								   				JOptionPane.showMessageDialog(createEventPanel,
								   			 "GWarning, Starting/Ending  time  Error.",
							   				    " WARNING",
							   				    JOptionPane.WARNING_MESSAGE);
								   			}else {
								   				CalendarEvent.add(new Event(eventTitle.getText(), datee, startTime.getText(), endTime.getText()));
								   		
								   				eventList = new JTextArea();
								   				eventList.setText(eventTitle.getText() + " " + datee + " " + startTime.getText() 
								   										+" " + endTime.getText());
								   		
								   				//eventDisplay.setText(model.getEventDetail(date));
								   				createEventPanel.setVisible(false);
								   			}
								   		} else {
								   			CalendarEvent.add(new Event(eventTitle.getText(), datee, startTime.getText(), endTime.getText()));
								   			eventList = new JTextArea();
							   				eventList.setText(eventTitle.getText() + " " + datee + " " + startTime.getText() 
							   										+" " + endTime.getText());
								   									   								
								   			createEventPanel.setVisible(false);
								   		}
								 	}
								}
								
							}catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

				       }
					
				   
				} 
			});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBackground(Color.WHITE);
		cancelButton.addActionListener(new
			ActionListener()
			{ 
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					createEventPanel.setVisible(false);
				}
			});
		
		startEndSavePanel.add(dateLabel);
		startEndSavePanel.add(startTime);
		startEndSavePanel.add(toLabel);
		startEndSavePanel.add(endTime);
		startEndSavePanel.add(saveButton);
		startEndSavePanel.add(cancelButton);
		
		createEventPanel.add(eventTitle, BorderLayout.NORTH);
		createEventPanel.add(startEndSavePanel, BorderLayout.SOUTH);
		
		getContentPane().add(createEventPanel, BorderLayout.SOUTH);
		

		this.revalidate();
		this.repaint();
	}
	
	/**
	 * Scroll down to see time and name of an event
	 * @return time and name of the event
	 * @throws IOException 
	 */
	public JScrollPane makeEventDisplay() throws IOException
	{
		JPanel eventPanel = new JPanel(new BorderLayout());
		
		JPanel time_events = new JPanel(new GridLayout(0,1));
		
		/*
		 * Display the time of events
		 */
		JTextField eventDisplayDate = new 
				JTextField(Days[model.getCalendar().get(Calendar.DAY_OF_WEEK)-1] + " " 
				+ Months[(model.getCalendar().get(Calendar.MONTH))] + " "
				+ model.getCalendar().get(Calendar.DAY_OF_MONTH) + ", "
				+ model.getCalendar().get(Calendar.YEAR))
				;

		String date = null;
		for(int i = 1; i < 25; i++)
		{
			
			if(i < 10)
			{
				String time = ("0" + i + ":00");
				date = time;
				date += " " + model.getEventDetail(time);
				
				time_events.add(new JLabel(date));


			}
			
			else
			{
				String time = i + ":00";
				date = time;
				date += " " + model.getEventDetail(time);
				
				time_events.add(new JLabel(date));
				
				/*
				 * Display specific date and name of event
				 */
			
			}
		}
		
		/*
		* Display specific date and name of event
		 */
			
		//JTextField eventDisplay = new JTextField(model.getEventDetail(date));
		
		//eventDisplay = new JTextArea();
		//eventDisplay.setText(model.getEvent());
		
		//String eventDisplay = eventDisplayText();
		
		eventPanel.add(eventDisplayDate, BorderLayout.NORTH);
		eventPanel.add(time_events, BorderLayout.WEST);
		eventList = new JTextArea();
		eventList.setLineWrap(true);
		eventList.setWrapStyleWord(true);
		eventList.setBackground(new Color(200, 200, 200));
		eventList.setBorder(BorderFactory.createBevelBorder(1));
		eventList.setForeground(new Color(55, 55, 55));
		eventList.setFont(new Font("Comic Sans", Font.CENTER_BASELINE, 15));
		eventList.setEditable(false);
		
		//eventList.setText(model.getEvent());
		eventPanel.add(eventList, BorderLayout.CENTER);

		
		JScrollPane scrollEventDisplay = new JScrollPane(eventPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		
		return scrollEventDisplay;
	}
	
	/**
	private JTextArea eventDisplay(JTextArea[] text) throws IOException{
	
		return model.setData(text);
	}
	*/

	/**
	 * Displays the Calendar with name events and time events
	 * @return name events and time events
	 */
	private JPanel makeCalendarDisplay()
	{
		
		ActionListener dateSetter = new 
				ActionListener() 
			{
				public void actionPerformed(ActionEvent e)
			    {
					String num = e.getActionCommand();
			        if (!num.equals("")) 
			        {
			          
			          model.setDay(Integer.parseInt(num));
			        }
			    }
			 };
			 
		JPanel monthDisplay = new JPanel(new BorderLayout());

		monthDisplay.add(new JLabel("" + Months[model.getCalendar().get(Calendar.MONTH)]), BorderLayout.NORTH);
		
		
		JPanel daysLayout = new JPanel(new GridLayout(0,7));
		
		for(int i = 0; i < 7; i++)
		{
			daysLayout.add(new JLabel(Days[i].toString().substring(0,2)));
		}
		
		
		GregorianCalendar temp = (new GregorianCalendar(model.getCalendar().get(Calendar.YEAR), 
				model.getCalendar().get(Calendar.MONTH),1));
		
		
		for(int i = 1; i < temp.get(Calendar.DAY_OF_WEEK); i++)
		{
			daysLayout.add(new JLabel(""));
		}
	
		for(int i = 0; i < model.getCalendar().getActualMaximum(Calendar.DAY_OF_MONTH); i++)
		{
			JButton calendarDay = new JButton(Integer.toString(i+1));
			if((i+1) == model.getCalendar().get(Calendar.DAY_OF_MONTH))
			{
				calendarDay.setBackground(Color.ORANGE);
			}
			else
			{
				calendarDay.setBackground(Color.WHITE);
			}
			calendarDay.addActionListener(dateSetter);
			
			daysLayout.add(calendarDay);			
		}
		monthDisplay.add(daysLayout, BorderLayout.WEST);
	
		return monthDisplay;
	}
	
	/**
	 * Involve the changes of calendar when a user clicks on day of calendar
	 */
	@Override
	public void stateChanged(ChangeEvent e) {

		remove(monthDisplay);
		monthDisplay = makeCalendarDisplay();
		getContentPane().add(monthDisplay,BorderLayout.WEST);
		
		remove(scrollEventDisplay);
		try {
			scrollEventDisplay = makeEventDisplay();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		getContentPane().add(scrollEventDisplay, BorderLayout.CENTER);
		
		this.revalidate();
		this.repaint();
	}
	

}









































