
public enum Faculty {
	ARCHITECTURE("architecture"),
	CIVIL_ENGINEERING("civil engineering"), 
	CHEMISTRY("chemistry"),
	ELECTRONICS("electronics"),
	COMPUTER_SCIENCE_AND_MANAGEMENT("computer science and management");
	
	
    private String faculty;
    
    @Override
    public String toString() { 
    	String s = super.toString();
    	return s.substring(0, 1) + s.substring(1).toLowerCase();
    }
    
    Faculty(String _faculty) {
    	this.faculty = _faculty;
    }
    
    public String getPrettyString() {
    	return this.faculty;
    }
}
