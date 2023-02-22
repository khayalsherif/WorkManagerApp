package az.khayalsharifli.workmanagerapp.data

class ImageRepositoryImpl(private val service: ApiService) : ImageRepository {
    override suspend fun getImage() = service.getImage()
}