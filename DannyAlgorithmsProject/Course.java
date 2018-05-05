
public class Course {

	int courseID;
	String dept;
	int courseNum;
	String courseName;
	String crossListed;
	int required;
	int capstone;
	String preReqs;
	String offered;
	int credits;
	String prereqFor;


	public Course(int courseID, String dept, int courseNum, String courseName, String crossListed, int required, int capstone, String preReqs, String offered, int credits, String prereqFor){
		this.courseID = courseID;
		this.dept=dept;
		this.courseNum=courseNum;
		this.courseName=courseName;
		this.crossListed=crossListed;
		this.required=required;
		this.capstone=capstone;
		this.preReqs=preReqs;
		this.offered=offered;
		this.credits=credits;
		this.prereqFor=prereqFor;
	}

	
	
	//GETTERS AND SETTERS
	public Course(String dept){
		this.dept=dept;
	}
	public int getCourseID() {
		return courseID;
	}
	public String getDept() {
		return dept;
	}
	public int getCourseNum() {
		return courseNum;
	}
	public String getCourseName() {
		return courseName;
	}
	public String getCrossListed() {
		return crossListed;
	}
	public int getRequired() {
		return required;
	}
	public int getCapstone() {
		return capstone;
	}
	public String getPreReqs() {
		return preReqs;
	}
	public String getOffered() {
		return offered;
	}
	public int getCredits() {
		return credits;
	}
	public String getPrereqFor() {
		return prereqFor;
	}
	
	public String toString(){
		return "Course = " + dept + " " + new Integer(courseNum).toString();

	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + capstone;
		result = prime * result + courseID;
		result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());
		result = prime * result + courseNum;
		result = prime * result + credits;
		result = prime * result + ((crossListed == null) ? 0 : crossListed.hashCode());
		result = prime * result + ((dept == null) ? 0 : dept.hashCode());
		result = prime * result + ((offered == null) ? 0 : offered.hashCode());
		result = prime * result + ((preReqs == null) ? 0 : preReqs.hashCode());
		result = prime * result + required;
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (capstone != other.capstone)
			return false;
		if (courseID != other.courseID)
			return false;
		if (courseName == null) {
			if (other.courseName != null)
				return false;
		} else if (!courseName.equals(other.courseName))
			return false;
		if (courseNum != other.courseNum)
			return false;
		if (credits != other.credits)
			return false;
		if (crossListed == null) {
			if (other.crossListed != null)
				return false;
		} else if (!crossListed.equals(other.crossListed))
			return false;
		if (dept == null) {
			if (other.dept != null)
				return false;
		} else if (!dept.equals(other.dept))
			return false;
		if (offered == null) {
			if (other.offered != null)
				return false;
		} else if (!offered.equals(other.offered))
			return false;
		if (preReqs == null) {
			if (other.preReqs != null)
				return false;
		} else if (!preReqs.equals(other.preReqs))
			return false;
		if (required != other.required)
			return false;
		return true;
	}
	
	
	
	
	
}
