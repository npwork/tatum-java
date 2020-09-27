package model.response.common

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

class TatumException(message: String) : Exception(message)

@JsonIgnoreProperties(ignoreUnknown = true)
class TatumError(
    val statusCode: Int,
    val errorCode: String? = null,
    val message: String,
)
