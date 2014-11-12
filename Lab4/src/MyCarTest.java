import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;


public class MyCarTest {

	@Test
	public void testMyCar() {
		MyCar car = new MyCar("Volvo", 200);
		assertEquals(car.getTankCapacity(), 200.0, 0.000001);
		assertEquals(car.getMaker(), CarMaker.VOLVO);
		assertEquals(car.getFuelCurrentValue(), 0, 0.000001);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTankGasOverCapacity() {
		MyCar car = new MyCar("Volvo", 10);
		car.tankGas(11);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testTankGasUnderCapacity() {
		MyCar car = new MyCar("Volvo", 10);
		car.tankGas(-1);
	}
	
	@Test
	public void testMakeTripDoubleDoubleCreateTrip() {
		MyCar car = new MyCar("Volvo", 10);
		car.tankGas(10);
		assertEquals(car.makeTrip(4, 3), true);
		
		assertEquals(car.totalDistance(), 4, 0.000001);
		assertEquals(car.getFuelCurrentValue(), 7, 0.000001);
		
		// Over gas combustion
		assertEquals(car.makeTrip(10, 10), false);
		
		// invalid distance
		assertEquals(car.makeTrip(-10, 10), false);
	}


	@Test
	public void testTotalDistance() {
		MyCar car = new MyCar("Volvo", 10);
		car.tankGas(10);
		car.makeTrip(4, 3);
		assertEquals(car.totalDistance(), 4, 0.000001);
		car.makeTrip(1, 1);
		assertEquals(car.totalDistance(), 5, 0.000001);
		car.makeTrip(100, 1);
		assertEquals(car.totalDistance(), 105, 0.000001);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testTotalDistanceDate() {
		MyCar car = new MyCar("Volvo", 10);
		car.tankGas(10);
		car.makeTrip(4, 3);
		assertEquals(car.totalDistance(new Date(2000)), 0, 0.000001);
		assertEquals(car.totalDistance(new Date()), 4, 0.000001);
		car.makeTrip(new Date(2009, 10, 10), 1, 1);
		car.makeTrip(new Date(2009, 10, 10), 1, 1);
		assertEquals(car.totalDistance(new Date(2009, 10, 10)), 2, 0.000001);
		car.makeTrip(new Date(2009, 10, 10), 100, 1);
		assertEquals(car.totalDistance(new Date(2009, 10, 10)), 102, 0.000001);
		assertEquals(car.totalDistance(new Date()), 4, 0.000001);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testTotalDistanceDateDate() {
		MyCar car = new MyCar("Volvo", 50);
		car.tankGas(50);
		car.makeTrip(new Date(2005, 5, 5), 4, 3);
		assertEquals(car.totalDistance(new Date(2005, 5, 4), new Date(2005, 5, 6)), 4, 0.000001);
		assertEquals(car.totalDistance(new Date(2005, 5, 6), new Date(2005, 5, 6)), 0, 0.000001);
	
		car.makeTrip(new Date(2009, 10, 10), 1, 1);
		car.makeTrip(new Date(2009, 11, 10), 1, 1);
		
		assertEquals(car.totalDistance(new Date(2009, 10, 9), new Date(2009, 11, 11)), 2, 0.000001);
		
		car.makeTrip(new Date(2009, 11, 1), 100, 1);
		System.out.println(car.totalDistance(new Date(2009, 11, 1), new Date(2014, 1, 1)));
		assertEquals(car.totalDistance(new Date(2009, 11, 1), new Date(2014, 1, 1)), 101, 0.000001);
	}

	
	@Test
	public void testTotalGasConsumption() {
		MyCar car = new MyCar("Volvo", 10);
		car.tankGas(10);
		car.makeTrip(4, 3);
		assertEquals(car.totalGasConsumption(), 3, 0.000001);
		car.makeTrip(1, 1);
		car.makeTrip(1, 1);
		assertEquals(car.totalGasConsumption(), 5, 0.00001 );
	}
}
