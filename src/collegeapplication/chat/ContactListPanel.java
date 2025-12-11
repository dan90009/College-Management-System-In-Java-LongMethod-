package collegeapplication.chat;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import collegeapplication.admin.Admin;
import collegeapplication.admin.AdminData;
import collegeapplication.admin.AdminMain;
import collegeapplication.common.ImageUtil;
import collegeapplication.cource.CourceData;
import collegeapplication.faculty.Faculty;
import collegeapplication.faculty.FacultyData;
import collegeapplication.faculty.FacultyMain;
import collegeapplication.student.Student;
import collegeapplication.student.StudentData;
import collegeapplication.student.StudentMain;

@SuppressWarnings("serial")
/*
 * Title : ContactListPanel.java
 * Created by : Ajaysinh Rathod
 * Purpose : To display all the contacts of the given user
 * Mail : ajaysinhrathod1290@gmail.com
 */
public class ContactListPanel extends JPanel implements ActionListener {

	/**
	 * Create the panel.
	 */
	int total=0;
	int totalmessages=0;
	public  ArrayList<JPanel> contactlist;
	public static ArrayList<ContactInfo> contactinfo;
	ChatMainPanel chatmainpanel;
	public int location=0;
	public String userid="";
	ChatData chatdata=new ChatData();
	Timer timer=new Timer(2000,this);
	Image messagecount =null;
	

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(330,location);
	}
	private ContactListPanel()
	{
		setLayout(null);
		setBackground(Color.WHITE);
		setSize(330,705);
		contactinfo=new ArrayList<ContactInfo>();
		contactlist=new ArrayList<JPanel>();
		try
		{
			messagecount=ImageIO.read(new File("./assets/messagecount.png"));
		}
		catch(IOException exp)
		{
			exp.printStackTrace();
		}
	}
	public ContactListPanel(AdminMain am,ChatMainPanel chatmainpanel) {

		this();
		this.chatmainpanel=chatmainpanel;
		userid="Admin";
		getAllcontacts();
		timer.start();
		
		
	}
	public ContactListPanel(FacultyMain fm,ChatMainPanel chatmainpanel) {
		
		this();
		this.chatmainpanel=chatmainpanel;
		userid=fm.f.getFacultyId()+"";
		getContacts(fm.f.getCourceCode(),fm.f.getSemorYear(),""," and facultyid!="+fm.f.getFacultyId()+"");
		timer.start();
		
		
	}
	public ContactListPanel(StudentMain sm,ChatMainPanel chatmainpanel) {
		
		this();
		this.chatmainpanel=chatmainpanel;
		userid=sm.s.getUserId();
		getContacts(sm.s.getCourceCode(),sm.s.getSemorYear()," and s.userid!='"+sm.s.getUserId()+"' ","");
		timer.start();
		
		
	}
	//For Faculty or Student
	public void getContacts(String courcecode,int sem,String slastcondition,String flastcondition)
	{
		//Adding groups
		{
					Admin a=new AdminData().getAdminData();
					addLabel("Groups");
					{
						int members=new StudentData().getTotalStudentInCource(courcecode, sem)+new FacultyData().getTotalFaculaty(courcecode, sem)+1;
						if(members>1)
						{
							
							Group group=new Group();
							group.setCourceCode(courcecode);
							group.setSem(sem);
							group.setMembers(members);
							group.setImage(a.getProfilePic());
							String semoryear=new CourceData().getsemoryear(courcecode);
							group.setGroupName(courcecode+" "+semoryear+"-"+sem+" Official Group");
							
							JPanel contactlistpanel=createPanel(a.getProfilePic(),group.getGroupName(),group.getGroupName(),false);
							contactlistpanel.setName(group.getGroupName()+"#"+group.getGroupName()+"#"+total);
							contactlistpanel.setLocation(0, location);
							location+=60;
							total++;
							contactlist.add(contactlistpanel);
							add(contactlistpanel);
							
							
							ContactInfo cf=new ContactInfo();
							cf.setGroup(group);
							contactinfo.add(cf);
							
						}
					}
					if(!flastcondition.isEmpty())
					{
						int members=new FacultyData().getTotalFaculaty(courcecode, sem);
						if(members>1)
						{
							
							Group group=new Group();
							group.setCourceCode(courcecode);
							group.setSem(sem);
							group.setMembers(members);
							group.setImage(a.getProfilePic());
							String semoryear=new CourceData().getsemoryear(courcecode);
							group.setGroupName(courcecode+" "+semoryear+"-"+sem+" Faculties Group");
							
							JPanel contactlistpanel=createPanel(a.getProfilePic(),group.getGroupName(),group.getGroupName(),false);
							contactlistpanel.setName(group.getGroupName()+"#"+group.getGroupName()+"#"+total);
							contactlistpanel.setLocation(0, location);
							location+=60;
							total++;
							contactlist.add(contactlistpanel);
							add(contactlistpanel);
							
							
							ContactInfo cf=new ContactInfo();
							cf.setGroup(group);
							contactinfo.add(cf);
							
						}
					}
					if(!slastcondition.isEmpty())
					{
						int members=new StudentData().getTotalStudentInCource(courcecode, sem);
						if(members>1)
						{
							
							Group group=new Group();
							group.setCourceCode(courcecode);
							group.setSem(sem);
							group.setMembers(members);
							group.setImage(a.getProfilePic());
							String semoryear=new CourceData().getsemoryear(courcecode);
							group.setGroupName(courcecode+" "+semoryear+"-"+sem+" Students Group");
							
							JPanel contactlistpanel=createPanel(a.getProfilePic(),group.getGroupName(),group.getGroupName(),false);
							contactlistpanel.setName(group.getGroupName()+"#"+group.getGroupName()+"#"+total);
							contactlistpanel.setLocation(0, location);
							location+=60;
							total++;
							contactlist.add(contactlistpanel);
							add(contactlistpanel);
							
							
							ContactInfo cf=new ContactInfo();
							cf.setGroup(group);
							contactinfo.add(cf);
							
						}
					}
				
			
		}
		//Adding  Admin contact
		{
			addLabel("Admin");
			Admin a=new AdminData().getAdminData();
			JPanel contactlistpanel=createPanel(a.getProfilePic(),"Principal","Admin",a.getActiveStatus());
			contactlistpanel.setName("Principal-Admin"+"#"+"Admin"+"#"+total);
			total++;
			contactlistpanel.setLocation(0, location);
			location+=60;
			contactlist.add(contactlistpanel);
			add(contactlistpanel);
			ContactInfo cf=new ContactInfo();
			cf.setAdmin(new AdminData().getAdminData());
			contactinfo.add(cf);
		}
		
		//Adding Faculties
		{
			
				ArrayList<Faculty> flist=new FacultyData().getTotalFaculty(" where courcecode='"+courcecode+"' and semoryear="+sem+flastcondition);
				if(flist.size()>0)
				{
					addLabel("Faculties");
				}
				for(Faculty f: flist)
				{
					JPanel contactlistpanel=createPanel(f.getProfilePic(),f.getFacultyName()+"-"+f.getFacultyId(),f.getFacultyId()+"",f.getActiveStatus());
					contactlistpanel.setName(f.getFacultyName()+"-"+f.getFacultyId()+" faculty#"+f.getFacultyId()+"#"+total);
					total++;
					contactlistpanel.setLocation(0, location);
					location+=60;
					contactlist.add(contactlistpanel);
					add(contactlistpanel);
					ContactInfo cf=new ContactInfo();
					cf.setFaculty(f);
					contactinfo.add(cf);
				}
		}
		
//		Adding Students
		{
			
			ArrayList<Student> slist=new StudentData().getStudentsDetails(" where courcecode='"+courcecode+"' and semoryear="+sem+slastcondition);
			if(slist.size()>0)
			{
				addLabel("Students");
			}
			for(Student s: slist)
			{
				JPanel contactlistpanel=createPanel(s.getProfilePic(),s.getFullName()+"-"+s.getUserId(),s.getUserId(),s.getActiveStatus());
				contactlistpanel.setName(s.getFullName()+"-"+s.getUserId()+" student#"+s.getUserId()+"#"+total);
				total++;
				contactlistpanel.setLocation(0, location);
				location+=60;
				contactlist.add(contactlistpanel);
				add(contactlistpanel);
				ContactInfo cf=new ContactInfo();
				cf.setStudent(s);
				contactinfo.add(cf);
			}
			
		}	
	}
	
	//For Admin
	public void getAllcontacts()
	{

		//Adding groups
		{
			String[] str=new CourceData().getCourcecode();
			Admin a=new AdminData().getAdminData();
			boolean added=false;		
			for(String cource:str)
			{
				
				
				int sem=new CourceData().getTotalsemoryear(new CourceData().getcourcename(cource));
				String semoryear=new CourceData().getsemoryear(cource);
				for(int i=1; i<=sem; i++)
				{
					
					int members=new StudentData().getTotalStudentInCource(cource, i)+new FacultyData().getTotalFaculaty(cource, i)+1;
					if(members>1)
					{
						if(!added)
						{
							addLabel("Groups");	
							added=true;
						}
						Group group=new Group();
						group.setCourceCode(cource);
						group.setSem(i);
						group.setMembers(members);
						group.setImage(a.getProfilePic());
						group.setGroupName(cource+" "+semoryear+"-"+i+" Official Group");
						
					JPanel contactlistpanel=createPanel(a.getProfilePic(),group.getGroupName(),group.getGroupName(),false);
					contactlistpanel.setName(group.getGroupName()+"#"+group.getGroupName()+"#"+total);
					contactlistpanel.setLocation(0, location);
					location+=60;
					total++;
					contactlist.add(contactlistpanel);
					add(contactlistpanel);
					ContactInfo cf=new ContactInfo();
					cf.setGroup(group);
					
					contactinfo.add(cf);
					}
				}
			}
		}
		
		//Adding Faculties
		{
			
				ArrayList<Faculty> flist=new FacultyData().getTotalFaculty("");
				if(flist.size()>0)
				{
					addLabel("Faculties");
					
				}
				for(Faculty f: flist)
				{
					JPanel contactlistpanel=createPanel(f.getProfilePic(),f.getFacultyName()+"-"+f.getFacultyId(),f.getFacultyId()+"",f.getActiveStatus());
					contactlistpanel.setName(f.getFacultyName()+"-"+f.getFacultyId()+"faculty#"+f.getFacultyId()+"#"+total);
					contactlistpanel.setLocation(0, location);
					location+=60;
					total++;
					contactlist.add(contactlistpanel);
					add(contactlistpanel);
					ContactInfo cf=new ContactInfo();
					cf.setFaculty(f);
					contactinfo.add(cf);
				}
		}
		
//		Adding Students
		{
			
			ArrayList<Student> slist=new StudentData().getStudentsDetails("");
			if(slist.size()>0)
			{
				addLabel("Students");
			}
			for(Student s: slist)
			{
				JPanel contactlistpanel=createPanel(s.getProfilePic(),s.getFullName()+"-"+s.getUserId(),s.getUserId(),s.getActiveStatus());
				contactlistpanel.setName(s.getFullName()+"-"+s.getUserId()+" student#"+s.getUserId()+"#"+total);
				contactlistpanel.setLocation(0, location);
				location+=60;
				total++;
				contactlist.add(contactlistpanel);
				add(contactlistpanel);
				ContactInfo cf=new ContactInfo();
				cf.setStudent(s);
				contactinfo.add(cf);
			}
			
		}	
		
	}
	public void addLabel(String name)
	{
		JLabel grouplabel=new JLabel(name);
		grouplabel.setFont(new Font("Segoe UI",Font.PLAIN,17));
		grouplabel.setForeground(new Color(25,25,25));
		grouplabel.setBackground(new Color(245,245,245));
		grouplabel.setBounds(0, location,330,25);
		location+=25;
		grouplabel.setOpaque(true);
		grouplabel.setBorder(new EmptyBorder(3,15,3,240));
		add(grouplabel);
	}
	public JPanel createPanel(Image image, String username, String userid, boolean isactive) {
		JPanel usernamepanel = buildBaseUserPanel(image, username, isactive);
		configureMessagePreview(usernamepanel, userid);
		attachContactPanelMouseListener(usernamepanel);
		return usernamepanel;
	}

	private JPanel buildBaseUserPanel(Image image, String username, boolean isactive) {
		JPanel usernamepanel = new JPanel();
		usernamepanel.setLayout(null);
		usernamepanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		usernamepanel.setSize(330, 60);
		usernamepanel.setBackground(Color.white);

		BufferedImage profilepic = ImageUtil.toBufferedImage(
				image.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		profilepic = ImageUtil.makeRoundedCorner(profilepic, 50);
		JLabel profilepiclabel = new JLabel(new ImageIcon(profilepic));
		profilepiclabel.setLocation(10, 5);
		profilepiclabel.setSize(50, 50);
		usernamepanel.add(profilepiclabel);

		JLabel onlinestatus = new JLabel(new ImageIcon("./assets/onlinestatus.png"));
		onlinestatus.setBounds(45, 40, 15, 15);
		onlinestatus.setName("onlinestatus");
		onlinestatus.setVisible(isactive);
		usernamepanel.add(onlinestatus);

		JLabel usernamelabel = new JLabel(username);
		usernamelabel.setName("username");
		usernamelabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
		usernamelabel.setLocation(70, 5);
		usernamelabel.setSize(180, 30);
		usernamepanel.add(usernamelabel);

		JLabel messagetimelabel = new JLabel();
		messagetimelabel.setForeground(new Color(30, 178, 170));
		messagetimelabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		messagetimelabel.setLocation(250, 6);
		messagetimelabel.setName("messagetime");
		messagetimelabel.setSize(70, 30);
		messagetimelabel.setHorizontalAlignment(JLabel.RIGHT);
		usernamepanel.add(messagetimelabel);

		JLabel totalnewmessages = new JLabel();
		totalnewmessages.setLocation(290, 30);
		totalnewmessages.setText("0");
		totalnewmessages.setName("totalnewmessages");
		totalnewmessages.setSize(60, 22);
		totalnewmessages.setFont(new Font("Arial", Font.BOLD, 10));
		totalnewmessages.setForeground(Color.white);
		totalnewmessages.setHorizontalTextPosition(JLabel.CENTER);
		totalnewmessages.setVerticalTextPosition(JLabel.CENTER);
		usernamepanel.add(totalnewmessages);

		JLabel lastlabel = new JLabel();
		lastlabel.setForeground(Color.gray);
		lastlabel.setName("lastmessage");
		lastlabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lastlabel.setBackground(Color.white);
		lastlabel.setSize(220, 30);
		lastlabel.setLocation(70, 25);
		usernamepanel.add(lastlabel);

		return usernamepanel;
	}

	private void configureMessagePreview(JPanel usernamepanel, String userid) {
		JLabel lastlabel = (JLabel) findComponentByName(usernamepanel, "lastmessage");
		JLabel messagetimelabel = (JLabel) findComponentByName(usernamepanel, "messagetime");
		JLabel totalnewmessages = (JLabel) findComponentByName(usernamepanel, "totalnewmessages");

		if (userid == null || userid.isEmpty()) {
			lastlabel.setText("Start new Conversion");
			totalnewmessages.setVisible(false);
			return;
		}

		NewMessage newmessage = chatdata.getNewMessages(this.userid, userid);
		if (newmessage.total == 0) {
			messagetimelabel.setForeground(Color.gray);
			lastlabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			lastlabel.setForeground(Color.gray);
			totalnewmessages.setVisible(false);
			messagetimelabel.setText(newmessage.messagetime);
		} else {
			lastlabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
			lastlabel.setForeground(Color.DARK_GRAY);
			totalnewmessages.setVisible(true);
			totalnewmessages.setText(newmessage.total + "");
			messagetimelabel.setText(newmessage.messagetime);
			if (newmessage.total > 99) {
				totalnewmessages.setIcon(new ImageIcon(
						messagecount.getScaledInstance(24 + totalnewmessages.getText().length(), 24, Image.SCALE_SMOOTH)));
			} else {
				totalnewmessages.setIcon(new ImageIcon(messagecount));
			}
		}

		lastlabel.setText(newmessage.message);
	}

	private Component findComponentByName(JPanel panel, String name) {
		for (Component c : panel.getComponents()) {
			if (name.equals(c.getName())) {
				return c;
			}
		}
		return null;
	}

	private void attachContactPanelMouseListener(JPanel usernamepanel) {
		usernamepanel.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					handleContactPanelClick((JPanel) e.getSource());
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				JPanel panel = (JPanel) e.getSource();
				if (panel.getBackground() == Color.white) {
					panel.setBackground(new Color(245, 245, 245));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				JPanel panel = (JPanel) e.getSource();
				if (panel.getBackground().equals(new Color(245, 245, 245))) {
					panel.setBackground(Color.white);
				}
			}
		});
	}

	private void handleContactPanelClick(JPanel panel) {
		chatmainpanel.searchfield.setFocusable(false);

		resetContactListVisuals();
		highlightSelectedPanel(panel);
		openChatForPanel(panel);
	}

	private void highlightSelectedPanel(JPanel panel) {
		panel.setBackground(new Color(30, 178, 170));
		for (Component c : panel.getComponents()) {
			c.setForeground(Color.white);
			if ("lastmessage".equals(c.getName())) {
				c.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			}
			if ("totalnewmessages".equals(c.getName())) {
				c.setVisible(false);
			}
		}
	}

	private void openChatForPanel(JPanel panel) {
		StringTokenizer str = new StringTokenizer(panel.getName(), "#");
		str.nextToken();
		str.nextToken();
		int pos = Integer.parseInt(str.nextToken());
		ContactInfo cf = contactinfo.get(pos);
		String s = cf.getClassName();

		if ("Student".equals(s)) {
			chatmainpanel.chatinfopanel.setData(cf.getStudent());
			chatmainpanel.chatpanel.setToUserData(
					s,
					cf.getStudent().getUserId(),
					cf.getStudent().getFullName() + "-" + cf.getStudent().getUserId(),
					cf.getStudent().getProfilePic(),
					cf.getStudent().getLastLogin(),
					cf.getStudent().getActiveStatus());
		} else if ("Faculty".equals(s)) {
			chatmainpanel.chatinfopanel.setData(cf.getFaculty());
			chatmainpanel.chatpanel.setToUserData(
					s,
					cf.getFaculty().getFacultyId() + "",
					cf.getFaculty().getFacultyName() + "-" + cf.getFaculty().getFacultyId(),
					cf.getFaculty().getProfilePic(),
					cf.getFaculty().getLastLogin(),
					cf.getFaculty().getActiveStatus());
		} else if ("Group".equals(s)) {
			chatmainpanel.chatinfopanel.setData(cf.getGroup());
			chatmainpanel.chatpanel.setToUserData(
					s,
					cf.getGroup().getGroupName(),
					cf.getGroup().getGroupName(),
					cf.getGroup().getImage(),
					cf.getGroup().getMembers() + " Members",
					false);
		} else {
			chatmainpanel.chatinfopanel.setData(cf.getAdmin());
			chatmainpanel.chatpanel.setToUserData(
					s,
					"Admin",
					"Principal",
					cf.getAdmin().getProfilePic(),
					cf.getAdmin().getLastLogin(),
					cf.getAdmin().getActiveStatus());
		}
	}

	@Override
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!isCreateAccountEvent(e)) {
			return;
		}

		JComponent errorField = findFirstEmptyField();
		if (errorField != null) {
			showerror(errorField);
			return;
		}

		Admin ad = buildAdminFromForm();
		int result = new AdminData().updateAdminDetails(ad);
		if (result > 0) {
			refreshAdminProfileView();
			this.dispose();
		}
	}

	private boolean isCreateAccountEvent(ActionEvent e) {
		return e.getSource() == createaccountbutton;
	}

	private JComponent findFirstEmptyField() {
		if (collagenamefield.getText().isEmpty()) {
			return collagenamefield;
		}
		if (emailidfield.getText().isEmpty()) {
			return emailidfield;
		}
		if (contactnumberfield.getText().isEmpty()) {
			return contactnumberfield;
		}
		if (websitefield.getText().isEmpty()) {
			return websitefield;
		}
		// NOTE: getPassword() returns char[], not String
		if (passwordfield.getPassword().length == 0) {
			return passwordfield;
		}
		if (addresstextarea.getText().isEmpty()) {
			return scrollpaneforaddress;
		}
		return null;
	}

	private Admin buildAdminFromForm() {
		Admin ad = new Admin();
		ad.setCollageName(collagenamefield.getText());
		ad.setEmailId(emailidfield.getText());
		ad.setContactNumber(contactnumberfield.getText());
		ad.setAddress(addresstextarea.getText());
		ad.setPassword(String.valueOf(passwordfield.getPassword()));
		ad.setWebsite(websitefield.getText());

		if (file != null) {
			try {
				ad.setProfilePic(ImageIO.read(file));
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		} else {
			ad.setProfilePic(a.getProfilePic());
		}
		return ad;
	}

	private void refreshAdminProfileView() {
		am.adminprofilepanel.setVisible(false);
		am.adminprofilepanel = new AdminProfilePanel(am);
		am.adminprofilepanel.setLocation(am.panelx, am.panely);
		am.adminprofilepanel.setVisible(true);
		am.contentPane.add(am.adminprofilepanel);
		am.setCollageDetails();
	}
	// helper to track which section labels were already added
	private static class ContactFilterFlags {
		boolean group;
		boolean students;
		boolean faculties;
	}

	public void filterContact(String search) {
		resetContactFilterView();

		String normalizedSearch = normalizeSearch(search);
		ContactFilterFlags flags = new ContactFilterFlags();

		for (JPanel p : contactlist) {
			String contactName = getContactNameFromPanel(p);

			if (!matchesSearch(contactName, normalizedSearch)) {
				continue;
			}

			addSectionLabelsForContact(contactName, flags);
			addFilteredContactPanel(p);
		}

		this.setVisible(true);
	}
	// Reset UI before applying filter
	private void resetContactFilterView() {
		this.removeAll();
		this.setVisible(false);
		location = 0;
	}

	// Normalize user input (null-safe, trimmed, lowercased)
	private String normalizeSearch(String search) {
		if (search == null) {
			return "";
		}
		return search.trim().toLowerCase();
	}

	// Extract the logical "contact name" part from panel name
	private String getContactNameFromPanel(JPanel panel) {
		String rawName = panel.getName();
		if (rawName == null) {
			return "";
		}

		StringTokenizer str = new StringTokenizer(rawName, "#");
		if (!str.hasMoreTokens()) {
			return "";
		}

		return str.nextToken().toLowerCase();
	}

	// Check if this contact matches the search string
	private boolean matchesSearch(String contactName, String search) {
		if (search.isEmpty()) {
			// if you want "empty search shows all", this keeps that behavior
			return true;
		}
		return contactName.contains(search);
	}

	// Add “Groups / Students / Faculties / Admin” headings as needed
	private void addSectionLabelsForContact(String contactName, ContactFilterFlags flags) {
		if (contactName.contains("group") && !flags.group) {
			addLabel("Groups");
			flags.group = true;
		}
		if (contactName.contains("student") && !contactName.contains("group") && !flags.students) {
			addLabel("Students");
			flags.students = true;
		}
		if (contactName.contains("faculty") && !contactName.contains("group") && !flags.faculties) {
			addLabel("Faculties");
			flags.faculties = true;
		}
		// Note: original code did NOT guard "Admin" with a flag, so keep same behavior.
		if (contactName.contains("admin") && contactName.contains("principal")) {
			addLabel("Admin");
		}
	}

	// Actually add the contact panel to the view and position it
	private void addFilteredContactPanel(JPanel panel) {
		add(panel);
		panel.setLocation(0, location);
		location += 60;
	}


}
class ContactInfo
{
	private Faculty faculty;
	private Student student;
	private Group group;
	private Admin admin;
	
	public void setFaculty(Faculty faculty)
	{
			this.faculty=faculty;
	}
	public void setStudent(Student student)
	{
		this.student=student;
	}
	public void setGroup(Group group)
	{
		this.group=group;
	}
	public void setAdmin(Admin admin)
	{
		this.admin=admin;
	}

	public Faculty getFaculty()
	{
		return faculty;
	}
	public Student getStudent()
	{
		return student;
	}
	public Group getGroup()
	{
		return group;
	}
	public Admin getAdmin()
	{
		return admin;
	}
	public String getClassName()
	{
		if(student!=null)
		{
			return "Student";
		}
		else if(faculty!=null)
		{
			return "Faculty";
		}
		else if(group!=null) 
		{
			return "Group";
		}
		else if(admin!=null)
		{
			return "Admin";
		}
			
		return null;
	}
}
