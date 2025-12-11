package collegeapplication.common;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import collegeapplication.cource.CourceData;
import collegeapplication.faculty.Faculty;
import collegeapplication.faculty.FacultyData;
import collegeapplication.faculty.FacultyPanel;
import collegeapplication.faculty.ViewFacultyPanel;
import collegeapplication.student.Student;
import collegeapplication.student.StudentData;
import collegeapplication.student.StudentPanel;
import collegeapplication.student.ViewStudentPanel;


/*
 * Title : PhotoViewPanel.java
 * Created by : Ajaysinh Rathod
 * Purpose : To displaying students and faculties image
 * Mail : ajaysinhrathod1290@gmail.com
 */
@SuppressWarnings("serial")
public class PhotoViewPanel extends JPanel {
	int xpos[];
	JPanel panel[][];
	JLabel profilepiclabel[][];
	JLabel namelabel[][];
	JLabel degreelabel[][];
	int totalfaculties=-1;
	int totalstudents=-1;
	int maxphotosinrow=3;
	int incrementx=0;
	int incrementy=0;
	FacultyPanel fp;
	StudentPanel sp;
	/**
	 * Create the panel.
	 * 
	 */
	 
	@Override
	public Dimension getPreferredSize()
	{
		int n=0;
		if(totalfaculties!=-1)
		{
		  n=totalfaculties;
		}
		if(totalstudents!=-1)
		{
			n=totalstudents;
		}
		 int row=n%maxphotosinrow==0?n/maxphotosinrow:(n/maxphotosinrow)+1;
		 if(row==1)
		 {
			 return new Dimension(xpos[maxphotosinrow-1]+xpos[1]-xpos[0],incrementy+20);
		 }
		 
	    return new Dimension( 1116,row*(incrementy));
	}
	public PhotoViewPanel(FacultyPanel facultyPanel, int maxphoto) {

		this.fp = facultyPanel;
		this.maxphotosinrow = maxphoto;

		initComponentDefaults();
		computeXPositions();
		setupGridPanels();
	}
	private void initComponentDefaults() {
		this.setFocusable(true);
		this.xpos = new int[maxphotosinrow];
		this.incrementx = (4 * 270) / maxphotosinrow;
		this.incrementy = incrementx + 50;

		this.totalfaculties = fp.table.getRowCount();

		setBackground(Color.WHITE);
		this.setBounds(0, 189, 1116, 1000);
		setLayout(null);
	}

	private void computeXPositions() {
		int start = 20;
		for (int i = 0; i < maxphotosinrow; i++) {
			xpos[i] = start;
			start += incrementx;
		}
	}
	private void setupGridPanels() {
		int n = totalfaculties;
		int rows = (n % maxphotosinrow == 0) ? (n / maxphotosinrow) : (n / maxphotosinrow) + 1;
		int lastColumn = (n % maxphotosinrow == 0) ? maxphotosinrow : (n % maxphotosinrow);

		panel = new JPanel[rows][maxphotosinrow];
		profilepiclabel = new JLabel[rows][maxphotosinrow];
		namelabel = new JLabel[rows][maxphotosinrow];
		degreelabel = new JLabel[rows][maxphotosinrow];

		int y = 10;
		int index = 1;

		for (int i = 0; i < rows && index <= totalfaculties; i++) {
			int totalColumnsForRow = (i == rows - 1) ? lastColumn : maxphotosinrow;
			index = createRowPanels(i, totalColumnsForRow, index, y);
			y += incrementy;
		}
	}

