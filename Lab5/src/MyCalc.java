import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class MyCalc {
	static final int MAX_VARIABLES = 10;
	static final ScriptEngineManager mgr = new ScriptEngineManager();
	static final ScriptEngine engine = mgr.getEngineByName("JavaScript");
	
	private HashMap<String, String> variables = new HashMap<String, String>(MAX_VARIABLES);
	
	public MyCalc() {}
	
	public void processExpression(String expr) throws 
		MyCalcExceptions.UndefinedVariable, 
		MyCalcExceptions.VariablesMaxReached,
		MyCalcExceptions.InvalidExpressionsString
	{
		if(expr == null) {
			throw new IllegalArgumentException("Expression is not defined");
		}
		
		if(expr.length() >= "print".length() && expr.substring(0, "print".length()).toLowerCase().equals("print")) {
			String name = expr.substring("print".length()+1).trim();
			if(name.equals("all")) {
				this.printAllVariables();
			} else {
				this.printKey(name);
			}
		} else {
			String[] splittedExp = validateExpression(expr);
			if(splittedExp[0] == null) { 
				throw new  MyCalcExceptions.InvalidExpressionsString(expr);
			} else if(splittedExp[0].toLowerCase().equals("all")) {
				throw new IllegalArgumentException("all is reserver variable name");
			}
			
			String processedExpr = this.replaceVariables(splittedExp[1]);
			String result = "";
			try {
				result = engine.eval(processedExpr).toString();
			} catch (ScriptException e) {
				throw new MyCalcExceptions.InvalidExpressionsString(processedExpr);
			}
			
			this.addVariable(splittedExp[0], result);	
		}
	}
	
	private String replaceVariables(String expr) throws 
			MyCalcExceptions.UndefinedVariable 
	{
		Pattern p = Pattern.compile("(\\b[a-zA-Z]+)\\b");
		Matcher m = p.matcher(expr);
		
		StringBuffer sb = new StringBuffer();

		while (m.find())
		{
		    String repString = this.variables.get(m.group(1));
		    if (repString == null) {
		    	throw new MyCalcExceptions.UndefinedVariable(m.group(1));
		    } else {
		    	m.appendReplacement(sb, repString);		    	
		    }
		}
		
		m.appendTail(sb);
		
		return sb.toString();
	}
	
	static public String[] validateExpression(String expr) { 
		// first item is a name, second expression 
		String[] values = new String[2];
		Pattern p = Pattern.compile("^(\\b[a-zA-Z]+\\b)+\\s*=\\s*([\\w\\s*+-/]+)\\s*$");
	    Matcher m = p.matcher(expr);
	    if (m.find()) {
	    	values[0] = m.group(1);
	    	values[1] = m.group(2);
	    }
		return values;
	}
	
	private void addVariable(String name, String value) throws MyCalcExceptions.VariablesMaxReached {
		if(name == null || name.trim().length() == 0) {
			throw new IllegalArgumentException("Name has to have at least 1 character.");
		}
		
		if(value == null || name.trim().length() == 0) {
			throw new IllegalArgumentException("Value has to have at least 1 character.");
		}
		
		try {
			Double.parseDouble(value);
		} catch (Exception e) {
			throw new IllegalArgumentException("Value is not a number");
		}
		
		if(this.variables.size() < MAX_VARIABLES) {
			this.variables.put(name.trim(), value.trim());
		} else {
			throw new MyCalcExceptions.VariablesMaxReached(MAX_VARIABLES);
		}
	}
	
	public HashMap<String, String> getVariables() {
		return this.variables;
	}
	
	private void printAllVariables() {
		for (Entry<String, String> entry : this.getVariables().entrySet()) {
		    System.out.println(entry.getKey() + " = " + entry.getValue());
		}
	}
	
	private void printKey(String key) throws MyCalcExceptions.UndefinedVariable {
		if(this.getVariables().containsKey(key)) {
			for (Entry<String, String> entry : this.getVariables().entrySet()) {
				if(entry.getKey().equals(key)) {
					System.out.println(entry.getKey() + " = " + entry.getValue());
					break;
				}
			}			
		} else {
			throw new MyCalcExceptions.UndefinedVariable(key);
		}
	}
}
