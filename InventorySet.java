package mchesney_hw5;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;

/**
 * An Inventory implemented using a <code>HashMap&lt;Video, Record&gt;</code>.
 * Keys are Videos; Values are Records.
 *
 * <p><b>Class Type:</b> Mutable Collection of Records</p>
 * <p><b>Object Invariant:</b></p>
 *   Every key and value in the map is non-<code>null</code>.
 * <p><b>Object Invariant:</b></p>
 *   Each value <code>r</code> is stored under key <code>r.video</code>.
 */
final class InventorySet {

	/** <p><b>Invariant:</b> <code>_data != null</code> </p>*/
	private final Map<VideoObj, Record> data = new HashMap<VideoObj, Record>();

	/**
	 * Default constructor.
	 */
	InventorySet() { }

	/**
	 * Return the number of Records.
	 */
	public int size() {
		return data.size();
	}

	/**
	 *  Return a copy of the record for a given Video; if not present, return <code>null</code>.
	 */
	public Record get(VideoObj v) {
        if (data.containsKey(v))
            return data.get(v).copy();
        else
            return null;
	}

	/**
	 * Return a copy of the records as a collection.
	 * Neither the underlying collection, nor the actual records are returned.
	 */
	public Collection<Record> toCollection() {
		// Recall that an ArrayList is a Collection.
		ArrayList<Record> copied = new ArrayList<>();
        for (Record rec : data.values()) {
        	copied.add(rec.copy());
        }
		return copied;
	}

	/**
	 * Add or remove copies of a video from the inventory.
	 * If a video record is not already present (and change is
	 * positive), a record is created.
	 * If a record is already present, <code>numOwned</code> is
	 * modified using <code>change</code>.
	 * If <code>change</code> brings the number of copies to be zero,
	 * the record is removed from the inventory.
	 * @param video the video to be added.
	 * @param change the number of copies to add (or remove if negative).
	 * @throws IllegalArgumentException if video null, change is zero,
	 *  if attempting to remove more copies than are owned, or if
	 *  attempting to remove copies that are checked out.
	 * <p><b>Postcondition:</b> changes the record for the video</p>
	 */
	public void addNumOwned(VideoObj video, int change)
                            throws IllegalArgumentException {
        // Exception: if video null
        if (video == null)
            throw new IllegalArgumentException();
        // Exception: if change is zero
        if (change == 0)
            throw new IllegalArgumentException();

        // Is record NOT present?
        if (!data.containsKey(video)) {
            // if change > 0, then create new record; otherwise, exception
            if (change < 0)
                throw new IllegalArgumentException();
            else
                data.put(video, new Record(video, change, 0, 0));
        } else {
            // record IS present
            // Exception: if attempting to remove more copies than are owned
            if (data.get(video).numOwned + change < 0)
                throw new IllegalArgumentException();
            // Exception: if attempting to remove copies that are checked out
            if (data.get(video).numOwned - data.get(video).numOut + change < 0)
                throw new IllegalArgumentException();

            data.get(video).numOwned += change;

            // If number of copies becomes zero, record removed from inventory
            if (data.get(video).numOwned == 0)
                data.remove(video);
        }
	}

	/**
	 * Check out a video.
	 * @param video the video to be checked out.
	 * @throws IllegalArgumentException if video has no record or numOut
	 * equals numOwned.
	 * <p><b>Postcondition:</b> changes the record for the video</p>
	 */
	public void checkOut(VideoObj video) throws IllegalArgumentException {
        // no record found
        if (!data.containsKey(video))
            throw new IllegalArgumentException();
        // no copies available
        if (data.get(video).numOut == data.get(video).numOwned)
            throw new IllegalArgumentException();

        data.get(video).numOut += 1;
	}

	/**
	 * Check in a video.
	 * @param video the video to be checked in.
	 * @throws IllegalArgumentException if video has no record or numOut
	 * non-positive.
	 * <p><b>Postcondition:</b> changes the record for the video</p>
	 */
	public void checkIn(VideoObj video) throws IllegalArgumentException {
        // no record found
        if (!data.containsKey(video))
            throw new IllegalArgumentException();

        // numOut must be positive to check in.
        if (data.get(video).numOut <= 0)
            throw new IllegalArgumentException();

        data.get(video).numOut -= 1;
	}

	/**
	 * Remove all records from the inventory.
	 * <p><b>Postcondition:</b> <code>size() == 0</code></p>
	 */
	public void clear() {
		data.clear();
	}

	/**
	 * Return the contents of the inventory as a string.
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("Database:\n");
		for (Record r : data.values()) {
			buffer.append("  ");
			buffer.append(r);
			buffer.append("\n");
		}
		return buffer.toString();
	}

}
