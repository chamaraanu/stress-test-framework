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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.io.IOException;


public class StressTest {

    private static final Log log = LogFactory.getLog(StressTest.class);

    @Parameters({ "jmeterHome" , "testPlan" , "resultCsv"})
    @Test
    public void runJmeterProcess(String jmeterHome, String testPlan, String resultCsv) {

        String jmeterCommand = "sh " + jmeterHome + "/bin/jmeter.sh -n -t" + testPlan + " -l " +
                resultCsv;
        Runtime runtime = Runtime.getRuntime();

        try {
            Process exec = runtime.exec(jmeterCommand);
            exec.waitFor();

        } catch (IOException e) {
            String msg = "Error occurred while executing jmeter script";
            log.error(msg, e);
        } catch (InterruptedException e) {
            String msg = "Jmeter execution interrupted";
            log.error(msg, e);
        }

    }
}