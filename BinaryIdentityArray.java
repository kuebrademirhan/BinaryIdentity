/**
 * This class represents an array wrapped into an object.
 */
public interface BinaryIdentityArray {
	/**
	 * Returns the number of elements in this array.
	 *
	 * @return the number of elements in this array
	 */
	int getLength();

	/**
	 * Returns the element at the specified position in this array.
	 *
	 * @param index index of the element to return
	 * @return the element at the specified position in this array
	 */
	int getValue(int index);
}