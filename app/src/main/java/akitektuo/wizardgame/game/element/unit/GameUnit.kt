package akitektuo.wizardgame.game.element.unit

import akitektuo.wizardgame.game.GameLoop
import android.graphics.Canvas
import android.graphics.Paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlin.math.pow
import kotlin.math.sqrt

abstract class GameUnit(
    var positionX: Float = 0f,
    var positionY: Float = 0f,
    maximumSpeed: Float,
    color: Color
) {
    private val speed = maximumSpeed / GameLoop.MAXIMUM_UPDATES_PER_SECOND
    protected val paint = Paint()

    private var velocityX = 0f
    private var velocityY = 0f

    init {
        paint.color = color.toArgb()
    }

    abstract fun draw(canvas: Canvas)

    open fun update() {
        positionX += velocityX
        positionY += velocityY
    }

    fun setVelocity(actuatorX: Float, actuatorY: Float) {
        velocityX = actuatorX * speed
        velocityY = actuatorY * speed
    }

    fun getDistanceToObject(otherUnit: GameUnit) =
        sqrt((otherUnit.positionX - positionX).pow(2) + (otherUnit.positionY - positionY).pow(2))
}