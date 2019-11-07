package mchesney_hw5;

/**
 * Immutable Data Class for video objects.
 * Comprises a triple: title, year, director.
 *
 * <p><b>Class Type:</b> Immutable Data Class</p>
 * <p><b>Object Invariant:</b></p>
 *   Title is non-null, no leading or final spaces, not empty string.
 * <p><b>Object Invariant:</b></p>
 *   Year is greater than 1800, less than 5000.
 * <p><b>Object Invariant:</b></p>
 *   Director is non-null, no leading or final spaces, not empty string.
 */
final class VideoObj implements Comparable<VideoObj> {

	/** <p><b>Invariant:</b> non-null, no leading or final spaces, not empty string </p>*/
	private final String title;

	/** <p><b>Invariant:</b> greater than 1800, less than 5000 </p>*/
	private final int year;

	/** <p><b>Invariant:</b> non-null, no leading or final spaces, not empty string </p>*/
	private final String director;

	/**
	 * Initialize all object attributes.
	 * Title and director are "trimmed" to remove leading and final space.
	 * @throws IllegalArgumentException if any object invariant is violated.
	 */
	VideoObj(String title, int year, String director)
                                        throws IllegalArgumentException {
		if (title == null || title.trim().equals("")) {
            throw new IllegalArgumentException();
        }
        if (year <= 1800 || year >= 5000) {
            throw new IllegalArgumentException();
        }
		if (director == null || director.trim().equals("")) {
            throw new IllegalArgumentException();
        }

		this.title = title.trim();
		this.year = year;
		this.director = director.trim();
	}

	/**
	 * Return the value of the attribute.
	 */
	public String director() {
		return director;
	}

	/**
	 * Return the value of the attribute.
	 */
	public String title() {
		return title;
	}

	/**
	 * Return the value of the attribute.
	 */
	public int year() {
		return year;
	}

	/**
	 * Compare the attributes of this object with those of thatObject.
	 * @param thatObject the Object to be compared.
	 * @return deep equality test between this and thatObject.
	 */
	@Override
	public boolean equals(Object thatObject) {
        if (this == thatObject) return true;
        if (!(thatObject instanceof VideoObj)) return false;
        VideoObj thatVid = (VideoObj)thatObject;
        return (title.equals(thatVid.title)) && (year == thatVid.year) &&
            (director.equals(thatVid.director));
	}

	/**
	 * Return a hash code value for this object using the algorithm from Bloch:
	 * fields are added in the following order: title, year, director.
     * @return distinct hash code int value
	 */
	@Override
	public int hashCode() {
       int result = title.hashCode();
       result = 31 * result + Integer.hashCode(year);
       result = 31 * result + director.hashCode();
       return result;
	}

	/**
	 * Compares the attributes of this object with those of thatObject, in
	 * the following order: title, year, director.
	 * @param that the VideoObj to be compared.
	 * @return a negative integer, zero, or a positive integer as this
	 *  object is less than, equal to, or greater than that object.
	 */
	@Override
	public int compareTo(VideoObj thatObject) {
        int result = String.CASE_INSENSITIVE_ORDER.compare(title,
                                                           thatObject.title);
        if (result == 0) {
            result = Integer.compare(year, thatObject.year);
            if (result == 0) {
                result = String.CASE_INSENSITIVE_ORDER.compare(director,
                                                             thatObject.title);
            }
        }
        return result;
	}

	/**
	 * Return a string representation of the object in the following format:
	 * <code>"title (year) : director"</code>.
     * @return string representation of object
	 */
	@Override
	public String toString() {
        return title + " (" + year + ") : " + director;
	}

}
