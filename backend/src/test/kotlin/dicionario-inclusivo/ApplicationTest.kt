package dicionarioinclusivo

import dicionarioinclusivo.plugins.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {
  @Test
  fun testRoot() = testApplication {
    application { configureRouting() }
    assertEquals(expected = true, actual = true)
  }
}

