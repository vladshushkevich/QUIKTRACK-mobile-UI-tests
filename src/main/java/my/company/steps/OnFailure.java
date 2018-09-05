package my.company.steps;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.stormnetdev.reporter.Reporter;
import org.stormnetdev.webdriver.browsers.AppiumEmulatorDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.09.14
 */
public class OnFailure extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult tr) {
        createAttachment();
        if (AppiumEmulatorDriver.driver != null) {
            takeScreenShot();
        }
    }

    @Attachment(value = "Detailed steps description", type = "text/html")
    public String createAttachment() {
        return Reporter.description;
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] takeScreenShot(){
        final byte[] screenshot = (AppiumEmulatorDriver.driver).getScreenshotAs(OutputType.BYTES);
        return screenshot;
    }
}