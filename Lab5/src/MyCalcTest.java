import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MyCalcTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	    System.setErr(null);
	}
	
	
	// Helper method to generate random variables names;
	public static String generateString(int max)
	{
		Random ran = new Random();
		char data = ' ';
		String dat = "";

		for (int i=0; i<= max; i++) {
		  data = (char)(ran.nextInt(25)+97);
		  dat = data + dat;
		}
		return dat;
	}
	
	@Test
	public void ValidateMaximumNumberOfVariables() throws 
		MyCalcExceptions.UndefinedVariable,
		MyCalcExceptions.InvalidExpressionsString
	{
		MyCalc myCalc = new MyCalc();
		int exceptions = 0;
		assertEquals(10, MyCalc.MAX_VARIABLES);
		
		for(int i = 0; i <= MyCalc.MAX_VARIABLES; i += 1) {
			try {
				myCalc.processExpression(generateString(5) + " = " + i);
			} catch (MyCalcExceptions.VariablesMaxReached e) {
				exceptions += 1;
			}
			
		}
		
		// MAX_VARIABLES + 1 should result in false
		assertEquals(exceptions, 1);
	}
	
	@Test(expected = MyCalcExceptions.UndefinedVariable.class)
	public void UsageOfUndefinedVariable() throws 
		MyCalcExceptions.UndefinedVariable,
		MyCalcExceptions.VariablesMaxReached,
		MyCalcExceptions.InvalidExpressionsString
	{
		MyCalc myCalc = new MyCalc();
		myCalc.processExpression("a=b");
	}
	
	@Test(expected = MyCalcExceptions.InvalidExpressionsString.class)
	public void InvalidExpressionString() throws 
		MyCalcExceptions.UndefinedVariable,
		MyCalcExceptions.VariablesMaxReached,
		MyCalcExceptions.InvalidExpressionsString
	{
		MyCalc myCalc = new MyCalc();
		myCalc.processExpression("a=3*/2");
	}
	
	@Test
	public void validateWholeExpressionParser() throws 
		MyCalcExceptions.UndefinedVariable,
		MyCalcExceptions.VariablesMaxReached,
		MyCalcExceptions.InvalidExpressionsString
	{
		MyCalc myCalc = new MyCalc();
		String[] wrongExpressions = {
		    "a",
		    "a=",
		    "b=1=",
		    "b=(a+b)"
		};
		int exceptions = 0;
		for(String s : wrongExpressions) {
			try {
				myCalc.processExpression(s);
			} catch (MyCalcExceptions.InvalidExpressionsString e) {
				exceptions += 1;
			}
		}
		assertEquals(wrongExpressions.length, exceptions);
	}
	
	@Test
	public void ValidatePrintAll() throws 
		MyCalcExceptions.UndefinedVariable,
		MyCalcExceptions.VariablesMaxReached,
		MyCalcExceptions.InvalidExpressionsString
	{
		MyCalc myCalc = new MyCalc();
		myCalc.processExpression("a=1");
		myCalc.processExpression("print all");
		assertEquals("a = 1\n", outContent.toString());
	}
	
	@Test
	public void validateJSParsing()  throws 
		MyCalcExceptions.UndefinedVariable,
		MyCalcExceptions.VariablesMaxReached,
		MyCalcExceptions.InvalidExpressionsString
	{
		MyCalc myCalc = new MyCalc();
		myCalc.processExpression("a=1");
		myCalc.processExpression("b=2");
		myCalc.processExpression("c=a+b");
		
		myCalc.processExpression("print c");
		assertEquals("c = 3\n", outContent.toString());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkReserverVariableName()  throws 
		MyCalcExceptions.UndefinedVariable,
		MyCalcExceptions.VariablesMaxReached,
		MyCalcExceptions.InvalidExpressionsString
	{
		MyCalc myCalc = new MyCalc();
		myCalc.processExpression("all=1");
	}
	
	@Test(expected =  MyCalcExceptions.UndefinedVariable.class)
	public void tryToPrintUndefinedVariable()  throws 
		MyCalcExceptions.UndefinedVariable,
		MyCalcExceptions.VariablesMaxReached,
		MyCalcExceptions.InvalidExpressionsString
	{
		MyCalc myCalc = new MyCalc();
		myCalc.processExpression("print x");
	}


}
