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

package org.apache.hadoop.ozone.recon.api.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Class that represents the API Response structure of Containers.
 */
public class ContainersResponse {
  /**
   * Contains a map with total count of containers and list of containers.
   */
  @JsonProperty("data")
  private ContainersResponseData containersResponseData;

  public ContainersResponse() {
    this(0, new ArrayList<>(), 0);
  }

  public ContainersResponse(long totalCount,
                            Collection<ContainerMetadata> containers,
                            long prevKey) {
    this.containersResponseData =
        new ContainersResponseData(totalCount, containers, prevKey);
  }

  public ContainersResponseData getContainersResponseData() {
    return containersResponseData;
  }

  public void setContainersResponseData(ContainersResponseData
                                            containersResponseData) {
    this.containersResponseData = containersResponseData;
  }

  /**
   * Class that encapsulates the data presented in Containers API Response.
   */
  public static class ContainersResponseData {
    /**
     * Total count of the containers.
     */
    @JsonProperty("totalCount")
    private long totalCount;

    /**
     * prevKey will be the last key of the previous page.
     */
    @JsonProperty("prevKey")
    private long prevKey;

    /**
     * An array of containers.
     */
    @JsonProperty("containers")
    private Collection<ContainerMetadata> containers;

    ContainersResponseData(long totalCount,
                           Collection<ContainerMetadata> containers,
                           long prevKey) {
      this.totalCount = totalCount;
      this.containers = containers;
      this.prevKey = prevKey;
    }

    public long getTotalCount() {
      return totalCount;
    }

    public Collection<ContainerMetadata> getContainers() {
      return containers;
    }

    public long getPrevKey() {
      return prevKey;
    }
  }
}
