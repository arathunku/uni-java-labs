
public class Lab2 {

	public static void main(String[] args) {
		StudentGroup ss = new StudentGroup(3);
		ss.addStudent(new Student()); // id: 0
		ss.addStudent(new Student()); // id: 1
		ss.addStudent(new Student()); // id: 2
		ss.addStudent(new Student()); // id: 3. It will result in false
		ss.remove(2); // removes element id: 2
		ss.addStudent(new Student()); // id: 4 added
		ss.showStudents(false);
	}

}
