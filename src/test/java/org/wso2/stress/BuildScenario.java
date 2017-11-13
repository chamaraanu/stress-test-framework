/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.stress;

import com.blazemeter.jmeter.threads.concurrency.ConcurrencyThreadGroup;
import kg.apc.jmeter.threads.UltimateThreadGroup;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.JMeterThread;
import org.apache.jmeter.threads.SetupThreadGroup;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jmeter.visualizers.SimpleDataWriter;
import org.apache.jorphan.collections.HashTree;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class BuildScenario {

    private static final Log log = LogFactory.getLog(MakeStress.class);

    @Test
    public void buildScenario() throws IOException {

        File jmeterFilePath=new File("/home/chamara");

        StandardJMeterEngine jm = new StandardJMeterEngine();
        JMeterUtils.setJMeterHome("/home/chamara/Programs/apache-jmeter-3.2");
        JMeterUtils.loadJMeterProperties("/home/chamara/Programs/apache-jmeter-3.2/bin/jmeter" +
                ".properties");
        HashTree hashTree = new HashTree();


        HTTPSampler httpSampler = new HTTPSampler();
        httpSampler.setDomain("localhost");
        httpSampler.setPort(9443);
        httpSampler.setPath("/carbon");
        httpSampler.setMethod("GET");

        // Loop Controller
        TestElement loopCtrl = new LoopController();
        ((LoopController)loopCtrl).setLoops(1);
        ((LoopController)loopCtrl).addTestElement(httpSampler);
        ((LoopController)loopCtrl).setFirst(true);

        // Thread Group
        /*SetupThreadGroup threadGroup = new SetupThreadGroup();
        threadGroup.setNumThreads(1);
        threadGroup.setRampUp(1);
        threadGroup.setSamplerController((LoopController)loopCtrl);*/

        /*UltimateThreadGroup ultimateThreadGroup = new UltimateThreadGroup();
        ultimateThreadGroup.setNumThreads(100);*/

        ConcurrencyThreadGroup concurrencyThreadGroup = new ConcurrencyThreadGroup();
        concurrencyThreadGroup.setNumThreads(100);
        concurrencyThreadGroup.setRampUp("10");
        concurrencyThreadGroup.setSteps("0");
        concurrencyThreadGroup.setHold("60");

        SimpleDataWriter simpleDataWriter = new SimpleDataWriter();
        simpleDataWriter.setFile("result.csv");

        TestPlan testPlan = new TestPlan("MY TEST PLAN");

        hashTree.add("testPlan", testPlan);
        hashTree.add("loopCtrl", loopCtrl);
        hashTree.add("threadGroup", concurrencyThreadGroup);
        hashTree.add("httpSampler", httpSampler);
        hashTree.add("simpleDataWriter", simpleDataWriter);

        SaveService.saveTree(hashTree, new FileOutputStream(jmeterFilePath + "/" + "example" +
                ".jmx"));

        Summariser summer = null;
        String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
        if (summariserName.length() > 0) {
            summer = new Summariser(summariserName);
        }

        String logFile = jmeterFilePath + "/" + "example.jtl";
        ResultCollector logger = new ResultCollector(summer);
        logger.setFilename(logFile);
        hashTree.add(hashTree.getArray()[0], logger);

        jm.configure(hashTree);
        jm.run();

       /* TestPlan testPlan = new TestPlan("Create JMeter Script From Java Code");
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());

        hashTree.add(testPlan);
        HashTree threadGroupHashTree = hashTree.add(testPlan, ultimateThreadGroup);
        threadGroupHashTree.add(httpSampler);

        SaveService.saveTree(hashTree, new FileOutputStream(jmeterFilePath + "/" + "example" +
                ".jmx"));

        Summariser summer = null;
        String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
        if (summariserName.length() > 0) {
            summer = new Summariser(summariserName);
        }

        String logFile = jmeterFilePath + "/" + "example.jtl";
        ResultCollector logger = new ResultCollector(summer);
        logger.setFilename(logFile);
        hashTree.add(hashTree.getArray()[0], logger);

        jm.configure(hashTree);
        jm.run();

        System.out.println("Test completed. See " + jmeterFilePath + "/" + "example.jtl file for " +
                "results");
        System.out.println("JMeter .jmx script is available at " + jmeterFilePath + "/" +
                "example.jmx");*/

    }

   /* @Test
    public void smapleTest() {

        String msg = "Sample Test";
        log.info(msg);

    }*/
}
