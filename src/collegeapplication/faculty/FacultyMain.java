package collegeapplication.faculty;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.ColorUIResource;

import collegeapplication.admin.AdminProfilePanel;
import collegeapplication.chat.ChatData;
import collegeapplication.chat.ChatMainPanel;
import collegeapplication.common.DataBaseConnection;
import collegeapplication.common.HomePanel;
import collegeapplication.common.NotificationData;
import collegeapplication.common.NotificationPanel;
import collegeapplication.common.SearchPanel;
import collegeapplication.common.TimeUtil;
import collegeapplication.login.LoginPageFrame;
import collegeapplication.student.AttandanceReportPanel;
import collegeapplication.student.EnterMarksPanel;
import collegeapplication.student.MarkAttandancePanel;
import collegeapplication.student.MarkSheetPanel;
import collegeapplication.student.MarkSheetReportPanel;
import collegeapplication.student.StudentPanel;
import collegeapplication.student.ViewStudentPanel;
import collegeapplication.subject.AssignSubjectPanel;
import collegeapplication.subject.SubjectPanel;
/*
 * Title : FacultyMain.java
 * Created by : Ajaysinh Rathod
 * Purpose : Main faculty frame
 * Mail : ajaysinhrathod1290@gmail.com
 */
@SuppressWarnings("serial")
public class FacultyMain extends JFrame  implements ActionListener
{

	public JPanel contentPane;
	private JLabel facultynamelabel;
	private JLabel facultyprofilepiclabel;
	private JPanel profilepanel;
	private JButton homebutton;
	private JButton studentsbutton;
	private JButton subjectbutton;
	private JButton faculitiesbutton;
	private JButton entermarksbutton;
	private JButton assignedsubjectbutton;
	private JButton markattandancebutton;
	private JButton marksheetreportbutton;
	private JButton attandancereportbutton;
	private JButton searchbutton;
	private JButton notificationbutton;
	private JButton logoutbutton;
	private JButton exitbutton;
	private Color buttonbcolor=Color.DARK_GRAY;
	private Color buttonfcolor=Color.LIGHT_GRAY;
	private Font buttonfont=new Font("Tw Cen MT", Font.PLAIN, 20);
	public SubjectPanel subjectpanel;
	public HomePanel homepanel;
	public StudentPanel studentpanel;
	public ViewStudentPanel viewstudentpanel;
	public MarkSheetPanel marksheetpanel;
	public JScrollPane marksheetpanelscroll;
	public ViewFacultyPanel viewfacultypanel;
	public AssignSubjectPanel assignsubjectpanel;
	public EnterMarksPanel entermarkspanel;
	public JScrollPane entermarkspanelscroll;
	private MarkAttandancePanel markattandancepanel;
	private JScrollPane markattandancepanelscroll;
	public AttandanceReportPanel attandancereportpanel;
	public JScrollPane attandancereportpanelscroll;
	public FacultyPanel facultypanel;
	public AdminProfilePanel adminprofilepanel;
	public SearchPanel searchpanel;
	private ChatMainPanel chatmainpanel;
	public NotificationPanel notificationpanel;
	public int panely=0,panelx=250;
	private JButton btn;
	private JButton myprofilebutton;
	private String lastlogin;
	public Faculty f;
	private int row=63;
	private JButton chatbutton;
	public Socket socket;
	private Timer timer;
	public MarkSheetReportPanel marksheetreportpanel;
	public JScrollPane marksheetreportpanelscroll;
	private JButton contactusbutton;
	private BufferedImage messagecount;
	private JLabel totalnewnotification;
	private JLabel totalnewchatmessage;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				
					if(DataBaseConnection.checkconnection())
					{
						
						Faculty f=new FacultyData().getFacultyInfo(1);
						FacultyMain frame = new FacultyMain(f);
					    frame.setVisible(true);
					    
					}
					else
					{
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						JOptionPane.showMessageDialog(null, "You Are Not Connected To DataBase","Error",JOptionPane.ERROR_MESSAGE);
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FacultyMain(Faculty f) {
		this.f=f;
		ActionListener setActive=new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new FacultyData().setActiveStatus(true, f.getFacultyId());
				int notification=new NotificationData().getUnreadNotification(f.getFacultyId()+"", "Faculty", f.getCourceCode(), f.getSemorYear(),f.getJoinedDate());
				if(notification>0)
				{
				totalnewnotification.setVisible(true);
				totalnewnotification.setText(notification>999?"999+":notification+"");
				totalnewnotification.setIcon(new ImageIcon(messagecount.getScaledInstance(24+totalnewnotification.getText().length(), 24, Image.SCALE_SMOOTH)));
				}
				int chat=new ChatData().getUndreadMessageCountFaculty(f);
				if(chat>0)
				{
					totalnewchatmessage.setText(chat>999?"999+":chat+"");
					totalnewchatmessage.setVisible(true);
					totalnewchatmessage.setIcon(new ImageIcon(messagecount.getScaledInstance(26+totalnewchatmessage.getText().length(), 26, Image.SCALE_SMOOTH)));
				}
				else if(chat==0)
				{
					totalnewchatmessage.setVisible(false);
				}
			}
			
		};
		timer=new Timer(1000,setActive);
		timer.start();
		Color bgColor =new Color(32,178,170);
		Color frColor=Color.white;
		try
		{
			messagecount=ImageIO.read(new File("./assets/messagecount.png"));
		}
		catch(IOException exp)
		{
			exp.printStackTrace();
		}
		UIManager.put("ComboBoxUI", "com.sun.java.swing.plaf.windows.WindowsComboBoxUI");
	    UIManager.put("ComboBox.selectionBackground", new ColorUIResource(bgColor));
	    UIManager.put("ComboBox.background", new ColorUIResource(Color.white));
	    UIManager.put("ComboBox.foreground", new ColorUIResource(Color.DARK_GRAY));
	    UIManager.put("ComboBox.selectionForeground", new ColorUIResource(frColor));
	    UIManager.put("ScrollBarUI", "com.sun.java.swing.plaf.windows.WindowsScrollBarUI");
	  
