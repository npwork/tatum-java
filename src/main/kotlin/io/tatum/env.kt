package io.tatum

const val baseUrl = "https://api.tatum.io/v3"

fun getApiKeyHeader() = mapOf("x-api-key" to System.getenv("XAPIKEY"))
