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
        .formParam("numPassengers", "1")
        .formParam("advanceDiscount", "0")
        .formParam("seatType", "Coach")
        .formParam("seatPref", "None")
        .formParam("reserveFlights.x", "52")
        .formParam("reserveFlights.y", "8")
        .check(status is 200)
        .check(regex("<input[^>]*name=\"firstName\"[^>]*value=\"([^\"]*)\"").find.saveAs("firstName"))
        .check(regex("<input[^>]*name=\"lastName\"[^>]*value=\"([^\"]*)&#10;\"").find.saveAs("lastName"))
        .check(regex("<input[^>]*name=\"address1\"[^>]*value=\"([^\"]*)\"").find.saveAs("address1"))
        .check(regex("<input[^>]*name=\"address2\"[^>]*value=\"([^\"]*)\"").find.saveAs("address2"))
        .check(regex("<input[^>]*name=\"pass1\"[^>]*value=\"([^\"]*)&#10;\"").find.saveAs("pass1"))
        .check(regex("<input[^>]*name=\"creditCard\"[^>]*value=\"([^\"]*)\"").find.saveAs("creditCard"))
        .check(regex("<input[^>]*name=\"expDate\"[^>]*value=\"([^\"]*)&#10;\"").find.saveAs("expDate"))


  val book_flight_ticket: HttpRequestBuilder =
    http("ByTickets")
      .post("/cgi-bin/reservations.pl")
      .formParam("firstName", "#{firstName}")
      .formParam("lastName", "#{lastName}")
      .formParam("address1", "#{address1}")
      .formParam("address2", "#{address2}")
      .formParam("pass1", "#{pass1}")
      .formParam("creditCard", "#{creditCard}")
      .formParam("expDate", "#{expDate}")
      .formParam("saveCC", "on")
      .formParam("oldCCOption", "on")
      .formParam("numPassengers", "1")
      .formParam("seatType", "Coach")
      .formParam("seatPref", "None")
      .formParam("outboundFlight", "#{random_flight}")
      .formParam("advanceDiscount", "0")
      .formParam("returnFlight", "")
      .formParam("JSFormSubmit", "off")
      .formParam("buyFlights.x", "51")
      .formParam("buyFlights.y", "3")
      .formParam(".cgifields", "saveCC")
      .check(status is 200)

  val singOff: HttpRequestBuilder =
    http("singOff")
      .get("/cgi-bin/welcome.pl?signOff=1")
      .check(status.is(200))

  val home: HttpRequestBuilder =
    http("homePage")
      .get("/cgi-bin/nav.pl?in=home")
      .check(status.is(200))

}

