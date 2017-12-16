package cn.e3.activeMQ;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMQ {

	/**
	 * 需求：spring整合mq接收消息
	 * @throws Exception 
	 */
	@Test
	public void receiveMessage() throws Exception{
		//加载spring配置文件
		ApplicationContext app = new ClassPathXmlApplicationContext("classpath*:spring/activemq-consumer.xml");
		//模拟服务
		System.in.read();
	}
}
