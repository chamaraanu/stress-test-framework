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

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * Created by chamara on 11/8/17.
 */
public class MakeStress {

    private static final Log log = LogFactory.getLog(MakeStress.class);

    @Parameters({ "cpu", "io", "vms", "vmBytes", "timeout"})
    @Test
    public void makeStress(String cpu, String io, String vms, String vmBytes, String timeout) {
        String stressCommand = "stress --cpu " + cpu + " --io " + io + " --vm " + vms + " --vm-bytes " + vmBytes +
                " --timeout " + timeout + " --verbose";
        Runtime runtime = Runtime.getRuntime();

        try {
            Process exec = runtime.exec(stressCommand);
            exec.waitFor();

        } catch (IOException e) {
            String msg = "Error occurred while processing stress";
            log.error(msg, e);
        } catch (InterruptedException e) {
            String msg = "Stress execution interrupted";
            log.error(msg, e);
        }
    }
}