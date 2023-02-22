package az.khayalsharifli.workmanagerapp.data

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/photo-1617895153857-82fe79adfcd4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80")
    suspend fun getImage(): Response<ResponseBody>
}