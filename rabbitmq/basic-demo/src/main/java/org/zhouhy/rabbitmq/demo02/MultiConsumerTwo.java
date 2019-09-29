package org.zhouhy.rabbitmq.demo02;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class MultiConsumerTwo {
	private static final String Exchange_Name="rabbit:mq02:exchange:e01";
	private static final String Queue_Name_02="rabbit:mq02:queue:q02";
	
	public static void main(String[] args) {
		try {
			ConnectionFactory factory = new ConnectionFactory();
		    factory.setHost("127.0.0.1");
		    Connection connection = factory.newConnection();
		    Channel channel = connection.createChannel();
		    
		    //TODO：fanout-exchange无意识分发消息模型
		    channel.exchangeDeclare(Exchange_Name, BuiltinExchangeType.FANOUT);
		    channel.queueDeclare(Queue_Name_02, true, false, false, null);
		    channel.queueBind(Queue_Name_02, Exchange_Name, "");
		    
		    Consumer consumer = new DefaultConsumer(channel) {
		      @Override
		      public void handleDelivery(String consumerTag, Envelope envelope,
		                                 AMQP.BasicProperties properties, byte[] body) throws IOException {
		        String message = new String(body, "UTF-8");
		        System.out.println("消费者2接收到消息成功---> "+message);
		      }
		    };
		    
		    channel.basicConsume(Queue_Name_02, true, consumer);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
