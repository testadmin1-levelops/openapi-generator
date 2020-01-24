/**
 *  Copyright 2012 Wordnik, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import com.wordnik.swagger.model._
import com.wordnik.swagger.codegen.util._

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

import scala.collection.JavaConverters._
import scala.reflect.BeanProperty

@RunWith(classOf[JUnitRunner])
class ResourceExtractorTest extends FlatSpec with ShouldMatchers {
  val json = ScalaJsonUtil.getJsonMapper

  behavior of "ResourceExtractor"
  it should "get 3 apis from a resource listing" in {
    val resourceListing = ResourceExtractor.fetchListing("src/test/resources/petstore/resources.json")
    resourceListing should not be(null)
    resourceListing.apis.size should be (3)
  }
}

@RunWith(classOf[JUnitRunner])
class ApiExtractorTest extends FlatSpec with ShouldMatchers {
  val json = ScalaJsonUtil.getJsonMapper

  behavior of "ApiExtractor"
  it should "verify the deserialization of the store api" in {
    val resourceListing = ResourceExtractor.fetchListing("src/test/resources/petstore/resources.json")
    val docs = ApiExtractor.extractApiOperations("src/test/resources/petstore", resourceListing.apis)
    val m = docs.map(t => (t.resourcePath, t)).toMap
    val storeApi = m("/store")

    storeApi should not be (null)
    storeApi.apis.size should be (2)

    val f = storeApi.apis.map(m => (m.path, m)).toMap
    (f.keys.toSet & Set("/store.{format}/order/{orderId}","/store.{format}/order")).size should be (2)

    val storeOps = f("/store.{format}/order/{orderId}")
    val ops = storeOps.operations.map(o => (o.nickname, o)).toMap
    val getOrderById = ops("getOrderById")

    getOrderById should not be null

    getOrderById.httpMethod should be ("GET")
    getOrderById.parameters.size should be (1)
    getOrderById.errorResponses.size should be (2)
  }
}

@RunWith(classOf[JUnitRunner])
class CoreUtilsTest extends FlatSpec with ShouldMatchers {
  val json = ScalaJsonUtil.getJsonMapper
  sys.props += "fileMap" -> "src/test/resources/petstore"

  behavior of "CoreUtils"

  it should "verify models are extracted" in {
    val resourceListing = ResourceExtractor.fetchListing("src/test/resources/petstore/resources.json")
    val apis = ApiExtractor.extractApiOperations("src/test/resources/petstore", resourceListing.apis)

    val cu = CoreUtils.extractAllModels2(apis)
    cu.size should be (5)

    (cu.keys.toSet & Set("User", "Tag", "Pet", "Category", "Order")).size should be (5)
  }

  it should "verify operation names" in {
    val resourceListing = ResourceExtractor.fetchListing("src/test/resources/petstore/resources.json")
    val apis = ApiExtractor.extractApiOperations("src/test/resources/petstore", resourceListing.apis)

    val petApi = apis.filter(api => api.resourcePath == "/pet").head
    val eps = petApi.apis.map(api => (api.path, api)).toMap
    val ops = eps("/pet.{format}").operations.map(ep => (ep.nickname, ep)).toMap

    ops.size should be (2)

    (ops.keys.toSet & Set("addPet", "updatePet")).size should be (2)
  }
}