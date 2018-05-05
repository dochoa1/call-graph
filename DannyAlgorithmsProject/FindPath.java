import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//Make sure this package is included on your computer!!!
import com.opencsv.CSVReader;

/**
 * @author Danny, Kai, Spencer
 * Program that takes a student's inputed classes and determines if they are able to complete the Computer 
 * Science major at Macalester college
 * 
 */


public class FindPath {
	
	public static final int CURRENT_YEAR = 2017;
	public static final String CURRENT_SEMESTER = "SPRING"; //Can be either "SPRING" or "FALL"
	
	
	//Map containing all courses
	static Map<CourseID, Course> courseMap;    
	//Object that stores all important information about the user
	static StudentInfo stuInfo;
	//Tree that we use to find shortest path for the major
	static CourseTree courseTree;
	
	
	
	/*
	 * Initialize courseMap and courseTree
	 */
	public static void init() throws NumberFormatException, IOException{
		
		Map<CourseID, Course> courseMap1 = createCourses(new File("CoursesCSVs/ShortestPathMajorCS.csv"));
		Map<CourseID, Course> courseMap2 = createCourses(new File("CoursesCSVs/ShortestPathMajorMATH.csv"));
		Map<CourseID, Course> courseMap3 = createCourses(new File("CoursesCSVs/DummyCourses.csv"));

		courseMap = mergeMaps(courseMap1, courseMap2);
		courseMap = mergeMaps(courseMap, courseMap3);
		
		stuInfo = new StudentInfo();
		
		createStudentCourses();
		courseTree = new CourseTree(courseMap, stuInfo);
		//Updates the studentInfo object based on the students inputted courses
		for (Map.Entry<Course, Integer> entry: stuInfo.getStudentCourses().entrySet()){
			courseTree.setFlags(courseTree.getRoot(),entry.getKey(),0);
		}
		
	}
	
	
	/*
	 * Main method
	 */
	
	public static void main(String[] args) throws Exception {
		init();
		findPath();
		}
	
	
	
	/*
	 * Method that finds the shortest path to the desired major
	 */
private static void findPath(){
		
		int year = stuInfo.getYear();
		int semestersLeft = (8 - (year * 2) + 1);
		CourseID[][] allSemesters = courseTree.recurseSemesters(courseTree.getRoot(), new CourseID[semestersLeft + 1][4], 0, year);
		
		if (allSemesters!=null){
		
			for(int i = 0; i < semestersLeft; i++){
				System.out.println("Semester " + (i + (8 - semestersLeft) + 1) + " Courses: ");
				for(int j = 0; j < 4; j++){
					if (allSemesters[i][j] != null && allSemesters[i][j].getDept().equals("DUMMY")){
						System.out.print(courseMap.get(allSemesters[i][j]).getCourseName() + " ");
					}
					else if (allSemesters[i][j]==null){
						System.out.print("FREE ");
					}
					else{
						System.out.print(allSemesters[i][j] + " ");
					}
				}
				System.out.println("");
			}
		}
		
	}
	
	

	
	
	/*
	 * Method that stores information about the user in a StudentInfo object
	 */
	