	private int createRowPanels(int rowIndex, int totalColumns, int startIndex, int y) {
		int index = startIndex;
		for (int j = 0; j < totalColumns && index <= totalfaculties; j++) {
			createFacultyCard(rowIndex, j, index, y);
			index++;
		}
		return index;
	}
	private void createFacultyCard(int rowIndex, int colIndex, int facultyIndex, int y) {
		int fid = Integer.parseInt(fp.table.getValueAt(facultyIndex - 1, 0) + "");
		Faculty f = new FacultyData().getFacultyInfobyId(fid);

		JPanel cell = buildFacultyPanelCell(colIndex, y, f);
		panel[rowIndex][colIndex] = cell;

		profilepiclabel[rowIndex][colIndex] = createProfilePicLabel(cell, f);
		cell.add(profilepiclabel[rowIndex][colIndex]);

		namelabel[rowIndex][colIndex] = createNameLabel(cell, f);
		cell.add(namelabel[rowIndex][colIndex]);

		degreelabel[rowIndex][colIndex] = createDegreeLabel(cell, f);
		cell.add(degreelabel[rowIndex][colIndex]);
	}
	private JPanel buildFacultyPanelCell(int colIndex, int y, Faculty f) {
		JPanel cell = new JPanel();
		cell.setBackground(Color.WHITE);
		cell.setBounds(xpos[colIndex], y, incrementx - 20, incrementy - 10);
		cell.setVisible(true);
		cell.setToolTipText(f.getFacultyName());
		add(cell);

		cell.setLayout(null);
		cell.setName(String.valueOf(f.getFacultyId()));
		cell.setCursor(new Cursor(Cursor.HAND_CURSOR));

		attachFacultyClickListener(cell);

		return cell;
	}

	private JLabel createProfilePicLabel(JPanel cell, Faculty f) {
		JLabel pic = new JLabel();
		pic.setBounds(0, 0, cell.getWidth() - 10, cell.getHeight() - 60);
		pic.setBorder(new LineBorder(Color.LIGHT_GRAY));
		pic.setHorizontalAlignment(SwingConstants.CENTER);
		pic.setText("image");
		pic.setIcon(new ImageIcon(
				f.getProfilePic(
						pic.getWidth() + ((maxphotosinrow * 10) / 4 + 1),
						pic.getHeight()
				)
		));
		return pic;
	}

	private JLabel createNameLabel(JPanel cell, Faculty f) {
		JLabel name = new JLabel();
		name.setHorizontalAlignment(SwingConstants.CENTER);
		name.setText(f.getFacultyName());
		name.setFont(new Font("Tahoma", Font.BOLD, changeNameFont()));
		name.setBounds(0, cell.getHeight() - 60 + 5, cell.getWidth(), 22);
		return name;
	}

