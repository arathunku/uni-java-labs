import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ExpressionParser {
	private ExpressionType type;
	private String input;
	private String output;
	private static MyCalc calc = new MyCalc();
	
	
	ExpressionParser(String input) {
		if(input == null || (input = input.trim()).equals("")) {
			type = ExpressionType.EXPRESSION_ERROR;
			output = "Value is empty";
			return;
		}
		
		if(input.equals("Date")) {
			type = ExpressionType.DATE;
			output = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		} else if(input.equals("Time")) {
			type = ExpressionType.TIME;
			output = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
		} else if(input.length() >= "DaySpan".length() && input.substring(0, "DaySpan".length()).equals("DaySpan")) {
			Pattern p = Pattern.compile("DaySpan.\\s*([0-9]{2}/[0-9]{2}/[0-9]{4})\\s*");
			Matcher m = p.matcher(input);
			String date = null;
	    	type = ExpressionType.EXPRESSION_ERROR;
	    	output = "Invalid format! DD/MM/YYYY";
	    	
			while (m.find()) {
			    date = m.group(1);
			    if(date != null || !(date = date.trim()).equals("")) {
			    	type = ExpressionType.DAYSPAN;
			    	output = daySpan(date);
			    }
			}
			
		} else {
			try {
				output = calc.processExpression(input);
			} catch (MyCalcExceptions.UndefinedVariable |
					MyCalcExceptions.InvalidExpressionsString e) {
				type = ExpressionType.EXPRESSION_ERROR;
				output = e.toString();
			}
		}
	}
	
	public ExpressionType getType() {
		return type;
	}
	
	public String getInput() {
		return input;
	}
	
	public String getOutput() {
		return output;
	}
	
	private String daySpan(String date) {
	    long diff;
		try {
			Date today = new Date();
			diff = today.getTime() - new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime();
		} catch (ParseException e) {
	    	type = ExpressionType.EXPRESSION_ERROR;
	    	return "Invalid format! DD/MM/YYYY";
		}
	    
	    return Integer.toString(Math.abs((int)(diff / (1000 * 60 * 60 * 24))));
	}
}
