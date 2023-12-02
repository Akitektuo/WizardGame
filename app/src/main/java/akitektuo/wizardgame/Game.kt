package akitektuo.wizardgame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

class Game(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    private val gameLoop: GameLoop

    init {
        holder.addCallback(this)
        gameLoop = GameLoop(this, holder)
        isFocusable = true
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        gameLoop.startLoop()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawUpdatesPerSecond()
        canvas.drawFramesPerSecond()
    }

    private fun Canvas.drawUpdatesPerSecond() =
        drawText("UPS: ${gameLoop.averageUpdatesPerSecond}", 100f, 100f, Paint().apply {
            color = Color.Magenta.toArgb()
            textSize = 50f
        })

    private fun Canvas.drawFramesPerSecond() =
        drawText("FPS: ${gameLoop.averageFramesPerSecond}", 100f, 200f, Paint().apply {
            color = Color.Magenta.toArgb()
            textSize = 50f
        })

    fun update() {

    }
}