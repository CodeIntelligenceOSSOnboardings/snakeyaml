/**
 * Copyright (c) 2008, SnakeYAML
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.yaml.snakeyaml.constructor;

import java.util.TreeSet;

/**
 * Collection with non-recursive hashCode()
 */
public class RPTreeSet<T> extends TreeSet<T> {

  private boolean avoidHashCode = false;

  /**
   * Safe hashCode. For recursive structures it returns its address
   *
   * @return System.identityHashCode() for recursive structure
   */
  public int hashCode() {

    if (avoidHashCode) {
      return System.identityHashCode(this);
    }
    avoidHashCode = true;
    try {
      return super.hashCode();
    } finally {
      avoidHashCode = false;
    }
  }
}
