/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.fs.ozone;

import org.apache.hadoop.crypto.CryptoOutputStream;
import org.apache.hadoop.fs.StreamCapabilities;
import org.apache.hadoop.fs.impl.StoreImplementationUtils;
import org.apache.hadoop.hdds.scm.storage.ByteBufferStreamOutput;
import org.apache.hadoop.ozone.client.io.ECKeyOutputStream;
import org.apache.hadoop.ozone.client.io.KeyDataStreamOutput;
import org.apache.hadoop.ozone.client.io.KeyOutputStream;
import org.apache.hadoop.ozone.client.io.OzoneDataStreamOutput;
import org.apache.hadoop.util.StringUtils;

import java.io.OutputStream;


public class CapableOzoneFSDataStreamOutput extends OzoneFSDataStreamOutput
    implements StreamCapabilities {
  private final boolean isHsyncEnabled;
  public CapableOzoneFSDataStreamOutput(OzoneFSDataStreamOutput outputStream,
                                        boolean enabled) {
    super(outputStream.getByteBufferStreamOutput());
    this.isHsyncEnabled = enabled;
  }

  @Override
  public boolean hasCapability(String capability) {
    ByteBufferStreamOutput os = getByteBufferStreamOutput();

    /* if (os instanceof CryptoOutputStream) {
      ByteBufferStreamOutput wrapped = ((CryptoOutputStream) os).getWrappedStream();
      return hasWrappedCapability(wrapped, capability);
    } */
    return hasWrappedCapability(os, capability);
  }

  private boolean hasWrappedCapability(ByteBufferStreamOutput os,
      String capability) {

    if (os instanceof KeyDataStreamOutput) {
      switch (StringUtils.toLowerCase(capability)) {
      case StreamCapabilities.HFLUSH:
      case StreamCapabilities.HSYNC:
        return isHsyncEnabled;
      default:
        return false;
      }
    }
    return false;
  }
}
