package media.pixi.rx.algolia.search.data

interface SearchDataSource {

    fun search(keyword: String)

}