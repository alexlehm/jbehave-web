package org.jbehave.web.selenium

import com.github.tanob.groobe.GrooBe
import geb.Browser

public class GroovyGebFluentWebDriverPage extends FluentWebDriverPage {

    public GroovyGebFluentWebDriverPage(WebDriverProvider driverProvider) {
        super(driverProvider);
        GrooBe.activate()
    }

    def methodMissing(String name, args) {
        try {
            getBrowser()."$name"(* args)
        } catch (Exception re) {
            throw new RuntimeException(where(re), re)
        }
    }

    private String where(Exception e) {
        StackTraceElement[] stes = e.getStackTrace()
        for (int i = 0; i < stes.length; i++) {
            StackTraceElement ste = stes[i];
            if (ste.getClassName().equals(GroovyGebFluentWebDriverPage.class.getName())
                    && ste.getMethodName().equals("methodMissing")) {
                i++
                def className = ste.getClassName()
                while (className.startsWith("org.org.codehaus.groovy") ||
                       className.startsWith("groovy.lang") ||
                       className.startsWith("java.lang.reflect") ||
                       className.startsWith("sun.reflect.")) {
                    i++
                }
                ste = stes[i-1]
                return "Failure in:" + ste.getClassName() + "." + ste.getMethodName() + "(" + ste.getFileName() + ":" + ste.getLineNumber() + ")"
            }
        }
        return "unknown line causing methodMissing error"
    }

    private Browser getBrowser() {
        def browser = new Browser()
        browser.setDriver(getDriverProvider().get())
        browser
    }


}