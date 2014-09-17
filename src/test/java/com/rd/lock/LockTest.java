package com.rd.lock;

import java.util.concurrent.atomic.AtomicInteger;

public class LockTest {
	private int count = 0;

	private AtomicInteger l = new AtomicInteger();

	public static void main(String[] args) {
		new LockTest().test();
	}

	private void test() {
		TenderThread t1 = new TenderThread("a001", 1, 10);
		TenderThread t2 = new TenderThread("a101", 100, -100);
		TenderThread t3 = new TenderThread("a001", 1, 20);

		t1.start();
		t2.start();
		t3.start();
	}

	class TenderThread extends Thread {
		private String username;
		private String lock = "lock";
		private int age;
		private int data;

		private TenderThread(String username, int age, int data) {
			this.username = username;
			this.age = age;
			this.data = data;
		}

		public void run() {
			int index = LockTest.this.l.incrementAndGet();
			System.out.println(this.username + " is " + this.age + ",count="
					+ LockTest.this.count);
			LockTest.this.count += this.data;
			try {
				Thread.sleep(500L);
			} catch (InterruptedException localInterruptedException) {
			}
			System.out.println(this.username + " is " + this.age + ",count="
					+ LockTest.this.count);
		}
	}
}