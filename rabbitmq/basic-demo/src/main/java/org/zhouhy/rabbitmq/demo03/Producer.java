package org.zhouhy.rabbitmq.demo03;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
	
	private static final String Exchange_Name="rabbit:mq03:exchange:e01";
	
	private static final String Queue_Name_01="rabbit:mq03:queue:q01";
	private static final String Queue_Name_02="rabbit:mq03:queue:q02";
	
	private static final String Routing_Key_01="rabbit:mq03:routing:key:r01";
	private static final String Routing_Key_02="rabbit:mq03:routing:key:r02";
	private static final String Routing_Key_03="rabbit:mq03:routing:key:r03";
	
	private static final String Routing_Key_04="rabbit:mq03:routing:key:r04";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("127.0.0.1");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
	    
	    channel.exchangeDeclare(Exchange_Name, BuiltinExchangeType.DIRECT);
	    
	    channel.queueDeclare(Queue_Name_01, true, false, false, null);
	    channel.queueBind(Queue_Name_01, Exchange_Name, Routing_Key_01);
	    
	    channel.queueDeclare(Queue_Name_02, true, false, false, null);
	    channel.queueBind(Queue_Name_02, Exchange_Name, Routing_Key_02);
	    channel.queueBind(Queue_Name_02, Exchange_Name, Routing_Key_03);
	    
	    channel.queueBind(Queue_Name_02, Exchange_Name, Routing_Key_04);
	    
	    String message01 = "directExchange-publish我的消息-r01";
	    String message02 = "directExchange-publish我的消息-r02";
	    String message03 = "directExchange-publish我的消息-r03";
	    String message04 = "directExchange-publish我的消息-r04";
	    
	    channel.basicPublish(Exchange_Name, Routing_Key_01, null, message01.getBytes("UTF-8"));
	    channel.basicPublish(Exchange_Name, Routing_Key_02, null, message02.getBytes("UTF-8"));
	    channel.basicPublish(Exchange_Name, Routing_Key_03, null, message03.getBytes("UTF-8"));
	    channel.basicPublish(Exchange_Name, Routing_Key_04, null, message04.getBytes("UTF-8"));
	    
	    
	    System.out.println("生产者发送消息成功---> ");
	    channel.close();
	    connection.close();
	}
}
