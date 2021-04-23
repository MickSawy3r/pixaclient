package de.sixbits.pixaclient.network

import dagger.Module
import dagger.Provides
import de.sixbits.pixaclient.network.manager.PixabayManager
import de.sixbits.pixaclient.network.service.PixabayService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
open class NetworkModule {

    @Singleton
    @Provides
    fun providePixabayManager(pixabayService: PixabayService): PixabayManager {
        return PixabayManager(pixabayService)
    }

    @Singleton
    @Provides
    fun providePixabayService(retrofit: Retrofit): PixabayService {
        return retrofit.create(PixabayService::class.java)
    }
}