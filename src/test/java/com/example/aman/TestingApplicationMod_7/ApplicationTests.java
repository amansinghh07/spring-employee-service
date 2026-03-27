package com.example.aman.TestingApplicationMod_7;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ApplicationTests {
	@BeforeEach
	void setUp(){
		log.info("starting the method, setting up the configuration");
	}
	@AfterEach
	void tearDown(){
		log.info("Tearing down the method");
	}
/*	@Test
	void testNumberOne(){
		int a=5;
		int b=3;
		int result=addTwoNumbers(a,b);
//		Assertions.assertEquals(result,a);
*//*		Assertions.assertThat(result).isEqualTo(7)
				.isCloseTo(9, Offset.offset(1));*//*

		log.info("test one is running");
	}
	@Test
	void testNumberTwo(){
		log.info("test two is running");
		int a=5;
		int b=0;
		double result=divideTwoNumbers(a,b);
		System.out.println(result);
	}

	@Test
	void contextLoads() {
	}
	int addTwoNumbers(int a,int b){
		return a+b;
	}
	double divideTwoNumbers(int a,int b){
		try{
		 return 	(double) a/b;
		}
		catch(ArithmeticException e){
			log.error("Arithemetic exception occured"+e.getLocalizedMessage());
			throw new ArithmeticException(e.getLocalizedMessage());
		}
	}*/

}
