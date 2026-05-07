package otus

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import io.gatling.http.request.builder.HttpRequestBuilder

object Actions {

  val MainPage: HttpRequestBuilder =
    http("getMainPage")
      .get("/webtours/")
      .check(status is 200)

  val getCookies: HttpRequestBuilder =
    http("getCookies")
      .get("/cgi-bin/welcome.pl?signOff=true")
      .check(status.is(200))
      .check(headerRegex("Set-Cookie", "MSO=([^;]+)").saveAs("mso_cookie"))

  val getUserSession: HttpRequestBuilder =
    http("getUserSession")
      .get("/cgi-bin/nav.pl?in=home")
      .check(status is 200)
      .header("Cookie", "${mso_cookie}")
      .check(css("input[name=userSession]", "value").find.saveAs("user_session"))

  val   login: HttpRequestBuilder =
    http("Login")
      .post("/cgi-bin/login.pl")
      .formParam("userSession", "#{user_session}")
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
//      .check(regex("/<option[^>]*value=\"([^\"]+)\">/gm").findAll.saveAs("cities")) // Не работает, found nothing
//      .check(regex("/<option.* value=\"(.*)\">.*<\\/option>/").findRandom.saveAs("city_depart")) // Не работает, found nothing
//      .check(regex("/<option.* value=\"(.*)\">.*<\\/option>/").findRandom.saveAs("city_arrive")) // Не работает, found nothing

}

