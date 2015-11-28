package com.tobyjay.apparelcatalog

import spray.testkit.ScalatestRouteTest
import spray.http._
import StatusCodes._
import org.scalatest._

class ApparelCatalogServiceSpec extends FreeSpec with ScalatestRouteTest with ApparelCatalogService with Matchers {
  def actorRefFactory = system
  
  "ApparelCatalogService" - {

    "return a greeting for GET requests to the root path" in {
      Get() ~> myRoute ~> check {
        responseAs[String] should include("The service is online!")
      }
    }

    "leave GET requests to other paths unhandled" in {
      Get("/kermit") ~> myRoute ~> check {
        handled shouldBe false
      }
    }

    "return a MethodNotAllowed error for PUT requests to the root path" in {
      Put() ~> sealRoute(myRoute) ~> check {
        status should equal (MethodNotAllowed)
        responseAs[String] should equal ("HTTP method not allowed, supported methods: GET")
      }
    }

    "return an OK message for GET requests to /status" in {
      Get("/status") ~> myRoute ~> check {
        status should equal (OK)
        entity.toString should include("\"status\": \"OK\"")
      }
    }
  }
}
