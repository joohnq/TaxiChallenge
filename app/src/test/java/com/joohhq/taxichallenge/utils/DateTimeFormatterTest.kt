package com.joohhq.taxichallenge.utils

import com.google.common.truth.Truth
import org.junit.Test

class DateTimeFormatterTest {
    private val date = "2024-12-09T08:15:00"

    @Test
    fun `test the datetime formatter above api 26`() {
        val res = DateTimeFormatter(date)
        Truth.assertThat(res).isEqualTo("09/12/2024 at 08:15")
    }

    @Test
    fun `test the datetime formatter under api 26`() {
        val res = DateTimeFormatter(date)
        Truth.assertThat(res).isEqualTo("09/12/2024 at 08:15")
    }
}
