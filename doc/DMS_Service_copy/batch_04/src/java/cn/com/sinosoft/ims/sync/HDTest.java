package cn.com.sinosoft.ims.sync;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HDTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "/spring/HDjmsconfig.xml" });

		HDMessageProducer producer = (HDMessageProducer) context
				.getBean("messageProducer");
		// producer.send("总公司 ===============Hello World!!!");
	}
}
