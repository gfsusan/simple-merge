import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.*;
import javax.swing.border.MatteBorder;


public class MainView extends JFrame{
	private JPanel toolPanel;	// tool panel
	private JButton compareBtn;
	private JButton upBtn;
	private JButton downBtn;
	private JButton copyToLeftBtn;
	private JButton copyToRightBtn;

	private JPanel holderPanel;
	private PanelView leftPV;	// specific panel (left)
	private PanelView rightPV;	// specific panel (right)
	
	// image icon for ImageBtn
	private ImageIcon compare_icon;
	private ImageIcon up_icon;
	private ImageIcon down_icon;
	private ImageIcon left_icon;
	private ImageIcon right_icon;
	private ImageIcon view_icon;
	
	private int comparePressed;

	
	public MainView() throws Exception {
		super("Simple Merge");
		
		toolPanel		= new JPanel();
		holderPanel 	= new JPanel();
		leftPV			= new PanelView();
		rightPV			= new PanelView();
		
		comparePressed 	= 0; // comparePressed: even=NOT pressed, odd= pressed
		
		// set image icon
		compare_icon 	= new ImageIcon("res/compare.png");
		up_icon 		= new ImageIcon("res/up.png");
		down_icon		= new ImageIcon("res/down.png");
		left_icon		= new ImageIcon("res/left.png");
		right_icon		= new ImageIcon("res/right.png");
		view_icon		= new ImageIcon("res/not_compare.png");
		
		// set size of image button
		Image compare_img 	= compare_icon.getImage();	compare_img	= compare_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		Image up_img		= up_icon.getImage();		up_img		= up_img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		Image down_img		= down_icon.getImage();		down_img	= down_img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		Image left_img		= left_icon.getImage();		left_img	= left_img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		Image right_img		= right_icon.getImage();	right_img	= right_img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		Image view_img		= view_icon.getImage();		view_img	= view_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		
		compare_icon	= new ImageIcon(compare_img);
		up_icon			= new ImageIcon(up_img);
		down_icon		= new ImageIcon(down_img);
		left_icon 		= new ImageIcon(left_img);
		right_icon 		= new ImageIcon(right_img);
		view_icon 		= new ImageIcon(view_img);
		
		// set image button
		compareBtn		= new JButton(compare_icon); 	compareBtn.setContentAreaFilled(false);
		upBtn 			= new JButton(up_icon);			upBtn.setContentAreaFilled(false);
		downBtn 		= new JButton(down_icon);		downBtn.setContentAreaFilled(false);
		copyToLeftBtn 	= new JButton(left_icon);		copyToLeftBtn.setContentAreaFilled(false);
		copyToRightBtn 	= new JButton(right_icon);		copyToRightBtn.setContentAreaFilled(false);
		
		// make Image Button's border invisible
		compareBtn.setBorderPainted(false);				compareBtn.setFocusPainted(false);
		upBtn.setBorderPainted(false);					upBtn.setFocusPainted(false);
		downBtn.setBorderPainted(false);				downBtn.setFocusPainted(false);
		copyToLeftBtn.setBorderPainted(false);			copyToLeftBtn.setFocusPainted(false);
		copyToRightBtn.setBorderPainted(false);			copyToRightBtn.setFocusPainted(false);	

		// set color of panel
		leftPV.setPanelColor(255,0,0);
		leftPV.setBorder(new MatteBorder(0,0,0,1, Color.GRAY));
		rightPV.setPanelColor(0, 255, 0);
		
		
		// set tool panel
		toolPanel.setLayout(new GridLayout(1, 5));
		toolPanel.setBorder(new MatteBorder(0,0,1,0, Color.GRAY));
		toolPanel.add(compareBtn);
		toolPanel.add(upBtn);
		toolPanel.add(downBtn);
		toolPanel.add(copyToLeftBtn);
		toolPanel.add(copyToRightBtn);
		
		// set holder panel
		holderPanel.setLayout(new GridLayout(1, 2, 0, 0));
		holderPanel.add(leftPV);
		holderPanel.add(rightPV);
		

		// add all panels
		this.setLayout(new BorderLayout());
		this.add(toolPanel, BorderLayout.NORTH);
		this.add(holderPanel, BorderLayout.CENTER);
		
		this.pack();
		this.setSize(1200, 900);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setMVbutton(false);
		
		leftPV.loadBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				load(leftPV, rightPV);
			}
		});
		
		rightPV.loadBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				load(rightPV, leftPV);
			}
		});
		
		leftPV.xBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				closeFile(leftPV);
			}
		});
		
		rightPV.xBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				closeFile(rightPV);
			}
		});
		
		
		compareBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("compare button pressed.");
				
				comparePressed++;
				
				if(comparePressed % 2 == 1) {
					// compareBtn pressed once->do compare
					compareBtn.setIcon(view_icon);
					
					setMode(Mode.COMPARE);
				}
				else{
					// compareBtn pressed twice->try to escape compare mode
					if(comparePressed >= 0) {
						int a = leftPV.showSaveDialog();
						int b = rightPV.showSaveDialog();
						
						if(a == 2 || b == 2) {
							// keep compare mode
							comparePressed++;
						} else{
							// convert to view mode
							compareBtn.setIcon(compare_icon);
							setMode(Mode.VIEW);
						}
					}
				}
			}
			
		});
		
		upBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("up button pressed.");
			
				
			
			}
			
		});
		
		downBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("down button pressed.");

			}
			
		});
		
		copyToLeftBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("copy to left button pressed.");
				

			}
			
		});
		
		copyToRightBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("copy to right button pressed.");
				
			}
			
		});
		
	}
	
	private void load(PanelView mine, PanelView yours) {
		System.out.println("Load button pressed.");
		
		int dirtyCheck = mine.showSaveDialog();
	
		if (dirtyCheck == 2) {
			System.out.println("File load canceled.");
			return;
		}
		
		// Load file via fileDialog
		FileDialog fd = new FileDialog(this, "Open File", FileDialog.LOAD);
		fd.setFilenameFilter(new FilenameFilter() {

			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.endsWith(".txt") || name.endsWith(".c") || name.endsWith(".cpp") || name.endsWith(".java") || name.endsWith(".md");
			}
			
		});
		fd.setVisible(true);
	
		if (fd.getFile() != null) {		// Pressed "Open" in FileDialog
			String filePath = fd.getDirectory() + fd.getFile();
        	
        	if (filePath.equals(yours.pc.getFilePath())) {	// if the files are equal, cancel everything
        		JOptionPane.showMessageDialog(null, "File is already open in another panel.", "ERROR!", JOptionPane.ERROR_MESSAGE);
        		System.out.println("File is already open in another panel.");
        	}
        	else {
        		if (mine.pc.load(filePath)) {		// if load succeeds, set to View mode and update View
    				mine.setMode(Mode.VIEW);
        			mine.textArea.setText(mine.pc.getOriginalFileContent());
        		}
        		else {
        			JOptionPane.showMessageDialog(null, "Failed to open file.1", "ERROR!", JOptionPane.ERROR_MESSAGE);
        			System.out.println("Failed to open file.");
        		}
        	}
        }
		
		else {	// Pressed "Cancel" in FileDialog
            System.out.println("File load canceled.");
        }
		
		updateView();
	}
	
	private void closeFile(PanelView pv) {
		
		// TODO Auto-generated method stub
		System.out.println("xbutton pressed.");
		
		pv.pc.closeFile();
		
		// Set Mode
		setMode(Mode.VIEW);
	}
	
	public void setMVbutton(boolean tf){
		compareBtn.setEnabled(tf);
		upBtn.setEnabled(tf);
		downBtn.setEnabled(tf);
		copyToLeftBtn.setEnabled(tf);
		copyToRightBtn.setEnabled(tf);
	}
	
	
	// Check file format
