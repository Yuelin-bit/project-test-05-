package org.springframework.samples.petclinic.frontend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.*;

import com.mysql.cj.jdbc.Driver;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FrontendTest1 {

	WebDriver driver;

	@BeforeEach
	void setUp() {
		driver = new ChromeDriver();

	}

	@Test
	@Order(1)
	void aaa() {
		driver.get("http://localhost:8080");
		driver.findElement(By.className("glyphicon  glyphicon-search")).click();
	}

	@AfterEach
	void end() {
		driver.quit();
	}

}
