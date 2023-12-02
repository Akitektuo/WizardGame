package akitektuo.wizardgame.game.element.unit

import android.graphics.Canvas
import androidx.compose.ui.graphics.Color

open class CircleGameUnit(
    positionX: Float,
    positionY: Float,
    maximumSpeed: Float,
    color: Color,
    private var radius: Float = 30f,
) : GameUnit(positionX, positionY, maximumSpeed, color) {
    override fun draw(canvas: Canvas) {
        canvas.drawCircle(positionX, positionY, radius, paint)
    }
}