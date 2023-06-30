package com.victor.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.victor.entities.Enemy2;

class TestEnemy2 {

	@Test
	void test() {
		Enemy2 enemy2 = new Enemy2(0, 0, 0, 0, 0, null);
		assertEquals(false, enemy2.test());
	}
	
}
