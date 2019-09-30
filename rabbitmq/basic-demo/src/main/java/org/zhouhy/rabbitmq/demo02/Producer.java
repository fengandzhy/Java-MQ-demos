package org.zhouhy.rabbitmq.demo02;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * ����������﷢����Ϣ������exchange�﷢����Ϣ
 * */
public class Producer {
	private static final String Exchange_Name="rabbit:mq02:exchange:e01";
	
	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("127.0.0.1");
	    try {
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			
			//TODO��fanout-exchange����ʶ�ַ���Ϣģ��
			/**
			 * ����Fanoutģʽ������·�ɼ�������Ҫ�򵥵Ľ����а󶨵�exchange�ϣ�
			 * һ�����͵�exchange����Ϣ���ᱻת�������exchange�󶨵����ж����ϡ�
			 * ����㲥������ÿ̨�����ڵ������������һ�ݸ��Ƶ���Ϣ��
			 * */
		    channel.exchangeDeclare(Exchange_Name, BuiltinExchangeType.FANOUT);
		    
		    String message = "fanoutExchange-publish����Ϣ";
		    channel.basicPublish(Exchange_Name, "", null, message.getBytes("UTF-8"));
		    
		    System.out.println("�����߷�����Ϣ�ɹ�---> ");
		    channel.close();
		    connection.close();		    
		} catch (TimeoutException | IOException e) {
			// TODO: handle exception
		}
	    
	    
	}
}
