package collegeapplication.student;

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
import collegeapplication.faculty.FacultyPanel;
import collegeapplication.faculty.ViewFacultyPanel;
import collegeapplication.login.LoginPageFrame;
import collegeapplication.subject.AssignSubjectPanel;
import collegeapplication.subject.SubjectPanel;

/*
 * Title : StudentMain.java
 * Created by : Ajaysinh Rathod
 * Purpose : Student Main Frame
 * Mail : ajaysinhrathod1290@gmail.com
 */

@SuppressWarnings("serial")
public class StudentMain extends JFrame  implements ActionListener
{

	public JPanel contentPane;
	private JLabel studentnamelabel;
	private JLabel studentprofilepiclabel;
	private JPanel profilepanel;
	private JButton homebutton;
	private JButton studentsbutton;
	private JButton subjectbutton;
	private JButton faculitiesbutton;
	private JButton marksheetbutton;
	private JButton attandancereportbutton;
	private JButton searchbutton;
	private JButton notificationbutton;
	private JButton contactusbutton;
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
	public Student s;
	private int row=63;
	private JButton assignedsubjectbutton;
	private JButton chatbutton;
	public Socket socket;
	private Timer timer;
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
						
						Student s=new StudentData().getStudentDetails("CE",1, 1001);
						StudentMain frame = new StudentMain(s);
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
	public StudentMain(Student s) {

		this.s = s;

		loadMessageCountIcon();
		configureLookAndFeel();
		initContentPane();
		JPanel sidebarpanel = createSidebarPanel();
		createProfilePanel(sidebarpanel);

		createSidebarButtons(sidebarpanel, s);

		activeButton(homebutton);
		homepanel.setVisible(true);

		initializeStudentSession(s);
		initActiveStatusTimer(s);
		addWindowCloseBehavior();
	}

	/* ===================== TIMER / ACTIVE STATUS ===================== */

