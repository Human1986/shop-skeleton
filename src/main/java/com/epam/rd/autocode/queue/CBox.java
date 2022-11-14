package com.epam.rd.autocode.queue;

import java.util.Deque;
import java.util.LinkedList;


public class CBox {
	
	private int number;
	
	private Deque<Byer> byers;
	
	private State state;
	
	public enum State {
		ENABLED, DISABLED, IS_CLOSING
	}
	
	public CBox(int number) {
	}

	public Deque<Byer> getQueue() {
		return null;
	}

	public Byer serveByer() {
		return null;
	}

	public boolean inState(State state) {
		return false;
	}

	public boolean notInState(State state) {
		return false;
	}
	
	public void setState(State state) {
	}

	public State getState() {
		return null;
	}

	public void addLast(Byer byer) {
	}

	public Byer removeLast() {
		return null;
	}
	
	@Override
	public String toString() {
		return null;
	}

}
