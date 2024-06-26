package akitektuo.wizardgame

import akitektuo.wizardgame.game.Game
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideNavigation()

        game = Game(this)
        setContentView(game)
    }

    override fun onPause() {
        game.pause()
        super.onPause()
    }

    private fun hideNavigation() = with(window.decorView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowInsetsController?.hide(WindowInsets.Type.navigationBars())
        } else {
            @Suppress("DEPRECATION")
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
    }
}