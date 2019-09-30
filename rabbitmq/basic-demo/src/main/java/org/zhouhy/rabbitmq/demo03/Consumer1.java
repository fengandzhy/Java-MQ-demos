package org.zhouhy.rabbitmq.demo03;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Consumer1 {
	private static final String Exchange_Name="rabbit:mq03:exchange:e01";	
	private static final String Queue_Name_01="rabbit:mq03:queue:q01";
	private static final String Routing_Key_01="rabbit:mq03:routing:key:r01";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("127.0.0.1");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
	    
	    channel.exchangeDeclare(Exchange_Name, BuiltinExchangeType.DIRECT);
	    channel.queueDeclare(Queue_Name_01, true, false, false, null);
	    channel.queueBind(Queue_Name_01, Exchange_Name, Routing_Key_01);
	    
	    Consumer consumer = new DefaultConsumer(channel) {
	    	@Override
	    	public void handleDelivery(String consumerTag, Envelope envelope,
		                                 AMQP.BasicProperties properties, byte[] body) throws IOException {
	    		String message = new String(body, "UTF-8");
		        System.out.println("消费者1接收到消息成功---> "+message);
	    	}
	    };	    
	    channel.basicConsume(Queue_Name_01, true, consumer);	    
	}
}
