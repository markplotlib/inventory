package mchesney_hw5;

import static org.junit.Assert.*;

import org.junit.Test;

public class VideoObjTest {

	@Test
	public void testConstructorAndAttributes() {
		String title1 = "XX";
		String director1 = "XY";
		String title2 = " XX ";
		String director2 = " XY ";
		int year = 2002;

		VideoObj v1 = new VideoObj(title1, year, director1);
		assertSame(title1, v1.title());
		assertEquals(year, v1.year());
		assertSame(director1, v1.director());

		VideoObj v2 = new VideoObj(title2, year, director2);
		assertEquals(title1, v2.title());
		assertEquals(director1, v2.director());
	}

	@Test
	public void testConstructorExceptionYear() {
		try {
			new VideoObj("X", 1800, "Y");
			fail();
		} catch (IllegalArgumentException e) { }
		try {
			new VideoObj("X", 5000, "Y");
			fail();
		} catch (IllegalArgumentException e) { }
		try {
			new VideoObj("X", 1801, "Y");
			new VideoObj("X", 4999, "Y");
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	@Test
	public void testConstructorExceptionTitle() {
		try {
			new VideoObj(null, 2002, "Y");
			fail();
		} catch (IllegalArgumentException e) { }
		try {
			new VideoObj("", 2002, "Y");
			fail();
		} catch (IllegalArgumentException e) { }
		try {
			new VideoObj(" ", 2002, "Y");
			fail();
		} catch (IllegalArgumentException e) { }
	}

	@Test
	public void testConstructorExceptionDirector() {
        // Director is non-null
        try {
            new VideoObj("ET", 1982, null);
            fail();
        } catch (IllegalArgumentException e) {}
        // Director is no leading or final spaces
        try {
            new VideoObj("ET", 1982, " ");
            fail();
        } catch (IllegalArgumentException e) {}
        // Director is not empty string.
        try {
            new VideoObj("ET", 1982, "");
            fail();
        } catch (IllegalArgumentException e) {}
	}

	@Test
	public void testHashCode() {
		assertEquals
		(-1869722747, new VideoObj("None", 2009, "Zebra").hashCode());
		assertEquals
		(2057189520, new VideoObj("Blah", 1954, "Cante").hashCode());
	}

    @Test
    public void testEquals() {
        VideoObj vid1 = new VideoObj("ET", 1982, "Steven Spielberg");
        // test reflexivity -- identical objects (identical memory allocation)
        assertTrue(vid1.equals(vid1));
        // two objects of all equal fields -- copies of same video
        VideoObj vid2 = new VideoObj("ET", 1982, "Steven Spielberg");
        assertTrue(vid1.equals(vid2));
        // test symmetry
        assertTrue(vid2.equals(vid1));
        // test transivity
        VideoObj vid3 = new VideoObj("ET", 1982, "Steven Spielberg");
        assertTrue(vid2.equals(vid3) && vid1.equals(vid3));
        // object is not of the correct type
        Integer i = new Integer(3);
        assertFalse(vid1.equals(i));
        // unequal fields
        // unequal titles
        VideoObj schin = new VideoObj("Schin List", 1982, "Steven Spielberg");
        assertFalse(vid1.equals(schin));
        // unequal years
        VideoObj et2000 = new VideoObj("ET", 2000, "Steven Spielberg");
        assertFalse(vid1.equals(et2000));
        // unequal director
        VideoObj etJoeB = new VideoObj("ET", 1982, "Joe Biden");
        assertFalse(vid1.equals(etJoeB));
    }

	@Test
	public void testCompareTo() {
        VideoObj v1 = new VideoObj("t1", 1984, "d1");
        // test reflexivity
        VideoObj v1copy = new VideoObj("t1", 1984, "d1");
        assertTrue(v1.compareTo(v1copy) == 0);
        // test symmetry
        VideoObj v2 = new VideoObj("t2", 1989, "d1");
        VideoObj v3 = new VideoObj("t1", 2016, "d2");
        assertEquals(v1.compareTo(v3), -v3.compareTo(v1));
        assertEquals(v1.compareTo(v2), -v2.compareTo(v1));
        // test transivity
        boolean condition1, condition2;
        condition1 = v3.compareTo(v2) < 0 && v2.compareTo(v1) < 0;
        condition2 = v3.compareTo(v1) < 0;
        assertEquals(condition1, condition2);
        // test consistency with this.equals(that)
        assertEquals(v1.compareTo(v1) == 0, v1.equals(v1));
	}

	@Test
	public void testToString() {
		String s = new VideoObj("A", 2000, "B").toString();
		assertEquals( "A (2000) : B", s );
		s = new VideoObj(" A ", 2000, " B ").toString();
		assertEquals( "A (2000) : B", s );
	}

}
