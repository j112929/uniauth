package com.dianrong.common.uniauth.server;

import java.io.IOException;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import lombok.extern.slf4j.Slf4j;

//@ComponentScan(basePackages= {"com.dianrong"})
//@ImportResource(value = {"classpath:/spring/spring-config.xml",
//		"classpath:/spring/dubbo-config.xml",
//		"classpath:/spring/spring-kafka-config.xml"})
//@PropertySource(factory=SampPropertySourceFactory.class,value = {"classpath*:*-${spring.profiles.active}.properties"})  
@Slf4j
public class UniauthServerBoot {
	private static ClassPathXmlApplicationContext context;

	public static void main(String[] args) throws IOException {
		context = new ClassPathXmlApplicationContext(new String[] { "classpath:/test/applicationContext-test.xml" });
		context.start();
		log.info("uniauth-server started Success");
	}

	public void stop() {
		try {
			if (context != null) {
				context.stop();
				context.close();
				context = null;
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		}
	}
}
