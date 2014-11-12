import java.util.Date;
import java.util.Vector;

//Using implement a class MyNewCar that according to the following specification: 
//1. It should contain at least the following data members: 

//b. Double tankCapacity; the maximal amount of fuel that the car can hold. 

//2. It should implement at least the following methods: 
//a. The constructor of a MyCar(String maker, Double tankCapacity); 
//b. void tankGas(double f); the tank capacity could not be exceeded 
//c. bool makeTrip(double distance, double gasUsed); there must be enough gas in 
//the tank; 


public class MyCar {
    private Vector<Trip> allTrips = new Vector<Trip>(10);
    private CarMaker maker;
    private double fuelCurrentValue;
    private double tankCapacity;
    
	public MyCar(String maker, double tankCapacity) {
		this.setMaker(maker);
		this.setTankCapacity(tankCapacity);
	}
	
	public boolean tankGas(double howMuch) {
		if (howMuch < 0) 
			throw new IllegalArgumentException("You cannot tank negative value.");
		
		if(this.getFuelCurrentValue() + howMuch > this.getTankCapacity())
			throw new IllegalArgumentException("You cannot fuel more than your capacity");
			
     	this.fuelCurrentValue += howMuch;
		return true;
	}
	

	public boolean makeTrip(double distance, double gasUsed) {
		return makeTrip(new Date(), distance, gasUsed);
	}
	
	public boolean makeTrip(Date d, double distance, double gasUsed) {
		double newFuelValue = this.fuelCurrentValue - gasUsed;
		Trip trip;
		try {
			 trip = new Trip(d, distance, gasUsed);			
		} catch (IllegalArgumentException e) {
			return false;
		}
		
		if(newFuelValue < 0) {
			return false;
		} else {
			this.fuelCurrentValue = newFuelValue;
			this.allTrips.addElement(trip);
			return true;
		}
	}
	
	double totalDistance() {
		double totalDistance = 0;
		for(Trip trip : this.allTrips) {
			totalDistance += trip.getDistance();
		}
		return totalDistance;
	}
	
	@SuppressWarnings("deprecation")
	double totalDistance(Date d) {
		double totalDistance = 0;
		for(Trip trip : this.allTrips) {
			if(trip.getDate().getTime().getMonth() == d.getMonth() && 
				trip.getDate().getTime().getDate() == d.getDate() &&
				trip.getDate().getTime().getYear() == d.getYear()
			)
				totalDistance += trip.getDistance();
		}
		return totalDistance;
	}
	
	double totalDistance(Date from, Date to) {
		double totalDistance = 0;
		for(Trip trip : this.allTrips) {
			if(trip.getDate().getTime().compareTo(from) >= 0 
				&& trip.getDate().getTime().compareTo(to) <= 0)
				totalDistance += trip.getDistance();
		}
		return totalDistance;
	}
	
	double totalGasConsumption() {
		double gasConsumed = 0;
		for(Trip trip : this.allTrips) {
			gasConsumed += trip.getGasConsumed();
		}
		return gasConsumed;
	}
	
	public double getTankCapacity() { return this.tankCapacity; }
	public double getFuelCurrentValue() { return this.fuelCurrentValue; }

	public CarMaker getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		try { 
			this.maker = CarMaker.valueOf(maker.toUpperCase());
		} catch (Exception e) {
			throw new IllegalArgumentException(maker + " isn't a proper maker string.");
		}
		
	}
	
	public void setTankCapacity(double tankCapacity) {
		if (tankCapacity < 0) 
			throw new IllegalArgumentException("You cannot tank negative value.");
		
		this.tankCapacity = tankCapacity;
	}

}
