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
package org.yaml.snakeyaml.issues.issue531;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

// Stackoverflow [OSS-Fuzz - 47081]

/**
 * OSS-Fuzz - 47081.
 * <ul>
 *     <li><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-38752">CVE-2022-38752</a></li>
 *     <li><a href="https://nvd.nist.gov/vuln/detail/CVE-2022-41854">CVE-2022-41854</a></li>
 * </ul>
 * <p>
 *
 */
public class Fuzzy47081Test {

  @Test
  public void parse47081_allow_recursion() {
    String strYaml = "&a\n" + "- *a\n" + "- *a:\n";
    List<Object> parsed = grind(strYaml);
    assertEquals(strYaml, 2, parsed.size());
  }

  @Test
  public void parse47081_allow_recursion2() {
    String strYaml = "&a\n*a:\n  - *a:\n";
    Map<Object, Object> parsed = grind(strYaml);
    assertEquals(strYaml, 1, parsed.size());
  }

  @Test
  public void parse47081_no_colon() {
    LoaderOptions options = new LoaderOptions();
    options.setAllowRecursiveKeys(true);
    Yaml yaml = new Yaml(options);
    // this YAML doesn't cause a recursion failure even if
    // the setAllowRecursiveKeys(false) is used. Because the
    // recursion is not in the keys. However, the result is still
    // a recursive tree that is prone to stack-overflows.
    String strYaml = "&a\n" + "- *a\n" + "- *a\n";
    List<Object> parsed = yaml.load(strYaml);
    assertEquals(strYaml, 2, parsed.size());
    parsed.hashCode();
  }

  private <T> T grind(String strYaml) {

    LoaderOptions options = new LoaderOptions();
    options.setAllowRecursiveKeys(false); // must be set to false for untrusted source
    Yaml yaml = new Yaml(options);
    try {
      yaml.load(strYaml);
      fail("Should report invalid YAML: " + strYaml);
    } catch (YAMLException e) {
      assertEquals("Recursive key for mapping is detected but it is not configured to be allowed.",
              e.getMessage());
    }

    options.setAllowRecursiveKeys(true);
    yaml = new Yaml(options);
    return yaml.load(strYaml);

  }

}