	private static void createStudentCourses (){
		Map<Course, Integer> studentCourseList = new HashMap<Course, Integer>();
				
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner (System.in);
		System.out.print("What year in college are you? (1 - 4) ");
		int stuYear = scanner.nextInt();
		if (stuYear >= 1 && stuYear <= 4){
			stuInfo.setYear(stuYear);
		}
		else{
			System.out.print("You entered something wrong, sorry.");
			System.exit(0);
		}
		ArrayList<String> majors = new ArrayList<String>(); 
		
		while(true){
			System.out.print("What departments do you plan do major in? (Enter QUIT when you are done): ");
			String major = scanner.next().toUpperCase();
			if (major.equals("QUIT")){
				break;
			}
			majors.add(major);
		}
		stuInfo.setMajors(majors);
		
		System.out.print("Have you satisfied your writing requirement? (Y or N): ");
		if (scanner.next().toUpperCase().equals("Y")){
			stuInfo.setWriting(true);}
		System.out.print("Have you satisfied your U.S. Identities requirement? (Y or N): ");
		if (scanner.next().toUpperCase().equals("Y")){
			stuInfo.setUsID(true);}
		System.out.print("Have you satisfied your Internationalism requirement? (Y or N): ");
		if (scanner.next().toUpperCase().equals("Y")){
			stuInfo.setInternationalism(true);}
		System.out.print("Have you satisfied your Quantitative requirement? (Y or N): ");
		String a = scanner.next();	
		stuInfo.setQuantitative(true);
		
		System.out.print("What is the equivalent number of language courses you have credit for? (0-4): ");
		stuInfo.setLanguage(scanner.nextInt());
		System.out.print("How many Social Science classes have you taken? (0-2) ");
		stuInfo.setSocialSci(scanner.nextInt());
		System.out.print("How many Natural Science classes have you taken? (0-2) ");
		String b = scanner.next();	
		stuInfo.setNaturalSci(2);
		System.out.print("How many Humanities/Fine Arts classes have you taken? (0-3) ");
		stuInfo.setHumanFArts(scanner.nextInt());
		
		System.out.println(" ");
		System.out.println("Now, enter all of the CS/Math courses that you have taken/skipped:");
		System.out.println(" ");
		
		//Gather inputted courses from the user, store them in studentInfo object
		while (true){
			System.out.print("Enter a course Department or QUIT if no more courses:  ");  
			String deptName = scanner.next().toUpperCase(); // Get what the user types.
			if (deptName.equals("QUIT")){
				break;
			}
			System.out.print("Enter a course Number: "); 
			int courseNum = scanner.nextInt();
			CourseID testID = new CourseID(deptName, courseNum);
			System.out.println(testID);
			if (courseMap.containsKey(testID)) {
				studentCourseList.put(courseMap.get(testID), new Integer(1));
				System.out.println(deptName + " " + courseNum + " has been added.");
			}
			else {
				System.out.println("Sorry that course is not listed under our register.");
			}
		}
		scanner.close();
					
	stuInfo.setStudentCourses(studentCourseList);

	}

	
	
	/*
	 * Creates a Map of course objects - these are all courses contained in the given CSV
	 */
	private static Map<CourseID, Course> createCourses(File file) throws NumberFormatException, IOException{
		Map<CourseID, Course> courseMap = new HashMap<CourseID, Course>();
		
		@SuppressWarnings("resource")
		CSVReader reader = new CSVReader(new FileReader(file));
		
		// nextLine is an array of Strings that represent all entries in a particular row of the given CSV
		String [] nextLine;
		
		//This is to get past the header row
		reader.readNext();
		
		while ((nextLine = reader.readNext()) != null) { //Read over every row and convert the row to a entry in the course map
	    		//Create CourseID object
	    		CourseID id = new CourseID(nextLine[1], Integer.parseInt(nextLine[2]));
	    		//Create Course object
	    		Course course = new Course(Integer.parseInt(nextLine[0]), nextLine[1], Integer.parseInt(nextLine[2]), 
	    									nextLine[3], nextLine[4], Integer.parseInt(nextLine[5]), Integer.parseInt(nextLine[6]),
	    									nextLine[7], nextLine[8], Integer.parseInt(nextLine[9]), nextLine[10]);
	    		//Add newly created entry to the map of courses
	    		courseMap.put(id, course);
	    	}
	return courseMap;	
	}
	
	
	
	/*
	 * Helper method to merge the data from the CSVs into one courseMap
	 */
	
	public static Map<CourseID, Course> mergeMaps(Map<CourseID, Course> map1, Map<CourseID, Course> map2){
		for (Map.Entry<CourseID,Course> entry : map2.entrySet()){
			if (map1.get(entry.getKey()) == null){
				map1.put(entry.getKey(),entry.getValue());
			}
		}
		return map1;
	}
	
	
	//Getters and setters
	
	public static Map<CourseID, Course> getCourseMap() {
		return courseMap;
	}

	public static void setCourseMap(Map<CourseID, Course> courseMap) {
		FindPath.courseMap = courseMap;
	}

	public static StudentInfo getStuInfo() {
		return stuInfo;
	}

	public static void setStuInfo(StudentInfo stuInfo) {
		FindPath.stuInfo = stuInfo;
	}
	



}
	
