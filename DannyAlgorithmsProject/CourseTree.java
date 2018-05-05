import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


/**
 * @author Danny, Kai, Spencer
 * An object (a tree of Node objects) that builds the optimal path to the desired major,
 *
 */

public class CourseTree {
	
	Map<CourseID, Course> courseMap;
	StudentInfo stuInfo;
	Node root;
	
	
	public CourseTree( Map<CourseID, Course> courseMap, StudentInfo stuInfo){
		this.courseMap = courseMap;
		this.stuInfo = stuInfo;
		
		Node root = new Node();
		root.setStuInfo(stuInfo);
		this.root=root;
	}
	
	
	/*
	 * This is the method that creates the path to the inputted major
	 */
	
	public  CourseID[][] recurseSemesters(Node node, CourseID[][] allSemesters, int semester, int stuYear){
		if (semester == (8 - (stuYear * 2) + 1)){
			System.out.println(" ");
			System.out.println("YOU ARE NOT ABLE TO SATISFY YOUR MAJOR ON TIME");
			return null;
		}
		
		allSemesters[semester] = generateChildren(node);
		node.setC1(courseMap.get(allSemesters[semester][0]));
		node.setC2(courseMap.get(allSemesters[semester][1]));
		node.setC3(courseMap.get(allSemesters[semester][2]));
		node.setC4(courseMap.get(allSemesters[semester][3]));
		node.updateStuCourses();
		setFlags(node, node.getC1(),0);
		setFlags(node, node.getC2(),0);
		setFlags(node, node.getC3(),0);
		setFlags(node, node.getC4(),0);
		
		
		//Check if the majors are in the arrayList
		if (stuInfo.getMajors().contains("COMP") && !stuInfo.getMajors().contains("MATH")){
			if (node.stuInfo.compMajorSatisfied() == true){
				System.out.println("COMP MAJOR SATISFIED!");
				return allSemesters;
			}
		}
		if(stuInfo.getMajors().contains("MATH") && !stuInfo.getMajors().contains("COMP")){
			if (node.stuInfo.mathMajorSatisfied() == true){
				System.out.println("MATH MAJOR SATISFIED!");
				return allSemesters;
			}
		}
		else if(stuInfo.getMajors().contains("MATH") && stuInfo.getMajors().contains("COMP")){
			if (node.stuInfo.compMajorSatisfied() == true && node.stuInfo.mathMajorSatisfied() == true){
				System.out.println("MATH AND COMP MAJORS SATISFIED!");
				return allSemesters;
			}
		}
		Node child = new Node();
		node.setChild(child);
		child.setParent(node);
		child.setStuInfo(child.getParent().getStuInfo());
		child.incrementSemester();
		
		
		
		return recurseSemesters(child, allSemesters, semester + 1, stuYear);
	}
	
	
	
		
	/*
	 * Generates the courses to be taken in the next semester, returns an array of 4 courseID objects
	 */
	
	public CourseID[] generateChildren(Node n){
		
		//This method will add the children instance variable for each node
		HashMap<CourseID, Integer> validCourses = new HashMap<CourseID, Integer>();
		
	    for(Entry<CourseID, Course> entry: courseMap.entrySet()) {
	        Course c = (entry.getValue());
	        if (isValidChildCourse(n, c) == true){
	        	validCourses.put(entry.getKey(), (Integer) generateScore(n, c));
	        }
	    }
	    
	    ArrayList<Entry<CourseID, Integer>> sortedCourses = sortMap(validCourses);
	    
	    CourseID[] top4 = new CourseID[4];
	    
	    //Check to see if there are less than 4 valid courses in a valid semester.
	    //This should never happen, but this will prevent some null-pointer errors
	    if (sortedCourses.size() < 4){
	    	for(int i = 0; i < sortedCourses.size(); i++){
	    		top4[i] = sortedCourses.get(i).getKey();
	    	}
	    	for(int j = 0 + sortedCourses.size(); j < 4; j++){
	    		top4[j] = new CourseID("FREE", 100);
	    	}
	    }
	    
	    else{
	    	for(int i = 0; i < 4; i++){
	    		//Also a null pointer check
	    		if (sortedCourses.get(i).getKey() == null){
	    			top4[i] = new CourseID("Free", 100);
	    		}
	    		top4[i] = sortedCourses.get(i).getKey();
	    	}
	    }
	    return top4;
	}
	
	
	/*
	 * Determines if ONE course is a valid choice for next semester (in one Node) 
	 */
	
