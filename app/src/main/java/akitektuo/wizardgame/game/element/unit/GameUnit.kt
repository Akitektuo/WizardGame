package akitektuo.wizardgame.game.element.unit

import akitektuo.wizardgame.game.GameLoop
import akitektuo.wizardgame.game.definition.GameObject
import akitektuo.wizardgame.game.model.Vector
import android.graphics.Paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

abstract class GameUnit(
    val position: Vector = Vector(),
    maximumSpeed: Float,
    color: Color
) : GameObject {
    private val speed = maximumSpeed / GameLoop.MAXIMUM_UPDATES_PER_SECOND
    protected val paint = Paint()

    private var velocity = Vector()
    var direction = Vector()
        private set

    init {
        paint.color = color.toArgb()
    }

    override fun update() {
        position += velocity
    }

    fun setVelocity(actuator: Vector) {
        setDirection(actuator)
        velocity = actuator * speed
    }

    fun getDistanceToObject(otherUnit: GameUnit) = position.getLinearDistanceTo(otherUnit.position)

    private fun setDirection(actuator: Vector) {
        if (!actuator.isOrigin) {
            direction = actuator
        }
    }
}