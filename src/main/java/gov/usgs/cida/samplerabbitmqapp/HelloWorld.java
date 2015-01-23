package gov.usgs.cida.samplerabbitmqapp;

import gov.usgs.cida.samplerabbitmqapp.helloworld.Send;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dmsibley
 */
public class HelloWorld {
	private static final Logger log = LoggerFactory.getLogger(HelloWorld.class);
	public static void main(String[] args) {
		String hostName = "10.0.1.11";
		List<Thread> threads = new ArrayList<Thread>();
		for (int i = 0; i < 50; i++) {
			Send send = new Send(hostName, "foo-queue", "durpa" + i, "test-user", "test-pass");
			threads.add(new Thread(send));
		}
		
//		Receive receive = new Receive(hostName, Common.QUEUE_NAME, Common.USERNAME, Common.PASSWORD);
//		Thread receiveThread = new Thread(receive);
		
		for (Thread thread : threads) {
			thread.start();
		}
		
//		receiveThread.start();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			log.info("HelloWorld interrupted");
		} finally {
			for (Thread thread : threads) {
				thread.interrupt();
			}
//			receiveThread.interrupt();
		}
		return;
	}
}
