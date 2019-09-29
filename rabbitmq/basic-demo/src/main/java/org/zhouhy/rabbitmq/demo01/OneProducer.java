package org.zhouhy.rabbitmq.demo01;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class OneProducer {
	
	private static final String Queue_Name="rabbit:mq01:queue";
	
	public static void main(String[] args)  {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		try {
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put("abc","bcd");
			
			channel.queueDeclare(Queue_Name, true, false, false, map);
			String message = "�ҵĵ�һ����Ϣ-Hello World!";
			
			//������Ϣ
			channel.basicPublish("", Queue_Name, null, message.getBytes("UTF-8"));
			
			System.out.println("�����߷�����Ϣ�ɹ�---> ");

		    channel.close();
		    connection.close();
		} catch (TimeoutException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
