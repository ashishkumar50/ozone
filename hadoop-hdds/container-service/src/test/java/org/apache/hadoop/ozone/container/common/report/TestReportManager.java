/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.ozone.container.common.report;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.ScheduledExecutorService;
import org.apache.hadoop.hdds.conf.OzoneConfiguration;
import org.apache.hadoop.ozone.container.common.statemachine.StateContext;
import org.junit.jupiter.api.Test;

/**
 * Test cases to test {@link ReportManager}.
 */
public class TestReportManager {

  @Test
  public void testReportManagerInit() {
    OzoneConfiguration conf = new OzoneConfiguration();
    StateContext dummyContext = mock(StateContext.class);
    ReportPublisher dummyPublisher = mock(ReportPublisher.class);
    ReportManager.Builder builder = ReportManager.newBuilder(conf);
    builder.setStateContext(dummyContext);
    builder.addPublisher(dummyPublisher);
    ReportManager reportManager = builder.build();
    reportManager.init();
    verify(dummyPublisher, times(1)).init(eq(dummyContext),
        any(ScheduledExecutorService.class));

  }
}
