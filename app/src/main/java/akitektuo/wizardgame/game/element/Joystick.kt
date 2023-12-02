package akitektuo.wizardgame.game.element

import android.graphics.Canvas
import android.graphics.Paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sqrt

class Joystick(
    private val positionX: Float,
    private val positionY: Float,
    private val areaRadius: Float,
    private val thumbRadius: Float,
    private val onChange: ((actuatorX: Float, actuatorY: Float) -> Unit)? = null
) {
    private val areaPaint = Paint()
    private val thumbPaint = Paint()

    private var thumbPositionX = positionX
    private var thumbPositionY = positionY
    private var isPressed = false
    private var actuatorX = 0f
    private var actuatorY = 0f

    init {
        areaPaint.color = Color.Gray.toArgb()
        areaPaint.style = Paint.Style.FILL_AND_STROKE

        thumbPaint.color = Color.White.toArgb()
        thumbPaint.style = Paint.Style.FILL_AND_STROKE
    }

    fun draw(canvas: Canvas) {
        canvas.drawCircle(positionX, positionY, areaRadius, areaPaint)
        canvas.drawCircle(thumbPositionX, thumbPositionY, thumbRadius, thumbPaint)
    }

    fun update() {
        thumbPositionX = positionX + actuatorX * areaRadius
        thumbPositionY = positionY + actuatorY * areaRadius
    }

    fun setInput(touchPositionX: Float, touchPositionY: Float) {
        if (!isPressed)
            return

        val deltaX = touchPositionX - positionX
        val deltaY = touchPositionY - positionY
        val deltaDistance = sqrt(deltaX.pow(2) + deltaY.pow(2))
        val referenceDistance = max(areaRadius, deltaDistance)
        setActuator(deltaX / referenceDistance, deltaY / referenceDistance)
    }

    fun release() {
        isPressed = false
        resetActuator()
    }

    fun startTrackingIfPressed(touchPositionX: Float, touchPositionY: Float): Boolean {
        val areaCenterToTouchDistance = sqrt(
            (positionX - touchPositionX).pow(2) + (positionY - touchPositionY).pow(2)
        )
        isPressed = areaCenterToTouchDistance < areaRadius
        return isPressed
    }

    private fun resetActuator() = setActuator(0f, 0f)

    private fun setActuator(x: Float, y: Float) {
        actuatorX = x
        actuatorY = y
        onChange?.invoke(x, y)
    }
}
