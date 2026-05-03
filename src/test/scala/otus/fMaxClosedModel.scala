package otus

import io.gatling.core.Predef._

import scala.concurrent.duration.DurationInt

class fMaxClosedModel extends Simulation{


  setUp(
    CommonScenario()

      .inject(
              incrementConcurrentUsers(5)
                .times(5)
                .eachLevelLasting(5.minutes)
                .separatedByRampsLasting(10)
                .startingFrom(10)

    ).protocols(otus.httpProtocol))
    .maxDuration(duration = 1000)


}
