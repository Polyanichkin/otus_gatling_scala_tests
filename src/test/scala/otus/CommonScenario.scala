package otus

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.structure.ScenarioBuilder


object CommonScenario {

  def apply(): ScenarioBuilder = new CommonScenario().scn

}

class CommonScenario {
  val scn = scenario("Debug")
    .exec(Actions.MainPage)
    .exec(Actions.login)
    .exec(Actions.flights)
    .exec(Actions.reservations)
//    .exec(Actions.feed) // for vc.ru tests
}


