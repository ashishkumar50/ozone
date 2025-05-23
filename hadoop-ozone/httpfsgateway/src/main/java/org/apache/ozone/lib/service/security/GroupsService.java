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

package org.apache.ozone.lib.service.security;

import java.io.IOException;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdds.annotation.InterfaceAudience;
import org.apache.ozone.lib.server.BaseService;
import org.apache.ozone.lib.server.ServiceException;
import org.apache.ozone.lib.service.Groups;
import org.apache.ozone.lib.util.ConfigurationUtils;

/**
 * Service implementation to provide group mappings.
 */
@InterfaceAudience.Private
public class GroupsService extends BaseService implements Groups {
  private static final String PREFIX = "groups";

  private org.apache.hadoop.security.Groups hGroups;

  public GroupsService() {
    super(PREFIX);
  }

  @Override
  protected void init() throws ServiceException {
    Configuration hConf = new Configuration(false);
    ConfigurationUtils.copy(getServiceConfig(), hConf);
    hGroups = new org.apache.hadoop.security.Groups(hConf);
  }

  @Override
  public Class getInterface() {
    return Groups.class;
  }

  @Override
  public List<String> getGroups(String user) throws IOException {
    return hGroups.getGroups(user);
  }

}
