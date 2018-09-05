package org.stormnetdev.reporter;

import io.qameta.allure.Attachment;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import java.util.Set;

public class TestListener implements ITestListener{

    @Override
	public void onFinish(ITestContext context) {
		Set<ITestResult> failedTests = context.getFailedTests().getAllResults();
		for (ITestResult temp : failedTests) {
			ITestNGMethod method = temp.getMethod();
			if (context.getFailedTests().getResults(method).size() > 1) {
				failedTests.remove(temp);
			} else {
				if (context.getPassedTests().getResults(method).size() > 0) {
					failedTests.remove(temp);
				}
			}
		}
	}

	@Override
	public void onStart(ITestContext context) {
		System.out.println(Reporter.ANSI_CYAN_BOLD + context.getName() + Reporter.ANSI_RESET);

		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult test) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailure(ITestResult test) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTestSkipped(ITestResult test) {
			createAttachment();
		}

	@Attachment(value = "Detailed steps description", type = "text/html")
	public String createAttachment() {
		return Reporter.description;
	}
	
	@Override
	public void onTestStart(ITestResult test) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTestSuccess(ITestResult test) {
		// TODO Auto-generated method stub
	}

}
