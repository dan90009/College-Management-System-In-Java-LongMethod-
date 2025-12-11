package collegeapplication.admin;

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
import java.io.File;
import java.io.IOException;

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

import collegeapplication.chat.ChatData;
import collegeapplication.chat.ChatMainPanel;
import collegeapplication.common.DataBaseConnection;
import collegeapplication.common.HomePanel;
import collegeapplication.common.SearchPanel;
import collegeapplication.common.TimeUtil;
import collegeapplication.common.UsersPanel;
import collegeapplication.cource.CourcePanel;
import collegeapplication.faculty.FacultyPanel;
import collegeapplication.faculty.ViewFacultyPanel;
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
 * Title : AdminMain.java
 * Created by : Ajaysinh Rathod
 * Purpose : Main admin frame
 * Mail : ajaysinhrathod1290@gmail.com
 */
@SuppressWarnings("serial")
public class AdminMain extends JFrame  implements ActionListener
{

	
	public JPanel contentPane;
	private JLabel collagenamelabel;
	private JLabel profilepiclabel;
	private JPanel profilepanel;
	private JButton homebutton;
	private JButton courcebutton;
	private JButton studentsbutton;
	private JButton subjectbutton;
	private JButton faculitiesbutton;
	private JButton usersbutton;
	private JButton entermarksbutton;
	private JButton assignsubjectbutton;
	private JButton markattandancebutton;
	private JButton attandancereportbutton;
	private JButton searchbutton;
	private JButton exitbutton;
	private JButton btn;
	private JButton adminprofilebutton;
	
	private Color buttonbcolor=Color.DARK_GRAY;
	private Color buttonfcolor=Color.LIGHT_GRAY;
	private Font buttonfont=new Font("Tw Cen MT", Font.PLAIN, 20);
	private CourcePanel courcepanel;
	private SubjectPanel subjectpanel;
	private HomePanel homepanel;
	
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
	public MarkSheetReportPanel marksheetreportpanel;
	public JScrollPane marksheetreportpanelscroll;
	public FacultyPanel facultypanel;
	public AdminProfilePanel adminprofilepanel;
	public SearchPanel searchpanel;
	public ChatMainPanel chatmainpanel;
	public UsersPanel userspanel;
	
	public int panely=0,panelx=250;
	
