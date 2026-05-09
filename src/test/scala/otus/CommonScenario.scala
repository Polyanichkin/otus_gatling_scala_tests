package otus

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.javaapi.core.internal.Feeders


object CommonScenario {

  def apply(): ScenarioBuilder = new CommonScenario().scn

}

class CommonScenario {

  val mainPageLogin = group("Open main page and login")(
    exec(Actions.MainPage)
    exec(Actions.getCookies)
    exec(Actions.getUserSession)
    exec(session => {
      println(s"Extracted userSession: ${session("user_session").as[String]}")
      session
    })
    exec(Actions.login)
//    exitBlockOnFail(
//      forever(
//        randomSwitch(
//          possibilities = 60.0 -> checkPages,
//          40.0 -> baseOverview
//        )
//      )
//    )

  )

  val chooseFlights = group("Open flights page and choose flight")(
    exec(Actions.search)
    exec(Actions.flights)
    exec(Actions.reservations)
    exec(session => {
      println(s"Extracted city_depart: ${session("city_depart").as[String]}" +
        s" and extracted city_arrive: ${session("city_arrive").as[String]}")
      session
    })
    exec(Actions.choose_cities_and_dates)
  )

  val scn = scenario("Debug")
    .exec(mainPageLogin)
    .exec(chooseFlights)
//    .exec(buyTicket)


}


