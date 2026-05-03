package otus

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.javaapi.core.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class fMaxOpenedModel extends Simulation{

  setUp(
    CommonScenario()

      .inject(
                incrementUsersPerSec(5.0)
                .times(5)
                .eachLevelLasting(5.minutes)
                .separatedByRampsLasting(10)
                .startingFrom(1)

    ).protocols(otus.httpProtocol))
    .maxDuration(duration = 1000)
}


