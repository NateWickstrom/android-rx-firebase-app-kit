package media.pixi.appkit.ui.search

import com.algolia.instantsearch.core.highlighting.HighlightedString
import com.algolia.instantsearch.helper.highlighting.Highlightable
import com.algolia.search.model.Attribute
import kotlinx.serialization.Transient
import kotlinx.serialization.json.JsonObject


data class User(
    val firstname: String,
    val lastname: String,
    val username: String,
    val imageUrl: String?,
    val id: String,
    override val _highlightResult: JsonObject?
) : Highlightable {

    @Transient
    public val highlightedFirstName: HighlightedString?
        get() = getHighlight(Attribute("firstname"))

    @Transient
    public val highlightedLastName: HighlightedString?
        get() = getHighlight(Attribute("lastname"))
}