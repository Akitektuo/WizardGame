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
    private var isPressed = false
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

        setActuator(position.getVelocityTowards(touchPosition))
    }

    fun release() {
        isPressed = false
        resetActuator()
    }

    fun startTrackingIfPressed(touchPosition: Vector): Boolean {
        val areaCenterToTouchDistance = position.getLinearDistanceTo(touchPosition)

        isPressed = areaCenterToTouchDistance < areaRadius
        return isPressed
    }

    private fun resetActuator() = setActuator(Vector())

    private fun setActuator(vector: Vector) {
        actuator = vector
        onChange?.invoke(actuator)
    }
}
