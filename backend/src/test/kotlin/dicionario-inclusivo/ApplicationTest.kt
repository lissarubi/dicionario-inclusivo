package dicionarioinclusivo

import dicionarioinclusivo.plugins.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {
  @Test
  fun testApp() = testApplication {
    assertEquals(expected = true, actual = true)
  }
}

