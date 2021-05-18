package org.Ex2FW;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiTest1 {

	@Test
	public void testResponsecode()
	{
	
	ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark.html");
	ExtentReports report = new ExtentReports();
	report.attachReporter(spark);
	
	ExtentTest mylog = report.createTest("New tc1");
	
	mylog.log(Status.INFO,"test case started");
	String URL = "http://od-api.newhomesource.com/api/v2/detail/community?partnerid=8664&commid=71540";
	Response resp = RestAssured.get(URL);
	mylog.log(Status.FAIL, "test is failed.");
	report.flush();
	int code = resp.getStatusCode();
	//String data = resp.getBody().prettyPrint();
	//System.out.println("Status is "+code+" > "+URL);
	ExtentTest mylog1 = report.createTest("New tc 2");
	mylog1.log(Status.INFO, "In progress");
	System.out.println("Response time in milisec :"+resp.getTime());
	Assert.assertEquals(code, 200);

	//System.out.println("Response is "+data);
	mylog1.log(Status.PASS, "test is passed.");
	report.flush();
	}

}
