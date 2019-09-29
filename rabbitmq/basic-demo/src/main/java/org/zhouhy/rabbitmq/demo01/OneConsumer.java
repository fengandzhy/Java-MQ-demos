package org.zhouhy.rabbitmq.demo01;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
public class OneConsumer {
	private static final String Queue_Name="rabbit:mq01:queue";
	
	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("127.0.0.1");
	    
	    try {
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			channel.queueDeclare(Queue_Name, true, false, false, null);
			
			Consumer consumer = new DefaultConsumer(channel) {
			      @Override
		      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
		          throws IOException {
		        String message = new String(body, "UTF-8");
		        System.out.println("消费端接受到消息---> "+message);
		      }
		    };
		    
		    /**
		     * 	1.消费者消费消息之后需要ack确认消费：
				a.回调告知mq 某条队列中的 消息已经被我consumer消费了 - 如果不设置，mq是不会把指定的queue的消息推给你的
				b.autoAck ：true-确认消费，false-没有确认消费，消息依旧存在queue里面-下次再启动时 mq会重新分发message
		     * */
		    channel.basicConsume(Queue_Name, true, consumer);
		} catch (TimeoutException | IOException e) {
			e.printStackTrace();
		}
	}
}
