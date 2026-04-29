package otus

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import io.gatling.http.request.builder.HttpRequestBuilder

object Actions {
  val MainPage: HttpRequestBuilder =
    http("getMainPage")
      .get("/webtours/")

  val Login: HttpRequestBuilder =
    http("Login")
      .post("/cgi-bin/login.pl")
      .formParam("userSession", "143984.012231673HtAzVHHpzcQVzzzHtttDHpAzfHHf")
      .formParam("username", "bilbo")
      .formParam("password", "riddle")

  val Flights: HttpRequestBuilder =
    http("FlightsPage")
      .get("/cgi-bin/nav.pl?")
      .formParam("page", "menu")
      .formParam("in", "flights")

  val Reservations: HttpRequestBuilder =
    http("ReservationsList")
      .get("/cgi-bin/reservations.pl?page=welcome")

}
// for vc.ru tests
//object Actions {
//  val MainPage: HttpRequestBuilder =
//    http("getMainPage")
//      .get("https://vc.ru")
//      .formUpload("page", "welcome")
//
//  val feed: HttpRequestBuilder =
//    http(requestName = "feed_open")
//      .get("/v2.10/feed")
//      .queryParam(name="markdown", value = false)
//      .queryParam(name="markdown", value = false)
//      .queryParam(name="markdown", value = false)
//
//}
