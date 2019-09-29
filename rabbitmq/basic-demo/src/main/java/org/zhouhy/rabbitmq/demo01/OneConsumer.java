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
		        System.out.println("���Ѷ˽��ܵ���Ϣ---> "+message);
		      }
		    };
		    
		    /**
		     * 	1.������������Ϣ֮����Ҫackȷ�����ѣ�
				a.�ص���֪mq ĳ�������е� ��Ϣ�Ѿ�����consumer������ - ��������ã�mq�ǲ����ָ����queue����Ϣ�Ƹ����
				b.autoAck ��true-ȷ�����ѣ�false-û��ȷ�����ѣ���Ϣ���ɴ���queue����-�´�������ʱ mq�����·ַ�message
		     * */
		    channel.basicConsume(Queue_Name, true, consumer);
		} catch (TimeoutException | IOException e) {
			e.printStackTrace();
		}
	}
}
