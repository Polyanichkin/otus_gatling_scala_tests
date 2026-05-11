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
      .check(regex("<option[^>]*value=\"([^\"]+)\">").findRandom.saveAs("city_depart"))
      .check(regex("<option[^>]*value=\"([^\"]+)\">").findRandom.saveAs("city_arrive"))

  val choose_cities_and_dates: HttpRequestBuilder =
    http("ChooseCitiesAndDates")
      .post("/cgi-bin/reservations.pl")
      .formParam("advanceDiscount", "0")
      .formParam("depart", "#{city_depart}")
      .formParam("departDate", "10/25/2026")
      .formParam("arrive", "#{city_arrive}")
      .formParam("returnDate", "10/30/2026")
      .formParam("numPassengers", "1")
      .formParam("seatPref", "None")
      .formParam("seatType", "Coach")
      .formParam("findFlights.x", "52")
      .formParam("findFlights.y", "8")
      .formParam(".cgifields", "roundtrip")
      .formParam(".cgifields", "seatType")
      .formParam(".cgifields", "seatPref")
      .check(status is 200)
      .check(regex("name=\"outboundFlight\" value=\"([^\"]+)\"").findRandom.saveAs("random_flight"))


    val choose_random_flight: HttpRequestBuilder =
      http("ChooseFlight")
        .post("/cgi-bin/reservations.pl")
        .formParam("outboundFlight", "#{random_flight}")
        .formParam("advanceDiscount", "0")
        .formParam("numPassengers", "1")
        .formParam("seatPref", "None")
        .formParam("seatType", "Coach")
        .formParam("findFlights.x", "52")
        .formParam("findFlights.y", "8")
        .check(status is 200)
        // Надо поизвлекать данные пассажира для последующей покупки

//  val book_flight_ticket: HttpRequestBuilder =
//    http("ChooseFlight")
//      .post("/cgi-bin/reservations.pl")
//      .formParam("outboundFlight", "#{random_flight}")
//      .formParam("advanceDiscount", "0")
//      .formParam("numPassengers", "1")
//      .formParam("seatPref", "None")
//      .formParam("seatType", "Coach")
//      .formParam("findFlights.x", "52")
//      .formParam("findFlights.y", "8")
//      .check(status is 200)

}

