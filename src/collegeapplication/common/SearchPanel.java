package collegeapplication.common;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

import collegeapplication.admin.AdminMain;
import collegeapplication.cource.CourceData;
import collegeapplication.faculty.Faculty;
import collegeapplication.faculty.FacultyData;
import collegeapplication.faculty.FacultyMain;
import collegeapplication.faculty.ViewFacultyPanel;
import collegeapplication.student.Student;
import collegeapplication.student.StudentData;
import collegeapplication.student.StudentMain;
import collegeapplication.student.ViewStudentPanel;
import net.proteanit.sql.DbUtils;


/*
 * Title : SearchPanel.java
 * Created by : Ajaysinh Rathod
 * Purpose : For searching student of faculty
 * Mail : ajaysinhrathod1290@gmail.com
 */


@SuppressWarnings("serial")
public class SearchPanel extends JPanel implements ActionListener {

	private JTable table;
	private JScrollPane tableviewscroll;
	private JTextField searchfield;
	private JComboBox<String> courcenamecombo;
	private JComboBox<String> semoryearcombo;
	private JComboBox<String> studentandfacultycombo;

	private JButton searchbutton;
	/**
	 * Create the panel.
	 */
	public SearchPanel(AdminMain am)
	{
		this();
		table.addMouseListener(new MouseAdapter()
			{
				public void mousePressed(MouseEvent e)
				{
					if(e.getClickCount()>1  && e.getButton()==MouseEvent.BUTTON1)
					{
					if(studentandfacultycombo.getSelectedIndex()==0)
					{
						JTable t=(JTable) e.getSource();
						int row=t.getSelectedRow();
						String courcecode=table.getValueAt(row,0)+"";
						String  strsem=table.getValueAt(row, 4)+"";
						int sem=Integer.parseInt(strsem.substring(strsem.indexOf('-')+1));
						String strroll=table.getValueAt(row, 1)+"";
						long rollnumber=Long.parseLong(strroll);
						Student s=new StudentData().getStudentDetails(courcecode,sem,rollnumber);
						
						am.viewstudentpanel=new ViewStudentPanel(s,am,am.searchpanel);
						am.viewstudentpanel.setVisible(true);
						am.searchpanel.setVisible(false);
						am.viewstudentpanel.setLocation(am.panelx,0);
						am.viewstudentpanel.setVisible(true);
						am.viewstudentpanel.setFocusable(true);
						am.contentPane.add(am.viewstudentpanel);
					}
					else 
					{
						JTable t=(JTable) e.getSource();
						int fid=Integer.parseInt(t.getValueAt(t.getSelectedRow(), 0)+"");
						Faculty f=new FacultyData().getFacultyInfobyId(fid);
						
						am.viewfacultypanel=new ViewFacultyPanel(f,am,am.searchpanel);
						am.viewfacultypanel.setVisible(true);
						am.searchpanel.setVisible(false);
						am.viewfacultypanel.setLocation(am.panelx,am.panely);
						am.viewfacultypanel.setVisible(true);
						am.viewfacultypanel.setFocusable(true);
						am.contentPane.add(am.viewfacultypanel);
					}
				}
			}
		});
		
		
	}
	public SearchPanel(FacultyMain fm)
	{
		this();
		courcenamecombo.setSelectedItem(new CourceData().getcourcename(fm.f.getCourceCode()));
		courcenamecombo.setEnabled(false);
		 courcenamecombo.setRenderer(new DefaultListCellRenderer() {
		        @Override
		        public void paint(Graphics g) {
		            setForeground(Color.BLACK);
		            setBackground(Color.WHITE);
		            super.paint(g);
		        }
		    });
		 semoryearcombo.setSelectedIndex(fm.f.getSemorYear());
		 semoryearcombo.setEnabled(false);
		 semoryearcombo.setRenderer(new DefaultListCellRenderer() {
		        @Override
		        public void paint(Graphics g) {
		            setForeground(Color.BLACK);
		            setBackground(Color.WHITE);
		            super.paint(g);
		        }
		    });
		 this.createtablemodel();
		 table.addMouseListener(new MouseAdapter()
			{
				public void mousePressed(MouseEvent e)
				{
					if(e.getClickCount()>1  && e.getButton()==MouseEvent.BUTTON1)
					{
						if(studentandfacultycombo.getSelectedIndex()==0)
						{
							JTable t=(JTable) e.getSource();
							int row=t.getSelectedRow();
							String courcecode=table.getValueAt(row,0)+"";
							String  strsem=table.getValueAt(row, 4)+"";
							int sem=Integer.parseInt(strsem.substring(strsem.indexOf('-')+1));
							String strroll=table.getValueAt(row, 1)+"";
							long rollnumber=Long.parseLong(strroll);
							Student s=new StudentData().getStudentDetails(courcecode,sem,rollnumber);
							
							fm.viewstudentpanel=new ViewStudentPanel(s,fm,fm.searchpanel);
							fm.viewstudentpanel.setVisible(true);
							fm.searchpanel.setVisible(false);
							fm.viewstudentpanel.setLocation(fm.panelx,0);
							fm.viewstudentpanel.setVisible(true);
							fm.viewstudentpanel.setFocusable(true);
							fm.contentPane.add(fm.viewstudentpanel);
						}
						else 
						{
							JTable t=(JTable) e.getSource();
							int fid=Integer.parseInt(t.getValueAt(t.getSelectedRow(), 0)+"");
							Faculty f=new FacultyData().getFacultyInfobyId(fid);
							
							fm.viewfacultypanel=new ViewFacultyPanel(f,fm,fm.searchpanel);
							fm.viewfacultypanel.setVisible(true);
							fm.searchpanel.setVisible(false);
							fm.viewfacultypanel.setLocation(fm.panelx,fm.panely);
							fm.viewfacultypanel.setVisible(true);
							fm.viewfacultypanel.setFocusable(true);
							fm.contentPane.add(fm.viewfacultypanel);
						}
					}
				}
			});
			
	}
	public SearchPanel(StudentMain sm)
	{
		this();
		courcenamecombo.setSelectedItem(new CourceData().getcourcename(sm.s.getCourceCode()));
		courcenamecombo.setEnabled(false);
		 courcenamecombo.setRenderer(new DefaultListCellRenderer() {
		        @Override
		        public void paint(Graphics g) {
		            setForeground(Color.BLACK);
		            setBackground(Color.WHITE);
		            super.paint(g);
		        }
		    });
		 semoryearcombo.setSelectedIndex(sm.s.getSemorYear());
		 semoryearcombo.setEnabled(false);
		 semoryearcombo.setRenderer(new DefaultListCellRenderer() {
		        @Override
		        public void paint(Graphics g) {
		            setForeground(Color.BLACK);
		            setBackground(Color.WHITE);
		            super.paint(g);
		        }
		    });
		 this.createtablemodel();
		 table.addMouseListener(new MouseAdapter()
			{
				public void mousePressed(MouseEvent e)
				{
					if(e.getClickCount()>1  && e.getButton()==MouseEvent.BUTTON1)
					{
						if(studentandfacultycombo.getSelectedIndex()==0)
						{
							JTable t=(JTable) e.getSource();
							int row=t.getSelectedRow();
							String courcecode=table.getValueAt(row,0)+"";
							String  strsem=table.getValueAt(row, 4)+"";
							int sem=Integer.parseInt(strsem.substring(strsem.indexOf('-')+1));
							String strroll=table.getValueAt(row, 1)+"";
							long rollnumber=Long.parseLong(strroll);
							Student s=new StudentData().getStudentDetails(courcecode,sem,rollnumber);
							
							sm.viewstudentpanel=new ViewStudentPanel(s,sm,sm.searchpanel);
							sm.viewstudentpanel.setVisible(true);
							sm.searchpanel.setVisible(false);
							sm.viewstudentpanel.setLocation(sm.panelx,0);
							sm.viewstudentpanel.setVisible(true);
							sm.viewstudentpanel.setFocusable(true);
							sm.contentPane.add(sm.viewstudentpanel);
						}
						else 
						{
							JTable t=(JTable) e.getSource();
							int fid=Integer.parseInt(t.getValueAt(t.getSelectedRow(), 0)+"");
							Faculty f=new FacultyData().getFacultyInfobyId(fid);
							
							sm.viewfacultypanel=new ViewFacultyPanel(f,sm,sm.searchpanel);
							sm.viewfacultypanel.setVisible(true);
							sm.searchpanel.setVisible(false);
							sm.viewfacultypanel.setLocation(sm.panelx,sm.panely);
							sm.viewfacultypanel.setVisible(true);
							sm.viewfacultypanel.setFocusable(true);
							sm.contentPane.add(sm.viewfacultypanel);
						}
					}
				}
			});
			
	}
	public SearchPanel() {
		this.setName("Search Panel");
		setBackground(new Color(255, 255, 255));
		this.setSize(1116, 705);
		setLayout(null);
		
		tableviewscroll = new JScrollPane();
		tableviewscroll.setBorder(new EmptyBorder(0, 0, 0, 0));
		tableviewscroll.setBounds(10, 194, 1096, 500);
		for(Component c : tableviewscroll.getComponents())
		{
			c.setBackground(Color.white);
		}
		add(tableviewscroll);
		
		
		
		table = new JTable();
		
		table.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		
		table.getTableHeader().setBackground(new Color(32,178,170));
		table.getTableHeader().setForeground(Color.white);
		table.setSelectionBackground(new Color(240, 255, 255));
		table.getTableHeader().setFont(new Font("Arial",Font.BOLD,20));
		table.setFont(new Font("Segoe UI",Font.PLAIN,20));
		table.setModel(DbUtils.resultSetToTableModel(new StudentData().getStudentinfo("")));
		table.getTableHeader().setPreferredSize(new Dimension(50,40));
		table.setFocusable(false);
		table.setDragEnabled(false);
		table.setRowHeight(40);
		table.setDefaultEditor(Object.class, null);
		table.setCursor(new Cursor(Cursor.HAND_CURSOR));
		table.setGridColor(Color.LIGHT_GRAY);
		table.getTableHeader().setReorderingAllowed(false);	
		tableviewscroll.setViewportView(table);
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(32, 178, 170));
		panel.setBounds(10, 0, 1096, 183);
		add(panel);
		panel.setLayout(null);
		JLabel lblStudentManagement = new JLabel("Search");
		lblStudentManagement.setIcon(null);
		lblStudentManagement.setBounds(10, 38, 224, 44);
		panel.add(lblStudentManagement);
		lblStudentManagement.setBackground(new Color(32, 178, 170));
		lblStudentManagement.setHorizontalAlignment(SwingConstants.LEFT);
		lblStudentManagement.setForeground(Color.WHITE);
		lblStudentManagement.setFont(new Font("Segoe UI", Font.BOLD, 30));
		lblStudentManagement.setOpaque(true);
		
