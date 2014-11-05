
public class ValidationExceptions {
	
	public static class InvalidIntegerException extends Exception {
		private static final long serialVersionUID = -2537473213965679273L;
		private static final String _msg = "That's not an integer.";
		private InputErrCode _setValue;
		private int _fieldNumber;

		public InvalidIntegerException(String value, int fieldNumber) {
			this._setValue = InputErrCode.NOT_A_VALID_INTEGER.setValue(value);
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

	public static class InvalidDoubleException extends Exception {
		private static final long serialVersionUID = -2537473213965679273L;
		private static final String _msg = "That's not an double value.";
		private InputErrCode _setValue;
		private int _fieldNumber;
		
		public InvalidDoubleException(String value, int fieldNumber) {
			this._setValue = InputErrCode.NOT_A_VALID_DOUBLE.setValue(value);
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
	
	
	public static class ValueTooLowException extends Exception {
		private static final long serialVersionUID = -4326839177028996223L;
		private double _lowLimit;
		private double _highLimit;
		private InputErrCode _setValue;
		private int _fieldNumber;

		public  ValueTooLowException(String value, double lowLimit, double highLimit, int fieldNumber) {
			this._lowLimit = lowLimit;
			this._highLimit = highLimit;
			this._setValue = InputErrCode.VALUE_TOO_LOW.setValue(value);
			this._fieldNumber = fieldNumber;
		}
		
		public String toString() {
			return _setValue.toString() + " Value must be: " 
					+ this._lowLimit + " - " + this._highLimit
					+ "\nField number: " + this._fieldNumber;
		}
		
		public InputErrCode getInputErrCode() {
			return this._setValue;
		}
	}
	
	public static class ValueTooHighException extends Exception {
		private static final long serialVersionUID = 5964661165465798235L;
		private double _lowLimit;
		private double _highLimit;
		
		private InputErrCode _setValue;
		private int _fieldNumber;

		public ValueTooHighException(String value, double lowLimit, double highLimit, int fieldNumber) {
			this._lowLimit = lowLimit;
			this._highLimit = highLimit;
			this._setValue = InputErrCode.VALUE_TOO_HIGH.setValue(value);
			this._fieldNumber = fieldNumber;
		}
		
		public String toString() {
			return _setValue.toString() + " Value must be: "
					+ this._lowLimit + " - " + this._highLimit
					+ "\nField number: " + this._fieldNumber;
		}
		
		public InputErrCode getInputErrCode() {
			return this._setValue;
		}
	}
	
	public static class InvalidEnumException extends Exception  {
		private static final long serialVersionUID = 4007765119035632799L;
		private InputErrCode _setValue;
		private int _fieldNumber;
		
		public InvalidEnumException(String value, int fieldNumber) {
			this._setValue = InputErrCode.INVALID_ENUMERATION.setValue(value);
			this._fieldNumber = fieldNumber;
		}
		
		public String toString() { return _setValue.toString() + "\nField number: " + this._fieldNumber;}
		
		public InputErrCode getInputErrCode() {
			return this._setValue;
		}
	}
}
