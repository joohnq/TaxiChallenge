package com.joohhq.taxichallenge.data.mapper

import java.util.Locale

fun Double.formatTo2Decimal(): String =String.format(Locale.ROOT, "%.2f", this)