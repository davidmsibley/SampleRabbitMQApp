package gov.usgs.cida.samplerabbitmqapp.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dmsibley
 */
public class Receive implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(Receive.class);

	private final String host;
	private final String queue;
	private final String username;
	private final String password;

	public Receive() {
		this.host = Common.HOST_NAME;
		this.queue = Common.QUEUE_NAME;
		this.username = Common.USERNAME;
		this.password = Common.PASSWORD;
	}

	public Receive(String host, String queue, String username, String password) {
		this.host = host;
		this.queue = queue;
		this.username = username;
		this.password = password;
	}
	
	@Override
	public void run() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		factory.setUsername(username);
		factory.setPassword(password);
		Connection connection = null;
		Channel channel = null;
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();

			channel.queueDeclare(queue, false, false, false, null);
			log.info(" [*] Waiting for messages...");
			
			QueueingConsumer consumer = new QueueingConsumer(channel);
			channel.basicConsume(queue, true, consumer);
			
			while (true) {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody());
				log.info(" [x] Received '" + message + "'");
			}
		} catch (InterruptedException e) {
			log.info(" [*] Receive Interrupted");
		} catch (Exception e) {
			log.error("Could not receive messages.", e);
		} finally {
			log.info(" [*] Receive DONE");
		}
	}

}
