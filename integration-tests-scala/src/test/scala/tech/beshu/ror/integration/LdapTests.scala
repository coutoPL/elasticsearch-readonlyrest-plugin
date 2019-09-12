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
package tech.beshu.ror.integration

import com.dimafeng.testcontainers.{Container, ForAllTestContainer}
import org.scalatest.WordSpec
import tech.beshu.ror.utils.containers.ReadonlyRestEsCluster

class LdapTests extends WordSpec with ForAllTestContainer {

  override val container: Container = ReadonlyRestEsCluster.createLocalClusterContainer(
    name = "ROR1",
    rorConfigFileName = "/ldap/readonlyrest.yml",
    numberOfInstances = 1
  )
  "A test" in {
    val i = 0
  }
}
