import java.util.NoSuchElementException;

public class BinaryIdentity implements BinaryIdentityInterface {
    public BinaryIdentity() {
    }

    @Override
    public int getIdentity(BinaryIdentityArray array) throws IllegalArgumentException, NoSuchElementException {
        int length = array.getLength();
        if (array == null || length == 0) {
            throw new IllegalArgumentException();
        }

        return getIdentityHelper(array,0, length, length / 2);
    }

    private int getIdentityHelper(BinaryIdentityArray a, int begin,int length, int middle) {
        int currentValue = a.getValue(middle);
        if (middle == currentValue) {
            return middle;
       } else if (middle == 0 || middle ==length-1 ) {
            throw new NoSuchElementException();
       } else if (middle > currentValue) {
            return getIdentityHelper(a, middle,length, (middle + length) / 2);
        } else {
            return getIdentityHelper(a,begin,middle, (middle + begin) / 2);
        }

    }
}
