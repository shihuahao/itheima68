package cn.e3.activeMQ;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class SpringMQ {

	/**
	 * 需求：mq整个spring发送
	 * 模式：点对点
	 */
	@Test
	public void sendMessageWithPTPWithSpring(){
		//加载spring配置文件
		ApplicationContext app = new ClassPathXmlApplicationContext("classpath*:spring/activemq-provider.xml");
		//获取对象 使用模板对象发送消息
		JmsTemplate jmsTemplate = app.getBean(JmsTemplate.class);
		
		//获取消息发送目的地
		Destination destination = app.getBean(ActiveMQQueue.class);
		//发送消息
		jmsTemplate.send(destination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				//发送
				TextMessage message = session.createTextMessage("王轩不穿袜");
				
				return message;
			}
		});
	}
	
	
	/**
	 * 需求：mq整个spring发送
	 * 模式：订阅模式
	 */
	@Test
	public void sendMessageWithPSWithSpring(){
		//加载spring配置文件
		ApplicationContext app = new ClassPathXmlApplicationContext("classpath*:spring/activemq-provider.xml");
		//获取对象 使用模板对象发送消息
		JmsTemplate jmsTemplate = app.getBean(JmsTemplate.class);
		
		//获取消息发送目的地
		Destination destination = app.getBean(ActiveMQTopic.class);
		//发送消息
		jmsTemplate.send(destination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				//发送
				TextMessage message = session.createTextMessage("王轩不穿袜！");
				
				return message;
			}
		});
	}
}
