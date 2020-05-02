package com.meta1203.overload;

import java.util.Vector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Overload {
	@Bean(name = "serverId")
	public int serverId() {
		return ((int)Math.floor(Math.random() * 1000));
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Overload.class, args);
	}
	
	private static boolean running = false;
	
	/**
	 * @param load percent of all cpu cores to use
	 * @param memory memory to use in MiB
	 * @param duration time in seconds to run
	 */
	public static boolean overload(double load, int memory, int duration) {
		if (running) return false;
		Runtime rt = Runtime.getRuntime();
		int cores = rt.availableProcessors();
		int maxMemory = (int)(rt.maxMemory()/1048576);
		int umem = memory > maxMemory ? maxMemory : memory;
		System.out.println(cores + " cores with " + maxMemory + " MiB");
		System.out.println("Using " + (load * 100) + "% CPU and " + umem + " MiB of memory for " + duration + " seconds");
		
		// run CPU
		for (int x = 0; x < cores; x++) {
			new Thread(() -> {
				try {
					long startTime = System.currentTimeMillis();
					while (System.currentTimeMillis() - startTime < duration * 1000) {
						if (System.currentTimeMillis() % 100 == 0) {
							Thread.sleep((long) Math.floor((1-load)*100));
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
		}

		new Thread(() -> {
			// consume memory
			Vector<byte[]> v = new Vector<byte[]>();
			for (int x = 0; x < umem; x++) {
				byte b[] = new byte[1048576];
				v.add(b);
			}
			System.out.println(v.size() + " MiB");
			// sleep for the duration
			try {
				Thread.sleep((long) duration*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// clear memory
			v.removeAllElements();
			v = null;
			rt.gc();
		}).start();
		
		running = true;
		return true;
	}
}
