package hu.benefanlabs.moviebudgets.android.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hu.benefanlabs.moviebudgets.android.screens.MovieDetailsScreen
import hu.benefanlabs.moviebudgets.android.screens.MovieListScreen
import hu.benefanlabs.moviebudgets.android.screens.SplashScreen
import hu.benefanlabs.moviebudgets.features.MovieDetailsViewModel
import hu.benefanlabs.moviebudgets.features.MovieListViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import org.kodein.di.compose.instance

@ExperimentalMaterialApi
@InternalCoroutinesApi
@Composable
fun Navigation(
    navController: NavHostController,
    showSnackbar: (String, SnackbarDuration) -> Unit
) {

    NavHost(navController = navController, startDestination = NavigationRoutes.Splash.route) {
        composable(route = NavigationRoutes.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = NavigationRoutes.MovieList.route) {
            val movieListViewModel: MovieListViewModel by instance()
            movieListViewModel.onStart()
            MovieListScreen(
                navController = navController,
                movieListViewModel = movieListViewModel,
                showSnackbar = showSnackbar
            )
        }
        composable(route = NavigationRoutes.MovieDetails.route) { navBackStackEntry ->
            val movieId = navBackStackEntry.arguments?.getString("movieId")
            val movieDetailsViewModel: MovieDetailsViewModel by instance(arg = movieId!!.toInt())
            movieDetailsViewModel.onStart()

            MovieDetailsScreen(
                movieDetailsViewModel = movieDetailsViewModel,
                showSnackbar = showSnackbar
            )
        }
    }
}