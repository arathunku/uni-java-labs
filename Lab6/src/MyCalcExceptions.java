
public class MyCalcExceptions {
	public static class UndefinedVariable extends Exception {
		private static final long serialVersionUID = 5964661165465798235L;
		
		private String value;

		public UndefinedVariable(String value) {
			this.value = value;
		}

		public String toString() { 
			return this.value + " is undefined.";
		}
	}
	
	public static class VariablesMaxReached extends Exception {
		private static final long serialVersionUID = 5934661165465798235L;
		
		private int max;

		public VariablesMaxReached(int max) {
			this.max = max;
		}

		public String toString() { 
			return this.max + " is a maximum number of expresions.";
		}
	}
	
	public static class InvalidExpressionsString extends Exception {
		private static final long serialVersionUID = 5964661165465798235L;
		
		private String value;

		public InvalidExpressionsString(String value) {
			this.value = value;
		}

		public String toString() { 
			return this.value + " is invalid expression. Check your sytanx.";
		}
	}
}
