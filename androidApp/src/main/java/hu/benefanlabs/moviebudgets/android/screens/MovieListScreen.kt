package hu.benefanlabs.moviebudgets.android.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.insets.navigationBarsPadding
import hu.benefanlabs.moviebudgets.android.components.MoviesRow
import hu.benefanlabs.moviebudgets.android.components.SearchBar
import hu.benefanlabs.moviebudgets.android.navigation.NavigationRoutes
import hu.benefanlabs.moviebudgets.domain.entities.Movie
import hu.benefanlabs.moviebudgets.features.MovieListViewModel
import kotlinx.coroutines.flow.collect
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable
fun MovieListScreen(
    navController: NavController,
    movieListViewModel: MovieListViewModel,
    showSnackbar: (String, SnackbarDuration) -> Unit
) {
    val state by movieListViewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        movieListViewModel.news.collect { news ->
            when (news) {
                is MovieListViewModel.News.ShowError -> showSnackbar(
                    "Something went wrong",
                    SnackbarDuration.Short
                )
                MovieListViewModel.News.EmptySearchResult -> showSnackbar(
                    "No search result for ${state.queryString}",
                    SnackbarDuration.Short
                )
            }
        }
    }

    MovieListScreenContent(
        popularMoviesList = state.popularMoviesList,
        nowPlayingMoviesList = state.nowPlayingMoviesList,
        searchedMoviesList = state.searchedMoviesList,
        queryString = state.queryString ?: "",
        onQueryStringChange = {
            movieListViewModel.sendWish(MovieListViewModel.Wish.SetQueryString(it))
        },
        searchForMovies = {
            movieListViewModel.sendWish(MovieListViewModel.Wish.SearchForMovies)
        },
        isLoading = state.loading,
        navigateToDetails = { movieId ->
            navController.navigate(NavigationRoutes.MovieDetails.getNavLinkByArgument(movieId.toString()))
        }
    )
}

@ExperimentalMaterialApi
@Composable
private fun MovieListScreenContent(
    popularMoviesList: List<Movie>?,
    nowPlayingMoviesList: List<Movie>?,
    searchedMoviesList: List<Movie>?,
    queryString: String,
    onQueryStringChange: (String) -> Unit,
    searchForMovies: () -> Unit,
    isLoading: Boolean,
    navigateToDetails: (Int) -> Unit
) {

    var didSearchForMovies by rememberSaveable { mutableStateOf(false) }
    val toolbarHeight = 120.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // try to consume before LazyColumn to collapse toolbar if needed, hence pre-scroll
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                // here's the catch: let's pretend we consumed 0 in any case, since we want
                // LazyColumn to scroll anyway for good UX
                // We're basically watching scroll without taking it
                return Offset.Zero
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            // attach as a parent to the nested scroll system
            .nestedScroll(nestedScrollConnection)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(top = toolbarHeight)
        ) {
            item {
                if (didSearchForMovies && isLoading) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 36.dp)) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = MaterialTheme.colors.secondary
                        )
                    }
                }
            }
            item {
                if (didSearchForMovies && !searchedMoviesList.isNullOrEmpty()) {
                    MoviesRow(
                        modifier = Modifier.padding(top = 36.dp),
                        title = "Searched movies",
                        movies = searchedMoviesList,
                        isLoading = isLoading,
                        onItemClick = navigateToDetails
                    )
                }
                MoviesRow(
                    modifier = Modifier.padding(top = 36.dp),
                    title = "Popular movies",
                    movies = popularMoviesList,
                    isLoading = isLoading,
                    onItemClick = navigateToDetails
                )

                MoviesRow(
                    modifier = Modifier
                        .padding(vertical = 36.dp),
                    title = "Now playing",
                    movies = nowPlayingMoviesList,
                    isLoading = isLoading,
                    onItemClick = navigateToDetails
                )
            }
        }

        MovieListScreenToolbar(
            toolbarHeight = toolbarHeight,
            toolbarOffsetHeightPx = toolbarOffsetHeightPx.value,
            searchValue = queryString,
            onSearchValueChange = onQueryStringChange,
            onSearch = {
                searchForMovies()
                didSearchForMovies = true
            }
        )
    }
}


@Composable
fun MovieListScreenToolbar(
    toolbarHeight: Dp,
    toolbarOffsetHeightPx: Float,
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    val localFocusManager = LocalFocusManager.current

    TopAppBar(
        modifier = Modifier
            .height(toolbarHeight)
            .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.roundToInt()) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp)
        ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .align(Alignment.Center),
                hint = "Search for movies",
                searchValue = searchValue,
                onSearchValueChange = onSearchValueChange,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    localFocusManager.clearFocus()
                    onSearch()
                })
            )
        }
    }
}