import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/*
 * GUI that we have not yet linked to our program
 */

public class Interface extends Frame{
	// Constructor to setup the GUI components
	public Interface() {
		
	}


	public static void main(String[] args) {
		
		
	   Dimension d = new Dimension(100, 10);

	   JFrame frame = new JFrame("Fastest Path to your Major");
	   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   
	   JPanel mainPanel = new JPanel();
	   mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	   frame.add(mainPanel);
	   
	   
	   //create a dropdown menu to select your year in college
	   JPanel yearPanel = new JPanel();
	   mainPanel.add(yearPanel);
	   
	   JLabel yearLabel = new JLabel("What year in college are you?", SwingConstants.LEFT);
	   yearPanel.add(yearLabel);
	   yearLabel.setVisible(true);
	  
	   String[] yearChoices = {"Freshman (0-31 credits)","Sophomore (32-63 credits)", "Junior (64-95 credits)","Senior (96+ credits)"};
	   final JComboBox<String> yearDropdown = new JComboBox<String>(yearChoices);
	   yearDropdown.setMaximumSize(new Dimension(200, 10)); //Takes a dimension object
	   yearPanel.add(yearDropdown);
	   yearDropdown.setVisible(true);
	   
	   //checkboxes to indicate which major you plan on completing
	   JPanel majorPanel = new JPanel();
	   mainPanel.add(majorPanel);
	   JLabel majorLabel = new JLabel("What is your major?");
	   majorPanel.add(majorLabel);
	   majorLabel.setVisible(true);
	   JCheckBox math = new JCheckBox ("Mathematics");
	   JCheckBox compSci = new JCheckBox ("Computer Science");
	   math.setSelected(false);
	   compSci.setSelected(false);
	   majorPanel.add(math);
	   majorPanel.add(compSci);
	   math.setVisible(true);
	   compSci.setVisible(true);

	   
	   //dropdown to indicate whether the writing requirement has been met
	   JPanel writingPanel = new JPanel();
	   mainPanel.add(writingPanel);
	   JLabel writingLabel = new JLabel("Have you satisfied your Writing requirement?");
	   writingPanel.add(writingLabel);
	   writingLabel.setVisible(true);
	  
	   String[] writingChoices = {"Yes","No"};
	   final JComboBox<String> writingDropdown = new JComboBox<String>(writingChoices);
	   writingDropdown.setMaximumSize(new Dimension (100, 10));
	   
	   writingPanel.add(writingDropdown);
	   writingDropdown.setVisible(true);
	   
	   
	   //dropdown to indicate whether the US Identities requirement has been met
	   JPanel USIDPanel = new JPanel();
	   mainPanel.add(USIDPanel);
	   JLabel USIDLabel = new JLabel("Have you satisfied your US Identities requirement?");
	   USIDPanel.add(USIDLabel);
	   USIDLabel.setVisible(true);
	  
	   String[] USIDChoices = {"Yes","No"};
	   final JComboBox<String> USIDDropdown = new JComboBox<String>(USIDChoices);
	   USIDDropdown.setMaximumSize(new Dimension(100, 10));

	   USIDPanel.add(USIDDropdown);
	   USIDDropdown.setVisible(true);
	   
	   
	   //dropdown to indicate whether the Internationalism requirement has been met
	   JPanel internationalismPanel = new JPanel();
	   mainPanel.add(internationalismPanel);
	   JLabel internationalismLabel = new JLabel("Have you satisfied your Internationalism requirement?");
	   internationalismPanel.add(internationalismLabel);
	   internationalismLabel.setVisible(true);
	  
	   String[] internationalismChoices = {"Yes","No"};
	   final JComboBox<String> internationalismDropdown = new JComboBox<String>(internationalismChoices);
	   internationalismDropdown.setMaximumSize(new Dimension(100, 10));

	   internationalismPanel.add(internationalismDropdown);
	   internationalismDropdown.setVisible(true);
	   
	   
	   //dropdown to indicate whether the quantitative reasoning requirement has been met
	   JPanel quantitativePanel = new JPanel();
	   mainPanel.add(quantitativePanel);
	   JLabel quantitativeLabel = new JLabel("Have you satisfied your Quantitative Reasoning requirement?");
	   quantitativePanel.add(quantitativeLabel);
	   quantitativeLabel.setVisible(true);
	  
	   String[] quantitativeChoices = {"Yes","No"};
	   final JComboBox<String> quantitativeDropdown = new JComboBox<String>(quantitativeChoices);
	   quantitativeDropdown.setMaximumSize(new Dimension(100, 10));

	   quantitativePanel.add(quantitativeDropdown);
	   quantitativeDropdown.setVisible(true);
	   
	   
	   //dropdown to indicate whether the Foreign Language requirement has been met
	   JPanel languagePanel = new JPanel();
	   mainPanel.add(languagePanel);
	   JLabel languageLabel = new JLabel("How many Foreign Language classes have you taken?");
	   languagePanel.add(languageLabel);
	   languageLabel.setVisible(true);
	  
	   String[] languageChoices = {"0", "1", "2", "3", "4+"};
	   final JComboBox<String> languageDropdown = new JComboBox<String>(languageChoices);
	   languageDropdown.setMaximumSize(new Dimension(100, 10));
	   
	   languagePanel.add(languageDropdown);
	   languageDropdown.setVisible(true);
	   
	   
	   //dropdown to indicate whether the Social Science requirement has been met
	   JPanel socialSciencePanel = new JPanel();
	   mainPanel.add(socialSciencePanel);
	   JLabel socialScienceLabel = new JLabel("How many Social Science classes have you taken?");
	   socialSciencePanel.add(socialScienceLabel);
	   socialScienceLabel.setVisible(true);
	  
	   String[] socialScienceChoices = {"0", "1","2+"};
	   final JComboBox<String> socialScienceDropdown = new JComboBox<String>(socialScienceChoices);
	   socialScienceDropdown.setMaximumSize(new Dimension(100, 10));
	   
	   socialSciencePanel.add(socialScienceDropdown);
	   socialScienceDropdown.setVisible(true);
	   
	   
	   //dropdown to indicate whether the Natural Science requirement has been met
	   JPanel naturalSciencePanel = new JPanel();
	   mainPanel.add(naturalSciencePanel);
	   JLabel naturalScienceLabel = new JLabel("How many Natural Science classes have you taken?");
	   naturalSciencePanel.add(naturalScienceLabel);
	   naturalScienceLabel.setVisible(true);
	  
	   String[] naturalScienceChoices = {"0", "1","2+"};
	   final JComboBox<String> naturalScienceDropdown = new JComboBox<String>(naturalScienceChoices);
	   naturalScienceDropdown.setMaximumSize(new Dimension(100, 10));

	   naturalSciencePanel.add(naturalScienceDropdown);
	   naturalScienceDropdown.setVisible(true);
	   
	   
	 //dropdown to indicate whether the Humanities/Fine Arts requirement has been met
	   JPanel humanitiesPanel = new JPanel();
	   mainPanel.add(humanitiesPanel);
	   JLabel humanitiesLabel = new JLabel("How many Humanities/Fine Arts classes have you taken?");
	   humanitiesPanel.add(humanitiesLabel);
	   humanitiesLabel.setVisible(true);
	  
	   String[] humanitiesChoices = {"0", "1","2+"};
	   final JComboBox<String> humanitiesDropdown = new JComboBox<String>(humanitiesChoices);
	   humanitiesDropdown.setMaximumSize(new Dimension(100, 10));

	   humanitiesPanel.add(humanitiesDropdown);
	   humanitiesDropdown.setVisible(true);
	 
	
	   
	   JPanel nextPanel = new JPanel();
	   mainPanel.add(nextPanel);
	   JButton next = new JButton("Next");
	   next.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent e){
//		    	   ArrayList<String> majors = new ArrayList<String>();
//				   if(math.isSelected()) {
//					   majors.add("Math");
//				   }
//				   
//				   if(compSci.isSelected()) {
//					   majors.add("Computer Science");
//				   }
//				   FindPath.stuInfo.setMajors(majors);
//				   if(yearDropdown.getSelectedItem().toString().equals("Freshman (0-31 credits)")){
//					   FindPath.stuInfo.setYear(1);
//				   } else if(yearDropdown.getSelectedItem().toString().equals("Sophomore (32-63 credits)")){
//					   FindPath.stuInfo.setYear(2);
//				   } else if(yearDropdown.getSelectedItem().toString().equals("Junior (64-95 credits)")){
//					   FindPath.stuInfo.setYear(3);
//				   } else if(yearDropdown.getSelectedItem().toString().equals("Senior (96+ credits)")){
//					   FindPath.stuInfo.setYear(4);
//				   }
//				   
//				   
//				   if(writingDropdown.getSelectedItem().toString().equals("Yes")){
//					   FindPath.stuInfo.setWriting(true);
//				   } else if(yearDropdown.getSelectedItem().toString().equals("No")){
//					   FindPath.stuInfo.setWriting(false);
//				   }
//				   
//				   
//				   if(USIDDropdown.getSelectedItem().toString().equals("Yes")){
//					   FindPath.stuInfo.setUsID(true);
//				   } else if(USIDDropdown.getSelectedItem().toString().equals("No")){
//					   FindPath.stuInfo.setUsID(false);
//				   }
//				   
//				   
//				   if(internationalismDropdown.getSelectedItem().toString().equals("Yes")){
//					   FindPath.stuInfo.setInternationalism(true);
//				   } else if(internationalismDropdown.getSelectedItem().toString().equals("No")){
//					   FindPath.stuInfo.setInternationalism(false);
//				   }
//				   
//				   
//				   if(quantitativeDropdown.getSelectedItem().toString().equals("Yes")){
//					   FindPath.stuInfo.setQuantitative(true);
//				   } else if(quantitativeDropdown.getSelectedItem().toString().equals("No")){
//					   FindPath.stuInfo.setQuantitative(false);
//				   }
//				   
//				   
//				   if(languageDropdown.getSelectedItem().toString().equals("0")){
//					   FindPath.stuInfo.setLanguage(0);
//				   } else if(languageDropdown.getSelectedItem().toString().equals("1")){
//					   FindPath.stuInfo.setLanguage(1);
//				   } else if(languageDropdown.getSelectedItem().toString().equals("2")){
//					   FindPath.stuInfo.setLanguage(2);
//				   } else if(languageDropdown.getSelectedItem().toString().equals("3")){
//					   FindPath.stuInfo.setLanguage(3);
//				   } else if(languageDropdown.getSelectedItem().toString().equals("4+")){
//					   FindPath.stuInfo.setLanguage(4);
//				   }
//				   
//				   
//				   if(socialScienceDropdown.getSelectedItem().toString().equals("0")){
//					   FindPath.stuInfo.setSocialSci(0);
//				   } else if(socialScienceDropdown.getSelectedItem().toString().equals("1")){
//					   FindPath.stuInfo.setSocialSci(1);
//				   } else if(socialScienceDropdown.getSelectedItem().toString().equals("2+")){
//					   FindPath.stuInfo.setSocialSci(2);
//				   }
//				   
//				   if(naturalScienceDropdown.getSelectedItem().toString().equals("0")){
//					   FindPath.stuInfo.setNaturalSci(0);
//				   } else if(naturalScienceDropdown.getSelectedItem().toString().equals("1")){
//					   FindPath.stuInfo.setNaturalSci(1);
//				   } else if(naturalScienceDropdown.getSelectedItem().toString().equals("2+")){
//					   FindPath.stuInfo.setNaturalSci(2);
//				   }
//				   
//				   if(humanitiesDropdown.getSelectedItem().toString().equals("0")){
//					   FindPath.stuInfo.setHumanFArts(0);
//				   } else if(humanitiesDropdown.getSelectedItem().toString().equals("1")){
//					   FindPath.stuInfo.setHumanFArts(1);
//				   } else if(humanitiesDropdown.getSelectedItem().toString().equals("2+")){
//					   FindPath.stuInfo.setHumanFArts(2);
//				   }
				   
		    	   mainPanel.removeAll();
		    	   mainPanel.revalidate();
		    	   mainPanel.repaint();
		           
		           JPanel mathCoursesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		    	   mainPanel.add(mathCoursesPanel, BorderLayout.NORTH);
		    	   mathCoursesPanel.setPreferredSize(mathCoursesPanel.getPreferredSize());
		    	   JLabel mathCoursesLabel = new JLabel("Select all of the following math courses you have taken:");
		    	   mathCoursesPanel.add(mathCoursesLabel);
		    	   mathCoursesLabel.setVisible(true);
		    	   JCheckBox math125 = new JCheckBox ("Math 125");
		    	   JCheckBox math135 = new JCheckBox ("Math 135");
		    	   JCheckBox math137 = new JCheckBox ("Math 137");
		    	   JCheckBox math155 = new JCheckBox ("Math 155");
		    	   JCheckBox math212 = new JCheckBox ("Math 212");
		    	   JCheckBox math236 = new JCheckBox ("Math 236");
		    	   JCheckBox math237 = new JCheckBox ("Math 237");
		    	   JCheckBox math253 = new JCheckBox ("Math 253");
		    	   JCheckBox math279 = new JCheckBox ("Math 279");
		    	   JCheckBox math312 = new JCheckBox ("Math 312");
		    	   JCheckBox math313 = new JCheckBox ("Math 313");
		    	   JCheckBox math354 = new JCheckBox ("Math 354");
		    	   JCheckBox math376 = new JCheckBox ("Math 376");
		    	   JCheckBox math377 = new JCheckBox ("Math 377");
		    	   JCheckBox math378 = new JCheckBox ("Math 378");
		    	   JCheckBox math379 = new JCheckBox ("Math 379");
		    	   JCheckBox math432 = new JCheckBox ("Math 432");
		    	   JCheckBox math437 = new JCheckBox ("Math 437");
		    	   JCheckBox math453 = new JCheckBox ("Math 453");
		    	   JCheckBox math455 = new JCheckBox ("Math 455");
		    	   JCheckBox math471 = new JCheckBox ("Math 471");
		    	   JCheckBox math476 = new JCheckBox ("Math 476");
		    	   JCheckBox math477 = new JCheckBox ("Math 477");
		    	   math125.setSelected(false);
		    	   math135.setSelected(false);
		    	   math137.setSelected(false);
		    	   math155.setSelected(false);
		    	   math212.setSelected(false);
		    	   math236.setSelected(false);
		    	   math237.setSelected(false);
		    	   math253.setSelected(false);
		    	   math279.setSelected(false);
		    	   math312.setSelected(false);
		    	   math313.setSelected(false);
		    	   math354.setSelected(false);
		    	   math376.setSelected(false);
		    	   math377.setSelected(false);
		    	   math378.setSelected(false);
		    	   math379.setSelected(false);
		    	   math432.setSelected(false);
		    	   math437.setSelected(false);
		    	   math453.setSelected(false);
		    	   math455.setSelected(false);
		    	   math471.setSelected(false);
		    	   math476.setSelected(false);
		    	   math477.setSelected(false);
		    	   mathCoursesPanel.add(math125);
		    	   mathCoursesPanel.add(math135);
		    	   mathCoursesPanel.add(math137);
		    	   mathCoursesPanel.add(math155);
		    	   mathCoursesPanel.add(math212);
		    	   mathCoursesPanel.add(math236);
		    	   mathCoursesPanel.add(math237);
		    	   mathCoursesPanel.add(math279);
		    	   mathCoursesPanel.add(math312);
		    	   mathCoursesPanel.add(math313);
		    	   mathCoursesPanel.add(math354);
		    	   mathCoursesPanel.add(math376);
		    	   mathCoursesPanel.add(math377);
		    	   mathCoursesPanel.add(math378);
		    	   mathCoursesPanel.add(math379);
		    	   mathCoursesPanel.add(math432);
		    	   mathCoursesPanel.add(math437);
		    	   mathCoursesPanel.add(math453);
		    	   mathCoursesPanel.add(math455);
		    	   mathCoursesPanel.add(math471);
		    	   mathCoursesPanel.add(math476);
		    	   mathCoursesPanel.add(math477);
		    	   math125.setVisible(true);
		    	   math135.setVisible(true);
		    	   math137.setVisible(true);
		    	   math155.setVisible(true);
		    	   math212.setVisible(true);
		    	   math236.setVisible(true);
		    	   math237.setVisible(true);
		    	   math253.setVisible(true);
		    	   math279.setVisible(true);
		    	   math312.setVisible(true);
		    	   math313.setVisible(true);
		    	   math354.setVisible(true);
		    	   math376.setVisible(true);
		    	   math377.setVisible(true);
		    	   math378.setVisible(true);
		    	   math379.setVisible(true);
		    	   math432.setVisible(true);
		    	   math437.setVisible(true);
		    	   math453.setVisible(true);
		    	   math455.setVisible(true);
		    	   math471.setVisible(true);
		    	   math476.setVisible(true);
		    	   math477.setVisible(true);
		    	   
		    	   
		    	   
		    	   
		    	   
		    	   JPanel CSCoursesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		    	   mainPanel.add(CSCoursesPanel, BorderLayout.SOUTH);
		    	   CSCoursesPanel.setPreferredSize(CSCoursesPanel.getPreferredSize());
		    	   JLabel CSCoursesLabel = new JLabel("Select all of the following computer science courses you have taken:");
		    	   CSCoursesPanel.add(CSCoursesLabel);
		    	   CSCoursesLabel.setVisible(true);
		    	   JCheckBox CS123 = new JCheckBox ("Comp 123");
		    	   JCheckBox CS124 = new JCheckBox ("Comp 124");
		    	   JCheckBox CS221 = new JCheckBox ("Comp 221");
		    	   JCheckBox CS225 = new JCheckBox ("Comp 225");
		    	   JCheckBox CS240 = new JCheckBox ("Comp 240");
		    	   JCheckBox CS302 = new JCheckBox ("Comp 302");
		    	   JCheckBox CS340 = new JCheckBox ("Comp 340");
		    	   JCheckBox CS342 = new JCheckBox ("Comp 342");
		    	   JCheckBox CS346 = new JCheckBox ("Comp 346");
		    	   JCheckBox CS380 = new JCheckBox ("Comp 380");
		    	   JCheckBox CS440 = new JCheckBox ("Comp 440");
		    	   JCheckBox CS445 = new JCheckBox ("Comp 445");
		    	   JCheckBox CS484 = new JCheckBox ("Comp 484");
		    	   CS123.setSelected(false);
		    	   CS124.setSelected(false);
		    	   CS221.setSelected(false);
		    	   CS225.setSelected(false);
		    	   CS240.setSelected(false);
		    	   CS302.setSelected(false);
		    	   CS340.setSelected(false);
		    	   CS342.setSelected(false);
		    	   CS346.setSelected(false);
		    	   CS380.setSelected(false);
		    	   CS440.setSelected(false);
		    	   CS445.setSelected(false);
		    	   CS484.setSelected(false);
		    	   CSCoursesPanel.add(CS123);
		    	   CSCoursesPanel.add(CS124);
		    	   CSCoursesPanel.add(CS221);
		    	   CSCoursesPanel.add(CS225);
		    	   CSCoursesPanel.add(CS240);
		    	   CSCoursesPanel.add(CS302);
		    	   CSCoursesPanel.add(CS340);
		    	   CSCoursesPanel.add(CS342);
		    	   CSCoursesPanel.add(CS346);
		    	   CSCoursesPanel.add(CS380);
		    	   CSCoursesPanel.add(CS440);
		    	   CSCoursesPanel.add(CS445);
		    	   CSCoursesPanel.add(CS484);
		    	   CS123.setVisible(true);
		    	   CS124.setVisible(true);
		    	   CS221.setVisible(true);
		    	   CS225.setVisible(true);
		    	   CS240.setVisible(true);
		    	   CS302.setVisible(true);
		    	   CS340.setVisible(true);
		    	   CS342.setVisible(true);
		    	   CS346.setVisible(true);
		    	   CS380.setVisible(true);
		    	   CS440.setVisible(true);
		    	   CS445.setVisible(true);
		    	   CS484.setVisible(true);
		    	   
		    	   
		    	   
		    	   
		    	   JPanel crossListedCoursesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		    	   mainPanel.add(crossListedCoursesPanel, BorderLayout.SOUTH);
		    	   JLabel crossListedCoursesLabel = new JLabel("Select all of the following cross-listed courses you have taken:");
		    	   crossListedCoursesPanel.add(crossListedCoursesLabel);
		    	   crossListedCoursesLabel.setVisible(true);
		    	   JCheckBox crossList261 = new JCheckBox ("Math 361 / Comp 261");
		    	   JCheckBox crossList365 = new JCheckBox ("Math/Comp 365");
		    	   JCheckBox crossList479 = new JCheckBox ("Math/Comp 479");
		    	   crossList261.setSelected(false);
		    	   crossList365.setSelected(false);
		    	   crossList479.setSelected(false);
		    	   crossListedCoursesPanel.add(crossList261);
		    	   crossListedCoursesPanel.add(crossList365);
		    	   crossListedCoursesPanel.add(crossList479);
		    	   crossList261.setVisible(true);
		    	   crossList365.setVisible(true);
		    	   crossList479.setVisible(true);
		    	   
		    	   JButton generate = new JButton("Generate my major plan!");
		    	   crossListedCoursesPanel.add(generate);
		    	   generate.addActionListener(new ActionListener() {
		    		   public void actionPerformed(ActionEvent e) {
		    			   //TODO: Storing the courses the user has taken
		    			   if(math125.isSelected()) {
		    			   }
		    			   if(math135.isSelected()) {
		    				   
		    			   }
		    			   if(math137.isSelected()) {
		    				   
		    			   }
		    			   if(math155.isSelected()) {
		    				   
		    			   }
		    			   if(math212.isSelected()) {
		    				   
		    			   }
		    			   if(math236.isSelected()) {
		    				   
		    			   }
		    			   if(math237.isSelected()) {
		    				   
		    			   }
		    			   if(math279.isSelected()) {
		    				   
		    			   }
		    			   if(math312.isSelected()) {
		    				   
		    			   }
		    			   if(math313.isSelected()) {
		    				   
		    			   }
		    			   if(math354.isSelected()) {
		    				   
		    			   }
		    			   if(math376.isSelected()) {
		    				   
		    			   }
		    			   if(math377.isSelected()) {
		    				   
		    			   }
		    			   if(math378.isSelected()) {
		    				   
		    			   }
		    			   if(math379.isSelected()) {
		    				   
		    			   }
		    			   if(math432.isSelected()) {
		    				   
		    			   }
		    			   if(math437.isSelected()) {
		    				   
		    			   }
		    			   if(math453.isSelected()) {
		    				   
		    			   }
		    			   if(math455.isSelected()) {
		    				   
		    			   }
		    			   if(math471.isSelected()) {
		    				   
		    			   }
		    			   if(math476.isSelected()) {
		    				   
		    			   }
		    			   if(math477.isSelected()) {
		    				   
		    			   }
		    			   if(CS123.isSelected()) {
		    				   
		    			   }
		    			   if(CS124.isSelected()) {
		    				   
		    			   }
		    			   if(CS221.isSelected()) {
		    				   
		    			   }
		    			   if(CS225.isSelected()) {
		    				   
		    			   }
		    			   if(CS240.isSelected()) {
		    				   
		    			   }
		    			   if(CS302.isSelected()) {
		    				   
		    			   }
		    			   if(CS340.isSelected()) {
		    				   
		    			   }
		    			   if(CS342.isSelected()) {
		    				   
		    			   }
		    			   if(CS346.isSelected()) {
		    				   
		    			   }
		    			   if(CS380.isSelected()) {
		    				   
		    			   }
		    			   if(CS440.isSelected()) {
		    				   
		    			   }
		    			   if(CS445.isSelected()) {
		    				   
		    			   }
		    			   if(CS484.isSelected()) {
		    				   
		    			   }
		    			   if(crossList261.isSelected()) {
		    				   
		    			   }
		    			   if(crossList365.isSelected()) {
		    				   
		    			   }
		    			   if(crossList479.isSelected()) {
		    				   
		    			   }
		    			   
		    			   mainPanel.removeAll();
				    	   mainPanel.revalidate();
				    	   mainPanel.repaint();
				    	   
				    	   JLabel sem1Label = new JLabel("Semester 1:     ");
				    	   mainPanel.add(sem1Label);
				    	   sem1Label.setVisible(true);
				    	   
				    	   JLabel sem2Label = new JLabel("Semester 2:     ");
				    	   mainPanel.add(sem2Label);
				    	   sem2Label.setVisible(true);
				    	   
				    	   JLabel sem3Label = new JLabel("Semester 3:     ");
				    	   mainPanel.add(sem3Label);
				    	   sem3Label.setVisible(true);
				    	   
				    	   JLabel sem4Label = new JLabel("Semester 4:     ");
				    	   mainPanel.add(sem4Label);
				    	   sem4Label.setVisible(true);
				    	   
				    	   JLabel sem5Label = new JLabel("Semester 5:     ");
				    	   mainPanel.add(sem5Label);
				    	   sem5Label.setVisible(true);
				    	   
				    	   JLabel sem6Label = new JLabel("Semester 6:     ");
				    	   mainPanel.add(sem6Label);
				    	   sem6Label.setVisible(true);
				    	   
				    	   JLabel sem7Label = new JLabel("Semester 7:     ");
				    	   mainPanel.add(sem7Label);
				    	   sem7Label.setVisible(true);
				    	   
				    	   JLabel sem8Label = new JLabel("Semester 8:     ");
				    	   mainPanel.add(sem8Label);
				    	   sem8Label.setVisible(true);
				    	   
				    	   frame.setSize(500, 155);
		    		   }
		    		 });
		    	   frame.setSize(575, 550);
		    	   frame.setResizable(false);
		      }
		   });
	   nextPanel.add(next);
	 
	   frame.setLocation(300, 150);
	   frame.pack();
	   frame.setResizable(false);
	   frame.setVisible(true);
	   
	}
}
