package az.khayalsharifli.workmanagerapp.data

import okhttp3.ResponseBody
import retrofit2.Response

interface ImageRepository {
    suspend fun getImage():Response<ResponseBody>
}