package org.zhouhy.rabbitmq.demo01;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class OneProducer {
	
	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		try {
			Connection connection = factory.newConnection("127.0.0.1");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
