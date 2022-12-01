import static org.junit.Assert.*;
import org.junit.*;
import java.lang.reflect.*;
import java.util.*;

public class BinaryIdentityPublicTest {
	// ========== SYSTEM ==========
	protected static final String EX_NAME_BinaryIdentity = "BinaryIdentity";
	// --------------------
	// --------------------

	// ========== TEST-DATA ==========
	private static final Random RND = new Random(4711_0815_666L);

	// ========== CHECK INTESTINES ==========
	@Test(timeout = 666)
	public void pubTest__getIdentity__Intestines__IF_THIS_VERY_IMPORTANT_TEST_FAILS_THEN_YOU_WILL_GET_NO_POINTS_AT_ALL() {
		Class<?> clazz = BinaryIdentity.class;
		assertTrue(clazz + " must be public!", Modifier.isPublic(clazz.getModifiers()));
		assertFalse(clazz + " must not be abstract!", Modifier.isAbstract(clazz.getModifiers()));
		assertFalse(clazz + " must not be an annotation!", clazz.isAnnotation());
		assertFalse(clazz + " must not be an enumeration!", clazz.isEnum());
		assertFalse(clazz + " must not be an interface!", clazz.isInterface());
		assertSame(clazz + " must extend a certain super-class!", Object.class, clazz.getSuperclass());
		assertEquals(clazz + " must implement a certain number of interfaces!", 1, clazz.getInterfaces().length);
		assertSame(clazz + " must implement certain interfaces!", BinaryIdentityInterface.class, clazz.getInterfaces()[0]);
		assertEquals(clazz + " must declare a certain number of inner annotations!", 0, clazz.getDeclaredAnnotations().length);
		assertEquals(clazz + " must declare a certain number of inner classes!", 0, getDeclaredClasses(clazz).length);
		Field[] fields = getDeclaredFields(clazz);
		assertEquals(clazz + " must declare a certain number of fields!", 0, fields.length);
		Constructor<?>[] constructors = getDeclaredConstructors(clazz);
		assertEquals(clazz + " must declare a certain number of constructors (possibly including default constructor)!", 1, constructors.length);
		for (Constructor<?> constructor : constructors) {
			assertTrue(constructor + " - constructor must be public!", Modifier.isPublic(constructor.getModifiers()));
			assertEquals(constructor + " - constructor must have a certain number of parameters!", 0, constructor.getParameterTypes().length);
		}
		Method[] methods = getDeclaredMethods(clazz), interfaceMethods = getDeclaredMethods(BinaryIdentityInterface.class);
		assertTrue(clazz + " must declare a certain number of methods!", methods.length > 0);
		for (Method method : methods) {
			if (method.getName().equals(interfaceMethods[0].getName()) && method.getParameterTypes().length == interfaceMethods[0].getParameterTypes().length && method.getParameterTypes()[0] == interfaceMethods[0].getParameterTypes()[0]) {
				assertTrue(method + " - method must be public!", Modifier.isPublic(method.getModifiers()));
			} else {
				assertTrue(method + " - method must be private!", Modifier.isPrivate(method.getModifiers()));
			}
			assertFalse(method + " - method must not be abstract!", Modifier.isAbstract(method.getModifiers()));
			assertFalse(method + " - method must not be static!", Modifier.isStatic(method.getModifiers()));
		}
	}

	// ========== PUBLIC TEST ==========
	@Test(timeout = 11666)
	public void pubTest__getIdentity__sheet_and_random__IF_THIS_QUITE_IMPORTANT_TEST_FAILS_THEN_YOU_WILL_MOST_PROBABLY_GET_NO_POINTS_AT_ALL() {
		for (int pass = 0; pass < 20; pass++) {
			// ----- sheet A ----------
			BinaryIdentityPublicTest.check__getIdentity("Failed on A.", new int[]{-7, -3, -1, 0, 1, 4, 6, 8});
			// ----- sheet B ----------
			BinaryIdentityPublicTest.check__getIdentity("Failed on B.", new int[]{-1, 1, 2, 5, 7, 8, 10, 12});
			// ----- null and empty ----------
			BinaryIdentityPublicTest.check__getIdentity("Failed on null.", null);
			BinaryIdentityPublicTest.check__getIdentity("Failed on empty array.", new int[]{});
			// ----- random, no identity ----------
			BinaryIdentityPublicTest.check__getIdentity("Failed on random array with no identity.", BinaryIdentityPublicTest.generateRandomArray(5 + RND.nextInt(42), 0));
			// ----- random, one identity ----------
			BinaryIdentityPublicTest.check__getIdentity("Failed on small random array with exactly one identity.", BinaryIdentityPublicTest.generateRandomArray(5 + RND.nextInt(42), 1));
			BinaryIdentityPublicTest.check__getIdentity("Failed on big random array with exactly one identity.", BinaryIdentityPublicTest.generateRandomArray(4711_666 + RND.nextInt(666), 1));
		}
	}

	@Test//timeout = 22666)
	public void pubTest__getIdentity__random_huge_several_identities__IF_THIS_QUITE_IMPORTANT_TEST_FAILS_THEN_YOU_WILL_MOST_PROBABLY_GET_NO_POINTS_AT_ALL() {
		for (int pass = 0; pass < 8; pass++) {
			BinaryIdentityPublicTest.check__getIdentity("Failed on huge random array with several identities.", BinaryIdentityPublicTest.generateRandomArray(4711_0815 + RND.nextInt(666), 1 + RND.nextInt(10)));
		}
	}

