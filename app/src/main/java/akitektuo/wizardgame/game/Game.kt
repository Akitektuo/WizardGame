package akitektuo.wizardgame.game

import akitektuo.wizardgame.game.element.Enemy
import akitektuo.wizardgame.game.element.Spell
import akitektuo.wizardgame.game.model.toVector
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

class Game(context: Context) : SurfaceView(context),
    SurfaceHolder.Callback {
    companion object {
        const val JOYSTICK_MARGIN = 300f
    }

    private val gameLoop: GameLoop

    init {
        holder.addCallback(this)
        gameLoop = GameLoop(this, holder)
        isFocusable = true
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        state.initialize(width.toFloat(), height.toFloat())
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

        with(state) {
            player.draw(canvas)
            joystick.draw(canvas)
            enemies.forEach { it.draw(canvas) }
            spells.forEach { it.draw(canvas) }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean = with(state) {
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_POINTER_DOWN -> {
                if (joystick.isPressed) {
                    Spell.spellsToCast++
                } else if (!joystick.startTrackingIfPressed(
                        event.getActionId(),
                        event.toVector()
                    )
                ) {
                    Spell.spellsToCast++
                }
            }

            MotionEvent.ACTION_MOVE -> joystick.setInput(event.toVector())
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_POINTER_UP -> {
                if (joystick.actionId == event.getActionId()) {
                    joystick.release()
                }
            }

            else -> return super.onTouchEvent(event)
        }

        return true
    }

    fun update() = with(state) {
        player.update()
        joystick.update()

        if (Enemy.isReadyToSpawn()) {
            enemies.add(Enemy(boundary, player))
        }
        while (Spell.spellsToCast > 0) {
            spells.add(Spell(boundary, player))
            Spell.spellsToCast--
        }

        enemies.forEach(Enemy::update)
        enemies.removeIf { enemy ->
            enemy.update()
            spells.removeIf { spell ->
                spell.update()
                spell.isColliding(enemy)
            } || enemy.isCollidingWithPlayer()
        }
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

    private fun MotionEvent.getActionId() = getPointerId(actionIndex)
}