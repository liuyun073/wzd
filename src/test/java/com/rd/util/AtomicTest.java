package com.rd.util;

import java.io.File;
import java.io.FileFilter;
import java.io.PrintStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest {
	static long randomTime() {
		return (long) (Math.random() * 1000.0D);
	}

	public static void main(String[] args) {
		final BlockingQueue<File> queue = new LinkedBlockingQueue<File>(100);

		ExecutorService exec = Executors.newFixedThreadPool(5);
		File root = new File("D:\\ISO");

		final File exitFile = new File("");

		final AtomicInteger rc = new AtomicInteger();

		final AtomicInteger wc = new AtomicInteger();

		Runnable read = new Runnable() {
			
			public void run() {
				//scanFile(AtomicTest.this);
				scanFile(exitFile);
			}

			public void scanFile(File file) {
				if(!file.exists()) return;
				
				if (file.isDirectory()) {
					File[] files = file.listFiles(new FileFilter() {
						public boolean accept(File pathname) {
							return (pathname.isDirectory())
									|| (pathname.getPath().endsWith(".iso"));
						}
					});
					for (File one : files)
						scanFile(one);
				} else {
					try {
						int index = rc.incrementAndGet();
						System.out.println("Read0: " + index + " " + file.getPath());

						queue.put(file);
					} catch (InterruptedException localInterruptedException) {
					}
				}
			}
		};
		exec.submit(read);

		for (int index = 0; index < 4; ++index) {
			int num = index;
			
			Runnable write = new Runnable() {
				String threadName;

				public void run() {
					while (true)
						try {
							Thread.sleep(AtomicTest.randomTime());

							int index = wc.incrementAndGet();

							File file = (File) queue.take();

							if (file == exitFile) {
								queue.put(exitFile);
								return;
							}
							System.out.println(this.threadName + ": " + index + " " + file.getPath());
						} catch (InterruptedException localInterruptedException) {
						}
				}
			};
			exec.submit(write);
		}
		exec.shutdown();
	}
}