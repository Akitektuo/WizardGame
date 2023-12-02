package akitektuo.wizardgame.game.element.unit

import akitektuo.wizardgame.game.GameLoop
import akitektuo.wizardgame.game.model.Vector
import android.graphics.Canvas
import android.graphics.Paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

abstract class GameUnit(
    val position: Vector = Vector(),
    maximumSpeed: Float,
    color: Color
) {
    private val speed = maximumSpeed / GameLoop.MAXIMUM_UPDATES_PER_SECOND
    protected val paint = Paint()

    private var velocity = Vector()


    init {
        paint.color = color.toArgb()
    }

    abstract fun draw(canvas: Canvas)

    open fun update() {
        position += velocity
    }

    fun setVelocity(actuator: Vector) {
        velocity = actuator * speed
    }

    fun getDistanceToObject(otherUnit: GameUnit) = position.getLinearDistanceTo(otherUnit.position)
}