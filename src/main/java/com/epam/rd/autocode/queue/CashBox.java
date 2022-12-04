package com.epam.rd.autocode.queue;

import java.util.Deque;
import java.util.LinkedList;


public class CashBox {
	
	private int number;
	
	private Deque<Buyer> byers;
	
	private State state;
	
	public enum State {
		ENABLED, DISABLED, IS_CLOSING
	}
	
	public CashBox(int number) {
	}

	public Deque<Buyer> getQueue() {
		return null;
	}

	public Buyer serveBuyer() {
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

	public void addLast(Buyer byer) {
	}

	public Buyer removeLast() {
		return null;
	}
	
	@Override
	public String toString() {
		return null;
	}

}
