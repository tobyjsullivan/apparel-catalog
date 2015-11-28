package com.tobyjay.apparelcatalog

import akka.actor.Actor
import com.tobyjay.apparelcatalog.models.ServiceStatus
import com.tobyjay.apparelcatalog.models.ServiceStatusProtocol._
import spray.http.MediaTypes._
import spray.routing._
import spray.httpx.SprayJsonSupport._

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class ApparelCatalogServiceActor extends Actor with ApparelCatalogService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}


// this trait defines our service behavior independently from the service actor
trait ApparelCatalogService extends HttpService {

  val myRoute =
    path("") {
      get {
        respondWithMediaType(`text/html`) { // XML is marshalled to `text/xml` by default, so we simply override here
          complete {
            <html>
              <body>
                <h1>The service is online!</h1>
              </body>
            </html>
          }
        }
      }
    } ~
    path("status") {
      get {
        respondWithMediaType(`application/json`) {
          complete {
            ServiceStatus("OK")
          }
        }
      }
    }

}