	private boolean isValidChildCourse(Node node, Course course){
		
		if (course.getDept().equals("DUMMY")){
			if (course.getCourseName().equals("MATH XXX")){
				if(node.stuInfo.getMathCourse()>1){
					return false;}}
			
			if (course.getCourseName().equals("WRITING XXX") && node.stuInfo.getWriting() ==true){
				return false;}
			
			if (course.getCourseName().equals("USID XXX") && node.stuInfo.getUsID() ==true){
				return false;}
			
			if (course.getCourseName().equals("INTERNAT XXX") && node.stuInfo.getInternationalism() ==true){
				return false;}
			
			if (course.getCourseName().equals("QUANT XXX") && node.stuInfo.getQuantitative() ==true){
				return false;}
			
			if (course.getCourseName().equals("LANG XXX") && node.stuInfo.getLanguage()>3){
				return false;}
			
			if (course.getCourseName().equals("SOCSCI XXX") && node.stuInfo.getSocialSci()>1){
				return false;}
			
			if (course.getCourseName().equals("NATSCI XXX") && node.stuInfo.getNaturalSci()>1){
				return false;}
			
			if (course.getCourseName().equals("HUMANITY/FINEARTS XXX") && node.stuInfo.getHumanFArts()>2){
				return false;}
		
			return true;
		}
		
		if (course.getDept().equals("MATH") && course.getCourseNum() == 135){
			if (node.stuInfo.studentCourses.containsKey(courseMap.get(new CourseID("MATH",137))) || node.stuInfo.studentCourses.containsKey(courseMap.get(new CourseID("MATH",237)))){
				return false;
			}
		}
		
		if (course.getDept().equals("MATH") && course.getCourseNum() == 137){
			if (node.stuInfo.studentCourses.containsKey(courseMap.get(new CourseID("MATH",237)))){
				return false;
			}
		}
		
		//Checking preReqs
		if (preReqsSatisfied(node, course) == false){
			return false;}
		
		//Check if we have already not taken the course
		if (courseNotTaken(node, course) == false){
			return false;}
		
		//Check if the course is offered next semester
		if (courseOffered(node, course) == false){
			return false;
		}
		return true;
	}
	
	
	
	/*
	 * Returns true if the student has taken the pre-reqs for the inputted course
	 */
	
	public boolean preReqsSatisfied(Node node, Course course){
		
		String preReqs = course.getPreReqs();
		
		if (preReqs.equals("N/A")){
			return true;}
		
		
		//This has list of PreReqs as Strings (ex. "COMP 123") note, some are two courses separated by "/"
		ArrayList<String> stringList =  new ArrayList<String>(Arrays.asList(preReqs.split(",")));
		
		//This has List of preReqs as Course objects
		ArrayList<Course> preReqCourses = new ArrayList<Course>();
		
		
		for (String s: stringList){
			boolean orClassesSatisfied = true; 
			
			if (s.contains("/")){
				ArrayList<String> orList = new ArrayList<String>( Arrays.asList(s.split("/")));
				
				for (String element1 : orList){
					ArrayList<String> orList2 = new ArrayList<String>( Arrays.asList(element1.split(" ")));
					CourseID tempID1 = new CourseID(orList2.get(0), new Integer(orList2.get(1)));
					if ((node.getStuInfo().studentCourses.get(courseMap.get(tempID1)) == null)){
						orClassesSatisfied = false;}
					else{
						orClassesSatisfied = true;
						break;}
				}
				
				if (orClassesSatisfied == false){
					return false;}
			}
			else{
			 ArrayList<String> stringList2 = new ArrayList<String> (Arrays.asList(s.split(" ")));
			 
			 CourseID tempID = new CourseID(stringList2.get(0), new Integer(stringList2.get(1)));
			 preReqCourses.add(courseMap.get(tempID));
			}
		}
		
		for (Course c: preReqCourses){
			if (node.getStuInfo().studentCourses.get(c) == null){
				return false;}
		}
		return true;
	}
	
	
	/**
	 * Checks if the given course has already been taken by the student, also check for cross-listed
	 * @param node
	 * @param course
	 * @return
	 */
	public boolean courseNotTaken(Node node, Course course){
		if (node.getStuInfo().getStudentCourses().containsKey(course)){
			return false;}
		
		if (courseCrossListed(node, course)){
			return false;}
		
		return true;
	}
	
	
	/*
	 * Checks to see if the inputted course is offered the next semester
	 */
	
