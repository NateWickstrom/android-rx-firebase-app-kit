package media.pixi.rx.algolia.search.data

data class PeopleSearchResult(
    val exhaustiveNbHits: Boolean,
    val hits: List<PersonSearchResult>,
    val hitsPerPage: Int,
    val nbHits: Int,
    val nbPages: Int,
    val page: Int,
    val params: String,
    val processingTimeMS: Int,
    val query: String
)