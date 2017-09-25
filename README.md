# Project 2 - *NYTSearch*

**NYTSearch** is an android app that allows a user to search for articles on web using simple filters. The app utilizes [New York Times Search API](http://developer.nytimes.com/docs/read/article_search_api_v2).

Time spent: **24** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can enter a search query that will display a grid of news articles using the thumbnail and headline from the New York Times Search API.
* [x] User can click on "filter" icon which allows selection of advanced search options to filter articles.
    * [x] An example of a query with filters (begin_date, sort, and news_desk)
* [x] User can configure advanced search filters applied to the articles search
    * [x] Begin Date
    * [x] Sort order (oldest or newest) using a spinner dropdown
    * [x] News desk values (Arts, Fashion & Style, Sports) using checkboxes
* [x] Subsequent searches have any filters applied to the search results
* [x] User can tap on any article in results to view the contents in an embedded browser.
* [x] User can **scroll down to see more articles**. The maximum number of articles is limited by the API search.

The following **optional** features are implemented:
* [x] Robust error handling, check if internet is available, handle error cases, network failures
* [x] Use the ActionBar SearchView or custom layout as the query box instead of an EditText.
* [x] User can share a link to their friends or email it to themselves.
* [x] Replace Filter Settings Activity with a lightweight modal overlay.
* [x] Improve the user interface and experiment with image assets and/or styling and coloring

The following **stretch** features are implemented:
* [x] Use the RecyclerView with the StaggeredGridLayoutManager to display improve the grid of image results (see Picasso guide too)
* [x] For different news articles that only have text or have text with thumbnails, use Heterogenous Layouts with RecyclerView.
* [x] Use Parcelable instead of Serializable using the popular Parceler library.
* [x] Replace all icon drawables and other static image assets with vector drawables where appropriate.
* [x] Leverage the data binding support module to bind data into one or more activity layout templates.
* [x] Replace Picasso with Glide for more efficient image rendering.
* [x] Switch to using retrolambda expressions to cleanup event handling blocks.
* [x] Leverage the popular GSON library to streamline the parsing of JSON data.
* [x] Consume the New York Times API using the popular Retrofit networking library instead of Android Async HTTP.
* [x] Replace the embedded WebView for viewing news articles with a Chrome Custom Tab using a custom action icon for sharing.

The following **stretch** features(not included in the given list was also implemented).
* [x] Added animation for the filter dialog (Upon clicking on it, it comes from down and upon clicking on Save button, it slides to the bottom).
* [x] Date picker does not allow user to select future dates.
* [x] Implemented two progress bars, one for general loading of articles which will be centered and the other is for infinite scrolling (it is placed at the bottom of the screen).
* [x] Implemented retries for network calls by adding an interceptor (more details can be found at RetrofitUtils class).
* [x] Added butterknife broilerplate for SearchActivity class.

Here's a walkthrough of implemented user stories:
