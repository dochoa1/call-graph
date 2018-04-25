/*
 * Sample Java 8 code to be parsed by parseJava.py
 */

public class CallingMethodsInSameClass {

	public static void main(String[] args) {
		printOne();
		printOne();
		printTwo();
	}

	private static void printOne() {
		System.out.println("Hello World");
	}

	private static void printTwo() {
		printOne();
		printOne();
	}
}
