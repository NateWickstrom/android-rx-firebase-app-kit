package media.pixi.appkit.data.search

data class PersonSearchResult(
    val firstname: String,
    val lastname: String,
    val username: String,
    val imageUrl: String?,
    val id: String
)