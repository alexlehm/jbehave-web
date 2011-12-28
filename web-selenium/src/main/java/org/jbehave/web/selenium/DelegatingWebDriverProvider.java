package org.jbehave.web.selenium;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

/**
 * Delegating abstract implementation that provides {@link WebDriver}s specified
 * by the concrete delegate.
 */
public abstract class DelegatingWebDriverProvider implements WebDriverProvider {

    protected ThreadLocal<WebDriver> delegate = new ThreadLocal<WebDriver>();

    public WebDriver get() {
        WebDriver webDriver = delegate.get();
        if (webDriver == null) {
            throw new DelegateWebDriverNotFound();
        }
        return webDriver;
    }

    public void end() {
        delegate.get().quit();
        delegate.set(null);
    }

    public boolean saveScreenshotTo(String path) {
        WebDriver driver = get();
        if (driver instanceof TakesScreenshot) {
            File file = new File(path);
            try {
                byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                file.getParentFile().mkdirs();
                file.createNewFile();
                IOUtils.write(bytes, new FileOutputStream(file));
                return true;
            } catch (WebDriverException e) {
                if (e.getMessage().indexOf("Job on Sauce already complete.") == -1) {
                    throw new RuntimeException("Failed to save screenshot to " + file, e);
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to save screenshot to " + file, e);
            }
        }
        return false;
    }

    @SuppressWarnings("serial")
    public static class DelegateWebDriverNotFound extends RuntimeException {
        public DelegateWebDriverNotFound() {
            super("WebDriver has not been found for this thread.\n"
                    + "Please verify you are using the correct WebDriverProvider, "
                    + "with the appropriate credentials if using remote access, " +
                      "e.g. to SauceLabs: -DSAUCE_USERNAME=xxxxxx " +
                      "-DSAUCE_ACCESS_KEY=xxx-xxxx-xxxx-xxxx-xxx ");
        }
    }
}