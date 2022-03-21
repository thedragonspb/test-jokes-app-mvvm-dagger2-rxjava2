package thedragonspb.testjokesapp.search.gateway.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("result")
    val searchResult: List<SearchResult>,
    val total: Int
)

data class SearchResult(
    val categories: List<String>,
    val created_at: String,
    val icon_url: String,
    val id: String,
    val updated_at: String,
    val url: String,
    val value: String
)