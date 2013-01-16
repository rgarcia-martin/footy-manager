package test;

import static org.junit.Assert.*;

import org.junit.Test;

import stadium.*;

public class TestStadiumZone {
	
	
	@Test
	public void testN_G(){
		StadiumZone sz1 = new StadiumZoneImpl(SZLocation.NORTH, SZMode.GRASS, SZTier.FIRST);
		StadiumZone sz2 = new StadiumZoneImpl(SZLocation.NORTH, SZMode.GRASS, SZTier.SECOND);
		StadiumZone sz3 = new StadiumZoneImpl(SZLocation.NORTH, SZMode.GRASS, SZTier.THIRD);
		
		assertTrue(sz1.getCapacity() == 1000 && sz2.getCapacity() == 1000 && sz3.getCapacity() == 1000) ;
	}
	
	
	@Test
	public void testS_G(){
		StadiumZone sz1 = new StadiumZoneImpl(SZLocation.SOUTH, SZMode.GRASS, SZTier.FIRST);
		StadiumZone sz2 = new StadiumZoneImpl(SZLocation.SOUTH, SZMode.GRASS, SZTier.SECOND);
		StadiumZone sz3 = new StadiumZoneImpl(SZLocation.SOUTH, SZMode.GRASS, SZTier.THIRD);
		
		assertTrue(sz1.getCapacity() == 1000 && sz2.getCapacity() == 1000 && sz3.getCapacity() == 1000) ;
	}
	
	
	@Test
	public void testE_G(){
		StadiumZone sz1 = new StadiumZoneImpl(SZLocation.EAST, SZMode.GRASS, SZTier.FIRST);
		StadiumZone sz2 = new StadiumZoneImpl(SZLocation.EAST, SZMode.GRASS, SZTier.SECOND);
		StadiumZone sz3 = new StadiumZoneImpl(SZLocation.EAST, SZMode.GRASS, SZTier.THIRD);
		
		assertTrue(sz1.getCapacity() == 1000 && sz2.getCapacity() == 1000 && sz3.getCapacity() == 1000) ;
	}
	
	@Test
	public void testW_G(){
		StadiumZone sz1 = new StadiumZoneImpl(SZLocation.WEST, SZMode.GRASS, SZTier.FIRST);
		StadiumZone sz2 = new StadiumZoneImpl(SZLocation.WEST, SZMode.GRASS, SZTier.SECOND);
		StadiumZone sz3 = new StadiumZoneImpl(SZLocation.WEST, SZMode.GRASS, SZTier.THIRD);
		
		assertTrue(sz1.getCapacity() == 1000 && sz2.getCapacity() == 1000 && sz3.getCapacity() == 1000) ;
	}
	
	@Test
	public void testC_G(){
		StadiumZone sz1 = new StadiumZoneImpl(SZLocation.CORNER, SZMode.GRASS, SZTier.INNER_CORNER);
		StadiumZone sz2 = new StadiumZoneImpl(SZLocation.CORNER, SZMode.GRASS, SZTier.OUTER_CONNER);
		
		assertTrue(sz1.getCapacity() == 1000 && sz2.getCapacity() == 1000) ;
	}
}
