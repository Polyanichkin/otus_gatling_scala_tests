package otus

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import io.gatling.http.request.builder.HttpRequestBuilder

object Actions {
  val MainPage: HttpRequestBuilder =
    http("getMainPage")
      .get("/webtours/")
      .check(status is 200)

  val login: HttpRequestBuilder =
    http("Login")
      .post("/cgi-bin/login.pl")
      .formParam("userSession", "143984.012231673HtAzVHHpzcQVzzzHtttDHpAzfHHf")
      .formParam("username", "bilbo")
      .formParam("password", "riddle")
      .check(status is 200)

  val flights: HttpRequestBuilder =
    http("FlightsPage")
      .get("/cgi-bin/nav.pl?")
      .formParam("page", "menu")
      .formParam("in", "flights")
      .check(status is 200)

  val reservations: HttpRequestBuilder =
    http("ReservationsList")
      .get("/cgi-bin/reservations.pl?page=welcome")
      .check(status is 200)

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
