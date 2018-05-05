import java.util.ArrayList;
import java.util.Map;


/**
 * Keeps track of all of the information that we have about the user
 * @author Danny and Kai
 *
 */

public class StudentInfo {

	//We also have to add a list of majors to studentInfo
	
	Map<Course, Integer> studentCourses;
	
	//1 through 4
	int yearInSchool;
	
	// Ex: 2017, 2018
	int year;
	
	ArrayList<String> majors;

	String semester;
	
	
	//Here we can set flags to determine if certain requirements have been met
	//Example:
	//CS requirements
	Boolean comp123 = false;
	Boolean comp124 = false;
	Boolean comp221 = false;
	Boolean comp225 = false;
	Boolean comp240 = false;
	Boolean comp261 = false;
	//Use math279 requirement under MATH
	int mathCourse = 0; //This must be greater then or equal to 2, math 279 does not count towards these two. 
	int electives = 0; //This must be greater than or equal to 3. Must be satisfied by courses 300 - 500. 
	Boolean capstone = false; //Satisfied by comp 342, comp 346, comp 380, comp 440. comp 445, comp 479, comp 484. One of these courses must be taken
	//in the junior year or fall of the senior year. 

	
	//MATH requirements
	Boolean math236 = false;
	Boolean math237 = false;
	Boolean math279 = false;
	Boolean math155 = false;
	Boolean CSclass = false;
	int mathElectives = 0; //This must be greater than or equal to 5, only 300 or higher level courses count. 
	boolean mathCapstone = false;
	
	//General Requirements
	Boolean writing = false;
	Boolean usID = false;
	Boolean internationalism = false;
	Boolean quantitative = false;
	int language = 0; //This could potentially take up to 4 classes. 
	int socialSci = 0; //This must be greater than or equal to two.
	int naturalSci = 0; //This also includes math classes, must be greater than or equal to two. 
	int humanFArts = 0; //Humanities/Fine Arts, this must be greater than or equal to 3. 


	public StudentInfo(){
		this.year = FindPath.CURRENT_YEAR;
		this.semester = FindPath.CURRENT_SEMESTER;
	}
	
