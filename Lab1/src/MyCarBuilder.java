public class MyCarBuilder {
    static final double TANK_MIN_VALUE = 20;
    static final double TANK_DEFUALT_VALUE = 40;
    static final double TANK_MAX_VALUE = 80;
    static final int MILEAGE_MIN_VALUE = 3;
    static final int MILEAGE_DEFAULT_VALUE = 5;
    static final int MILEAGE_MAX_VALUE = 20;

    private int _combustion = MILEAGE_DEFAULT_VALUE;
    private double _tankCapacity = TANK_DEFUALT_VALUE;
    
    public MyCarBuilder() {}
    
    public MyCar buildCar() {
    	return new MyCar(_tankCapacity, _combustion);
    }
    
    public MyCarBuilder tankCapacity(double _tankCapacity) {
    	if(_tankCapacity > TANK_MAX_VALUE || _tankCapacity < TANK_MIN_VALUE) {
    		throw new IllegalArgumentException(
    				"Tank capacity has to be between: " +
    		         TANK_MIN_VALUE +  " - " + TANK_MAX_VALUE);
    	} else {
            this._tankCapacity = _tankCapacity;
    	}
        return this;
    }
    
    public MyCarBuilder combustion(int _combustion) {
    	if(_combustion > MILEAGE_MAX_VALUE || _combustion < MILEAGE_MIN_VALUE) {
    		throw new IllegalArgumentException(
    				"Allow Mileage: " +
    				MILEAGE_MIN_VALUE +  " - " + MILEAGE_MAX_VALUE);
    	} else {
            this._combustion = _combustion;
    	}
        return this;
    }   
}