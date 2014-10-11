public class Lab1 {

	public static void main(String[] args) {
//		testMe();
		new InputMyCarHandler();
	}
	
	public static void testMe() {
		try {
			testBuilderArguments();
			testMyCar();
		} catch (Exception e) {
	    	 e.printStackTrace();
		}
	}
	
	public static void testBuilderArguments() throws Exception {
		try {
			new MyCarBuilder().tankCapacity(19);
		    fail( "It should have thrown exception about tank capacity too low" );
		} catch (Exception IllegalArgumentException) {}
		try {
			new MyCarBuilder().tankCapacity(81);
		    fail( "It should have thrown exception about tank capacity too high" );
		} catch (Exception IllegalArgumentException) {}
		try {
			new MyCarBuilder().combustion(2);
			fail( "It should have thrown exception about mileage too low" );
		} catch (Exception IllegalArgumentException) {}
		try {
			new MyCarBuilder().combustion(21);
			fail( "It should have thrown exception about mileage too high" );
		} catch (Exception IllegalArgumentException) {}
		
		if(new MyCarBuilder().buildCar().tankCapacity() != 40) 
			fail("Wrong default for tank capacity");
			
		if(new MyCarBuilder().buildCar().mileage() != 5) 
			fail("Wrong default for tank capacity");
		
	}
	
	public static void testMyCar() throws Exception {
		MyCar myCar = new MyCarBuilder().buildCar();
		try {
			myCar.tankIt(-1);
			fail( "It should have thrown exception about wrong tank capacity" );
		} catch (Exception IllegalArgumentException) {}
		try {
			myCar.tankIt(myCar.tankCapacity() + 1);
			fail( "It should have thrown exception about exceeded capacity" );
		} catch (Exception IllegalArgumentException) {}	
		
		myCar.tankIt(5);
		if(myCar.getFuelLevel() != 5)
			fail("It should have updated tank level");
		
		try {
			myCar.makeTrip(-1d);
			fail( "It should have thrown exception about distance" );
		} catch (Exception IllegalArgumentException) {}	
		
		double previousTotalDistance = myCar.getTotalDistance();
		double previousFuelValue = myCar.getFuelLevel();
		if(myCar.makeTrip(myCar.getFuelLevel() * myCar.mileage()*100 + 1)) {
			fail("It should have returned false");
		}
		
		if(previousTotalDistance != myCar.getTotalDistance() || 
		   previousFuelValue != myCar.getFuelLevel()) {
			fail( "It should have retuned updated distance or fuel");
		}
		
		myCar.makeTrip(100);
		if(myCar.getFuelLevel() != 0 || myCar.getTotalDistance() != 100 || myCar.getLastTripDistance() != 100) {
			fail( "It should have updated properities of car correctly");
		}
	}
	
	private static void fail(String errorMessage) throws Exception {
		System.out.println(errorMessage);
		throw new Exception(errorMessage);
	}

}