	private JLabel createDegreeLabel(JPanel cell, Faculty f) {
		JLabel degree = new JLabel();
		degree.setVerticalAlignment(SwingConstants.TOP);

		if ("Not Assigned".equals(f.getPosition())) {
			degree.setText("");
		} else {
			degree.setText(f.getPosition());
		}

		degree.setHorizontalAlignment(SwingConstants.CENTER);
		degree.setFont(new Font("Tahoma", Font.PLAIN, changeDegreeFont()));
		degree.setBounds(0, cell.getHeight() - 60 + 25, cell.getWidth(), 22);
		return degree;
	}
	private void attachFacultyClickListener(JPanel cell) {
		cell.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					int facultyId = Integer.parseInt(e.getComponent().getName());
					Faculty f = new FacultyData().getFacultyInfobyId(facultyId);
					openFacultyView(f);
				}
			}
		});
	}

	private void openFacultyView(Faculty f) {
		if (fp.am != null) {
			openFacultyForAdminMain(f);
		} else if (fp.fm != null) {
			openFacultyForFacultyMain(f);
		} else if (fp.sm != null) {
			openFacultyForStudentMain(f);
		}
	}

	private void openFacultyForAdminMain(Faculty f) {
		fp.am.viewfacultypanel = new ViewFacultyPanel(f, fp.am, fp);
		fp.am.viewfacultypanel.setVisible(true);
		fp.am.facultypanel.setVisible(false);
		fp.am.viewfacultypanel.setLocation(fp.am.panelx, fp.am.panely);
		fp.am.viewfacultypanel.setVisible(true);
		fp.am.viewfacultypanel.setFocusable(true);
		fp.am.contentPane.add(fp.am.viewfacultypanel);
	}

	private void openFacultyForFacultyMain(Faculty f) {
		fp.fm.viewfacultypanel = new ViewFacultyPanel(f, fp.fm, fp);
		fp.fm.viewfacultypanel.setVisible(true);
		fp.fm.facultypanel.setVisible(false);
		fp.fm.viewfacultypanel.setLocation(fp.fm.panelx, fp.fm.panely);
		fp.fm.viewfacultypanel.setVisible(true);
		fp.fm.viewfacultypanel.setFocusable(true);
		fp.fm.contentPane.add(fp.fm.viewfacultypanel);
	}

	private void openFacultyForStudentMain(Faculty f) {
		fp.sm.viewfacultypanel = new ViewFacultyPanel(f, fp.sm, fp);
		fp.sm.viewfacultypanel.setVisible(true);
		fp.sm.facultypanel.setVisible(false);
		fp.sm.viewfacultypanel.setLocation(fp.sm.panelx, fp.sm.panely);
		fp.sm.viewfacultypanel.setVisible(true);
		fp.sm.viewfacultypanel.setFocusable(true);
		fp.sm.contentPane.add(fp.sm.viewfacultypanel);
	}
	public PhotoViewPanel(StudentPanel sp, int maxphoto) {
		this.sp = sp;
		this.maxphotosinrow = maxphoto;

		initComponentDefaults();
		computeXPositions();
		setupGridPanels();
	}
	private void initComponentDefaults() {
		xpos = new int[maxphotosinrow];
		incrementx = (4 * 270) / maxphotosinrow;
		incrementy = incrementx + 50;

		totalstudents = sp.table.getRowCount();

		setBackground(Color.WHITE);
		setBounds(0, 189, 1116, 1000);
		setLayout(null);
	}

	private void computeXPositions() {
		int start = 20;
		for (int i = 0; i < maxphotosinrow; i++) {
			xpos[i] = start;
			start += incrementx;
		}
	}
	private void setupGridPanels() {
		int n = totalstudents;
		int rows = (n % maxphotosinrow == 0) ? (n / maxphotosinrow) : (n / maxphotosinrow) + 1;
		int lastColumn = (n % maxphotosinrow == 0) ? maxphotosinrow : (n % maxphotosinrow);

		panel = new JPanel[rows][maxphotosinrow];
		profilepiclabel = new JLabel[rows][maxphotosinrow];
		namelabel = new JLabel[rows][maxphotosinrow];
		degreelabel = new JLabel[rows][maxphotosinrow];

		int y = 10;
		int index = 1;

		for (int i = 0; i < rows && index <= totalstudents; i++) {
			int totalColumnsForRow = (i == rows - 1) ? lastColumn : maxphotosinrow;
			index = createRowPanels(i, totalColumnsForRow, index, y);
			y += incrementy;
		}
	}

	private int createRowPanels(int rowIndex, int totalColumns, int startIndex, int y) {
		int index = startIndex;
		for (int j = 0; j < totalColumns && index <= totalstudents; j++) {
			createStudentCard(rowIndex, j, index, y);
			index++;
		}
		return index;
	}
	private void createStudentCard(int rowIndex, int colIndex, int studentIndex, int y) {
		Student s = loadStudentFromTable(studentIndex);

		JPanel cell = buildStudentPanelCell(colIndex, y, s);
		panel[rowIndex][colIndex] = cell;

		profilepiclabel[rowIndex][colIndex] = createProfilePicLabel(cell, s);
		cell.add(profilepiclabel[rowIndex][colIndex]);

		namelabel[rowIndex][colIndex] = createNameLabel(cell, s);
		cell.add(namelabel[rowIndex][colIndex]);

		degreelabel[rowIndex][colIndex] = createDegreeLabel(cell, s);
		cell.add(degreelabel[rowIndex][colIndex]);
	}

	private Student loadStudentFromTable(int index) {
		String courseCode = sp.table.getValueAt(index - 1, 0) + "";
		String strSem = sp.table.getValueAt(index - 1, 4) + "";
		int sem = Integer.parseInt(strSem.substring(strSem.indexOf('-') + 1));
		String strRoll = sp.table.getValueAt(index - 1, 1) + "";
		long rollnumber = Long.parseLong(strRoll);

		return new StudentData().getStudentDetails(courseCode, sem, rollnumber);
	}
	private JPanel buildStudentPanelCell(int colIndex, int y, Student s) {
		JPanel cell = new JPanel();
		cell.setBackground(Color.WHITE);
		cell.setBounds(xpos[colIndex], y, incrementx - 20, incrementy - 10);
		cell.setVisible(true);
		cell.setToolTipText(s.getFullName());
		add(cell);

		cell.setName(String.valueOf(s.getSrNo()));
		cell.setCursor(new Cursor(Cursor.HAND_CURSOR));
		cell.setLayout(null);

		attachStudentClickListener(cell);

		return cell;
	}

	private JLabel createProfilePicLabel(JPanel cell, Student s) {
		JLabel pic = new JLabel();
		pic.setBounds(0, 0, cell.getWidth(), cell.getHeight() - 60);
		pic.setBorder(new LineBorder(Color.LIGHT_GRAY));
		pic.setHorizontalAlignment(SwingConstants.CENTER);
		pic.setText("image");
		pic.setIcon(new ImageIcon(
				s.getProfilePic(
						pic.getWidth() + ((maxphotosinrow * 10) / 4 + 1),
						pic.getHeight()
				)
		));
		return pic;
	}

	private JLabel createNameLabel(JPanel cell, Student s) {
		JLabel name = new JLabel();
		name.setHorizontalAlignment(SwingConstants.CENTER);
		name.setText(s.getFullName());
		name.setFont(new Font("Tahoma", Font.BOLD, changeNameFont()));
		name.setBounds(0, cell.getHeight() - 60 + 3, cell.getWidth(), 22);
		return name;
	}

	private JLabel createDegreeLabel(JPanel cell, Student s) {
		JLabel degree = new JLabel();
		degree.setVerticalAlignment(SwingConstants.TOP);

		String semYear = new CourceData().getsemoryear(s.getCourceCode()) + "-" +
				s.getSemorYear() + " (" + s.getCourceCode() + ")";

		degree.setText(semYear);
		degree.setHorizontalAlignment(SwingConstants.CENTER);
		degree.setFont(new Font("Tahoma", Font.PLAIN, changeDegreeFont()));
		degree.setBounds(0, cell.getHeight() - 60 + 25, cell.getWidth(), 22);
		return degree;
	}
	private void attachStudentClickListener(JPanel cell) {
		cell.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					int srNo = Integer.parseInt(e.getComponent().getName());
					Student s = new StudentData().getStudentDetails(srNo);
					openStudentView(s);
				}
			}
		});
	}

	private void openStudentView(Student s) {
		if (sp.am != null) {
			openStudentForAdminMain(s);
		} else if (sp.fm != null) {
			openStudentForFacultyMain(s);
		} else if (sp.sm != null) {
			openStudentForStudentMain(s);
		}
	}

	private void openStudentForAdminMain(Student s) {
		sp.am.viewstudentpanel = new ViewStudentPanel(s, sp.am, sp);
		sp.am.viewstudentpanel.setVisible(true);
		sp.am.studentpanel.setVisible(false);
		sp.am.viewstudentpanel.setLocation(sp.am.panelx, sp.am.panely);
		sp.am.viewstudentpanel.setVisible(true);
		sp.am.viewstudentpanel.setFocusable(true);
		sp.am.contentPane.add(sp.am.viewstudentpanel);
	}

	private void openStudentForFacultyMain(Student s) {
		sp.fm.viewstudentpanel = new ViewStudentPanel(s, sp.fm, sp);
		sp.fm.viewstudentpanel.setVisible(true);
		sp.fm.studentpanel.setVisible(false);
		sp.fm.viewstudentpanel.setLocation(sp.fm.panelx, sp.fm.panely);
		sp.fm.viewstudentpanel.setVisible(true);
		sp.fm.viewstudentpanel.setFocusable(true);
		sp.fm.contentPane.add(sp.fm.viewstudentpanel);
	}

	private void openStudentForStudentMain(Student s) {
		sp.sm.viewstudentpanel = new ViewStudentPanel(s, sp.sm, sp);
		sp.sm.viewstudentpanel.setVisible(true);
		sp.sm.studentpanel.setVisible(false);
		sp.sm.viewstudentpanel.setLocation(sp.sm.panelx, sp.sm.panely);
		sp.sm.viewstudentpanel.setVisible(true);
		sp.sm.viewstudentpanel.setFocusable(true);
		sp.sm.contentPane.add(sp.sm.viewstudentpanel);
	}

	public int changeNameFont()
	  {
		return maxphotosinrow<4?22:maxphotosinrow<8?17:maxphotosinrow<12?13:10;
	  }
	public int changeDegreeFont()
	  {
		return maxphotosinrow<4?18:maxphotosinrow<8?14:maxphotosinrow<12?13:10;
	  }
	
	


}
