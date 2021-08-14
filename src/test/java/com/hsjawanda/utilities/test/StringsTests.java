package com.hsjawanda.utilities.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hsjawanda.utilities.base.Holdall;
import com.hsjawanda.utilities.base.Strings;


public class StringsTests {

	private final String divider = ":::";

	@Test
	public void endsWith_AtEnd() {
		StringBuilder sb = new StringBuilder(40).append("a").append(this.divider);
		assertTrue(Strings.endsWith(sb, this.divider));
	}

	@Test
	public void endsWith_ContainsOnly() {
		StringBuilder sb = new StringBuilder(40).append(this.divider);
		assertTrue(Strings.endsWith(sb, this.divider));
	}

	@Test
	public void endsWith_DoesntContain() {
		StringBuilder sb = new StringBuilder(40).append("a");
		assertFalse(Strings.endsWith(sb, this.divider));
	}

	@Test
	public void endsWith_NotAtEnd() {
		StringBuilder sb = new StringBuilder(40).append("a").append(this.divider).append("b");
		assertFalse(Strings.endsWith(sb, this.divider));
	}

	@Test
	public void showCause() {
		Exception e = new RuntimeException("Top-level exception",
				new IllegalArgumentException("Level 1 exception", new NullPointerException("Level 2 exception")));
		System.out.println(Holdall.showCause(e));
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

}
