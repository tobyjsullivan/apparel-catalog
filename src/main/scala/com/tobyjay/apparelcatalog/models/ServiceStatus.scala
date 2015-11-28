package com.tobyjay.apparelcatalog.models

import spray.json.DefaultJsonProtocol

case class ServiceStatus(status: String)

object ServiceStatusProtocol extends DefaultJsonProtocol {
  implicit val serviceStatusFormat = jsonFormat1(ServiceStatus)
}
