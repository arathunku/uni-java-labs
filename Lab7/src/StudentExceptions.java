
public class StudentExceptions {
	public static class InvalidLastNameException extends Exception {
		private static final long serialVersionUID = 5964661165465798235L;
		private static final String _msg = "Sequence of Letters staring with an upper case "
				+ "letter followed but lower case letters";
		
		private InputErrCode _setValue;
		private int _fieldNumber;

		public InvalidLastNameException(String value, int fieldNumber) {
			this._setValue = InputErrCode.INVALID_FIRST_NAME.setValue(value);
			this._fieldNumber = fieldNumber;
		}

		public String toString() { 
			return _msg;
		}
		
		public InputErrCode getInputErrCode() {
			return this._setValue;
		}
	}

	public static class InvalidFirstNameException extends Exception {
		private static final long serialVersionUID = 5964661165465798235L;
		private static final String _msg = "Name is not in the list.";
		private InputErrCode _setValue;
		private int _fieldNumber;

		public InvalidFirstNameException(String value, int fieldNumber) {
			this._setValue = InputErrCode.INVALID_LAST_NAME.setValue(value);
			this._fieldNumber = fieldNumber;
		}
		
		public String toString() {
			return _setValue.toString() + " " + _msg
					 + "\nField number: " + this._fieldNumber;
		}
		
		public InputErrCode getInputErrCode() {
			return this._setValue;
		}
	}
}
