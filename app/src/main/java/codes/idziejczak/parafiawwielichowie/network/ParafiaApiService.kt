package codes.idziejczak.parafiawwielichowie.network

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.parser.Parser
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import java.lang.reflect.Type
import java.nio.charset.Charset

private const val BASE_URL = "https://parafiawielichowo.pl/"

val logging: HttpLoggingInterceptor =
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
private val client: OkHttpClient = OkHttpClient().newBuilder()
    .addInterceptor(logging)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(JsoupConverterFactory)
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface ParafiaApiService {
    @GET("ogloszenia,parafialne,{id}.html")
    suspend fun getOgloszenie(@Path("id") id: String): Document

    @GET("ogloszeniaparafialne.html")
    suspend fun getIdOgloszenia(): Document
}

object ParafiaApi {
    val retrofitService: ParafiaApiService by lazy {
        retrofit.create(ParafiaApiService::class.java)
    }
}

object JsoupConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type?,
        annotations: Array<Annotation>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody, *>? {
        return when (type) {
            Document::class.java -> JsoupConverter(retrofit!!.baseUrl().toString())
            else -> null
        }
    }

    private class JsoupConverter(val baseUri: String) : Converter<ResponseBody, Document?> {

        override fun convert(value: ResponseBody?): Document? {
            val charset = value?.contentType()?.charset() ?: Charset.forName("ISO-8859-2")

            val parser = when (value?.contentType().toString()) {
                "application/xml", "text/xml" -> Parser.xmlParser()
                else -> Parser.htmlParser()
            }

            return Jsoup.parse(value?.byteStream(), charset.name(), baseUri, parser)
        }

    }

}