package com.example

import com.example.ui.viewmodel.AdminConfig
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun adminAuthorization_onlyValidEmailsAllowed() {
    assertTrue(AdminConfig.isAuthorizedEmail("atchouyaosylvain59@gmail.com"))
    assertTrue(AdminConfig.isAuthorizedEmail("ail4c03@gmail.com"))
    assertTrue(AdminConfig.isAuthorizedEmail("  AtchouyaoSylvain59@gmail.com  "))
    assertTrue(AdminConfig.isAuthorizedEmail("AIL4C03@GMAIL.COM"))

    assertFalse(AdminConfig.isAuthorizedEmail("user@gmail.com"))
    assertFalse(AdminConfig.isAuthorizedEmail("hacker@ong-ail4c.ci"))
    assertFalse(AdminConfig.isAuthorizedEmail(null))
    assertFalse(AdminConfig.isAuthorizedEmail(""))
  }

  @Test
  fun adminPassword_matchesRequiredConstant() {
    assertEquals("AIL4CCI", AdminConfig.ADMIN_PASSWORD)
  }
}

