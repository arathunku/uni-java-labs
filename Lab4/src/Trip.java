import java.util.Calendar;
import java.util.Date;

public class Trip {
	private static double MAX_GAS_CONSUMPTION = 100.0;
	private static double MIN_GAS_CONSUMPTION = 0.5;
	private static double MAX_DISTANCE = 1000.0;
	private static double MIN_DISTANCE = 1.0;
	
	private Calendar date;
	private double distance;
	private double gasConsumed;
	
	public Trip(Date date, double distance, double gasConsumed) {
		setDate(date);
		setDistance(distance);
		setGasConsumed(gasConsumed);
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Date date) {
	    Calendar limit = Calendar.getInstance();
	    limit.set(2000, 1, 1);
	    Calendar parsedDate = Calendar.getInstance();
	    parsedDate.setTime(date);
	    
		if(parsedDate.before(limit)) {
			throw new IllegalArgumentException(date + " isn't before 01.01.2000 which isn't valid date");
		}
		this.date = parsedDate;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		if(distance < MIN_DISTANCE || distance > MAX_DISTANCE) {
			throw new IllegalArgumentException(distance + " is inproper value. Has to be in range: 1-100");
		}
		this.distance = distance;
	}

	public double getGasConsumed() {
		return gasConsumed;
	}

	public void setGasConsumed(double gasConsumed) {
		if(gasConsumed < MIN_GAS_CONSUMPTION || gasConsumed > MAX_GAS_CONSUMPTION) {
			throw new IllegalArgumentException(gasConsumed + "is inproper value. "
					+ "Has to be larger than 0.5 and lower than 100.");
		}
		
		this.gasConsumed = gasConsumed;
	}
}
