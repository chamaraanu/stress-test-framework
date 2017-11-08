package org.wso2.stress;

import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class StressTest {

    @Test
    public void runJmeter() throws Exception {
        StandardJMeterEngine jmeter = new StandardJMeterEngine();

        JMeterUtils.setJMeterHome("/home/chamara/Programs/apache-jmeter-3.2 ");
        JMeterUtils.loadJMeterProperties("/home/chamara/stress-test-framework/src/test/resources/CUSTOM/user.properties");
        JMeterUtils.initLogging();
        JMeterUtils.initLocale();

        SaveService.loadProperties();

        FileInputStream in = new FileInputStream("/home/chamara/stress-test-framework/src/test/jmeter/CUSTOM/Concurrency_Thread_Group.jmx");
        HashTree testPlanTree = SaveService.loadTree(in);
        in.close();

        jmeter.configure(testPlanTree);
        jmeter.run();


    }
}