package collegeapplication.common;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import collegeapplication.admin.AdminMain;
import collegeapplication.faculty.FacultyMain;
import collegeapplication.student.Student;
import collegeapplication.student.StudentMain;


/*
 * Title : PrintPageDialog.java
 * Created by : Ajaysinh Rathod
 * Purpose : To download student marksheet in selected format 
 * Mail : ajaysinhrathod1290@gmail.com
 */

@SuppressWarnings("serial")
public class PrintMarksheetDialog extends JDialog {

	private static PrintMarksheetDialog dialog;
	private final JPanel contentPanel = new JPanel();
	private  AdminMain am;
	private String filename;
	private BufferedImage image;
	private JLabel filedownloadedlabel;
	public printMarksheetPDF p;
	private FacultyMain fm;
	private StudentMain sm;
	private JLabel imagelabel;
	private JLabel filepathlabel;
	private JButton btnPdf;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			dialog = new PrintMarksheetDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**+
	 * Create the dialog.
	 */
	public PrintMarksheetDialog(AdminMain am,Student s)
	{
		this(s);
		this.am=am;
	
		image=new BufferedImage(am.marksheetpanel.getWidth(),am.marksheetpanel.getHeight(),BufferedImage.TYPE_INT_ARGB);
		am.marksheetpanel.disablebutton();
		am.marksheetpanel.print(image.getGraphics());
		am.marksheetpanel.enablebutton();
		BufferedImage imageicon=ImageUtil.resizeImage(image, imagelabel.getWidth()+20, imagelabel.getHeight()-20);
		imagelabel.setIcon(new ImageIcon(imageicon));
		p=new printMarksheetPDF(am);	
		p.setPdfTitle(s.getUserId()+" Marksheet");
		p.setPath(filepathlabel.getText()+"\\"+filename+".pdf");
		p.disposeDialog(this);
		btnPdf.addActionListener(p);
	}
	public PrintMarksheetDialog(FacultyMain fm,Student s)
	{
		this(s);
		this.fm=fm;
		
		image=new BufferedImage(fm.marksheetpanel.getWidth(),fm.marksheetpanel.getHeight(),BufferedImage.TYPE_INT_ARGB);
		fm.marksheetpanel.disablebutton();
		fm.marksheetpanel.print(image.getGraphics());
		fm.marksheetpanel.enablebutton();
		BufferedImage imageicon=ImageUtil.resizeImage(image, imagelabel.getWidth()+20, imagelabel.getHeight()-20);
		imagelabel.setIcon(new ImageIcon(imageicon));
		p=new printMarksheetPDF(fm);
		p.setPdfTitle(s.getUserId()+" Marksheet");
		p.setPath(filepathlabel.getText()+"\\"+filename+".pdf");
		p.disposeDialog(this);
		btnPdf.addActionListener(p);
	}
	public PrintMarksheetDialog(StudentMain sm,Student s)
	{
		this(s);
		this.sm=sm;
		
		image=new BufferedImage(sm.marksheetpanel.getWidth(),sm.marksheetpanel.getHeight(),BufferedImage.TYPE_INT_ARGB);
		sm.marksheetpanel.downloadbutton.setVisible(false);
		sm.marksheetpanel.print(image.getGraphics());
		sm.marksheetpanel.downloadbutton.setVisible(true);
		BufferedImage imageicon=ImageUtil.resizeImage(image, imagelabel.getWidth()+20, imagelabel.getHeight()-20);
		imagelabel.setIcon(new ImageIcon(imageicon));
		p=new printMarksheetPDF(sm);
		p.setPdfTitle(s.getUserId()+" Marksheet");
		p.setPath(filepathlabel.getText()+"\\"+filename+".pdf");
		p.disposeDialog(this);
		btnPdf.addActionListener(p);
	}
	private PrintMarksheetDialog(Student s) {
		super(dialog, null, Dialog.ModalityType.APPLICATION_MODAL);

		initDialogProperties();
		initContentPanel();
		initImageLabel();
		initFilePathLabel();
		initFileNameLabel(s);
		initExportButtons(s);
		initMetaLabels();
		initDownloadStatusLabel();
		initHeaderPanel();
	}

	/* -------------------- Dialog / Layout Initialization -------------------- */

	private void initDialogProperties() {
		getContentPane().setBackground(new Color(255, 255, 255));
		setBackground(new Color(220, 220, 220));
		setResizable(false);
		setTitle("Print Marksheet");
		setBounds(100, 100, 516, 294);
		getContentPane().setLayout(null);
	}

