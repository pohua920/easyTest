package cn.com.sinosoft.ims.sync;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BCTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "/spring/BCjmsconfig.xml" });

		// BCMessageProducer producer = (BCMessageProducer) context
		// .getBean("messageProducer");
		// producer.send("江苏公司===============Hello World!!!");
	}
}
