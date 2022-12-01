import java.util.NoSuchElementException;

public interface BinaryIdentityInterface {
	/**
	 * This method searches the specified array for an <i>identity</i> and returns the index of any arbitrary <i>identity</i> if at least one exists.
	 * <p>An <i>identity</i> is	a value of the specified array that equals to its index within this array.</p>
	 * <b>Note</b> that this method must be <b>recursive</b> and have a <b>logarithmic runtime in O(n)</b> w.r.t. the length n of the specified array.
	 *
	 * @param array the array to search for an identity
	 * @return the index of any arbitrary identity if it exists
	 * @throws IllegalArgumentException if the specified array is null or empty
	 * @throws NoSuchElementException   if the specified array contains at least one element but no identity
	 */
	int getIdentity(BinaryIdentityArray array) throws IllegalArgumentException, NoSuchElementException;
}