	private void initContentPanel() {
		contentPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBounds(0, 55, 510, 210);
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
	}

	/* -------------------- Left Image & File Info -------------------- */

	private void initImageLabel() {
		imagelabel = new JLabel("image");
		imagelabel.setBounds(10, 11, 124, 126);
		imagelabel.setOpaque(true);
		imagelabel.setBackground(new Color(255, 255, 255));
		imagelabel.setHorizontalAlignment(SwingConstants.CENTER);
		imagelabel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		contentPanel.add(imagelabel);
	}

	private void initFilePathLabel() {
		String home = System.getProperty("user.home");
		filepathlabel = new JLabel(home + "\\Downloads\\");
		filepathlabel.setForeground(new Color(0, 0, 0));
		filepathlabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		filepathlabel.setBounds(144, 37, 346, 26);
		contentPanel.add(filepathlabel);
	}

	private void initFileNameLabel(Student s) {
		JLabel filenamelabel = new JLabel();
		filename = s.getCourceCode() + "-" + s.getSemorYear() + "-" + s.getRollNumber() + "-"
				+ s.getFullName() + "-" + "mark-sheet";
		filenamelabel.setText(filename);
		filenamelabel.setForeground(Color.BLACK);
		filenamelabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		filenamelabel.setBounds(144, 80, 346, 33);
		contentPanel.add(filenamelabel);
	}

	/* -------------------- Export Buttons (PNG / JPG / PDF / Print) -------------------- */

	private void initExportButtons(Student s) {
		JButton btnPng = createPngButton();
		contentPanel.add(btnPng);

		JButton btnJpg = createJpgButton();
		contentPanel.add(btnJpg);

		btnPdf = createPdfButton();
		contentPanel.add(btnPdf);

		JButton btnPrint = createPrintButton(s);
		contentPanel.add(btnPrint);
	}

	private JButton createPngButton() {
		JButton btnPng = new JButton("PNG");
		btnPng.setIcon(new ImageIcon(".\\assets\\pngbutton.png"));
		btnPng.setFocusable(false);
		beforebutton(btnPng);
		btnPng.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnPng.setBounds(10, 163, 116, 33);

		btnPng.addActionListener(e -> handlePngExport(btnPng));
		return btnPng;
	}

