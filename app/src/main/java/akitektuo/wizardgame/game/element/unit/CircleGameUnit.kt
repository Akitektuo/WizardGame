package akitektuo.wizardgame.game.element.unit

import akitektuo.wizardgame.game.model.Vector
import android.graphics.Canvas
import androidx.compose.ui.graphics.Color

open class CircleGameUnit(
    position: Vector,
    maximumSpeed: Float,
    color: Color,
    private var radius: Float = 30f,
) : GameUnit(position, maximumSpeed, color) {
    override fun draw(canvas: Canvas) {
        canvas.drawCircle(position.x, position.y, radius, paint)
    }

    fun getDistanceToCollision(otherCircle: CircleGameUnit) = radius + otherCircle.radius

    fun isColliding(otherCircle: CircleGameUnit) =
        getDistanceToObject(otherCircle) < getDistanceToCollision(otherCircle)
}