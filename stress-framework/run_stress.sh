#!/bin/bash
# Copyright 2017 WSO2 Inc. (http://wso2.org)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

echo "Begin Stress Test"

jmeter_dir=""
for dir in $HOME/apache-jmeter*; do
    [ -d "${dir}" ] && jmeter_dir="${dir}" && break
done
export JMETER_HOME="${jmeter_dir}"
export PATH=$JMETER_HOME/bin:$PATH

THREAD_COUNT_ARRAY=(1000 1500)

for threads in ${THREAD_COUNT_ARRAY[@]}
do
    report_location=target/${threads}Threads
    jmeter -n -t ../src/test/jmeter/CUSTOM/Ultimate_Thread_Group.jmx -X \
        -Jtotal_threads=$threads \
        -l ${report_location}/results.jtl
    jmeter -g ${report_location}/results.jtl -o ${report_location}/dashboard

done
