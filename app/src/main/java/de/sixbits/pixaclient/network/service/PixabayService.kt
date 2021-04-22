package de.sixbits.pixaclient.network.service

import de.sixbits.pixaclient.network.response.PixabaySearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface PixabayService {
    @GET("{query}")
    fun getSearchResult(@Path("query") query: String): Observable<PixabaySearchResponse?>
}
