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

  val search: HttpRequestBuilder =
    http("SearchPage")
      .get("/cgi-bin/nav.pl?")
      .queryParam("page", "search")
      .check(status is 200)

  val flights: HttpRequestBuilder =
    http("FlightsPage")
      .get("/cgi-bin/nav.pl?")
      .queryParam("page", "menu")
      .queryParam("in", "flights")
      .check(status is 200)

  val reservations: HttpRequestBuilder =
    http("ReservationsList")
      .get("/cgi-bin/reservations.pl?page=welcome")
      .check(status is 200)
//      .check(regex("<option[^>]*value=\"([^\"]+)\">").findAll.saveAs("cities"))
      .check(regex("<option[^>]*value=\"([^\"]+)\">").findRandom.saveAs("city_depart")) // Не работает, found nothing
      .check(regex("<option[^>]*value=\"([^\"]+)\">").findRandom.saveAs("city_arrive")) // Не работает, found nothing

  val choose_cities_and_dates: HttpRequestBuilder =
    http("ChooseCitiesAndDates")
      .post("/cgi-bin/reservations.pl")
      .formParam("advanceDiscount", "0")
      .formParam("depart", "#{city_depart}")
      .formParam("departDate", "25/10/2026")
      .formParam("arrive", "#{city_arrive}")
      .formParam("returnDate", "30/10/2026")
      .formParam("numPassengers", "1")
      .formParam("seatPref", "None")
      .formParam("seatType", "Coach")
      .formParam("findFlights.x", "52")
      .formParam("findFlights.y", "8")
      .formParam(".cgifields", "roundtrip")
      .formParam(".cgifields", "seatType")
      .formParam(".cgifields", "seatPref")
      .check(status is 200)
      // В ОТВЕТЕ ПОКА НЕТ 4х рейсов на выбор

}

