
public enum InputErrCode {
	NOT_A_VALID_INTEGER(100),
	NOT_A_VALID_DOUBLE(101),
	VALUE_TOO_HIGH(102),
	VALUE_TOO_LOW(103),
	INVALID_ENUMERATION(104),
	INVALID_LAST_NAME(105),
	INVALID_FIRST_NAME(106),
	FILE_NOT_FOUND(107);
	
	private int _code = -1;
	private String _value = "";
	
	InputErrCode(int _code) {
		this._code = _code;
	}
		
	public InputErrCode setValue(String _value) {
		this._value = _value;
		return this;
	}
	
    public String toString() {
    	String s = super.toString();
    	return s+ " Error code: " + this._code + ". Inproper value: " + this._value;
    }
    
}
