package com.apiumhub.vyou_core

import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import java.nio.charset.Charset

class TestUtils {
  companion object {
    val randomizer = EasyRandom(
      EasyRandomParameters()
        .charset(Charset.forName("UTF-8"))
        .stringLengthRange(10, 50)
        .collectionSizeRange(1, 5)
        .scanClasspathForConcreteTypes(true)
        .overrideDefaultInitialization(false)
        .ignoreRandomizationErrors(true)
    )
  }
}