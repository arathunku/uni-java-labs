import java.util.Vector;


public class StudentGroup {
	private Vector<Student> students;
	
	StudentGroup(int n) {
		this.students = new Vector<Student>(n);
	}
	
	public boolean addStudent(Student st) {
		if( this.students.capacity() > this.students.size() ) {
			this.students.addElement(st);
			return true;
		} else { 
			return false;
		}
	}
	
	public void showStudents(boolean full) {
		for (Student element : this.students)
			element.print(full);
	}
	
	private void showRemark(Student s) {
		s.print(false);
		System.out.println("Remarks: " + s.getRemarks());
	}
	
	private void showRemark(Student s, String text) {
		if(s.getRemarks().contains(text)) showRemark(s);
	}
	
	public void showRemarks() {
		for (Student element : this.students) showRemark(element);
	}
	
	public void showRemarks(String text) {
		for (Student element : this.students) showRemark(element, text);
	}
	
	public void showRemarks(String text, Gender g) {
		for (Student element : this.students)
			if(element.getGender() == g)
				showRemark(element, text);
	}
	
	public boolean remove(int i) {
		if(this.students.size() >= i && i >= 0) {
			this.students.remove(i);
			return true;
		} else {
			return false;
		}
			
	}
}
