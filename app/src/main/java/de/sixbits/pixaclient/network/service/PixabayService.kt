package de.sixbits.pixaclient.network.service

import de.sixbits.pixaclient.network.response.PixabaySearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PixabayService {
    @GET("/api/")
    fun getSearchResult(
        @Query("q") query: String,
        @Query("key") apiKey: String
    ): Observable<PixabaySearchResponse>

    @GET("/api/")
    fun getImageDetails(
        @Query("id") id: Int,
        @Query("key") apiKey: String
    ): Observable<PixabaySearchResponse>
}
