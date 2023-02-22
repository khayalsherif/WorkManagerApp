package az.khayalsharifli.workmanagerapp.di

import az.khayalsharifli.workmanagerapp.BuildConfig
import az.khayalsharifli.workmanagerapp.data.ApiService
import az.khayalsharifli.workmanagerapp.data.ImageRepository
import az.khayalsharifli.workmanagerapp.data.ImageRepositoryImpl
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {

    /////////////////////////////////////////////// REMOTE //////////////////////////////////////////////
    single {
        val client = OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .callTimeout(20, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logger =
                HttpLoggingInterceptor().setLevel(level = HttpLoggingInterceptor.Level.BODY)
            client.addInterceptor(logger)
        }
        client.build()
    }

    factory<ApiService> { get<Retrofit>().create(ApiService::class.java) }

    single {
        Retrofit.Builder()
            .baseUrl(getProperty<String>("base"))
            .client(get())
            .build()
    }

    /////////////////////////////////////////////// REPOSITORY //////////////////////////////////////////////
    factory<ImageRepository> { ImageRepositoryImpl(service = get()) }
}