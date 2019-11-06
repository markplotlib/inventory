package mchesney_hw5;

import static org.junit.Assert.*;

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
        s.clear()
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
        s.addNumOwned(v1, 5);
        VideoObj vGot = s.get(v1);
        assertTrue(v1.equals(vGot));
        // Get should return a COPY of the records, not the records themselves.
        assertNotSame(v1, vGot);
        // if not present, return <code>null</code>.
        VideoObj vDoNotGot = s.get(v2);
        assertNull(vDoNotGot);
	}

	@Test
	public void testToCollection() {
		// TODO: complete testToCollection test
		// Be sure to test that changing records in the returned
		// collection does not change the original records in the
		// inventory.  ToCollection should return a COPY of the records,
		// not the records themselves.
        Collection<Record> emptiness = s.toCollection();
        assertEquals(0, emptiness.size());
        s.addNumOwned(v1, 1);
        s.addNumOwned(v2, 2);
        Collection<Record> copied = s.toCollection();
        // underlying collection not returned.
        assertNotSame(s, copied);
        // actual records not returned.
        assertNotSame(s.get(v1), copied.get(v1));
        for (VideoObj v : s) {
            assertNotSame(s.get(v1), copied.get(v1));
        }
        // changing records in returned collection does not change original
        copied.addNumOwned(v1, 9);
        assertFalse(s.get(v1).numOwned > 1);
        // changing records in original collection does not change returned
        s.addNumOwned(v2, -1);
        assertFalse(copied.get(v2).numOwned == s.get(v2).numOwned);
        s.clear();
        assertTrue(copied.size() > 0);
	}

}
