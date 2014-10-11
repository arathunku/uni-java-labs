public class MyCar {
    private static final double DISTANCE_PER_FUEL_CONSUMPTION_UNIT = 100d;
    
    private double _fuelCurrentValue;
    private int _combustion;
    private double _tankCapacity;
    private Double _lastTripDistance;
    private Double _totalDistance;
    
	public MyCar(double tankCapacity, int combustion) {
		_totalDistance = 0d;
		_lastTripDistance = 0d;
		_tankCapacity = tankCapacity;
		_combustion = combustion;
		_fuelCurrentValue = 0;
	}
	
	public boolean tankIt(double howMuch) {
		if (howMuch < 0) 
			throw new IllegalArgumentException("You cannot tank negative value.");
		
		if(this._fuelCurrentValue + howMuch > this._tankCapacity)
			throw new IllegalArgumentException("You cannot fuel more than your capacity");
			
     	this._fuelCurrentValue += howMuch;
		return true;
	}
	
	public boolean makeTrip(double distance) {
		if (distance < 0) {
			throw new IllegalArgumentException("Distance has to be >= 0.");
		}
		
		double newFuelValue = this._fuelCurrentValue - 
				distance/DISTANCE_PER_FUEL_CONSUMPTION_UNIT * this._combustion;
		
		if(newFuelValue < 0) {
			return false;
		} else {
			this._fuelCurrentValue = newFuelValue;
			this._lastTripDistance = distance;
			this._totalDistance += distance;
			return true;
		}
	}
	
	public double getLastTripDistance() { return this._lastTripDistance; }
	public double getTotalDistance() { return this._totalDistance; }
	public double getFuelLevel() { return this._fuelCurrentValue; }
	public double tankCapacity() { return this._tankCapacity; }
	public double mileage() { return this._combustion; }

}
