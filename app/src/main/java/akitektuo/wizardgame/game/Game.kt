package akitektuo.wizardgame.game

import akitektuo.wizardgame.game.element.Enemy
import akitektuo.wizardgame.game.element.Spell
import akitektuo.wizardgame.game.hud.PerformanceMetrics
import akitektuo.wizardgame.game.model.toVector
import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class Game(context: Context) : SurfaceView(context),
    SurfaceHolder.Callback {
    companion object {
        const val JOYSTICK_MARGIN = 300f
    }

    private var gameLoop: GameLoop
    private var performanceMetrics: PerformanceMetrics

    init {
        holder.addCallback(this)
        gameLoop = GameLoop(this, holder)
        performanceMetrics = PerformanceMetrics(gameLoop)
        isFocusable = true
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        if (gameLoop.state == Thread.State.TERMINATED) {
            gameLoop = GameLoop(this, holder)
            performanceMetrics = PerformanceMetrics(gameLoop)
        }

        state.initialize(width.toFloat(), height.toFloat())
        gameLoop.startLoop()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        performanceMetrics.draw(canvas)

        with(state) {
            player.draw(canvas)
            joystick.draw(canvas)
            enemies.forEach { it.draw(canvas) }
            spells.forEach { it.draw(canvas) }

            if (player.isDead)
                gameOver.draw(canvas)
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
        if (player.isDead) return@with

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

    fun pause() {
        gameLoop.stopLoop()
    }

    private fun MotionEvent.getActionId() = getPointerId(actionIndex)
}