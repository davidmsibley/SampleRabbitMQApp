package gov.usgs.cida.samplerabbitmqapp.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dmsibley
 */
public class Send implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(Send.class);
	
	private final String host;
	private final String queue;
	private final String message;
	private final String username;
	private final String password;

	public Send() {
		this.host = Common.HOST_NAME;
		this.queue = Common.QUEUE_NAME;
		this.message = Common.MESSAGE_CONTENTS;
		this.username = Common.USERNAME;
		this.password = Common.PASSWORD;
	}

	public Send(String host, String queue, String message, String username, String password) {
		this.host = host;
		this.queue = queue;
		this.message = message;
		this.username = username;
		this.password = password;
	}
	
	@Override
	public void run() {
		Connection connection = null;
		Channel channel = null;
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(host);
			factory.setUsername(username);
			factory.setPassword(password);
			factory.setPort(5672);
			connection = factory.newConnection();
			channel = connection.createChannel();
			
//			channel.queueDeclare(queue, false, false, false, null);
			channel.basicPublish("", queue, null, message.getBytes());
			log.info(" [x] Sent '" + message + "'");
		} catch (Exception e) {
			log.error("Could not run Send example.", e);
		} finally {
			if (null != channel) {
				try {
					channel.close();
				} catch (Exception e) {
					log.debug("Tried to safely close channel.", e);
				}
			}
			if (null != connection) {
				try {
					connection.close();
				} catch (Exception e) {
					log.debug("Tried to safely close connection.", e);
				}
			}
			log.info(" [*] Send DONE");
		}
	}
}