//	private boolean accept(File file) {
//		if(file.isFile()) {
//			String fileName = file.getName();
//			if(fileName.endsWith(".txt")) return true; // txt format
//		}
//		
//		return false;
//	}
	
	private void updateView() {
		// set enable [compare/merge/traverse] button only when two panel loaded
		if(leftPV.pc.fileIsOpen() && rightPV.pc.fileIsOpen()){
			setMVbutton(true);
		} else {
			setMVbutton(false);
		}
		
		leftPV.updateView();
		rightPV.updateView();
		System.out.println("Main View is updated");
	}

	private void setMode(Mode mode) {
		switch(mode) {
		case VIEW:
			leftPV.setMode(Mode.VIEW);
			rightPV.setMode(Mode.VIEW);		
			compareBtn.setEnabled(true);
			upBtn.setEnabled(false);
			downBtn.setEnabled(false);
			copyToLeftBtn.setEnabled(false);
			copyToRightBtn.setEnabled(false);
			break;
			
		case COMPARE:
			leftPV.setMode(Mode.COMPARE);
			rightPV.setMode(Mode.COMPARE);
			compareBtn.setEnabled(true);
			upBtn.setEnabled(true);
			downBtn.setEnabled(true);
			copyToLeftBtn.setEnabled(true);
			copyToRightBtn.setEnabled(true);

			break;
		default:
			break;
		}
		
		updateView();
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Start!");
		
		MainView mv = new MainView();
		
		System.out.println("End!");
	}
}