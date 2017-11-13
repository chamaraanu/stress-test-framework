package org.wso2.stress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.testng.annotations.Test;


import java.io.File;
import java.io.IOException;

public class JmeterTest {

    private static final Log log = LogFactory.getLog(JmeterTest.class);

    @Test
    public void runJmeter() {
        System.out.println("Jmeter Test");
        StandardJMeterEngine jmeter = new StandardJMeterEngine();

        JMeterUtils.setJMeterHome("/home/chamara/Programs/apache-jmeter-3.2");
        JMeterUtils.loadJMeterProperties("/home/chamara/Programs/apache-jmeter-3.2/bin/" +
                "jmeter.properties");
        JMeterUtils.initLogging();
        JMeterUtils.initLocale();

        try {
            SaveService.loadProperties();

            File in = new File("/home/chamara/stress-test-framework/src/test/jmeter/" +
                    "CUSTOM/Concurrency_Thread_Group.jmx");
            /*File in = new File("/home/chamara/Federation_Proxy.jmx");*/
            HashTree testPlanTree = SaveService.loadTree(in);
            jmeter.configure(testPlanTree);
            jmeter.run();

        } catch (IOException e) {
            String msg = "Error occurred while executing jmeter script";
            log.error(msg, e);
        }

    }
}
