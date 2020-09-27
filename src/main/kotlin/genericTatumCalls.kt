import khttp.get
import khttp.post
import model.response.common.TatumError
import model.response.common.TatumException
import java.math.BigInteger

fun getNumberFrom(endpoint: String): BigInteger {
    val response = get(
        url = "$baseUrl$endpoint",
        headers = getApiKeyHeader(),
    )
    val body = response.text // returned Content-type is in text/html instead of application/json

    return body.toBigInteger()
}

inline fun <reified T> getObjectFrom(
    endpoint: String,
    params: Map<String, String> = emptyMap(),
): T {
    val response = get(
        url = "$baseUrl$endpoint",
        headers = getApiKeyHeader(),
        params = params,
    )
    val body = response.jsonObject.toString()

    return if (response.statusCode in 200..206) mapper.readValue(body, T::class.java) else
        throw TatumException(mapper.readValue(body, TatumError::class.java).message)
}

inline fun <reified T> postObjectTo(
    endpoint: String,
    params: Map<String, String> = emptyMap(),
    payload: Any? = null
): T {
    val data = payload?.let { mapper.convertValue(it, Map::class.java) }.orEmpty()
    val response = post(
        url = "$baseUrl$endpoint",
        headers = getApiKeyHeader(),
        params = params,
        json = data,
    )
    val body = response.jsonObject.toString()

    return if (response.statusCode in 200..206) mapper.readValue(body, T::class.java) else
        throw TatumException(mapper.readValue(body, TatumError::class.java).message)
}

inline fun <reified T> postObjectGetArrayTo(
    endpoint: String,
    params: Map<String, String> = emptyMap(),
    payload: Any? = null
): Array<T> {
    val data = payload?.let { mapper.convertValue(it, Map::class.java) }.orEmpty()
    val response = post(
        url = "$baseUrl$endpoint",
        headers = getApiKeyHeader(),
        params = params,
        json = data,
    )
    val body = response.jsonArray.toString()

    return if (response.statusCode in 200..206)
        mapper.readValue(body, mapper.typeFactory.constructArrayType(T::class.java))
    else
        throw TatumException(mapper.readValue(body, TatumError::class.java).message)
}

inline fun <reified T> putObjectTo(
    endpoint: String,
    params: Map<String, String> = emptyMap(),
    payload: Any? = null
): T {
    val data = payload?.let { mapper.convertValue(it, Map::class.java) }.orEmpty()
    val response = khttp.put(
        url = "$baseUrl$endpoint",
        headers = getApiKeyHeader(),
        params = params,
        json = data,
    )
    val body = response.jsonObject.toString()

    return if (response.statusCode in 200..206) mapper.readValue(body, T::class.java) else
        throw TatumException(mapper.readValue(body, TatumError::class.java).message)
}

inline fun <reified T> getArrayFrom(
    endpoint: String,
    params: Map<String, String> = emptyMap(),
): Array<T> {
    val response = get(
        url = "$baseUrl$endpoint",
        headers = getApiKeyHeader(),
        params = params,
    )
    val body = response.jsonArray.toString()

    return mapper.readValue(body, mapper.typeFactory.constructArrayType(T::class.java))
}

fun deleteFrom(
    endpoint: String,
    params: Map<String, String> = emptyMap(),
) {
    khttp.delete(
        url = "$baseUrl$endpoint",
        headers = getApiKeyHeader(),
        params = params,
    )
}

fun putTo(endpoint: String) {
    khttp.put(
        url = "$baseUrl$endpoint",
        headers = getApiKeyHeader(),
    )
}
