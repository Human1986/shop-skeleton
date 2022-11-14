package com.epam.rd.autocode.queue;

/**
 * @author D. Kolesnikov
 */
public final class Byer {
	
	private static char nextName; 
	
	static {
		resetNames();
	}
	
	public static void resetNames() {
		nextName = 'A'; 
	}

	public static Byer nextByer() {
		return new Byer(nextName++);
	}
	
	private final char name;
	
	private Byer(char name) {
		this.name = name;
	}
	
	@Override
	public final String toString() {
		return String.valueOf(name);
	}
	
}
