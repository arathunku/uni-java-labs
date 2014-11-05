public class Lab3 {

	public static void main(String[] args) throws ValidationExceptions.ValueTooHighException {
		try {
			new Student("Michal;Violet;female;1990;70;3;achievements;chemistry");
		} catch (StudentExceptions.InvalidLastNameException | 
				StudentExceptions.InvalidFirstNameException |
				ValidationExceptions.InvalidEnumException | 
				ValidationExceptions.InvalidIntegerException |
				ValidationExceptions.ValueTooLowException | 
				ValidationExceptions.InvalidDoubleException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		StudentGroup ss = new StudentGroup(100);
		ss.selectProperLines("./Tests.txt");
	}

}
