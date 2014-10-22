import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputMyCarHandler {
	private MyCar myCar;
	private Scanner scan = new Scanner(System.in);
	private final Pattern TANK_REGEXP = Pattern.compile("tank ([-+]?([0-9]*\\.[0-9]+|[0-9]+)$)");
	private final Pattern TRIP_REGEXP = Pattern.compile("trip ([-+]?([0-9]*\\.[0-9]+|[0-9]+)$)");
	
	public InputMyCarHandler() {
		this.myCar = GenerateCar();
		System.out.println("Great job! You've got the car.");
		displayMenu();
		scan.nextLine();
		handleMenu();
		System.out.println("Goodbye.");
	}

	private void displayMenu() {
		System.out.println("Available commands: ");
		System.out.println("tank X:");
		System.out.println("trip X:");
		System.out.println("total distance");
		System.out.println("total fuel");
		System.out.println("exit");
	}
	
	private void handleMenu() {
		String command = scan.nextLine().trim();
		Matcher m = TANK_REGEXP.matcher(command);
		if(m.find()) 
			handleTank(Double.parseDouble(m.group(1)));
		else {
			m = TRIP_REGEXP.matcher(command);
			if(m.find()) 
				handleTrip(Integer.parseInt(m.group(1)));
			
			else if(command.equals("total distance")) System.out.println(this.myCar.getTotalDistance());
			else if(command.equals("total fuel")) System.out.println(this.myCar.getFuelLevel());
			else if(command.equals("exit")) return;
			else System.out.println("Wrong or unsupported command");
		}

		handleMenu();
	}

	private void handleTrip(int distance) {
		try {
			if(myCar.makeTrip(distance)) {
				System.out.println("You've travelled " + distance);
			} else {
				System.out.println("Wrong distance. Maybe you're trying to go too far?");
			}
			
		} catch (Exception e) { e.printStackTrace(); }
	}

	private void handleTank(double howMuch) {
		try {
			if(myCar.tankIt(howMuch)) {
				System.out.println("You've tanked " + howMuch);
			} else {
				System.out.println("Couldn't tank?");
			}
			
		} catch (Exception e) { e.printStackTrace(); }
	}

	private void failedCreation(Throwable throwable) {
		System.out.println("There were errors in your data. Please start again.");
		System.out.println(throwable.getMessage());
		GenerateCar();
	}

	private double handleNumberOrNull() {
		String command = scan.nextLine().trim();
		
		if(command.matches("[-+]?([0-9]*\\.[0-9]+|[0-9]+)$")) {
			return Double.parseDouble(command);
		} else if(command.equals("")) return -1;
		else {
			throw new IllegalStateException("That's not a number.");
		}
	}
	private MyCar GenerateCar() {
		MyCarBuilder myCarBuilder = new MyCarBuilder();

		System.out
				.println("Hello in the Car Creator! Let us create new car for you: ");
		System.out.println("What tank capacity your car should have: ("
				+ MyCarBuilder.TANK_MIN_VALUE + "-"
				+ MyCarBuilder.TANK_MAX_VALUE + ", default: "
				+ MyCarBuilder.TANK_DEFUALT_VALUE + ")");
		
		try {
			Double value = handleNumberOrNull();
			if(value != -1) myCarBuilder.tankCapacity(value);
		} catch (Throwable throwable) { failedCreation(throwable); }
		
		System.out.println("What mileage your car should have: ("
				+ MyCarBuilder.MILEAGE_MIN_VALUE + "-"
				+ MyCarBuilder.MILEAGE_MAX_VALUE + ", default: "
				+ MyCarBuilder.MILEAGE_DEFAULT_VALUE+ ")");
		
		try {
			Integer value = (int) handleNumberOrNull();
			if(value != -1) myCarBuilder.combustion(value);
		} catch (Throwable throwable) { failedCreation(throwable); }
		
		return myCarBuilder.buildCar();
	}
}