		studentandfacultycombo = new JComboBox<String>();
		studentandfacultycombo.setModel(new DefaultComboBoxModel<String>(new String[] {"Students", "Faculties"}));
		this.arrangeStudentTable();
		studentandfacultycombo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		studentandfacultycombo.setBounds(10, 128, 205, 40);
		studentandfacultycombo.addActionListener(this);
		panel.add(studentandfacultycombo);
		
		String courcename[]=new CourceData().getCourceName();
		courcename[0]="All Cources";
		courcenamecombo = new JComboBox<String>(courcename);
		
		courcenamecombo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		courcenamecombo.setBounds(225, 128, 255, 40);
		courcenamecombo.addActionListener(this);
		
		panel.add(courcenamecombo);
		
		semoryearcombo = new JComboBox<String>();
		semoryearcombo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		semoryearcombo.setBounds(490, 128, 214, 40);
		semoryearcombo.addActionListener(this);
		panel.add(semoryearcombo);
		
		searchfield = new HintTextField("Search");
		searchfield.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		searchfield.setForeground(Color.DARK_GRAY);
		searchfield.setBounds(714, 129, 248, 40);
		panel.add(searchfield);
		searchfield.setColumns(10);
		
		searchbutton = new JButton();
		searchbutton.setForeground(new Color(0, 139, 139));
		searchbutton.setFont(new Font("Segoe UI", Font.BOLD, 15));
		searchbutton.setText("Search");
		searchbutton.setBorder(new EmptyBorder(0, 0, 0, 0));
		searchbutton.setBackground(new Color(255, 255, 255));
		searchbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		searchbutton.setIcon(new ImageIcon("./assets/search.png"));
		searchbutton.setBounds(972, 129, 114, 40);
		searchbutton.addActionListener(this);
		searchbutton.setFocusable(false);
		panel.add(searchbutton);
		   

	}

	
	public void arrangeStudentTable()
	{
		table.getColumnModel().getColumn(0).setMaxWidth(150);
		table.getColumnModel().getColumn(1).setMaxWidth(200);
		table.getColumnModel().getColumn(2).setMaxWidth(300);
		table.getColumnModel().getColumn(3).setMaxWidth(300);
		table.getColumnModel().getColumn(4).setMaxWidth(150);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	}
	public void arrangeFacultyTable()
	{
		table.getColumnModel().getColumn(0).setMaxWidth(200);
		table.getColumnModel().getColumn(1).setMaxWidth(300);
		table.getColumnModel().getColumn(2).setMaxWidth(500);
		table.getColumnModel().getColumn(3).setMaxWidth(250);
		table.getColumnModel().getColumn(4).setMaxWidth(250);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		DefaultTableCellRenderer cellrenderer=new DefaultTableCellRenderer();
		cellrenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(cellrenderer);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
			
		if(e.getSource()==courcenamecombo)
		{
			
			
			if(courcenamecombo.getSelectedIndex()==0)
			{
				semoryearcombo.setModel(new DefaultComboBoxModel<String>(new String[] {""}));
				
			}
			
			else
			{
			 String cource=(String) courcenamecombo.getSelectedItem();
			 String semoryear[]=new CourceData().getSemorYear(cource);
			 semoryear[0]="All "+semoryear[1].substring(0,semoryear[1].indexOf(' ')); 
			 semoryearcombo.setModel(new DefaultComboBoxModel<String>(semoryear));
			}
		 
		}
		if(e.getSource()==searchbutton)
		{
			
			createtablemodel();
		}
	
	}
	public void createtablemodel() {
		String searchText = searchfield.getText().trim();
		int selectedIndex = studentandfacultycombo.getSelectedIndex();

		if (selectedIndex == 0) {
			String query = buildStudentQuery(searchText);
			table.setModel(DbUtils.resultSetToTableModel(new StudentData().searchStudent(query)));
			this.arrangeStudentTable();
		} else if (selectedIndex == 1) {
			String query = buildFacultyQuery(searchText);
			table.setModel(DbUtils.resultSetToTableModel(new FacultyData().searchFaculty(query)));
			this.arrangeFacultyTable();
		}
	}
	private String buildStudentQuery(String searchText) {
		String defaultQuery =
				"select s.courcecode as 'Class' ,s.rollnumber as 'Roll Number',"
						+ "concat(s.firstname,' ',s.lastname) as 'Student Name',"
						+ "c.courcename as 'Cource Name',"
						+ "concat(c.semoryear,'-',s.semoryear) as 'Sem/Year' "
						+ "from students s left join cources c on s.courcecode=c.courcecode ";

		StringBuilder query = new StringBuilder(defaultQuery);

		appendCourseAndSemFilterForStudent(query);
		appendStudentSearchFilter(query, searchText);

		return query.toString();
	}

	private String buildFacultyQuery(String searchText) {
		String defaultQuery =
				"select facultyid as 'Faculty ID',"
						+ "facultyname as 'Faculty Name',"
						+ "emailid as 'Email ID',"
						+ "qualification as 'Qualification',"
						+ "experience as 'Experience' "
						+ "from faculties f ";

		StringBuilder query = new StringBuilder(defaultQuery);

		appendCourseAndSemFilterForFaculty(query);
		appendFacultySearchFilter(query, searchText);

		return query.toString();
	}
	private void appendCourseAndSemFilterForStudent(StringBuilder query) {
		if (courcenamecombo.getSelectedIndex() <= 0) {
			return;
		}

		String courcecode = new CourceData().getCourcecode(courcenamecombo.getSelectedItem() + "");
		query.append(" where s.courcecode='").append(courcecode).append("'");

		if (semoryearcombo.getSelectedIndex() > 0) {
			query.append(" and s.semoryear=").append(semoryearcombo.getSelectedIndex());
		}
	}

	private void appendCourseAndSemFilterForFaculty(StringBuilder query) {
		if (courcenamecombo.getSelectedIndex() <= 0) {
			return;
		}

		String courcecode = new CourceData().getCourcecode(courcenamecombo.getSelectedItem() + "");
		query.append(" where f.courcecode='").append(courcecode).append("'");

		if (semoryearcombo.getSelectedIndex() > 0) {
			query.append(" and f.semoryear=").append(semoryearcombo.getSelectedIndex());
		}
	}
	private void appendStudentSearchFilter(StringBuilder query, String searchText) {
		if (searchText == null || searchText.isEmpty()) {
			return;
		}

		String searchQuery =
				"s.firstname like '" + searchText + "%' "
						+ "or s.lastname like '" + searchText + "%' "
						+ "or s.rollnumber like '" + searchText + "%' ";

		appendSearchCondition(query, searchQuery);
	}

	private void appendFacultySearchFilter(StringBuilder query, String searchText) {
		if (searchText == null || searchText.isEmpty() || "Search".equals(searchText)) {
			return;
		}

		String searchQuery =
				" f.facultyname like '" + searchText + "%' "
						+ "or f.facultyid like '" + searchText + "%' ";

		appendSearchCondition(query, searchQuery);
	}
	private void appendSearchCondition(StringBuilder query, String searchCondition) {
		String lowerQuery = query.toString().toLowerCase();

		if (!lowerQuery.contains("where")) {
			query.append("where ").append(searchCondition);
		} else {
			query.append(" and ( ").append(searchCondition).append(" ) ");
		}
	}


}
