package akitektuo.wizardgame.game.hud

import akitektuo.wizardgame.game.GameLoop
import akitektuo.wizardgame.game.definition.GameObject
import android.graphics.Canvas
import android.graphics.Paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

class PerformanceMetrics(private val gameLoop: GameLoop) : GameObject {
    override fun update() {
        TODO("Not yet implemented")
    }

    override fun draw(canvas: Canvas) {
        drawUpdatesPerSecond(canvas)
        drawFramesPerSecond(canvas)
    }

    private fun drawUpdatesPerSecond(canvas: Canvas) =
        canvas.drawText("UPS: ${gameLoop.averageUpdatesPerSecond}", 100f, 100f, Paint().apply {
            color = Color.Magenta.toArgb()
            textSize = 50f
        })

    private fun drawFramesPerSecond(canvas: Canvas) =
        canvas.drawText("FPS: ${gameLoop.averageFramesPerSecond}", 100f, 200f, Paint().apply {
            color = Color.Magenta.toArgb()
            textSize = 50f
        })
}