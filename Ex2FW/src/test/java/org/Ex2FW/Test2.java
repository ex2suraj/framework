package org.Ex2FW;

import java.io.IOException;
import org.testng.annotations.Test;

public class Test2 extends base{

	@Test
	public void FirstRun() throws IOException{
		driver = initializeDriver();
		System.out.println("value of Test 2 is OK");
		driver.get(prop.getProperty(urlname));
		
	}
}
