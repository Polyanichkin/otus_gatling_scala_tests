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
    exec(Actions.homeUserSession)
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

  val scn = scenario("Debug")
    .exec(mainPageLogin)
    .exec(session => {
      println(s"Extracted userSession: ${session("user_session").as[String]}")
      session
    })
    .exec(Actions.flights)
    .exec(Actions.reservations)
//    .exec(buyTicket)


}


