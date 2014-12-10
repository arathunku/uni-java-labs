import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;


public class StudentGroup {
	private Vector<Student> students;
	
	StudentGroup(int n) {
		this.students = new Vector<Student>(n);
	}
	
	public void addStudent(Student st) {
		this.students.addElement(st);
	}
	
	
	private ArrayList<String> getFile(String filename) { 
		ArrayList<String> lines = new ArrayList<String>();
		
		try {
			Scanner fileScanner = new Scanner(new File(filename));
			while (fileScanner.hasNextLine()) {
				String s = fileScanner.nextLine().toString().trim();
				// I'm adding option to comment out lines
				if(!s.substring(0, 1).equals("#")){
					lines.add(s);
				}
			}
			fileScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("File "+ filename +" is not found");
			e.printStackTrace();
			System.exit(1);
		}
		
		return lines;
	}
	public void readStudents(String filename) {
		for(String s : getFile(filename)) {
			try {
				this.addStudent(new Student(s));
			} catch (StudentExceptions.InvalidLastNameException | 
					StudentExceptions.InvalidFirstNameException |
					ValidationExceptions.InvalidEnumException | 
					ValidationExceptions.InvalidIntegerException |
					ValidationExceptions.ValueTooLowException | 
					ValidationExceptions.ValueTooHighException |
					IllegalArgumentException |
					ValidationExceptions.InvalidDoubleException e) {
				e.printStackTrace();
				System.exit(1);
			}			
		}
	}
	
	public void readFromFile(String fileName) {
		// ???????
	}
	
	public void selectErrorLines(String fileName) {
		// All lines with any type of an error are to be written to the standard 
		// output with their line number
		int linenumber = 1;
		for(String s : getFile(fileName)) {
			try {
				this.addStudent(new Student(s));
			} catch (StudentExceptions.InvalidLastNameException | 
					StudentExceptions.InvalidFirstNameException |
					ValidationExceptions.InvalidEnumException | 
					ValidationExceptions.InvalidIntegerException |
					ValidationExceptions.ValueTooLowException | 
					ValidationExceptions.ValueTooHighException |
					IllegalArgumentException |
					ValidationExceptions.InvalidDoubleException e) {
				System.out.println(fileName + ":" + linenumber + " " + s + " is incorrect");
			}
			linenumber += 1;
		}
	}
	
	public void selectProperLines (String fileName)  {
		int linenumber = 1;
		for(String s : getFile(fileName)) {
			try {
				this.addStudent(new Student(s));
			} catch (StudentExceptions.InvalidLastNameException | 
					StudentExceptions.InvalidFirstNameException |
					ValidationExceptions.InvalidEnumException | 
					ValidationExceptions.InvalidIntegerException |
					ValidationExceptions.ValueTooLowException | 
					ValidationExceptions.ValueTooHighException |
					IllegalArgumentException |
					ValidationExceptions.InvalidDoubleException e) {
				linenumber += 1;
				continue;
			}
			System.out.println(fileName + ":" + linenumber + " " + s);
			linenumber += 1;
		}
	}
	
	public void selectErrorLines(String fileName, InputErrCode code) {
//		All lines with that error code are to be written to the standard output 
//		with their line number
		int linenumber = 1;
		for(String s : getFile(fileName)) {
			try {
				this.addStudent(new Student(s));
			} 
			catch (StudentExceptions.InvalidLastNameException e)   { if(e.getInputErrCode() == code) System.out.println(fileName + ":" + linenumber + " " + s); } 
			catch (StudentExceptions.InvalidFirstNameException e)  { if(e.getInputErrCode() == code) System.out.println(fileName + ":" + linenumber + " " + s); } 
			catch (ValidationExceptions.InvalidEnumException e)    { if(e.getInputErrCode() == code) System.out.println(fileName + ":" + linenumber + " " + s); } 
			catch (ValidationExceptions.InvalidIntegerException e) { if(e.getInputErrCode() == code) System.out.println(fileName + ":" + linenumber + " " + s); } 
			catch (ValidationExceptions.ValueTooLowException e)    { if(e.getInputErrCode() == code) System.out.println(fileName + ":" + linenumber + " " + s); } 
			catch (ValidationExceptions.ValueTooHighException e)   { if(e.getInputErrCode() == code) System.out.println(fileName + ":" + linenumber + " " + s); } 
			catch (ValidationExceptions.InvalidDoubleException e)  { if(e.getInputErrCode() == code) System.out.println(fileName + ":" + linenumber + " " + s); } 
			finally {
				linenumber += 1;
			}
		}
	}
	
	public String firstError(String fileName)  {
		for(String s : getFile(fileName)) {
			try {
				this.addStudent(new Student(s));
			} catch (StudentExceptions.InvalidLastNameException | 
					StudentExceptions.InvalidFirstNameException |
					ValidationExceptions.InvalidEnumException | 
					ValidationExceptions.InvalidIntegerException |
					ValidationExceptions.ValueTooLowException | 
					ValidationExceptions.ValueTooHighException |
					IllegalArgumentException |
					ValidationExceptions.InvalidDoubleException e) {
				return s;
			}
		}
		return "";
	}
	
	public String firstError(String filename, InputErrCode code)  {
		for(String s : getFile(filename)) {
			try {
				this.addStudent(new Student(s));
			} 
			catch (StudentExceptions.InvalidLastNameException e)   { if(e.getInputErrCode() == code) return s; } 
			catch (StudentExceptions.InvalidFirstNameException e)  { if(e.getInputErrCode() == code) return s; } 
			catch (ValidationExceptions.InvalidEnumException e)    { if(e.getInputErrCode() == code) return s; } 
			catch (ValidationExceptions.InvalidIntegerException e) { if(e.getInputErrCode() == code) return s; } 
			catch (ValidationExceptions.ValueTooLowException e)    { if(e.getInputErrCode() == code) return s; } 
			catch (ValidationExceptions.ValueTooHighException e)   { if(e.getInputErrCode() == code) return s; } 
			catch (ValidationExceptions.InvalidDoubleException e)  { if(e.getInputErrCode() == code) return s; } 
		}
		return "";
	}
}
