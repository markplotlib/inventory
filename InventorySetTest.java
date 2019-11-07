package mchesney_hw5;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

public class InventorySetTest {

	InventorySet s = new InventorySet();
	final VideoObj v1 = new VideoObj( "A", 2000, "B" );
	final VideoObj v1copy = new VideoObj( "A", 2000, "B" );
	final VideoObj v2 = new VideoObj( "B", 2000, "B" );

	@Test
	public void testAddNumOwned() {
		assertEquals( 0, s.size() );
		s.addNumOwned(v1, 1);     assertEquals( v1, s.get(v1).video );
		assertEquals( 1, s.get(v1).numOwned );
		s.addNumOwned(v1, 2);     assertEquals( 3, s.get(v1).numOwned );
		s.addNumOwned(v1, -1);    assertEquals( 2, s.get(v1).numOwned );
		s.addNumOwned(v2, 1);     assertEquals( 2, s.get(v1).numOwned );
		s.addNumOwned(v1copy, 1); assertEquals( 3, s.get(v1).numOwned );
		assertEquals( 2, s.size() );
		s.addNumOwned(v1, -3);
		assertEquals( 1, s.size() );
		try { s.addNumOwned(null, 1);   fail(); } catch ( IllegalArgumentException e ) {}
	}

	@Test
	public void testSize() {
		assertEquals( 0, s.size() );
		s.addNumOwned(v1,  1); assertEquals( 1, s.size() );
		s.addNumOwned(v1,  2); assertEquals( 1, s.size() );
		s.addNumOwned(v2,  1); assertEquals( 2, s.size() );
		s.addNumOwned(v2, -1); assertEquals( 1, s.size() );
		s.addNumOwned(v1, -3); assertEquals( 0, s.size() );
		try { s.addNumOwned(v1, -3); fail(); } catch ( IllegalArgumentException e ) {}
		assertEquals( 0, s.size() );
	}

	@Test
	public void testCheckOutCheckIn() {
        // test checkOut
        s.clear();
        // throws IllegalArgumentException if video has no record
        try {
            s.checkOut(v1);
            fail();
        } catch (IllegalArgumentException e) {}
        // happy path
        s.addNumOwned(v2, 1);
        s.checkOut(v2);
        // throws IllegalArgumentException if numOut equals numOwned.
        try {
            s.checkOut(v2);
            fail();
        } catch (IllegalArgumentException e) {}

        // test checkIn
        s.clear();
        // happy path
        s.addNumOwned(v1, 1);
        s.checkOut(v1);
        s.checkIn(v1);
        // throws IllegalArgumentException if video has no record.
        try {
            s.checkIn(v2);
            fail();
        } catch (IllegalArgumentException e) {}
        // throws IllegalArgumentException if numOut non-positive.
        try {
            s.checkIn(v1);
            fail();
        } catch (IllegalArgumentException e) {}
	}

	@Test
	public void testClear() {
        s.addNumOwned(v1, 5);
        s.addNumOwned(v2, 3);
        s.clear();
        assertEquals(0, s.size());
        // clear() results in an empty inventory state, even if it was empty
        s.clear();
        assertEquals(0, s.size());
	}

	@Test
	public void testGet() {
        // Return a copy of the record for a given Video
        s.addNumOwned(v1, 1);
        s.addNumOwned(v1copy, 1);
        Record recGotV1 = s.get(v1);
        Record recGotV1Copy = s.get(v1copy);
        assertTrue(recGotV1.toString().equals(recGotV1Copy.toString()));
        // Get should return a COPY of the records, not the records themselves.
        assertNotSame(recGotV1, recGotV1Copy);
        // if not present, return <code>null</code>.
        Record vDoNotGot = s.get(v2);
        assertNull(vDoNotGot);
	}


	@Test
	public void testToCollection() {
		Collection<Record> emptiness = s.toCollection();
        assertEquals(0, emptiness.size());
        s.addNumOwned(v1, 2);
        ArrayList<Record> copied = (ArrayList<Record>) s.toCollection();
        assertEquals(copied.size(), s.size());
        // actual records not returned.
        assertNotSame(s.get(v1), copied.get(0));
        // changing records in returned collection does not change original
        copied.set(0, new Record(v2, 99, 0, 0));
        assertTrue(s.get(v1).numOwned < 9);
        // changing records in original collection does not change returned
        s.addNumOwned(v1, -1);
        assertFalse(copied.get(0).numOwned == s.get(v1).numOwned);
        s.clear();
        assertFalse(copied.size() == s.size());
	}

}
