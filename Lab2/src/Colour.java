public enum Colour {
    WHITE, BLACK, RED, YELLOW, BLUE;  //; is required here.
           public String toString() {    //only capitalize the first letter
           String s = super.toString();
           return s.substring(0, 1) + s.substring(1).toLowerCase();
} };