	public boolean courseOffered(Node node, Course course){
		int year = node.stuInfo.getYear();
		String semester = node.stuInfo.getSemester();
		String offered = course.getOffered();
		
		if (semester.equals("SPRING")){
			
			if (offered.equals("Fall") || offered.equals("Even Fall") || offered.equals("Odd Fall")){
				return false;}
			
			if (year%2 == 0){
				if (offered.equals("Odd Spring")){
					return false;}}
			
			if (year%2 == 1){
				if (offered.equals("Even Spring")){
					return false;}}
		}
		
		if (semester.equals("FALL")){
			
			if (offered.equals("Spring") || offered.equals("Even Spring") || offered.equals("Odd Spring")){
				return false;}
			
			if (year%2 == 0){
				if (offered.equals("Odd Fall")){
					return false;}}
			
			if (year%2 == 1){
				if (offered.equals("Even Fall")){
					return false;}}		
		}
		return true;
	}
	
	
	
	/**
	 * Generates a score that is indicative of how to prioritize the course. 
	 * @param node
	 * @param course
	 * @return
	 */
	public int generateScore(Node node, Course course){
		int score = 0;
		if (node.getStuInfo().getMajors().contains(course.getDept())){
			score += 1;
		}
		ArrayList<String> crossListsquared =  new ArrayList<String>(Arrays.asList(course.getCrossListed().split(" ")));
		if (node.getStuInfo().getMajors().contains(crossListsquared.get(0))){
			score += 3;
		}
		if (node.getStuInfo().getMajors().contains(course.getDept()) && course.getRequired() == 1){
			score += 2;
		}
		if (course.getDept().equals("DUMMY")){
			score += 2;
		}
		
		if (node.getStuInfo().getSemester().equals("SPRING")){
			if(course.offered.equals("SPRING")){
				score += 1;
			}
			if(course.offered.equals("EVEN SPRING")){
				score += 2;
			}
			if(course.offered.equals("ODD SPRING")){
				score += 2;
			}
		}
		if (node.getStuInfo().getSemester().equals("FALL")){
			 if(course.offered.equals("FALL")){
				 score += 1;
			 }
			if(course.offered.equals("EVEN FALL")){
				score += 2;
			}
			if(course.offered.equals("ODD FALL")){
				score += 2;
			}
		}
		if (node.getStuInfo().getYearInSchool() == 3 || node.getStuInfo().getYearInSchool() == 4){
			if (node.getStuInfo().getCapstone().equals(false) && course.capstone == 1){
				score += 4;
			}
		}
		if (course.courseNum > 300){
			score += 1;
		}
		score += preReqScore(node, course);
		
		if(course.getDept().equals("FREE")){
			score = 0;
		}
		
		return score;
	
	}
	
	
	
	
	/*
	 * Helper function for generateScore method, calculates value of a course based on 
	 * how many courses is is a preReq for
	 */
	
	private int preReqScore(Node node, Course course){
		int score = 0;
		ArrayList<String> preReqList =  new ArrayList<String>(Arrays.asList(course.getPrereqFor().split(",")));
		for (String s: preReqList){
			if(s.equals("N/A")){
				break;
			}
		ArrayList<String> singlePreReq = new ArrayList<String>(Arrays.asList(s.split(" ")));
		CourseID tempID = new CourseID(singlePreReq.get(0), new Integer(singlePreReq.get(1)));
		if (stuInfo.getMajors().contains(courseMap.get(tempID).getDept())){
			score ++;
		}
		if(node.getStuInfo().getYear() == 2 || node.getStuInfo().getYear() == 3){ //special case if we are in a soph or junior year
			if (courseMap.get(tempID).getCapstone() == 1){
				score ++; //add one to the score per capstone that this is a preReq for
				}
			}
		}
		
		return score;
	}
	
	