	private Admin a;
	private String lastlogin;
	private JButton chatbutton;
	private int row=0;
	private JButton logoutbutton;
	private Timer timer;
	private JButton marksheetreportbutton;
	private JLabel totalnewchatmessage;
	private Image messagecount;
	private int chat;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				
					if(DataBaseConnection.checkconnection())
					{
						AdminMain frame = new AdminMain();	
					    frame.setVisible(true);
					}
					else
					{
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						JOptionPane.showMessageDialog(null, "You Are Not Connected To DataBase","Error",JOptionPane.ERROR_MESSAGE);
					}
					new Thread().start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminMain() {
	
		
		 a=new AdminData().getAdminData();
		 ActionListener setActive=new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0) 
				{
					int result=new AdminData().setActiveStatus(a.getActiveStatus());
					
					if(result>0)
					{
						chat=new ChatData().getUndreadMessageCountAdmin();
						if(chat>0)
						{
							totalnewchatmessage.setText(chat>999?"999+":chat+"");
							totalnewchatmessage.setVisible(true);
							totalnewchatmessage.setIcon(new ImageIcon(messagecount.getScaledInstance(26+totalnewchatmessage.getText().length(), 26, Image.SCALE_SMOOTH)));
						}
						else 
						{
							totalnewchatmessage.setVisible(false);
						}
					}
					
				}
				
			};
			timer=new Timer(2000,setActive);
			timer.start();
			
		Color bgColor =new Color(32,178,170);
		Color frColor=Color.white;

		UIManager.put("ComboBoxUI", "com.sun.java.swing.plaf.windows.WindowsComboBoxUI");
	    UIManager.put("ComboBox.selectionBackground", new ColorUIResource(bgColor));
	    UIManager.put("ComboBox.background", new ColorUIResource(Color.white));
	    UIManager.put("ComboBox.foreground", new ColorUIResource(Color.DARK_GRAY));
	    UIManager.put("ComboBox.selectionForeground", new ColorUIResource(frColor));
	    UIManager.put("ScrollBarUI", "com.sun.java.swing.plaf.windows.WindowsScrollBarUI");
	  
	    try
		{
			messagecount=ImageIO.read(new File("./assets/messagecount.png"));
		}
		catch(IOException exp)
		{
			exp.printStackTrace();
		}
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

		 profilepanel = new JPanel();
		 profilepanel.setBounds(5, 7, 240, 63);
		 contentPane.add(profilepanel);
		 profilepanel.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.LIGHT_GRAY));
		 profilepanel.setBackground(Color.DARK_GRAY);
		 profilepanel.setLayout(null);
		 
		 collagenamelabel = new JLabel();
		 collagenamelabel.setForeground(Color.WHITE);
		 collagenamelabel.setHorizontalAlignment(SwingConstants.LEFT);
		 collagenamelabel.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		 collagenamelabel.setBackground(Color.DARK_GRAY);
		 collagenamelabel.setText("Adminstrator");
		 collagenamelabel.setOpaque(true);
		 collagenamelabel.setBounds(65, 5, 171, 36);
		 profilepanel.add(collagenamelabel);
		 
		 profilepiclabel = new JLabel();
		 profilepiclabel.setBounds(5, 0, 50, 50);
		 profilepanel.add(profilepiclabel);
		 profilepiclabel.setHorizontalAlignment(SwingConstants.CENTER);
		 profilepiclabel.setBackground(Color.DARK_GRAY);
		 profilepiclabel.setBorder(new LineBorder(Color.black, 0));
		 profilepiclabel.setOpaque(true);
		 
		createHomepanel();
		
		
		//creating side bar panel
		JPanel sidebarpanel = new JPanel();
		sidebarpanel.setBorder(new MatteBorder(0, 0, 0, 2, (Color) new Color(64, 64, 64)));
		sidebarpanel.setBackground(Color.DARK_GRAY);
		sidebarpanel.setBounds(5, 75, 240, 654);
		contentPane.add(sidebarpanel);
		sidebarpanel.setLayout(null);
		
		//Adding buttons to sidebar
		homebutton =createButton("Home");
		sidebarpanel.add(homebutton);
		btn=homebutton;
		
		courcebutton = createButton("Cources");
		sidebarpanel.add(courcebutton);
		
		studentsbutton =createButton("Students");
		sidebarpanel.add(studentsbutton);
		
		subjectbutton = createButton("Subjects");
		sidebarpanel.add(subjectbutton);
		
		faculitiesbutton = createButton("Faculities");
		sidebarpanel.add(faculitiesbutton);
		
		assignsubjectbutton = createButton("Assign Subject");
		sidebarpanel.add(assignsubjectbutton);
		
		entermarksbutton = createButton("Enter Marks");
		sidebarpanel.add(entermarksbutton);
		
		marksheetreportbutton = createButton("Marksheet Report");
		sidebarpanel.add(marksheetreportbutton);
		
		markattandancebutton = createButton("Mark Attandance");
		sidebarpanel.add(markattandancebutton);
		
		attandancereportbutton =createButton("Attandance Report");
		sidebarpanel.add(attandancereportbutton);
		
		chatbutton = createButton("Chat");
		chatbutton.setLayout(new BorderLayout());
		sidebarpanel.add(chatbutton);
		chat=new ChatData().getUndreadMessageCountAdmin();
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
//		ActionListener refreshchat=e->
//		{
//		
//		};
//		Timer activechattimer=new Timer(2000,refreshchat);
//		activechattimer.start();
//		
		searchbutton = createButton("Search");
		sidebarpanel.add(searchbutton);
		
		usersbutton = createButton("Users");
		sidebarpanel.add(usersbutton);
		
		adminprofilebutton = createButton("Admin Profile","Profile");
		sidebarpanel.add(adminprofilebutton);

		logoutbutton = createButton("Logout");
		sidebarpanel.add(logoutbutton);
		
		exitbutton = createButton("Exit");
		sidebarpanel.add(exitbutton);

		activeButton(homebutton);
		homepanel.setVisible(true);
		
		this.setCollageDetails();
		lastlogin=a.getLastLogin();
		homepanel.setLastLogin(lastlogin);
		a.setLastLogin(TimeUtil.getCurrentTime());
		a.setActiveStatus(true);
		new AdminData().updateAdminDetails(a);
		
		
	        this.addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosing(final WindowEvent windowenent) {
	            	openPanel(exitbutton);
	            	
	            }
	        });
	        
	        
		
	}
	public void createHomepanel()
	{
		homepanel=new HomePanel(a);
		homepanel.setLocation(panelx,panely);
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
	private static class PanelHandler {
		JComponent panel;
		Runnable beforeHide;

		PanelHandler(JComponent panel, Runnable beforeHide) {
			this.panel = panel;
			this.beforeHide = beforeHide;
		}
	}
	public void disablepanel() {
		List<PanelHandler> handlers = getPanelHandlers();
		disableFirstVisiblePanel(handlers);
	}
	private List<PanelHandler> getPanelHandlers() {
		List<PanelHandler> handlers = new ArrayList<>();

		handlers.add(new PanelHandler(homepanel, null));
		handlers.add(new PanelHandler(courcepanel, null));
		handlers.add(new PanelHandler(subjectpanel, null));
		handlers.add(new PanelHandler(studentpanel, null));
		handlers.add(new PanelHandler(viewstudentpanel, null));
		handlers.add(new PanelHandler(facultypanel, null));
		handlers.add(new PanelHandler(viewfacultypanel, null));
		handlers.add(new PanelHandler(assignsubjectpanel, null));
		handlers.add(new PanelHandler(entermarkspanelscroll, null));
		handlers.add(new PanelHandler(marksheetpanelscroll, null));
		handlers.add(new PanelHandler(markattandancepanelscroll, null));
		handlers.add(new PanelHandler(attandancereportpanelscroll, null));
		handlers.add(new PanelHandler(marksheetreportpanelscroll, null));
		handlers.add(new PanelHandler(adminprofilepanel, null));
		handlers.add(new PanelHandler(searchpanel, null));

		// Chat panel needs special handling before hiding.
		handlers.add(new PanelHandler(chatmainpanel, this::closeChatSocketIfNeeded));

		handlers.add(new PanelHandler(userspanel, null));

		return handlers;
	}
	private void disableFirstVisiblePanel(List<PanelHandler> handlers) {
		for (PanelHandler handler : handlers) {
			if (handler.panel != null && handler.panel.isVisible()) {
				if (handler.beforeHide != null) {
					handler.beforeHide.run();
				}
				handler.panel.setVisible(false);
				break;
			}
		}
	}
	private void closeChatSocketIfNeeded() {
		if (chatmainpanel == null || chatmainpanel.chatpanel == null) {
			return;
		}
		try {
			if (chatmainpanel.chatpanel.subchatpanel != null &&
					chatmainpanel.chatpanel.subchatpanel.socket != null &&
					!chatmainpanel.chatpanel.subchatpanel.socket.isClosed()) {

				chatmainpanel.chatpanel.subchatpanel.socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) 
	{
		openPanel(e.getSource());
	}
	// inside AdminMain

	private Map<Object, Runnable> panelActions;

	public void openPanel(Object source) {
		ensurePanelActionsInitialized();
		Runnable action = panelActions.get(source);
		if (action != null) {
			// Always disable current panel before opening a new one
			disablepanel();
			action.run();
		}
	}

	private void ensurePanelActionsInitialized() {
		if (panelActions != null) {
			return;
		}
		panelActions = new HashMap<>();

		panelActions.put(homebutton, this::openHomePanel);
		panelActions.put(courcebutton, this::openCourcePanel);
		panelActions.put(subjectbutton, this::openSubjectPanel);
		panelActions.put(studentsbutton, this::openStudentsPanel);
		panelActions.put(faculitiesbutton, this::openFacultiesPanel);
		panelActions.put(assignsubjectbutton, this::openAssignSubjectPanel);
		panelActions.put(entermarksbutton, this::openEnterMarksPanel);
		panelActions.put(markattandancebutton, this::openMarkAttendancePanel);
		panelActions.put(attandancereportbutton, this::openAttendanceReportPanel);
		panelActions.put(marksheetreportbutton, this::openMarksheetReportPanel);
		panelActions.put(usersbutton, this::openUsersPanel);
		panelActions.put(chatbutton, this::openChatPanel);
		panelActions.put(searchbutton, this::openSearchPanel);
		panelActions.put(adminprofilebutton, this::openAdminProfilePanel);
		panelActions.put(exitbutton, this::confirmExit);
		panelActions.put(logoutbutton, this::confirmLogout);
	}

// --- Panel open helpers ---

	private void openHomePanel() {
		activeButton(homebutton);
		homepanel = new HomePanel(a);
		homepanel.setLocation(panelx, panely);
		homepanel.setFocusable(true);
		contentPane.add(homepanel);
		homepanel.setVisible(true);
		homepanel.setLastLogin(lastlogin);
	}

	private void openCourcePanel() {
		activeButton(courcebutton);
		courcepanel = new CourcePanel();
		courcepanel.setLocation(panelx, panely);
		courcepanel.setFocusable(true);
		contentPane.add(courcepanel);
		courcepanel.setVisible(true);
	}

	private void openSubjectPanel() {
		activeButton(subjectbutton);
		subjectpanel = new SubjectPanel(this);
		subjectpanel.setLocation(panelx, panely);
		subjectpanel.setFocusable(true);
		contentPane.add(subjectpanel);
		subjectpanel.setVisible(true);
	}

	private void openStudentsPanel() {
		activeButton(studentsbutton);
		studentpanel = new StudentPanel(this);
		studentpanel.setLocation(panelx, panely);
		studentpanel.setVisible(true);
		studentpanel.setFocusable(true);
		contentPane.add(studentpanel);
	}

	private void openFacultiesPanel() {
		activeButton(faculitiesbutton);
		facultypanel = new FacultyPanel(this);
		facultypanel.setLocation(panelx, panely);
		facultypanel.setVisible(true);
		facultypanel.setFocusable(true);
		contentPane.add(facultypanel);
	}

	private void openAssignSubjectPanel() {
		activeButton(assignsubjectbutton);
		assignsubjectpanel = new AssignSubjectPanel(this);
		assignsubjectpanel.setLocation(panelx, panely);
		assignsubjectpanel.setVisible(true);
		assignsubjectpanel.setFocusable(true);
		contentPane.add(assignsubjectpanel);
	}

	private void openEnterMarksPanel() {
		activeButton(entermarksbutton);
		entermarkspanel = new EnterMarksPanel();
		entermarkspanel.setVisible(true);
		entermarkspanelscroll = new JScrollPane(entermarkspanel,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		entermarkspanelscroll.setBounds(panelx, panely, 1116, 705);
		entermarkspanelscroll.setVisible(true);
		entermarkspanelscroll.getVerticalScrollBar().setUnitIncrement(80);
		contentPane.add(entermarkspanelscroll);
		for (Component c : entermarkspanelscroll.getComponents()) {
			c.setBackground(Color.white);
		}
	}

	private void openMarkAttendancePanel() {
		activeButton(markattandancebutton);
		markattandancepanel = new MarkAttandancePanel(this);
		markattandancepanel.setVisible(true);
		markattandancepanelscroll = new JScrollPane(markattandancepanel,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		markattandancepanelscroll.setBounds(panelx, panely, 1116, 705);
		markattandancepanelscroll.setVisible(true);
		markattandancepanelscroll.getVerticalScrollBar().setUnitIncrement(80);
		contentPane.add(markattandancepanelscroll);
		for (Component c : markattandancepanelscroll.getComponents()) {
			c.setBackground(Color.white);
		}
	}

	private void openAttendanceReportPanel() {
		activeButton(attandancereportbutton);
		attandancereportpanel = new AttandanceReportPanel(this);
		attandancereportpanel.setVisible(true);
		attandancereportpanelscroll = new JScrollPane(attandancereportpanel,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		attandancereportpanelscroll.setBounds(panelx, panely, 1116, 705);
		attandancereportpanelscroll.setVisible(true);
		attandancereportpanelscroll.setName("Attadance Report Panel Scroll");
		attandancereportpanelscroll.getVerticalScrollBar().setUnitIncrement(80);
		contentPane.add(attandancereportpanelscroll);
		for (Component c : attandancereportpanelscroll.getComponents()) {
			c.setBackground(Color.white);
		}
	}

	private void openMarksheetReportPanel() {
		activeButton(marksheetreportbutton);
		marksheetreportpanel = new MarkSheetReportPanel(this);
		marksheetreportpanel.setVisible(true);
		marksheetreportpanelscroll = new JScrollPane(marksheetreportpanel,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		marksheetreportpanelscroll.setBounds(panelx, panely, 1116, 705);
		marksheetreportpanelscroll.setVisible(true);
		marksheetreportpanelscroll.setName("Marksheet Report Panel Scroll");
		marksheetreportpanelscroll.getVerticalScrollBar().setUnitIncrement(80);
		contentPane.add(marksheetreportpanelscroll);
		for (Component c : marksheetreportpanelscroll.getComponents()) {
			c.setBackground(Color.white);
		}
	}

	private void openUsersPanel() {
		activeButton(usersbutton);
		userspanel = new UsersPanel(this);
		userspanel.setVisible(true);
		userspanel.setLocation(this.panelx, this.panely);
		contentPane.add(userspanel);
	}

	private void openChatPanel() {
		activeButton(chatbutton);
		chatmainpanel = new ChatMainPanel(this);
		chatmainpanel.setLocation(this.panelx, this.panely);
		chatmainpanel.setVisible(true);
		contentPane.add(chatmainpanel);
	}

	private void openSearchPanel() {
		activeButton(searchbutton);
		searchpanel = new SearchPanel(this);
		searchpanel.setLocation(this.panelx, this.panely);
		searchpanel.setVisible(true);
		contentPane.add(searchpanel);
	}

	private void openAdminProfilePanel() {
		activeButton(adminprofilebutton);
		adminprofilepanel = new AdminProfilePanel(this);
		adminprofilepanel.setLocation(panelx, panely);
		adminprofilepanel.setVisible(true);
		adminprofilepanel.setFocusable(true);
		contentPane.add(adminprofilepanel);
	}

	private void confirmExit() {
		int result = JOptionPane.showConfirmDialog(null,
				"Do you want to exit this application ?",
				"Exit",
				JOptionPane.INFORMATION_MESSAGE);
		if (result == JOptionPane.YES_OPTION) {
			a.setActiveStatus(false);
			timer.stop();
			new AdminData().setActiveStatus(false);
			this.disablepanel();
			DataBaseConnection.closeConnection();
			System.exit(0);
		}
	}

	private void confirmLogout() {
		int result = JOptionPane.showConfirmDialog(null,
				"Do you want to logout this application ?",
				"Logout",
				JOptionPane.INFORMATION_MESSAGE);
		if (result == JOptionPane.YES_OPTION) {
			a.setActiveStatus(false);
			timer.stop();
			new AdminData().setActiveStatus(false);
			LoginPageFrame loginpageframe = new LoginPageFrame();
			loginpageframe.setVisible(true);
			loginpageframe.setLocationRelativeTo(null);
			this.disablepanel();
			this.dispose();
		}
	}



	public void setCollageDetails()
	{
		 a=new AdminData().getAdminData();
		 profilepiclabel.setIcon(new ImageIcon(a.getRoundedProfilePic(50, 50, 50)));
	}
}
