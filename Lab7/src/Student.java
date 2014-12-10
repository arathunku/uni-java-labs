import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class Student { 
	final static int MIN_BIRTH = 1980;
	final static int MAX_BIRTH = 2004;
	private final static double MIN_WEIGHT =  3.0;
	private final static double MAX_WEIGHT =  140.0;
	final static int MIN_STUDY_YEAR =  1;
	final static int MAX_STUDY_YEAR =  8;
	
	private static int _idCounter = 0;
	private int _id = 0;
	private String _lastName;
	private String _firstName;
	private double _weight;
	private Gender _gender;
	private String _achievements;
	private Faculty _faculty;
	private int _studyYear;
	private int _birthYear;
	static Vector<String> availableStudentFemaleNames = new Vector<String>(10);
	static Vector<String> availableStudentMaleNames = new Vector<String>(10);

	
	public Student() {
		this.generateUniqueId();
	}
	// Example input:
	// 		Michal;Arath;male;1990;70;3;achievements;chemistry
	// vars:lastName;firstName;gender;birthYear;weight;studyYear;achievements;faculty
	public Student(String _data) 
		throws
		StudentExceptions.InvalidLastNameException,
		StudentExceptions.InvalidFirstNameException,
		ValidationExceptions.InvalidEnumException,
		ValidationExceptions.InvalidIntegerException,
		ValidationExceptions.ValueTooHighException,
		ValidationExceptions.ValueTooLowException,
		ValidationExceptions.InvalidDoubleException
	{
		String data[] = _data.split(";", 8);
		if(data.length != 8) {
			throw new IllegalArgumentException(data + " isn't in proper format. Use following format:"
					+ "lastName;firstName;gender;birthYear;weight;studyYear;achievements;faculty");
		}
		
		this.generateUniqueId();
		setLastName(data[0]);
		setGender(data[2]);
		setFirstName(data[1]);
		setBirthYear(data[3]);
		setWeight(data[4]);
		setStudyYear(data[5]);
		setAchievements(data[6]);
		setFaculty(data[7]);
	}
	
	/*
	 * Generated unique id integer for object. Called in the constructors
	 */
	private void generateUniqueId() {
		// 0 is a default value for _id
		if(this._id == 0) {
			Student._idCounter += 1;
			this._id = Student._idCounter;
		}
	}
	
	public int getId() {
		return _id;
	}

	public String getLastName() {
		return _lastName.substring(0, 1) + _lastName.substring(1).toLowerCase();
	}
	
	public void setLastName(String _lastName) throws StudentExceptions.InvalidLastNameException {
		if(_lastName == null || _lastName.length() == 0 ||
				!_lastName.equals(_lastName.substring(0, 1).toUpperCase() + _lastName.substring(1).toLowerCase())
		  ) {			
			throw new StudentExceptions.InvalidLastNameException(_lastName, 0);
		}
			
		this._lastName = _lastName;
	}
	
	public String getFirstName() {
		return _firstName;
	}
	
	public void setFirstName(String _firstName) throws StudentExceptions.InvalidFirstNameException {
		loadNames();
		if(this.getGender() == Gender.MALE) {
			if(!availableStudentMaleNames.contains(_firstName))
				throw new StudentExceptions.InvalidFirstNameException(_firstName, 1);
		} else if(this.getGender() == Gender.FEMALE) {
			if(!availableStudentFemaleNames.contains(_firstName))
				throw new StudentExceptions.InvalidFirstNameException(_firstName, 1);
		}
		this._firstName = _firstName;
	}

	public double getWeight() {
		return _weight;
	}
	
	public void setWeight(String _weight) throws 
		ValidationExceptions.ValueTooLowException, 
		ValidationExceptions.ValueTooHighException,
		ValidationExceptions.InvalidDoubleException
	{
		double weight;
		try {
			weight = Double.parseDouble(_weight);
		} catch (NumberFormatException e) {
			throw new ValidationExceptions.InvalidDoubleException(_weight, 4);
		}
			
		if(weight < MIN_WEIGHT) {
			throw new ValidationExceptions.ValueTooLowException(_weight, MIN_WEIGHT, MAX_WEIGHT, 4);
		} else if (weight > MAX_WEIGHT) {
			throw new ValidationExceptions.ValueTooHighException(_weight, MIN_WEIGHT, MAX_WEIGHT, 4);	
		}
		
//		6. weight: double value in the range from 3.0 to 140.0.
		this._weight = Double.parseDouble(_weight);
	}
	
	public Gender getGender() {
		return _gender;
	}
	
	public void setGender(String _gender) throws ValidationExceptions.InvalidEnumException {
		try {
			this._gender = Gender.valueOf(_gender.toUpperCase());			
		} catch (Exception e) { 
			throw new ValidationExceptions.InvalidEnumException(_gender, 3);
		}
	}
	
	public String getAchievements() {
		return _achievements;
	}
	
	public void setAchievements(String _achievements) {
		this._achievements = _achievements;
	}

	public Faculty getFaculty() {
		return _faculty;
	}

	public void setFaculty(String _faculty) throws ValidationExceptions.InvalidEnumException {
		try {
			this._faculty = Faculty.valueOf(_faculty.toUpperCase());
		} catch (Exception e) { 
			throw new ValidationExceptions.InvalidEnumException(_faculty, 7);
		}
	}

	public int getStudyYear() {
		return _studyYear;
	}

	public void setStudyYear(String _studyYear)
		throws 
		ValidationExceptions.ValueTooLowException, 
		ValidationExceptions.ValueTooHighException,
		ValidationExceptions.InvalidIntegerException
	{
		int studyYear;
		
		try { 
			studyYear = Integer.parseInt(_studyYear);
		} catch (NumberFormatException e) { 
			throw new ValidationExceptions.InvalidIntegerException(_studyYear, 5);
		}
		
		
		if(studyYear < MIN_STUDY_YEAR) {
			throw new ValidationExceptions.ValueTooLowException(_studyYear, MIN_STUDY_YEAR, MAX_STUDY_YEAR, 5);
		} else if (studyYear > MAX_STUDY_YEAR) {
			throw new ValidationExceptions.ValueTooHighException(_studyYear, MIN_STUDY_YEAR, MAX_STUDY_YEAR, 5);	
		}
		
		this._studyYear = studyYear;
	}

	public int getBirthYear() {
		return _birthYear;
	}

	public void setBirthYear(String _birthYear) 
		throws 
		ValidationExceptions.ValueTooLowException, 
		ValidationExceptions.ValueTooHighException,
		ValidationExceptions.InvalidIntegerException
	{
		int birthYear;
		
		try { 
			birthYear = Integer.parseInt(_birthYear);
		} catch (NumberFormatException e) { 
			throw new ValidationExceptions.InvalidIntegerException(_birthYear, 3);
		}
		
		
		if(birthYear < MIN_BIRTH) {
			throw new ValidationExceptions.ValueTooLowException(_birthYear, MIN_BIRTH, MAX_BIRTH, 3);
		} else if (birthYear > MAX_BIRTH) {
			throw new ValidationExceptions.ValueTooHighException(_birthYear, MIN_BIRTH, MAX_BIRTH, 3);	
		}
		
		this._birthYear = birthYear;
	}
	
	static void loadNames() {
		if(availableStudentMaleNames.size() > 0 || availableStudentMaleNames.size() > 0)
			return;
		
		Scanner fileScanner;
		boolean boys = false; // Value to initiate if we're reading boys or girls names
		int currentLineNumber = 1;
		try {
			fileScanner = new Scanner(new File("./PopularNames.txt"));
			fileScanner.nextLine(); // Omit first line, description of file.
			while (fileScanner.hasNextLine()) {
				String s = fileScanner.nextLine().toString().trim();
				currentLineNumber += 1;
				if(s == null || s.equals("")) {
					System.out.println("PopularNames.txt: Empty line at line: " + currentLineNumber);
				} else if(s.equals("Boys:")){
					boys = true;
				} else if(s.equals("Girls:")){
					boys = false;
				} else {
					if(boys) availableStudentMaleNames.add(s);
					else availableStudentFemaleNames.add(s);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File PopularNames.txt is not found");
			e.printStackTrace();
			System.exit(1);
		}
	}
			
}
