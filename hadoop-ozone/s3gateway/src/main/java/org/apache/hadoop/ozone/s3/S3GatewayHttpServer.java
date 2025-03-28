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

package org.apache.hadoop.ozone.s3;

import java.io.IOException;
import org.apache.hadoop.hdds.conf.MutableConfigurationSource;
import org.apache.hadoop.hdds.server.http.BaseHttpServer;

/**
 * Http server to provide S3-compatible API.
 */
public class S3GatewayHttpServer extends BaseHttpServer {

  /**
   * Default offset between two filters.
   */
  public static final int FILTER_PRIORITY_DO_AFTER = 50;

  public S3GatewayHttpServer(MutableConfigurationSource conf, String name)
      throws IOException {
    super(conf, name);
  }

  @Override
  protected boolean shouldAddDefaultApps() {
    return false;
  }

  @Override
  protected String getHttpAddressKey() {
    return S3GatewayConfigKeys.OZONE_S3G_HTTP_ADDRESS_KEY;
  }

  @Override
  protected String getHttpBindHostKey() {
    return S3GatewayConfigKeys.OZONE_S3G_HTTP_BIND_HOST_KEY;
  }

  @Override
  protected String getHttpsAddressKey() {
    return S3GatewayConfigKeys.OZONE_S3G_HTTPS_ADDRESS_KEY;
  }

  @Override
  protected String getHttpsBindHostKey() {
    return S3GatewayConfigKeys.OZONE_S3G_HTTPS_BIND_HOST_KEY;
  }

  @Override
  protected String getBindHostDefault() {
    return S3GatewayConfigKeys.OZONE_S3G_HTTP_BIND_HOST_DEFAULT;
  }

  @Override
  protected int getHttpBindPortDefault() {
    return S3GatewayConfigKeys.OZONE_S3G_HTTP_BIND_PORT_DEFAULT;
  }

  @Override
  protected int getHttpsBindPortDefault() {
    return S3GatewayConfigKeys.OZONE_S3G_HTTPS_BIND_PORT_DEFAULT;
  }

  @Override
  protected String getKeytabFile() {
    return S3GatewayConfigKeys.OZONE_S3G_KEYTAB_FILE;
  }

  @Override
  protected String getSpnegoPrincipal() {
    return S3GatewayConfigKeys.OZONE_S3G_WEB_AUTHENTICATION_KERBEROS_PRINCIPAL;
  }

  @Override
  protected String getEnabledKey() {
    return S3GatewayConfigKeys.OZONE_S3G_HTTP_ENABLED_KEY;
  }

  @Override
  protected String getHttpAuthType() {
    return S3GatewayConfigKeys.OZONE_S3G_HTTP_AUTH_TYPE;
  }

  @Override
  protected String getHttpAuthConfigPrefix() {
    return S3GatewayConfigKeys.OZONE_S3G_HTTP_AUTH_CONFIG_PREFIX;
  }

}
