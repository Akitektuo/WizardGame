package akitektuo.wizardgame.game

import akitektuo.wizardgame.game.element.Enemy
import akitektuo.wizardgame.game.element.Joystick
import akitektuo.wizardgame.game.element.Player
import akitektuo.wizardgame.game.model.Boundary
import akitektuo.wizardgame.game.model.Vector
import akitektuo.wizardgame.game.model.toVector
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

class Game(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    companion object {
        private const val JOYSTICK_MARGIN = 300f
    }

    private val gameLoop: GameLoop
    private lateinit var boundary: Boundary
    private lateinit var player: Player
    private lateinit var joystick: Joystick
    private lateinit var enemy: Enemy

    init {
        holder.addCallback(this)
        gameLoop = GameLoop(this, holder)
        isFocusable = true
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        initializeComponents()
        gameLoop.startLoop()
    }

    private fun initializeComponents() {
        boundary = Boundary(0f, width.toFloat(), height.toFloat(), 0f)
        player = Player(Vector(width / 2f, height / 2f), boundary)
        joystick = Joystick(
            Vector(JOYSTICK_MARGIN, height - JOYSTICK_MARGIN),
            100f,
            50f,
            player::setVelocity
        )
        enemy = Enemy(Vector(0f, height / 2f), player)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawUpdatesPerSecond()
        canvas.drawFramesPerSecond()

        player.draw(canvas)
        joystick.draw(canvas)
        enemy.draw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> joystick.startTrackingIfPressed(event.toVector())
            MotionEvent.ACTION_MOVE -> joystick.setInput(event.toVector())
            MotionEvent.ACTION_UP -> joystick.release()
            else -> return super.onTouchEvent(event)
        }

        return true
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
        player.update()
        joystick.update()
        enemy.update()
    }
}