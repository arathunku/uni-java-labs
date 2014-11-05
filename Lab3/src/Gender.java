public enum Gender {
    FEMALE("female"), MALE("male");
    
    private String gender;
    
    public String toString() { 
    	String s = super.toString();
    	return s.substring(0, 1) + s.substring(1).toLowerCase();
    }
    
    private Gender(String _gender) {
    	this.gender = _gender;
    }
}