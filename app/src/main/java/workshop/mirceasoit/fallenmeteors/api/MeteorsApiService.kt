package workshop.mirceasoit.fallenmeteors.api

import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import workshop.mirceasoit.fallenmeteors.model.Meteor

private const val BASE_URL = "https://data.nasa.gov/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .client(
        OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build())
    .build()

interface MeteorsApiService {
    @GET("resource/y77d-th95.json?\$where=year>='1900-01-01T00:00:00.000' ORDER BY mass")
    fun getMeteors(): Call<List<Meteor>>

    @GET("resource/y77d-th95.json?\$where=year>='1900-01-01T00:00:00.000' ORDER BY mass")
    fun getMeteorsWithRxJava(): Observable<List<Meteor>>

    @GET("resource/y77d-th95.json?\$where=year>='1900-01-01T00:00:00.000' ORDER BY mass")
    suspend fun getMeteorsWithSuspendFunction(): List<Meteor>
}

object MeteorsApi {
    val retrofitService: MeteorsApiService by lazy { retrofit.create(
        MeteorsApiService::class.java) }
}