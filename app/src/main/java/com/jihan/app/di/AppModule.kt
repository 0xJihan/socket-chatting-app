package com.jihan.app.di

import com.jihan.app.domain.utils.DatastoreUtil
import com.jihan.app.domain.utils.json
import com.jihan.app.domain.viewmodel.TokenViewmodel
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.converter.kotlinx.serialization.asConverterFactory


val appModule = module {
    val jsonConverterFactory = json.asConverterFactory("application/json; charset=UTF8".toMediaType())

    single {
        DatastoreUtil(androidContext())
    }

    viewModelOf(::TokenViewmodel)

}