	private void handlePngExport(JButton btnPng) {
		try {
			ImageIO.write(image, "png",
					new File(filepathlabel.getText() + "\\" + filename + ".png"));
			afterbutton(btnPng);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private JButton createJpgButton() {
		JButton btnJpg = new JButton("JPG");
		btnJpg.setIcon(new ImageIcon(".\\assets\\jpgbutton.png"));
		btnJpg.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnJpg.setFocusable(false);
		beforebutton(btnJpg);
		btnJpg.setBounds(136, 163, 115, 33);

		btnJpg.addActionListener(e -> handleJpgExport(btnJpg));
		return btnJpg;
	}

	private void handleJpgExport(JButton btnJpg) {
		try {
			BufferedImage goodImage = new BufferedImage(
					image.getWidth(),
					image.getHeight(),
					BufferedImage.TYPE_INT_RGB
			);
			Graphics2D g2d = goodImage.createGraphics();
			g2d.drawImage(image, 0, 0, null);
			g2d.dispose();

			ImageIO.write(
					goodImage,
					"jpeg",
					new File(filepathlabel.getText() + "\\" + filename + ".jpeg")
			);
			afterbutton(btnJpg);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private JButton createPdfButton() {
		JButton pdfButton = new JButton("PDF");
		pdfButton.setIcon(new ImageIcon(".\\assets\\pdfbutton.png"));
		pdfButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
		pdfButton.setFocusable(false);
		beforebutton(pdfButton);
		pdfButton.setBounds(261, 163, 115, 33);
		// Listener presumably added elsewhere in your class.
		return pdfButton;
	}

	private JButton createPrintButton(Student s) {
		JButton btnPrint = new JButton("Print");
		btnPrint.setIcon(new ImageIcon(".\\assets\\printbutton.png"));
		btnPrint.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnPrint.setFocusable(false);
		beforebutton(btnPrint);
		btnPrint.setBounds(386, 163, 114, 33);

		btnPrint.addActionListener(e -> handlePrintAction(s, btnPrint));
		return btnPrint;
	}

	private void handlePrintAction(Student s, JButton btnPrint) {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setJobName(s.getUserId() + " Marksheet");
		job.setPrintable(createPrintable());

		if (!job.printDialog()) {
			return;
		}

		try {
			disableMarksheetButtons();
			job.print();
			afterbutton(btnPrint);
			enableMarksheetButtons();
		} catch (PrinterException ex) {
			// handle exception if needed
			ex.printStackTrace();
		}
	}

	private Printable createPrintable() {
		return new Printable() {
			@Override
			public int print(Graphics pg, PageFormat pf, int pagenum) throws PrinterException {
				pf.setOrientation(PageFormat.LANDSCAPE);
				if (pagenum > 0) {
					return Printable.NO_SUCH_PAGE;
				}

				Graphics2D g = (Graphics2D) pg;
				g.translate(pf.getImageableX(), pf.getImageableY());
				g.scale(0.543, 0.60);

				JComponent marksheetPanel = getMarksheetPanel();
				if (marksheetPanel != null) {
					marksheetPanel.print(g);
				}
				return Printable.PAGE_EXISTS;
			}
		};
	}

	private JComponent getMarksheetPanel() {
		if (am != null) {
			return am.marksheetpanel;
		} else if (fm != null) {
			return fm.marksheetpanel;
		} else if (sm != null) {
			return sm.marksheetpanel;
		}
		return null;
	}

	private void disableMarksheetButtons() {
		if (am != null) {
			am.marksheetpanel.disablebutton();
		} else if (fm != null) {
			fm.marksheetpanel.disablebutton();
		} else if (sm != null) {
			sm.marksheetpanel.downloadbutton.setVisible(false);
		}
	}

	private void enableMarksheetButtons() {
		if (am != null) {
			am.marksheetpanel.enablebutton();
		} else if (fm != null) {
			fm.marksheetpanel.enablebutton();
		} else if (sm != null) {
			sm.marksheetpanel.downloadbutton.setVisible(true);
		}
	}

	/* -------------------- Labels & Header -------------------- */

	private void initMetaLabels() {
		JLabel lblFilePath = new JLabel("File Path :");
		lblFilePath.setForeground(Color.BLACK);
		lblFilePath.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFilePath.setBounds(144, 11, 346, 26);
		contentPanel.add(lblFilePath);

		JLabel lblFileName = new JLabel("File Name :");
		lblFileName.setForeground(Color.BLACK);
		lblFileName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFileName.setBounds(144, 60, 346, 26);
		contentPanel.add(lblFileName);
	}

	private void initDownloadStatusLabel() {
		filedownloadedlabel = new JLabel();
		filedownloadedlabel.setIcon(new ImageIcon(".\\assets\\downloadedbutton.png"));
		filedownloadedlabel.setText("Png Flie Downloaded Succesfully");
		filedownloadedlabel.setForeground(new Color(46, 139, 87));
		filedownloadedlabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
		filedownloadedlabel.setVisible(false);
		filedownloadedlabel.setBounds(144, 111, 366, 33);
		contentPanel.add(filedownloadedlabel);
	}

	private void initHeaderPanel() {
		JPanel panel = new JPanel();
		panel.setBackground(new Color(32, 178, 170));
		panel.setBounds(0, 2, 510, 47);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblPrintMarksheet = new JLabel("Print Marksheet");
		lblPrintMarksheet.setBounds(76, 11, 358, 25);
		panel.add(lblPrintMarksheet);
		lblPrintMarksheet.setForeground(new Color(255, 255, 255));
		lblPrintMarksheet.setBackground(new Color(32, 178, 170));
		lblPrintMarksheet.setFont(new Font("Segoe UI", Font.BOLD, 22));
		lblPrintMarksheet.setHorizontalAlignment(SwingConstants.CENTER);
	}
	public void dispose()
	{
		this.dispose();
	}
	public void beforebutton(JButton button)
	{
		button.setForeground(new Color(255, 255, 255));
		button.setFocusable(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setBorder(new EmptyBorder(0, 0, 0, 0));
		button.setBackground(new Color(32,178,170));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	public void afterbutton(JButton button)
	{
		filedownloadedlabel.setVisible(true);
		if(button.getText().contains("Print"))
		{
			filedownloadedlabel.setText(" File Printed Successfully");
		}
		else
		{
		filedownloadedlabel.setText(button.getText()+" File Downloaded Successfully");
		}
	}
	
}
