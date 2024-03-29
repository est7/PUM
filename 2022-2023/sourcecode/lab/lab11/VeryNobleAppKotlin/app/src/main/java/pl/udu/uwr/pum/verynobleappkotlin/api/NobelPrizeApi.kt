package pl.udu.uwr.pum.verynobleappkotlin.api

import pl.udu.uwr.pum.verynobleappkotlin.data.laureateresponse.LaureateResponse
import pl.udu.uwr.pum.verynobleappkotlin.data.laureateresponse.LaureateResponseItem
import pl.udu.uwr.pum.verynobleappkotlin.data.nobelprizeresponse.NobelPrizeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface NobelPrizeApi {
    @GET("2.1/nobelPrizes")
    suspend fun getNobelPrizes(
        @Query("limit") limit: Int = 200,
        @Query("sort") sort: String = "desc",
        @Query("nobelPrizeYear") yearFrom: Int = 1901,
        @Query("yearTo") yearTo: Int = 2022,
        @Query("nobelPrizeCategory") category: String,
        @Query("format") format: String = "json"
    ) : Response<NobelPrizeResponse>

    @GET("2.1/nobelPrizes")
    suspend fun getNobelPrize(
        @Query("limit") limit: Int = 1,
        @Query("sort") sort: String = "desc",
        @Query("nobelPrizeYear") year: Int,
        @Query("nobelPrizeCategory") category: String,
    ) : Response<NobelPrizeResponse>

    @GET("2.1/laureate/{laureateID}")
    suspend fun getLaureates(@Path("laureateID") id: String) : Response<LaureateResponse>
}