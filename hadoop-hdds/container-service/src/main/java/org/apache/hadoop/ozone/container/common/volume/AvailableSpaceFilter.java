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

package org.apache.hadoop.ozone.container.common.volume;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import org.apache.hadoop.hdds.fs.SpaceUsageSource;

/**
 * Filter for selecting volumes with enough space for a new container.
 * Keeps track of ineligible volumes for logging/debug purposes.
 */
public class AvailableSpaceFilter implements Predicate<HddsVolume> {

  private final long requiredSpace;
  private final Map<HddsVolume, AvailableSpace> fullVolumes =
      new HashMap<>();
  private long mostAvailableSpace = Long.MIN_VALUE;

  public AvailableSpaceFilter(long requiredSpace) {
    this.requiredSpace = requiredSpace;
  }

  @Override
  public boolean test(HddsVolume vol) {
    SpaceUsageSource usage = vol.getCurrentUsage();
    long volumeCapacity = usage.getCapacity();
    long free = usage.getAvailable();
    long committed = vol.getCommittedBytes();
    long available = free - committed;
    long volumeFreeSpaceToSpare = vol.getFreeSpaceToSpare(volumeCapacity);
    boolean hasEnoughSpace = VolumeUsage.hasVolumeEnoughSpace(free, committed,
        requiredSpace, volumeFreeSpaceToSpare);

    mostAvailableSpace = Math.max(available, mostAvailableSpace);

    if (!hasEnoughSpace) {
      fullVolumes.put(vol, new AvailableSpace(free, committed));
    }

    return hasEnoughSpace;
  }

  boolean foundFullVolumes() {
    return !fullVolumes.isEmpty();
  }

  long mostAvailableSpace() {
    return mostAvailableSpace;
  }

  @Override
  public String toString() {
    return "required space: " + requiredSpace +
        ", volumes: " + fullVolumes;
  }

  private static class AvailableSpace {
    private final long free;
    private final long committed;

    AvailableSpace(long free, long committed) {
      this.free = free;
      this.committed = committed;
    }

    @Override
    public String toString() {
      return "free: " + free +
          ", committed: " + committed;
    }
  }
}
