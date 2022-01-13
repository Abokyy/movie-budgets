package hu.benefanlabs.moviebudgets.android.screens

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import hu.benefanlabs.moviebudgets.android.R
import hu.benefanlabs.moviebudgets.android.components.formatToBudget
import hu.benefanlabs.moviebudgets.domain.entities.Movie
import hu.benefanlabs.moviebudgets.features.MovieDetailsViewModel
import hu.benefanlabs.moviebudgets.features.MovieListViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun MovieDetailsScreen(
    movieDetailsViewModel: MovieDetailsViewModel,
    showSnackbar: (String, SnackbarDuration) -> Unit
) {

    val state by movieDetailsViewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        movieDetailsViewModel.news.collect { news ->
            when (news) {
                is MovieDetailsViewModel.News.ShowError -> showSnackbar(
                    "Something went wrong",
                    SnackbarDuration.Short
                )
            }
        }
    }

    MovieDetailsContent(movie = state.movie)
}

@Composable
private fun MovieDetailsContent(
    movie: Movie?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        movie?.let {
            Image(
                modifier = Modifier
                    .height(450.dp)
                    .fillMaxWidth(),
                painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${movie.posterPath}"),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
            )

            Text(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
                text = movie.title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h3,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.secondary
            )

            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = "Budget: ${movie.budget.formatToBudget()}",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.secondary
            )

            Row(
                modifier = Modifier.padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource(id = R.drawable.star), contentDescription = null)
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = "Rating: ${movie.voteAverage}",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.secondary
                )
            }

        } ?: run {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colors.secondary
                )
            }
        }
    }
}