		this.setResizable(false);
		setTitle("Collage Data Management");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setForeground(Color.DARK_GRAY);
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.setBounds(-2,0,1370,733);
		createpanel();
		JPanel sidebarpanel = new JPanel();
		sidebarpanel.setBorder(new MatteBorder(0, 0, 0, 2, (Color) new Color(64, 64, 64)));
		sidebarpanel.setBackground(Color.DARK_GRAY);
		sidebarpanel.setBounds(5, 11, 240, 706);
		contentPane.add(sidebarpanel);
		sidebarpanel.setLayout(null);
		
		 profilepanel = new JPanel();
		 profilepanel.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.LIGHT_GRAY));
		profilepanel.setBackground(Color.DARK_GRAY);
		profilepanel.setBounds(0, 0, 240, 61);
		sidebarpanel.add(profilepanel);
		profilepanel.setLayout(null);
		
		facultynamelabel = new JLabel();
		facultynamelabel.setForeground(Color.WHITE);
		facultynamelabel.setHorizontalAlignment(SwingConstants.LEFT);
		facultynamelabel.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		facultynamelabel.setBackground(Color.DARK_GRAY);
		facultynamelabel.setOpaque(true);
		facultynamelabel.setBounds(65, 5, 171, 36);
		profilepanel.add(facultynamelabel);
		
		facultyprofilepiclabel = new JLabel();
		facultyprofilepiclabel.setBounds(5,0, 50, 50);
		profilepanel.add(facultyprofilepiclabel);
		facultyprofilepiclabel.setHorizontalAlignment(SwingConstants.CENTER);
		facultyprofilepiclabel.setBackground(Color.DARK_GRAY);
		
		facultyprofilepiclabel.setBorder(new LineBorder(Color.black,0));
		facultyprofilepiclabel.setOpaque(true);
		
	
		
		
		homebutton = createButton("Home");
		sidebarpanel.add(homebutton);
		btn=homebutton;
		
		studentsbutton = createButton("Students");
		sidebarpanel.add(studentsbutton);
		
		subjectbutton = createButton("Subjects");
		sidebarpanel.add(subjectbutton);
		
		faculitiesbutton =createButton("Co-Faculities","Faculities");
		sidebarpanel.add(faculitiesbutton);
		
		assignedsubjectbutton =createButton("Assigned Subject","Assign Subject");
		sidebarpanel.add(assignedsubjectbutton);
		
		entermarksbutton = createButton("Enter Marks");
		sidebarpanel.add(entermarksbutton);
		
		marksheetreportbutton=createButton("Marksheet Report");
		sidebarpanel.add(marksheetreportbutton);
		
		markattandancebutton = createButton("Mark Attandance");
		sidebarpanel.add(markattandancebutton);
		
		attandancereportbutton = createButton("Attandance Report");
		sidebarpanel.add(attandancereportbutton);
		
		chatbutton = createButton("Chat");
		chatbutton.setLayout(new BorderLayout());
		sidebarpanel.add(chatbutton);
		int chat=new ChatData().getUndreadMessageCountFaculty(f);
		totalnewchatmessage=new JLabel();
		totalnewchatmessage.setSize(60,30);
		totalnewchatmessage.setFont(new Font("Arial",Font.BOLD,12));
		totalnewchatmessage.setForeground(Color.white);
		totalnewchatmessage.setHorizontalTextPosition(JLabel.CENTER);
		totalnewchatmessage.setVerticalTextPosition(JLabel.CENTER);
		chatbutton.add(totalnewchatmessage,BorderLayout.LINE_END);
		if(chat>0)
		{
			totalnewchatmessage.setText(chat>999?"999+":chat+"");
			totalnewchatmessage.setVisible(true);
			totalnewchatmessage.setIcon(new ImageIcon(messagecount.getScaledInstance(26+totalnewchatmessage.getText().length(), 26, Image.SCALE_SMOOTH)));
		}
		
		searchbutton = createButton("Search");
		sidebarpanel.add(searchbutton);
		
		notificationbutton =createButton("Notification");
		notificationbutton.setLayout(new BorderLayout());
		sidebarpanel.add(notificationbutton);
		
		totalnewnotification=new JLabel();
		totalnewnotification.setSize(60,30);
		totalnewnotification.setFont(new Font("Arial",Font.BOLD,12));
		totalnewnotification.setForeground(Color.white);
		totalnewnotification.setHorizontalTextPosition(JLabel.CENTER);
		totalnewnotification.setVerticalTextPosition(JLabel.CENTER);
		notificationbutton.add(totalnewnotification,BorderLayout.LINE_END);
		int notification=new NotificationData().getUnreadNotification(f.getFacultyId()+"", "Faculty", f.getCourceCode(), f.getSemorYear(),f.getJoinedDate());
		System.out.println("Notification :"+notification);
		if(notification>0)
		{
			totalnewnotification.setText(notification>999?"999+":notification+"");
			totalnewnotification.setIcon(new ImageIcon(messagecount.getScaledInstance(26+totalnewnotification.getText().length(), 26, Image.SCALE_SMOOTH)));
		}
		
		
		myprofilebutton = createButton("My Profile","Profile");
		sidebarpanel.add(myprofilebutton);
		
		contactusbutton= createButton("Contact Us");
		sidebarpanel.add(contactusbutton);
		
		logoutbutton = createButton("logout");
		sidebarpanel.add(logoutbutton);

		exitbutton =createButton("Exit");
		sidebarpanel.add(exitbutton);
		
		activeButton(homebutton);
		homepanel.setVisible(true);
		
		
		
		this.setFacultyDetails();
		lastlogin=f.getLastLogin();
		homepanel.setLastLogin(lastlogin);
		f.setLastLogin(TimeUtil.getCurrentTime());
		f.setActiveStatus(true);
		new FacultyData().updateFacultyData(f, f);
		
	
		
	        this.addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosing(final WindowEvent windowenent) {
	            	openPanel(exitbutton);
	            }
	        });
	        
	        
		
	}
	public void createpanel()
	{
		
		homepanel=new HomePanel(f);
		homepanel.setLocation(panelx,panely);
		homepanel.setVisible(true);
		homepanel.setFocusable(true);
		contentPane.add(homepanel);
		
	}
	
	public void activeButton(JButton button)
	{
		btn.setBackground(buttonbcolor);
		btn.setForeground(buttonfcolor);
		btn.setFont(buttonfont);
		btn.setDisabledIcon(new ImageIcon(""));
		btn.setIcon(new ImageIcon("./assets/"+btn.getName()+"dac.png"));
		btn=button;
		btn.setForeground(Color.white);
		btn.setFont(new Font("Tw Cen MT", Font.BOLD, 23));
		btn.setIcon(new ImageIcon("./assets/"+btn.getName()+"ac.png"));
		disablepanel();
	}
	
	public JButton createButton(String text,String name)
	{
		JButton button=createButton(text);
		button.setName(name);
		button.setIcon(new ImageIcon("./assets/"+button.getName()+"dac.png"));
		return button;
	}
	public JButton createButton(String text)
	{
		JButton button=new JButton();
		button.setForeground(buttonfcolor);
		button.setFont(buttonfont);
		button.setBackground(buttonbcolor);
		button.setHorizontalAlignment(SwingConstants.LEFT);
		button.setFocusable(false);
		button.setContentAreaFilled(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setBorder(new EmptyBorder(0,0,0,0));
		button.setText(text);
		button.setName(text);
		button.setIcon(new ImageIcon("./assets/"+button.getName()+"dac.png"));
		button.addActionListener(this);
		button.setIconTextGap(10);
		button.setLocation(0, row);
		button.setSize(234, 40);
		row+=39;
		return button;
	}
	public void disablepanel() {
		// Hide the first non-chat panel that is visible
		if (hideFirstVisiblePanel()) {
			return;
		}

		// If none of the above panels were visible, check the chat panel
		hideChatPanelIfVisible();
	}

	/**
	 * Iterates over known panels and hides the first one that is visible.
	 * Returns true if a panel was hidden, false otherwise.
	 */
	private boolean hideFirstVisiblePanel() {
		Component[] panelsInPriority = {
				homepanel,
				subjectpanel,
				studentpanel,
				viewstudentpanel,
				facultypanel,
				viewfacultypanel,
				assignsubjectpanel,
				entermarkspanelscroll,
				marksheetpanelscroll,
				markattandancepanelscroll,
				marksheetreportpanelscroll,
				attandancereportpanelscroll,
				notificationpanel,
				adminprofilepanel,
				searchpanel
		};

		for (Component panel : panelsInPriority) {
			if (hideIfVisible(panel)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Hides a panel if it is non-null and currently visible.
	 * Returns true if it was hidden.
	 */
	private boolean hideIfVisible(Component panel) {
		if (panel != null && panel.isVisible()) {
			panel.setVisible(false);
			return true;
		}
		return false;
	}

	/**
	 * Handles hiding the chat panel and closing the underlying socket safely.
	 */
	private void hideChatPanelIfVisible() {
		if (chatmainpanel == null || !chatmainpanel.isVisible()) {
			return;
		}

		closeChatSocketSafely();
		chatmainpanel.setVisible(false);
	}

	private void closeChatSocketSafely() {
		try {
			if (chatmainpanel.chatpanel != null
					&& chatmainpanel.chatpanel.subchatpanel != null
					&& chatmainpanel.chatpanel.subchatpanel.socket != null
					&& !chatmainpanel.chatpanel.subchatpanel.socket.isClosed()) {

				chatmainpanel.chatpanel.subchatpanel.socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		this.openPanel(e.getSource());
	}
	public void openPanel(Object source) {
		if (source == exitbutton) {
			handleExit();
			return;
		}

		if (source == logoutbutton) {
			handleLogout();
			return;
		}

		handlePanelOpen(source);
	}

	/* ======================= MAIN PANEL DISPATCH ======================= */

	private void handlePanelOpen(Object source) {
		if (source == homebutton) {
			openHomePanel();
		} else if (source == subjectbutton) {
			openSubjectPanel();
		} else if (source == studentsbutton) {
			openStudentPanel();
		} else if (source == faculitiesbutton) {
			openFacultyPanel();
		} else if (source == assignedsubjectbutton) {
			openAssignSubjectPanel();
		} else if (source == entermarksbutton) {
			openEnterMarksPanel();
		} else if (source == marksheetreportbutton) {
			openMarksheetReportPanel();
		} else if (source == markattandancebutton) {
			openMarkAttendancePanel();
		} else if (source == attandancereportbutton) {
			openAttendanceReportPanel();
		} else if (source == chatbutton) {
			openChatPanel();
		} else if (source == searchbutton) {
			openSearchPanel();
		} else if (source == notificationbutton) {
			openNotificationPanel();
		} else if (source == contactusbutton) {
			openContactUsPanel();
		} else if (source == myprofilebutton) {
			openMyProfilePanel();
		}
	}

	/* ======================= SIMPLE PANEL HELPERS ======================= */

	private void configureBasePanel(JPanel panel) {
		panel.setLocation(panelx, panely);
		panel.setFocusable(true);
		panel.setVisible(true);
		contentPane.add(panel);
	}

	private void openHomePanel() {
		activeButton(homebutton);
		homepanel = new HomePanel(f);
		configureBasePanel(homepanel);
		homepanel.setLastLogin(lastlogin);
	}

	private void openSubjectPanel() {
		activeButton(subjectbutton);
		subjectpanel = new SubjectPanel(this);
		configureBasePanel(subjectpanel);
	}

	private void openStudentPanel() {
		activeButton(studentsbutton);
		studentpanel = new StudentPanel(this);
		configureBasePanel(studentpanel);
	}

	private void openFacultyPanel() {
		activeButton(faculitiesbutton);
		facultypanel = new FacultyPanel(this);
		configureBasePanel(facultypanel);
	}

	private void openAssignSubjectPanel() {
		activeButton(assignedsubjectbutton);
		assignsubjectpanel = new AssignSubjectPanel(this);
		configureBasePanel(assignsubjectpanel);
	}

	private void openChatPanel() {
		activeButton(chatbutton);
		chatmainpanel = new ChatMainPanel(this);
		chatmainpanel.setLocation(panelx, panely);
		chatmainpanel.setVisible(true);
		contentPane.add(chatmainpanel);
	}

	private void openSearchPanel() {
		activeButton(searchbutton);
		searchpanel = new SearchPanel(this);
		searchpanel.setLocation(panelx, panely);
		searchpanel.setVisible(true);
		contentPane.add(searchpanel);
	}

	private void openNotificationPanel() {
		activeButton(notificationbutton);
		if (totalnewnotification != null && totalnewnotification.isVisible()) {
			totalnewnotification.setVisible(false);
		}
		notificationpanel = new NotificationPanel(this);
		configureBasePanel(notificationpanel);
	}

	private void openContactUsPanel() {
		activeButton(contactusbutton);
		adminprofilepanel = new AdminProfilePanel();
		configureBasePanel(adminprofilepanel);
	}

	private void openMyProfilePanel() {
		activeButton(myprofilebutton);
		viewfacultypanel = new ViewFacultyPanel(f, this);
		configureBasePanel(viewfacultypanel);
	}

	/* ======================= SCROLLABLE PANEL HELPERS ======================= */

	private JScrollPane wrapPanelInScrollPane(JPanel panel, int unitIncrement, String name) {
		JScrollPane scrollPane = new JScrollPane(
				panel,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
		);
		scrollPane.setBounds(panelx, panely, 1116, 705);
		scrollPane.setVisible(true);
		if (name != null) {
			scrollPane.setName(name);
		}
		scrollPane.getVerticalScrollBar().setUnitIncrement(unitIncrement);
		contentPane.add(scrollPane);

		// keep original behavior: all child components white
		for (Component c : scrollPane.getComponents()) {
			c.setBackground(Color.white);
		}
		return scrollPane;
	}

	private void openEnterMarksPanel() {
		activeButton(entermarksbutton);
		entermarkspanel = new EnterMarksPanel(this);
		entermarkspanel.setVisible(true);
		entermarkspanelscroll = wrapPanelInScrollPane(entermarkspanel, 16, null);
	}

	private void openMarksheetReportPanel() {
		activeButton(marksheetreportbutton);
		marksheetreportpanel = new MarkSheetReportPanel(this);
		marksheetreportpanel.setVisible(true);
		marksheetreportpanelscroll = wrapPanelInScrollPane(
				marksheetreportpanel,
				80,
				"Marksheet Report Panel Scroll"
		);
	}

	private void openMarkAttendancePanel() {
		activeButton(markattandancebutton);
		markattandancepanel = new MarkAttandancePanel(this);
		markattandancepanel.setVisible(true);
		markattandancepanelscroll = wrapPanelInScrollPane(markattandancepanel, 16, null);
	}

	private void openAttendanceReportPanel() {
		activeButton(attandancereportbutton);
		attandancereportpanel = new AttandanceReportPanel(this);
		attandancereportpanel.setVisible(true);
		attandancereportpanelscroll = wrapPanelInScrollPane(
				attandancereportpanel,
				16,
				"Attadance Report Panel Scroll"
		);
	}

	/* ======================= EXIT / LOGOUT ======================= */

	private void handleExit() {
		int result = JOptionPane.showConfirmDialog(
				null,
				"Do you want to exit this application ?",
				"Exit",
				JOptionPane.INFORMATION_MESSAGE
		);
		if (result == JOptionPane.YES_OPTION) {
			f.setActiveStatus(false);
			timer.stop();
			new FacultyData().setActiveStatus(false, f.getFacultyId());
			disablepanel();
			System.exit(0);
		}
	}

	private void handleLogout() {
		int result = JOptionPane.showConfirmDialog(
				null,
				"Do you want to logout this application ?",
				"Logout",
				JOptionPane.INFORMATION_MESSAGE
		);
		if (result == JOptionPane.YES_OPTION) {
			f.setActiveStatus(false);
			timer.stop();
			new FacultyData().setActiveStatus(false, f.getFacultyId());
			LoginPageFrame loginpageframe = new LoginPageFrame();
			loginpageframe.setVisible(true);
			loginpageframe.setLocationRelativeTo(null);
			disablepanel();
			this.dispose();
		}
	}


	public void setFacultyDetails()
	{
		facultyprofilepiclabel.setIcon(new ImageIcon(f.getRoundedProfilePic(50, 50, 50)));
		facultynamelabel.setText(f.getFacultyName());
	}
	
}
