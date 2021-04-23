package de.sixbits.pixaclient.network

import dagger.Module
import dagger.Provides
import de.sixbits.pixaclient.network.manager.PixabayManager
import de.sixbits.pixaclient.network.service.PixabayService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
open class NetworkModule