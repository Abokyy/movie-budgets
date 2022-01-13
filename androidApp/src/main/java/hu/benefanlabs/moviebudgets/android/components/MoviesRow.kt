package hu.benefanlabs.moviebudgets.android.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.benefanlabs.moviebudgets.domain.entities.Movie

@ExperimentalMaterialApi
@Composable
fun MoviesRow(
    modifier: Modifier = Modifier,
    title: String,
    movies: List<Movie>?,
    isLoading: Boolean = false,
    onItemClick: (Int) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .padding(horizontal = 24.dp),
            text = title,
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.secondary,
            fontWeight = FontWeight.Bold
        )
        if (isLoading && movies.isNullOrEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colors.secondary
                )
            }
        } else {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                movies?.forEach { movie ->
                    item {
                        MovieCard(movie = movie, onClick = {
                            onItemClick(movie.id)
                        })
                    }
                }
            }
        }
    }
}