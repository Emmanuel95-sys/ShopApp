package mx.arturo.triple.model.webservice

import android.util.Log
import com.google.gson.JsonObject
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import mx.arturo.triple.model.webservice.response.ActiveWebServiceResponse
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

//use POST method
//full URL https://pilot-triplei.webteam.mx/team-service/Tracker.Procesos.svc/getConjuntotiendasUsuario
const val base_url =  "https://pilot-triplei.webteam.mx/team-service/Tracker.Procesos.svc/"

const val json_body_string  =  "{\"Usuario\":{\"Id\":\"11208\"},\"Proyecto\":{\"Id\":\"137\", \"Ufechadescarga\" : 0}}"



interface ActivesWebService {

    @POST("getConjuntotiendasUsuario")
    fun getActives(
    ): Deferred<ActiveWebServiceResponse>

    companion object{

        var requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"),json_body_string)

        operator fun invoke() : ActivesWebService{

            val requestInterceptor = Interceptor{chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .build()

                Log.i("URL REQUEST: " , url.toString())

                val request = chain.request()
                    .newBuilder()
                    .post(requestBody)
                    .url(url)
                    .build()
                return@Interceptor  chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(base_url)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ActivesWebService::class.java)
        }
    }
}