	/**
	 * Returns true if the student has taken a course that is cross listed with this course. 
	 * @param node
	 * @param course
	 * @return
	 */
	private boolean courseCrossListed(Node node, Course course){
		if (!course.getCrossListed().equals("N/A")){
			ArrayList<String> crossListedCourseString = new ArrayList<String> (Arrays.asList(course.getCrossListed().split(" ")));
			CourseID crossID = new CourseID(crossListedCourseString.get(0), new Integer(crossListedCourseString.get(1)));
			if (node.getStuInfo().getStudentCourses().containsKey(courseMap.get(crossID))){
				return true;
			}
		}
		return false;
	}
	
	
	
	
	/*
	 * If a course has been added as a suggestion for the user, this
	 * method updates the student info object accordingly
	 */
	public void setFlags(Node node, Course course, int b){
		if (!course.getDept().equals("FREE")){
		
			if (course.getCourseName().equals("WRITING XXX")){
				node.stuInfo.setWriting(true);
			}

			if (course.getCourseName().equals("USID XXX")){
				node.stuInfo.setUsID(true);
			}

			if (course.getCourseName().equals("INTERNAT XXX")){
				node.stuInfo.setInternationalism(true);
			}

			if (course.getCourseName().equals("QUANT XXX")){
				node.stuInfo.setQuantitative(true);
			}

			if (course.getCourseName().equals("LANG XXX")){
				node.stuInfo.setLanguage(node.stuInfo.getLanguage()+1);
			}

			if (course.getCourseName().equals("SOCSCI XXX")){
				node.stuInfo.setSocialSci(node.stuInfo.getSocialSci()+1);
			}

			if (course.getCourseName().equals("NATSCI XXX")){
				node.stuInfo.setNaturalSci(node.stuInfo.getNaturalSci()+1);
			}

			if (course.getCourseName().equals("HUMANITY/FINEARTS XXX")){
				node.stuInfo.setHumanFArts(node.stuInfo.getHumanFArts()+1);
			}

			if (course.getCourseName().equals("Core Concepts in Computer Science")){
				node.stuInfo.setComp123(true);
			}

			if (course.getCourseName().equals("Object-Oriented Programming and Data Structures")){
				node.stuInfo.setComp124(true);
			}

			if (course.getCourseName().equals("Algorithm Design and Analysis")){
				node.stuInfo.setComp221(true);
			}

			if (course.getCourseName().equals("Software Design and Development")){
				node.stuInfo.setComp225(true);
			}

			if (course.getCourseName().equals("Computer Systems Organization")){
				node.stuInfo.setComp240(true);
			}

			if (course.getCourseName().equals("Theory of Computation")){
				node.stuInfo.setComp261(true);
			}

			if (course.getCourseName().equals("Discrete Mathematics")){
				node.stuInfo.setMath279(true);
			}

			if (course.getCourseName().equals("Linear Algebra")){
				node.stuInfo.setMath236(true);
			}

			if (course.getCourseName().equals("Multivariable Calculus")){
				node.stuInfo.setMath237(true);
			}

			if (course.getCourseName().equals("Introduction to Statistical Modeling")){
				node.stuInfo.setMath155(true);
			}

			if (course.getDept().equals("COMP")){
				node.stuInfo.setCSclass(true);
			}

			if (course.getDept().equals("MATH")){
				if (course.getCourseNum() != 279){
					node.stuInfo.setMathCourse(node.stuInfo.getMathCourse()+1);
				}
			}

			if (course.getDept().equals("COMP")){
				if (course.getCourseNum()>=300){
					node.stuInfo.setElectives(node.stuInfo.getElectives()+1);
				}
			}

			if (course.getDept().equals("MATH")){
				if (course.getCourseNum()>=300){
					node.stuInfo.setMathElectives(node.stuInfo.getMathElectives()+1);
				}
			}

			if (course.getDept().equals("COMP")){
				if (course.getCapstone() == 1){
					node.stuInfo.setCapstone(true);
				}
			}

			if (course.getDept().equals("MATH")){
				if (course.getCapstone() == 1){
					node.stuInfo.setMathCapstone(true);
				}
			}
		}
		
		
		if (b==1){
			return;
		}
		if (!course.getCrossListed().equals("N/A")){
			ArrayList<String> crossListedCourseString = new ArrayList<String> (Arrays.asList(course.getCrossListed().split(" ")));
			CourseID crossID = new CourseID(crossListedCourseString.get(0), new Integer(crossListedCourseString.get(1)));
			Course c = courseMap.get(crossID);
			setFlags(node, c, 1);
			}
	}
	
	
	
	/*
	 * Converts a map into an arrayList that is sorted according to the maps values; descending order.
	 */
	
	private ArrayList<Entry<CourseID, Integer>> sortMap(HashMap<CourseID, Integer> map){
		Set<Entry<CourseID, Integer>> set = map.entrySet();
		ArrayList<Entry<CourseID, Integer>> list = new ArrayList<Entry<CourseID, Integer>>(set);
		Collections.sort(list, new Comparator<Map.Entry<CourseID, Integer>>(){
			public int compare( Map.Entry<CourseID, Integer> o1, Map.Entry<CourseID, Integer> o2)
			{
				return (o2.getValue()).compareTo( o1.getValue() );
			}
		});
		return list;
	}
	
	
	//GETTERS AND SETTERS
	
	public Map<CourseID, Course> getCourseMap() {
		return courseMap;}

	public void setCourseMap(Map<CourseID, Course> courseMap) {
		this.courseMap = courseMap;}

	public StudentInfo getStuInfo() {
		return stuInfo;}

	public void setStuInfo(StudentInfo stuInfo) {
		this.stuInfo = stuInfo;}
	
	public Node getRoot() {
		return root;}

	public void setRoot(Node root) {
		this.root = root;}

	
}
