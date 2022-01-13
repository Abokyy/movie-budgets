package hu.benefanlabs.moviebudgets.android

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import hu.benefanlabs.moviebudgets.android.navigation.Navigation
import hu.benefanlabs.moviebudgets.android.styling.AppTheme
import hu.benefanlabs.moviebudgets.features.FeaturesModule
import kotlinx.coroutines.InternalCoroutinesApi
import org.kodein.di.compose.withDI

@ExperimentalMaterialApi
@InternalCoroutinesApi
@Composable
fun MovieBudgetsApp() = withDI(di = FeaturesModule) {
    AppTheme {
        val appState: MovieBudgetsAppState = rememberMovieBudgetsAppState()


        val systemUiController = rememberSystemUiController()
        val useDarkIcons = MaterialTheme.colors.isLight

        SideEffect {
            systemUiController.setSystemBarsColor(
                color = Color.Transparent,
                darkIcons = useDarkIcons
            )
        }
        appState.apply {
            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = it, modifier = Modifier.navigationBarsWithImePadding())
                },
                scaffoldState = scaffoldState
            ) {
                Navigation(navController = navController, showSnackbar = { message, duration ->
                    showSnackbar(message = message, duration = duration)
                })
            }
        }
    }

}