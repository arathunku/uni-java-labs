import java.util.Scanner;
import java.util.Map.Entry;

import javax.script.ScriptException;



public class Lab5 {

	public static void main(String[] args) 
	{
		MyCalc myCalc = new MyCalc();
		
		System.out.println("Calculator started...Write expressions in format"
				+ "");
		
		System.out.println("Available commands:");
		System.out.println("print all");
		System.out.println("print ${VariableName}");
		System.out.println("aName = anExpressions");
		System.out.println("quit // finish program");
		
		Scanner scan = new Scanner(System.in);
		
		while(true) {
			while(scan.hasNext()) {
				String command = scan.nextLine();
					if(command.equals("quit")) {
						scan.close();
						System.out.println("Closing program...");
						System.exit(0);
					} else {
						try {
							myCalc.processExpression(command);
						} catch (MyCalcExceptions.UndefinedVariable |
								MyCalcExceptions.InvalidExpressionsString |
								MyCalcExceptions.VariablesMaxReached e) {
							System.out.println(e.toString());
						}	
					}
			}
		}
		
	}

}
