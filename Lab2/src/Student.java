enum Color {
    WHITE, BLACK, RED, YELLOW, BLUE;
    public String toString() {
    	String s = super.toString();
    	return s.substring(0, 1) + s.substring(1).toLowerCase();
    }
}
// That is the part of the task :( 
// IMO Gender should be defined as String. 
enum Gender {
    FEMALE, MALE;
    public String toString() { 
    	String s = super.toString();
    	return s.substring(0, 1) + s.substring(1).toLowerCase();
    }
}

public class Student {
	private static int _idCounter = -1;
	private int _id;
	private String _lastName = "Zx";
	private String _firstName = "";
	private int _height = 180;
	private double _weight = 70d;
	private Color _eyeColor = Color.BLUE;
	private Gender _gender = Gender.MALE;
	private String _remarks = "";
	
	public Student() {
		this._idCounter += 1;
		this._id = this._idCounter;
	}
	
	public Student(String lastName, String firstName, 
			int height, double weight, Color eyeColor, Gender gender) {
		// we create new student which provides as with an empty student with correct id
		Student newStudent = new Student();
		newStudent.setLastName(lastName);
		newStudent.setFirstName(firstName);
		newStudent.setHeight(height);
		newStudent.setWeight(weight);
		newStudent.setEyeColor(eyeColor);
		newStudent.setGender(gender);
	}
	
	public void print(boolean full) {
		System.out.println("ID: " + this.getId());
		System.out.println("Last name: " + this.getLastName());
		System.out.println("First name: " + this.getFirstName());
		if(full) {
			System.out.println("Height: " + this.getHeight());
			System.out.println("Weight: " + this.getWeight());
			System.out.println("EyeColor: " + this.getEyeColor());
			System.out.println("Gender: " + this.getGender());
			
		}
	}
	

	public int getId() {
		return _id;
	}

	public String getLastName() {
		return _lastName;
	}
	
	public void setLastName(String _lastName) {
		this._lastName = _lastName;
	}
	
	public String getFirstName() {
		return _firstName;
	}
	
	public void setFirstName(String _firstName) {
		this._firstName = _firstName;
	}
	
	public int getHeight() {
		return _height;
	}
	
	public void setHeight(int _height) {
		this._height = _height;
	}
	
	public double getWeight() {
		return _weight;
	}
	
	public void setWeight(double _weight) {
		this._weight = _weight;
	}
	
	public Color getEyeColor() {
		return _eyeColor;
	}
	
	public void setEyeColor(Color _eyeColor) {
		this._eyeColor = _eyeColor;
	}
	
	public Gender getGender() {
		return _gender;
	}
	
	public void setGender(Gender _gender) {
		this._gender = _gender;
	}
	
	public String getRemarks() {
		return _remarks;
	}
	
	public void setRemarks(String _remarks) {
		this._remarks = _remarks;
	}
	
	
			
}
