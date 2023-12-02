package akitektuo.wizardgame.game.element

import akitektuo.wizardgame.game.model.Vector
import android.graphics.Canvas
import android.graphics.Paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

class Joystick(
    private val position: Vector,
    private val areaRadius: Float,
    private val thumbRadius: Float,
    private val onChange: ((actuator: Vector) -> Unit)? = null
) {
    private val areaPaint = Paint()
    private val thumbPaint = Paint()

    private var thumbPosition = position.copy()
    var isPressed = false
        private set
    var actionId: Int = 0
        private set
    private var actuator = Vector()

    init {
        areaPaint.color = Color.Gray.toArgb()
        areaPaint.style = Paint.Style.FILL_AND_STROKE

        thumbPaint.color = Color.White.toArgb()
        thumbPaint.style = Paint.Style.FILL_AND_STROKE
    }

    fun draw(canvas: Canvas) {
        canvas.drawCircle(position.x, position.y, areaRadius, areaPaint)
        canvas.drawCircle(thumbPosition.x, thumbPosition.y, thumbRadius, thumbPaint)
    }

    fun update() {
        thumbPosition = position + actuator * areaRadius
    }

    fun setInput(touchPosition: Vector) {
        if (!isPressed)
            return

        setActuator(position.getVelocityTowards(touchPosition, areaRadius))
    }

    fun release() {
        isPressed = false
        actionId = 0
        resetActuator()
    }

    fun startTrackingIfPressed(actionId: Int, touchPosition: Vector): Boolean {
        val areaCenterToTouchDistance = position.getLinearDistanceTo(touchPosition)

        isPressed = areaCenterToTouchDistance < areaRadius
        if (isPressed)
            this.actionId = actionId
        return isPressed
    }

    private fun resetActuator() = setActuator(Vector())

    private fun setActuator(vector: Vector) {
        actuator = vector
        onChange?.invoke(actuator)
    }
}