	private void initActiveStatusTimer(Student student) {
		ActionListener setActive = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				handleActiveStatusTick(student);
			}
		};
		timer = new Timer(1000, setActive);
		timer.start();
	}

	private void handleActiveStatusTick(Student student) {
		int result = new StudentData().setActiveStatus(student.getActiveStatus(), student.getUserId());
		if (result == 0) {
			timer.stop();
			JOptionPane.showMessageDialog(
					null,
					"Your account is deleted by Admin",
					"Account deleted",
					JOptionPane.ERROR_MESSAGE
			);
			System.exit(0);
			return;
		}

		updateNotificationBadge(student);
		updateChatBadge(student);
	}

	private void updateNotificationBadge(Student student) {
		if (totalnewnotification == null) {
			return; // timer may tick before UI fully built
		}
		int notification = new NotificationData().getUnreadNotification(
				student.getUserId(),
				"Student",
				student.getCourceCode(),
				student.getSemorYear(),
				student.getAdmissionDate()
		);
		if (notification > 0) {
			String text = notification > 999 ? "999+" : String.valueOf(notification);
			totalnewnotification.setText(text);
			totalnewnotification.setVisible(true);
			totalnewnotification.setIcon(new ImageIcon(
					messagecount.getScaledInstance(26 + text.length(), 26, Image.SCALE_SMOOTH)
			));
		}
		// Note: original code does not hide when notification == 0, so we keep same behavior.
	}

	private void updateChatBadge(Student student) {
		if (totalnewchatmessage == null) {
			return; // timer may tick before UI fully built
		}
		int chat = new ChatData().getUndreadMessageCountStudent(student);
		if (chat > 0) {
			String text = chat > 999 ? "999+" : String.valueOf(chat);
			totalnewchatmessage.setText(text);
			totalnewchatmessage.setVisible(true);
			totalnewchatmessage.setIcon(new ImageIcon(
					messagecount.getScaledInstance(26 + text.length(), 26, Image.SCALE_SMOOTH)
			));
		} else if (chat == 0) {
			totalnewchatmessage.setVisible(false);
		}
	}

	/* ===================== LOOK & FEEL / FRAME ===================== */

	private void loadMessageCountIcon() {
		try {
			messagecount = ImageIO.read(new File("./assets/messagecount.png"));
		} catch (IOException exp) {
			exp.printStackTrace();
		}
	}

	private void configureLookAndFeel() {
		Color bgColor = new Color(32, 178, 170);
		Color frColor = Color.white;

		UIManager.put("ComboBoxUI", "com.sun.java.swing.plaf.windows.WindowsComboBoxUI");
		UIManager.put("ComboBox.selectionBackground", new ColorUIResource(bgColor));
		UIManager.put("ComboBox.background", new ColorUIResource(Color.white));
		UIManager.put("ComboBox.foreground", new ColorUIResource(Color.DARK_GRAY));
		UIManager.put("ComboBox.selectionForeground", new ColorUIResource(frColor));
		UIManager.put("ScrollBarUI", "com.sun.java.swing.plaf.windows.WindowsScrollBarUI");
	}

	private void initContentPane() {
		this.setResizable(false);
		setTitle("Collage Data Management");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		contentPane = new JPanel();
		contentPane.setForeground(Color.DARK_GRAY);
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.setBounds(-2, 0, 1370, 733);
		createpanel();
	}

	/* ===================== SIDEBAR / PROFILE ===================== */

	private JPanel createSidebarPanel() {
		JPanel sidebarpanel = new JPanel();
		sidebarpanel.setBorder(new MatteBorder(0, 0, 0, 2, new Color(64, 64, 64)));
		sidebarpanel.setBackground(Color.DARK_GRAY);
		sidebarpanel.setBounds(5, 11, 240, 706);
		contentPane.add(sidebarpanel);
		sidebarpanel.setLayout(null);
		return sidebarpanel;
	}

	private void createProfilePanel(JPanel sidebarpanel) {
		profilepanel = new JPanel();
		profilepanel.setBorder(new MatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));
		profilepanel.setBackground(Color.DARK_GRAY);
		profilepanel.setBounds(0, 0, 240, 61);
		sidebarpanel.add(profilepanel);
		profilepanel.setLayout(null);

		studentnamelabel = new JLabel();
		studentnamelabel.setForeground(Color.WHITE);
		studentnamelabel.setHorizontalAlignment(SwingConstants.LEFT);
		studentnamelabel.setFont(new Font("Tw Cen MT", Font.BOLD, 21));
		studentnamelabel.setBackground(Color.DARK_GRAY);
		studentnamelabel.setOpaque(true);
		studentnamelabel.setBounds(65, 5, 171, 36);
		profilepanel.add(studentnamelabel);

		studentprofilepiclabel = new JLabel();
		studentprofilepiclabel.setBounds(5, 0, 50, 50);
		profilepanel.add(studentprofilepiclabel);
		studentprofilepiclabel.setHorizontalAlignment(SwingConstants.CENTER);
		studentprofilepiclabel.setBackground(Color.DARK_GRAY);
		studentprofilepiclabel.setBorder(new LineBorder(Color.black, 0));
		studentprofilepiclabel.setOpaque(true);
	}

	private void createSidebarButtons(JPanel sidebarpanel, Student student) {
		homebutton = createButton("Home");
		sidebarpanel.add(homebutton);
		btn = homebutton;

		studentsbutton = createButton("Classmates", "Students");
		sidebarpanel.add(studentsbutton);

		subjectbutton = createButton("Subjects");
		sidebarpanel.add(subjectbutton);

		faculitiesbutton = createButton("Faculities");
		sidebarpanel.add(faculitiesbutton);

		assignedsubjectbutton = createButton("Assigned Subject", "Assign Subject");
		sidebarpanel.add(assignedsubjectbutton);

		marksheetbutton = createButton("Mark Sheet", "Marksheet Report");
		sidebarpanel.add(marksheetbutton);

		attandancereportbutton = createButton("Attandance Report");
		sidebarpanel.add(attandancereportbutton);

		createChatButton(sidebarpanel, student);
		createSearchAndNotificationButtons(sidebarpanel, student);
		createMiscButtons(sidebarpanel);
	}

	private void createChatButton(JPanel sidebarpanel, Student student) {
		chatbutton = createButton("Chat");
		chatbutton.setLayout(new BorderLayout());
		sidebarpanel.add(chatbutton);

		int chat = new ChatData().getUndreadMessageCountStudent(student);

		totalnewchatmessage = new JLabel();
		totalnewchatmessage.setSize(60, 30);
		totalnewchatmessage.setFont(new Font("Arial", Font.BOLD, 12));
		totalnewchatmessage.setForeground(Color.white);
		totalnewchatmessage.setHorizontalTextPosition(JLabel.CENTER);
		totalnewchatmessage.setVerticalTextPosition(JLabel.CENTER);
		chatbutton.add(totalnewchatmessage, BorderLayout.LINE_END);

		if (chat > 0) {
			String text = chat > 999 ? "999+" : String.valueOf(chat);
			totalnewchatmessage.setText(text);
			totalnewchatmessage.setVisible(true);
			totalnewchatmessage.setIcon(new ImageIcon(
					messagecount.getScaledInstance(26 + text.length(), 26, Image.SCALE_SMOOTH)
			));
		}
	}

	private void createSearchAndNotificationButtons(JPanel sidebarpanel, Student student) {
		searchbutton = createButton("Search");
		sidebarpanel.add(searchbutton);

		notificationbutton = createButton("Notification");
		notificationbutton.setLayout(new BorderLayout());
		sidebarpanel.add(notificationbutton);

		int notification = new NotificationData().getUnreadNotification(
				student.getUserId(),
				"Student",
				student.getCourceCode(),
				student.getSemorYear(),
				student.getAdmissionDate()
		);

		totalnewnotification = new JLabel();
		totalnewnotification.setSize(60, 30);
		totalnewnotification.setFont(new Font("Arial", Font.BOLD, 12));
		totalnewnotification.setForeground(Color.white);
		totalnewnotification.setHorizontalTextPosition(JLabel.CENTER);
		totalnewnotification.setVerticalTextPosition(JLabel.CENTER);
		notificationbutton.add(totalnewnotification, BorderLayout.LINE_END);

		if (notification > 0) {
			String text = notification > 999 ? "999+" : String.valueOf(notification);
			totalnewnotification.setText(text);
			totalnewnotification.setVisible(true);
			totalnewnotification.setIcon(new ImageIcon(
					messagecount.getScaledInstance(26 + text.length(), 26, Image.SCALE_SMOOTH)
			));
		}
	}

	private void createMiscButtons(JPanel sidebarpanel) {
		myprofilebutton = createButton("My Profile", "Profile");
		sidebarpanel.add(myprofilebutton);

		contactusbutton = createButton("Contact Us");
		sidebarpanel.add(contactusbutton);

		logoutbutton = createButton("logout");
		sidebarpanel.add(logoutbutton);

		exitbutton = createButton("Exit");
		sidebarpanel.add(exitbutton);
	}

	/* ===================== STUDENT SESSION / WINDOW ===================== */

	private void initializeStudentSession(Student student) {
		this.setCollageDetails();
		lastlogin = student.getLastLogin();
		homepanel.setLastLogin(lastlogin);

		student.setLastLogin(TimeUtil.getCurrentTime());
		student.setActiveStatus(true);
		new StudentData().updateStudentData(student, student);
	}

	private void addWindowCloseBehavior() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent windowenent) {
				openPanel(exitbutton);
			}
		});
	}
	public void createpanel()
	{
		
		homepanel=new HomePanel(s);
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
		row+=40;
		return button;
	}
	public void disablepanel() {
		// hide first visible "simple" panel in order
		if (hideFirstVisiblePanel(
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
				attandancereportpanelscroll,
				adminprofilepanel,
				searchpanel,
				notificationpanel
		)) {
			return;
		}

		// special case: chat panel (needs socket handling)
		disableChatPanelIfVisible();
	}

	private boolean hideFirstVisiblePanel(JComponent... panels) {
		if (panels == null) {
			return false;
		}
		for (JComponent panel : panels) {
			if (panel != null && panel.isVisible()) {
				panel.setVisible(false);
				return true;
			}
		}
		return false;
	}

	private void disableChatPanelIfVisible() {
		if (chatmainpanel == null || !chatmainpanel.isVisible()) {
			return;
		}

		try {
			if (chatmainpanel.chatpanel != null &&
					chatmainpanel.chatpanel.subchatpanel != null &&
					chatmainpanel.chatpanel.subchatpanel.socket != null &&
					!chatmainpanel.chatpanel.subchatpanel.socket.isClosed()) {

				chatmainpanel.chatpanel.subchatpanel.socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		chatmainpanel.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		this.openPanel(e.getSource());
	}
	

	public void setCollageDetails()
	{
		studentprofilepiclabel.setIcon(new ImageIcon(s.getRoundedProfilePic(50, 50, 50)));
		studentnamelabel.setText(s.getFullName());
		
	}
	public void openPanel(Object source) {
		if (source == homebutton) {
			openHomePanel();
		} else if (source == subjectbutton) {
			openSubjectPanel();
		} else if (source == studentsbutton) {
			openStudentPanel();
		} else if (source == faculitiesbutton) {
			openFacultyPanel();
		} else if (source == assignedsubjectbutton) {
			openAssignedSubjectPanel();
		} else if (source == marksheetbutton) {
			openMarksheetPanel();
		} else if (source == attandancereportbutton) {
			openAttendanceReportPanel();
		} else if (source == chatbutton) {
			openChatPanel();
		} else if (source == searchbutton) {
			openSearchPanel();
		} else if (source == notificationbutton) {
			openNotificationPanel();
		} else if (source == myprofilebutton) {
			openMyProfilePanel();
		} else if (source == contactusbutton) {
			openContactUsPanel();
		} else if (source == exitbutton) {
			handleExit();
		} else if (source == logoutbutton) {
			handleLogout();
		}
	}

// ==== Panel opening helpers ====

	private void openHomePanel() {
		activeButton(homebutton);
		homepanel = new HomePanel(s);
		homepanel.setLocation(panelx, panely);
		homepanel.setFocusable(true);
		homepanel.setLastLogin(lastlogin);
		contentPane.add(homepanel);
		homepanel.setVisible(true);
	}

	private void openSubjectPanel() {
		activeButton(subjectbutton);
		subjectpanel = new SubjectPanel(this);
		subjectpanel.setLocation(panelx, panely);
		subjectpanel.setFocusable(true);
		contentPane.add(subjectpanel);
		subjectpanel.setVisible(true);
	}

	private void openStudentPanel() {
		activeButton(studentsbutton);
		studentpanel = new StudentPanel(this);
		studentpanel.setLocation(panelx, panely);
		studentpanel.setFocusable(true);
		contentPane.add(studentpanel);
		studentpanel.setVisible(true);
	}

	private void openFacultyPanel() {
		activeButton(faculitiesbutton);
		facultypanel = new FacultyPanel(this);
		facultypanel.setLocation(panelx, panely);
		facultypanel.setFocusable(true);
		contentPane.add(facultypanel);
		facultypanel.setVisible(true);
	}

	private void openAssignedSubjectPanel() {
		activeButton(assignedsubjectbutton);
		assignsubjectpanel = new AssignSubjectPanel(this);
		assignsubjectpanel.setLocation(panelx, panely);
		assignsubjectpanel.setFocusable(true);
		contentPane.add(assignsubjectpanel);
		assignsubjectpanel.setVisible(true);
	}

	private void openMarksheetPanel() {
		activeButton(marksheetbutton);
		marksheetpanel = new MarkSheetPanel(this, s);
		marksheetpanel.setVisible(true);

		marksheetpanelscroll = new JScrollPane(
				marksheetpanel,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
		);
		marksheetpanelscroll.getVerticalScrollBar().setUnitIncrement(16);
		marksheetpanelscroll.setBounds(panelx, panely, 1116, 705);
		contentPane.add(marksheetpanelscroll);
		marksheetpanelscroll.setVisible(true);
	}

	private void openAttendanceReportPanel() {
		activeButton(attandancereportbutton);
		attandancereportpanel = new AttandanceReportPanel(this);
		attandancereportpanel.setVisible(true);

		attandancereportpanelscroll = new JScrollPane(
				attandancereportpanel,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
		);
		attandancereportpanelscroll.setBounds(panelx, panely, 1116, 705);
		attandancereportpanelscroll.setName("Attadance Report Panel Scroll");
		attandancereportpanelscroll.getVerticalScrollBar().setUnitIncrement(16);
		attandancereportpanelscroll.setVisible(true);
		contentPane.add(attandancereportpanelscroll);

		for (Component c : attandancereportpanelscroll.getComponents()) {
			c.setBackground(Color.white);
		}
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
		notificationpanel.setLocation(panelx, panely);
		notificationpanel.setFocusable(true);
		notificationpanel.setVisible(true);
		contentPane.add(notificationpanel);
	}

	private void openMyProfilePanel() {
		activeButton(myprofilebutton);
		viewstudentpanel = new ViewStudentPanel(s, this);
		viewstudentpanel.setLocation(panelx, 0);
		viewstudentpanel.setFocusable(true);
		viewstudentpanel.setVisible(true);
		contentPane.add(viewstudentpanel);
	}

	private void openContactUsPanel() {
		activeButton(contactusbutton);
		adminprofilepanel = new AdminProfilePanel();
		adminprofilepanel.setLocation(panelx, panely);
		adminprofilepanel.setFocusable(true);
		adminprofilepanel.setVisible(true);
		contentPane.add(adminprofilepanel);
	}

// ==== Exit / Logout helpers ====

	private void handleExit() {
		int result = JOptionPane.showConfirmDialog(
				null,
				"Do you want to exit this application ?",
				"Exit",
				JOptionPane.INFORMATION_MESSAGE
		);
		if (result != JOptionPane.YES_OPTION) {
			return;
		}

		try {
			s.setActiveStatus(false);
			timer.stop();
			new StudentData().setActiveStatus(false, s.getUserId());
			DataBaseConnection.closeConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		disablepanel();
		System.exit(0);
	}

	private void handleLogout() {
		int result = JOptionPane.showConfirmDialog(
				null,
				"Do you want to logout this application ?",
				"Logout",
				JOptionPane.INFORMATION_MESSAGE
		);
		if (result != JOptionPane.YES_OPTION) {
			return;
		}

		s.setActiveStatus(false);
		timer.stop();
		new StudentData().setActiveStatus(false, s.getUserId());

		LoginPageFrame loginpageframe = new LoginPageFrame();
		loginpageframe.setVisible(true);
		loginpageframe.setLocationRelativeTo(null);

		disablepanel();
		this.dispose();
	}
}