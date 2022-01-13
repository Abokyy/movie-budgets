package hu.benefanlabs.moviebudgets.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import kotlinx.coroutines.InternalCoroutinesApi


class MainActivity : AppCompatActivity() {
    @ExperimentalMaterialApi
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                MovieBudgetsApp()
            }
        }
    }
}
