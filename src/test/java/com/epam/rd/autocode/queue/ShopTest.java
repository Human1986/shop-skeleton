package com.epam.rd.autocode.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.epam.rd.autocode.queue.Shop;
import com.epam.rd.autocode.queue.CashBox.State;

import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.TypeFilter;

/**
 * @author D. Kolesnikov
 */
public class ShopTest {

	private Shop shop;
	
	@BeforeEach
	void setUp() {
		Buyer.resetNames();
		shop = new Shop(5);
	}
	
	@Test
	void test1() {
		shop.setCashBoxState(0, State.ENABLED);

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		shop.setCashBoxState(2, State.ENABLED);
		shop.tact();

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		shop.setCashBoxState(4, State.ENABLED);
		shop.tact();
		
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		String actual = getState(shop);
		String expected = "+DG-+E-+F";
		assertEquals(expected, actual);
	}

	@Test
	void test2() {
		shop.setCashBoxState(0, State.ENABLED);

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		shop.setCashBoxState(2, State.ENABLED);

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		shop.setCashBoxState(1, State.ENABLED);
		shop.setCashBoxState(3, State.ENABLED);
		shop.tact();

		String actual = getState(shop);
		String expected = "+BC+ED+GH+I-";
		assertEquals(expected, actual);
	}

	@Test
	void test3() {
		shop.setCashBoxState(0, State.ENABLED);

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		shop.setCashBoxState(2, State.ENABLED);

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		shop.setCashBoxState(2, State.IS_CLOSING);
		shop.tact();

		String actual = getState(shop);
		String expected = "+BCDE-|GHI--";
		assertEquals(expected, actual);
	}

	@Test
	void test4() {
		shop.setCashBoxState(0, State.ENABLED);

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		shop.setCashBoxState(2, State.ENABLED);

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		shop.setCashBoxState(2, State.IS_CLOSING);

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		shop.tact();

		shop.setCashBoxState(3, State.ENABLED);
		shop.tact();
		
		String actual = getState(shop);
		String expected = "+CDE-|HI+MLK-";
		assertEquals(expected, actual);
	}

	@Test
	void test5() {
		shop.setCashBoxState(0, State.ENABLED);

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		shop.setCashBoxState(2, State.ENABLED);

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		shop.setCashBoxState(2, State.IS_CLOSING);

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		shop.tact();

		shop.setCashBoxState(3, State.ENABLED);
		shop.tact();

		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());
		shop.addBuyer(Buyer.nextBuyer());

		String actual = getState(shop);
		String expected = "+CDENP-|HI+MLKOQ-";
		assertEquals(expected, actual);
	}
	
	@Test
	void complianceTestLambdaExpressionsAreRestrictedForUsing() {
		Stream.of(Shop.class)
			.map(Class::getDeclaredMethods)
			.flatMap(Stream::of)
			.filter(m -> Modifier.isStatic(m.getModifiers()))
			.filter(m -> Modifier.isPrivate(m.getModifiers()))
			.map(Method::getName)
			.filter(name -> name.contains("lambda$"))
			.findAny()
			.ifPresent(m -> 
					fail(() -> "Using of lambda expressions is restricted: " + m));
	}

	@Test
	void appShouldUseOnlyOptionalFromJavaUtilPackage() {
		SpoonAPI spoon = new Launcher();
		spoon.addInputResource("src/main/java/");
		spoon.buildModel();

		spoon.getModel()
			.getElements(new TypeFilter<>(CtTypeReference.class))
			.stream()
			.filter(r -> r.toString().startsWith("java.util.stream"))
			.map(CtTypeReference::getQualifiedName)
			.findAny()
			.ifPresent(name -> 
					fail(() -> "Using of stream API is restricted: " + name));
	}
	
	@Test
	void addBuyerShouldAddBuyerToProperCashBoxWhenThereAreMoreThanOneShortestQueues() {
		shop = new Shop(3);
		for (int j = 0; j < 3; j++) {
			shop.getCashBox(j).setState(State.ENABLED);
		}

		shop.addBuyer(Buyer.nextBuyer());
		assertEquals(shop.getCashBox(0).getQueue().getLast().toString(), "A");

		shop.addBuyer(Buyer.nextBuyer());
		assertEquals(shop.getCashBox(1).getQueue().getLast().toString(), "B");

		shop.addBuyer(Buyer.nextBuyer());
		assertEquals(shop.getCashBox(2).getQueue().getLast().toString(), "C");

		shop.addBuyer(Buyer.nextBuyer());
		assertEquals(shop.getCashBox(0).getQueue().getLast().toString(), "D");
	}
	
	private static String getState(Shop shop) {
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < shop.getCashBoxCount(); j++) {
			CashBox cashBox = shop.getCashBox(j);
			State s = cashBox.getState();
			sb.append(s == State.ENABLED ? '+' : (s == State.DISABLED ? '-' : '|'));
			for (Buyer b : shop.getCashBox(j).getQueue()) {
				sb.append(b);
			}
		}
		return sb.toString();
	}

}
