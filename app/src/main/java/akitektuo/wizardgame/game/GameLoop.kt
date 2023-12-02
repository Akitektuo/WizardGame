package akitektuo.wizardgame.game

import android.graphics.Canvas
import android.view.SurfaceHolder
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

class GameLoop(private val game: Game, private val surfaceHolder: SurfaceHolder) : Thread() {
    companion object {
        const val MAXIMUM_UPDATES_PER_SECOND = 60f
        private const val UPDATES_PER_SECOND_PERIOD = 1E+3 / MAXIMUM_UPDATES_PER_SECOND
    }

    private var isRunning: Boolean = false
    var averageFramesPerSecond: Double = 0.0
    var averageUpdatesPerSecond: Double = 0.0

    fun startLoop() {
        if (isRunning)
            return
        isRunning = true
        start()
    }

    override fun run() {
        super.run()

        var canvas: Canvas? = null

        var updateCount = 0
        var frameCount = 0
        var sleepTime: Long

        var startTime = System.currentTimeMillis()

        while (isRunning) {
            try {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    game.update()
                    updateCount++

                    if (canvas != null) {
                        game.draw(canvas)
                    }
                }
            } catch (exception: IllegalArgumentException) {
                exception.printStackTrace()
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas)
                    frameCount++
                }
            }


            var elapsedTime = System.currentTimeMillis() - startTime
            sleepTime = (updateCount * UPDATES_PER_SECOND_PERIOD - elapsedTime).toLong()
            if (sleepTime > 0) {
                sleep(sleepTime)
            }

            while (sleepTime < 0 && updateCount < MAXIMUM_UPDATES_PER_SECOND - 1) {
                game.update()
                updateCount++
                elapsedTime = System.currentTimeMillis() - startTime
                sleepTime = (updateCount * UPDATES_PER_SECOND_PERIOD - elapsedTime).toLong()
            }

            elapsedTime = System.currentTimeMillis() - startTime
            if (elapsedTime >= 1.seconds.toLong(DurationUnit.MILLISECONDS)) {
                val elapsedTimeInSeconds = elapsedTime * 1E-3
                averageUpdatesPerSecond = updateCount / elapsedTimeInSeconds
                averageFramesPerSecond = frameCount / elapsedTimeInSeconds
                updateCount = 0
                frameCount = 0
                startTime = System.currentTimeMillis()
            }
        }
    }
}
