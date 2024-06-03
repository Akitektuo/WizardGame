package akitektuo.wizardgame.game.hud

import akitektuo.wizardgame.game.definition.GameObject
import akitektuo.wizardgame.game.definition.ScreenSizeDependency
import android.graphics.Canvas
import android.graphics.Paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

class GameOver(var width: Float, var height: Float) : GameObject, ScreenSizeDependency {
    override fun update() {
        TODO("Not yet implemented")
    }

    override fun draw(canvas: Canvas) {
        canvas.drawText("Game Over", width / 2, 200f, Paint().apply {
            color = Color.Red.toArgb()
            textSize = 150f
        })
    }

    override fun updateScreenSize(width: Float, height: Float) {
        this.width = width
        this.height = height
    }
}