	// ========== HELPER ==========
	protected static void check__getIdentity(String message, int[] array) {
		BinaryIdentityInterface binaryIdentity = new BinaryIdentity();
		BigBrother bigBrother = new BigBrother(array);
		boolean expectIAE = array == null || array.length == 0, expectNSEE = true;
		for (int i = 0; array != null && i < array.length; i++) {
			expectNSEE &= array[i] != i;
		}
		try {
			int actual = binaryIdentity.getIdentity(bigBrother);
			if (expectIAE) {
				fail(message + " Expected " + IllegalArgumentException.class);
			} else if (expectNSEE) {
				fail(message + " Expected " + NoSuchElementException.class);
			} else {
				assertEquals(message + " There is no identity at this index.", actual, array[actual]);
				assertTrue(message + " Number of calls to getLength() is too high: " + bigBrother.numberOfCallsTo_getLength, bigBrother.numberOfCallsTo_getLength <= 1);
				long expected_numberOfCallsTo_getValue = (long) (1 + Math.log(array.length) / Math.log(2));
				assertTrue(message + " Number of calls to getValue() is too high: " + "was " + bigBrother.numberOfCallsTo_getValue + " but " + "expected <= " + expected_numberOfCallsTo_getValue, bigBrother.numberOfCallsTo_getValue <= expected_numberOfCallsTo_getValue);
			}
		} catch (IllegalArgumentException ia) {
			if (!expectIAE) {
				fail(message + " Did NOT expect " + ia);
			}
		} catch (NoSuchElementException nse) {
			if (!expectNSEE) {
				fail(message + " Did NOT expect " + nse);
			}
		}
	}

	protected static int[] generateRandomArray(int length, int numberOfIdentities) {
		int[] array = new int[length];
		if (0 < numberOfIdentities) {
			numberOfIdentities = Math.min(numberOfIdentities, length);
			int idxOfFirstIdentity = RND.nextInt(1 + length - numberOfIdentities);
			int idxOfLastIdentity = idxOfFirstIdentity + numberOfIdentities - 1;
			for (int i = idxOfFirstIdentity; i <= idxOfLastIdentity; i++) {
				array[i] = i;
			}
			for (int i = idxOfFirstIdentity - 1; i >= 0; i--) {
				array[i] = i == array[i + 1] - 1 ? array[i + 1] - 2 - RND.nextInt(21) : //
						array[i + 1] - 1 - RND.nextInt(21);
			}
			for (int i = idxOfLastIdentity + 1; i < length; i++) {
				array[i] = i == array[i - 1] + 1 ? array[i - 1] + 2 + RND.nextInt(21) : //
						array[i - 1] + 1 + RND.nextInt(21);
			}
		} else {
			for (int i = 0; i < length; i++) {
				array[i] = i == 0 ? (-666 + RND.nextInt(333)) : //
						i == array[i - 1] + 1 ? array[i - 1] + 2 + RND.nextInt(21) : //
								array[i - 1] + 1 + RND.nextInt(21);
			}
		}
		return array;
	}

	// ========== HELPER: BigBrother ==========
	protected static class BigBrother implements BinaryIdentityArray {
		private final int[] array;
		private long numberOfCallsTo_getLength = 0, numberOfCallsTo_getValue = 0;

		private BigBrother(int[] array) {
			this.array = array;
		}

		@Override
		public int getLength() {
			numberOfCallsTo_getLength++;
			return array == null ? 0 : array.length;
		}

		@Override
		public int getValue(int index) {
			numberOfCallsTo_getValue++;
			return array[index];
		}
	}

	// ========== HELPER: Intestines ==========
	// @AuD-STUDENT: DO NOT USE REFLECTION IN YOUR OWN SUBMISSION!
	private static Class<?>[] getDeclaredClasses(Class<?> clazz) {
		java.util.List<Class<?>> declaredClasses = new java.util.ArrayList<>();
		for (Class<?> c : clazz.getDeclaredClasses()) {
			if (!c.isSynthetic()) {
				declaredClasses.add(c);
			}
		}
		return declaredClasses.toArray(new Class[0]);
	}

	private static Field[] getDeclaredFields(Class<?> clazz) {
		java.util.List<Field> declaredFields = new java.util.ArrayList<>();
		for (Field f : clazz.getDeclaredFields()) {
			if (!f.isSynthetic()) {
				declaredFields.add(f);
			}
		}
		return declaredFields.toArray(new Field[0]);
	}

	private static Constructor<?>[] getDeclaredConstructors(Class<?> clazz) {
		java.util.List<Constructor<?>> declaredConstructors = new java.util.ArrayList<>();
		for (Constructor<?> c : clazz.getDeclaredConstructors()) {
			if (!c.isSynthetic()) {
				declaredConstructors.add(c);
			}
		}
		return declaredConstructors.toArray(new Constructor[0]);
	}

	private static Method[] getDeclaredMethods(Class<?> clazz) {
		java.util.List<Method> declaredMethods = new java.util.ArrayList<>();
		for (Method m : clazz.getDeclaredMethods()) {
			if (!m.isBridge() && !m.isSynthetic()) {
				declaredMethods.add(m);
			}
		}
		return declaredMethods.toArray(new Method[0]);
	}
}