package org.zhouhy.rabbitmq.demo02;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 它不向队列里发送消息而是向exchange里发送消息
 * */
public class Producer {
	private static final String Exchange_Name="rabbit:mq02:exchange:e01";
	
	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("127.0.0.1");
	    try {
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			
			//TODO：fanout-exchange无意识分发消息模型
			/**
			 * 这种Fanout模式不处理路由键，你需要简单的讲队列绑定到exchange上，
			 * 一个发送到exchange的消息都会被转发到与该exchange绑定的所有队列上。
			 * 很像广播子网，每台子网内的主机都获得了一份复制的消息。
			 * */
		    channel.exchangeDeclare(Exchange_Name, BuiltinExchangeType.FANOUT);
		    
		    String message = "fanoutExchange-publish的消息";
		    channel.basicPublish(Exchange_Name, "", null, message.getBytes("UTF-8"));
		    
		    System.out.println("生产者发送消息成功---> ");
		    channel.close();
		    connection.close();		    
		} catch (TimeoutException | IOException e) {
			// TODO: handle exception
		}
	    
	    
	}
}
