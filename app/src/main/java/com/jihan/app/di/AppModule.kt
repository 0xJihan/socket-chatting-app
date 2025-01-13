package com.jihan.app.di

import com.jihan.app.domain.networking.AuthApi
import com.jihan.app.domain.repository.AuthRepository
import com.jihan.app.domain.utils.Constants.BASE_URL
import com.jihan.app.domain.utils.DatastoreUtil
import com.jihan.app.domain.viewmodel.TokenViewmodel
import com.jihan.app.domain.viewmodel.AuthViewmodel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {

    single {
        DatastoreUtil(androidContext())
    }

    single<Retrofit.Builder> {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
    }

//    single {
//    OkHttpClient().newBuilder().addInterceptor(NetworkInterceptor(get())).build()
//    }

//    single {
//        get<Retrofit.Builder>().build().create(UserApi::class.java)
//    }

    singleOf(::AuthRepository)
    factory {
        get<Retrofit.Builder>().build().create(AuthApi::class.java)
    }
    viewModelOf(::TokenViewmodel)
    viewModelOf(::AuthViewmodel)

}