	//Checking is a computer science major has been satisfied
	public boolean compMajorSatisfied(){
		if(capstone==comp123==comp124==comp221==comp225==comp261==comp240==writing==usID==internationalism==quantitative==true ){
			if(language>3){
				if(socialSci>1){
					if(naturalSci>1){
						if (humanFArts>2){
							if (electives>2){
								if(mathCourse>1){
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	//checking to see if a math major has been satisfied. 
	public boolean mathMajorSatisfied(){
		if(mathCapstone==math236==math237==math155==CSclass==math279==writing==usID==internationalism==quantitative==true){
			if(language>3){
				if(socialSci>1){
					if(naturalSci>1){
						if (humanFArts>2){
							if (mathElectives>4){
								if (checkSpecialMath()){
								return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	
	/*
	 * A method to check if a student has taken the required math courses:
	 * Structure/Combinatorics or Real/Complex Analysis.
	 */
	
	private boolean checkSpecialMath(){
		boolean analysis = false;
		boolean combOrStruct = false;
		for (Map.Entry<Course, Integer> entry : studentCourses.entrySet()){
			if (entry.getKey().getCourseName().equals("Algebraic Structures") || entry.getKey().getCourseName().equals("Combinatorics")){
				combOrStruct = true;
			}
			if (entry.getKey().getCourseName().equals("Real Analysis") || entry.getKey().getCourseName().equals("Complex Analysis")){
				analysis = true;
			}
		}
		if (analysis && combOrStruct){
			return true;
		}
		return false; 
	}
	
	
	
	//GETTERS AND SETTERS
	
	public boolean getMathCapstone() {
		return mathCapstone;
	}

	public void setMathCapstone(boolean mathCapstone) {
		this.mathCapstone = mathCapstone;
	}

	public Map<Course, Integer> getStudentCourses() {
		return studentCourses;
	}
	public void setStudentCourses(Map<Course, Integer> studentCourseList) {
		this.studentCourses = studentCourseList;
	}
	public Boolean getComp123() {
		return comp123;
	}
	public void setComp123(Boolean comp123) {
		this.comp123 = comp123;
	}
	public Boolean getComp124() {
		return comp124;
	}
	public void setComp124(Boolean comp124) {
		this.comp124 = comp124;
	}
	public Boolean getComp221() {
		return comp221;
	}
	public void setComp221(Boolean comp221) {
		this.comp221 = comp221;
	}
	public Boolean getComp225() {
		return comp225;
	}
	public void setComp225(Boolean comp225) {
		this.comp225 = comp225;
	}
	public Boolean getComp240() {
		return comp240;
	}
	public void setComp240(Boolean comp240) {
		this.comp240 = comp240;
	}
	public Boolean getComp261() {
		return comp261;
	}
	public void setComp261(Boolean comp261) {
		this.comp261 = comp261;
	}
	public Boolean getMath279() {
		return math279;
	}
	public void setMath279(Boolean math279) {
		this.math279 = math279;
	}
	
	public Boolean getMath236() {
		return math236;
	}

	public void setMath236(Boolean math236) {
		this.math236 = math236;
	}

	public Boolean getMath237() {
		return math237;
	}

	public void setMath237(Boolean math237) {
		this.math237 = math237;
	}

	public Boolean getMath155() {
		return math155;
	}

	public void setMath155(Boolean math155) {
		this.math155 = math155;
	}

	public Boolean getCSclass() {
		return CSclass;
	}

	public void setCSclass(Boolean cSclass) {
		CSclass = cSclass;
	}

	public int getMathElectives() {
		return mathElectives;
	}

	public void setMathElectives(int mathElectives) {
		this.mathElectives = mathElectives;
	}

	public int getMathCourse() {
		return mathCourse;
	}
	public void setMathCourse(int mathCourse) {
		this.mathCourse = mathCourse;
	}
	public int getElectives() {
		return electives;
	}
	public void setElectives(int electives) {
		this.electives = electives;
	}
	public Boolean getCapstone() {
		return capstone;
	}
	public void setCapstone(Boolean capstone) {
		this.capstone = capstone;
	}
	public Boolean getWriting() {
		return writing;
	}
	public void setWriting(Boolean writing) {
		this.writing = writing;
	}
	public Boolean getUsID() {
		return usID;
	}
	public void setUsID(Boolean usID) {
		this.usID = usID;
	}
	public Boolean getInternationalism() {
		return internationalism;
	}
	public void setInternationalism(Boolean internationalism) {
		this.internationalism = internationalism;
	}
	public Boolean getQuantitative() {
		return quantitative;
	}
	public void setQuantitative(Boolean quantitative) {
		this.quantitative = quantitative;}
	
	public int getLanguage() {
		return language;}
	
	public void setLanguage(int language) {
		this.language = language;}
	
	public int getSocialSci() {
		return socialSci;}
	
	public void setSocialSci(int socialSci) {
		this.socialSci = socialSci;}
	
	public int getNaturalSci() {
		return naturalSci;}
	
	public void setNaturalSci(int naturalSci) {
		this.naturalSci = naturalSci;}
	
	public int getHumanFArts() {
		return humanFArts;}
	
	public void setHumanFArts(int humanFArts) {
		this.humanFArts = humanFArts;}
	
	public int getYear() {
		return year;}
	
	public void setYear(int year) {
		this.year = year;}
		
	public int getYearInSchool() {
		return yearInSchool;}

	public void setYearInSchool(int yearInSchool) {
		this.yearInSchool = yearInSchool;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public ArrayList<String> getMajors() {
		return majors;
	}
	public void setMajors(ArrayList<String> majors) {
		this.majors = majors;
	}
	
}
