package cn.e3.activeMQ;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class ReceiveMessage {

	/**
	 * 需求：接收消息
	 * 模式：点对点模式
	 * 方式：同步模式
	 * @throws Exception 
	 */
	@Test
	public void receiveMessageWithTONGBU() throws Exception{
		
		//指定消息服务器地址：ip 协议 端口
		String brokerURL = "tcp://192.168.213.129:61616";
		//创建消息工厂对象，连接activeMQ消息服务器
		ConnectionFactory cf = new ActiveMQConnectionFactory(brokerURL);
		//获取一个连接
		Connection connection = cf.createConnection();
		//开启连接
		connection.start();
		
		//从连接中获取一个会话对象session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//指定接收消息地址，指定的地址和创建发送消息空间地址一致
		Queue queue = session.createQueue("myqueue");
		//指定消息接收者
		MessageConsumer consumer = session.createConsumer(queue);
		
		//获取消息
		Message message = consumer.receive(1000);
		if(message instanceof TextMessage){
			TextMessage m = (TextMessage) message;
			System.out.println("点对点接收消息："+m.getText());
		}
		
		//关闭资源
		consumer.close();
		session.close();
		connection.close();
		
	}
	
	/**
	 * 需求：接收消息
	 * 模式：点对点模式
	 * 方式：异步模式
	 * @throws Exception 
	 */
	@Test
	public void receiveMessageWithYIBU() throws Exception{
		
		//指定消息服务器地址：ip 协议 端口
		String brokerURL = "tcp://192.168.213.129:61616";
		//创建消息工厂对象，连接activeMQ消息服务器
		ConnectionFactory cf = new ActiveMQConnectionFactory(brokerURL);
		//获取一个连接
		Connection connection = cf.createConnection();
		//开启连接
		connection.start();
		
		//从连接中获取一个会话对象session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//指定接收消息地址，指定的地址和创建发送消息空间地址一致
		Queue queue = session.createQueue("myqueue");
		//指定消息接收者
		MessageConsumer consumer = session.createConsumer(queue);
		
		//异步模式接收消息（监听模式）
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				if(message instanceof TextMessage){
					TextMessage m = (TextMessage) message;
					try {
						System.out.println("点对点接收消息："+m.getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		//模拟服务，端口一直阻塞
		System.in.read();
		
		//关闭资源
		consumer.close();
		session.close();
		connection.close();
		
	}
	
	/**
	 * 需求：接收消息
	 * 模式：订阅模式
	 * 方式：异步模式
	 * @throws Exception 
	 */
	@Test
	public void receiveMessageWithYIBUPS() throws Exception{
		
		//指定消息服务器地址：ip 协议 端口
		String brokerURL = "tcp://192.168.213.129:61616";
		//创建消息工厂对象，连接activeMQ消息服务器
		ConnectionFactory cf = new ActiveMQConnectionFactory(brokerURL);
		//获取一个连接
		Connection connection = cf.createConnection();
		//开启连接
		connection.start();
		
		//从连接中获取一个会话对象session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//指定接收消息地址，指定的地址和创建发送消息空间地址一致
		Topic topic = session.createTopic("mytopic");
		//指定消息接收者
		MessageConsumer consumer = session.createConsumer(topic);
		
		//异步模式接收消息（监听模式）
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				if(message instanceof TextMessage){
					TextMessage m = (TextMessage) message;
					try {
						System.out.println("订阅模式接收消息："+m.getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		//模拟服务，端口一直阻塞
		System.in.read();
		
		//关闭资源
		consumer.close();
		session.close();
		connection.close();
		
	}
}
