/*
 *    This file is part of ReadonlyREST.
 *
 *    ReadonlyREST is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    ReadonlyREST is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with ReadonlyREST.  If not, see http://www.gnu.org/licenses/
 */

package tech.beshu.ror.acl.blocks.rules.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.mockito.Mockito;
import tech.beshu.ror.acl.blocks.rules.RuleExitResult;
import tech.beshu.ror.acl.blocks.rules.SyncRule;
import tech.beshu.ror.requestcontext.__old_RequestContext;
import tech.beshu.ror.settings.rules.__old_ApiKeysRuleSettings;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by sscarduzio on 18/01/2017.
 */

public class __old_ApiKeysRuleTests {

  private RuleExitResult match(String configured, String found) {
    return match(configured, found, Mockito.mock(__old_RequestContext.class));
  }

  private RuleExitResult match(String configured, String found, __old_RequestContext rc) {
    when(rc.getHeaders()).thenReturn(ImmutableMap.of("X-Api-Key", found));

    SyncRule r = new __old_ApiKeysSyncRule(new __old_ApiKeysRuleSettings(Sets.newHashSet(configured)));
    return r.match(rc);
  }

  @Test
  public void testOK() {
    RuleExitResult res = match("1234567890", "1234567890");
    assertTrue(res.isMatch());
  }

  @Test
  public void testKO() {
    RuleExitResult res = match("1234567890", "x");
    assertFalse(res.isMatch());
  }
}
