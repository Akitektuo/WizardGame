package akitektuo.wizardgame.game.model

import android.view.MotionEvent
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sqrt

data class Vector(var x: Float = 0f, var y: Float = 0f) {
    val isOrigin = x == 0f && y == 0f

    fun getLinearDistanceTo(otherVector: Vector) =
        sqrt((otherVector.x - x).pow(2) + (otherVector.y - y).pow(2))

    fun getVelocityTowards(otherVector: Vector, maximumRadius: Float = 0f): Vector {
        val delta = otherVector - this

        var deltaDistance = getLinearDistanceTo(otherVector)
        if (deltaDistance <= 0) {
            return Vector()
        }

        if (maximumRadius > 0) {
            deltaDistance = max(maximumRadius, deltaDistance)
        }
        return Vector(
            delta.x / deltaDistance,
            delta.y / deltaDistance
        )
    }

    fun set(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun boundTo(boundary: Boundary) {
        if (x < boundary.left) {
            x = boundary.left
        } else if (x > boundary.right) {
            x = boundary.right
        }

        if (y < boundary.top) {
            y = boundary.top
        } else if (y > boundary.bottom) {
            y = boundary.bottom
        }
    }

    operator fun plus(otherVector: Vector) =
        Vector(x + otherVector.x, y + otherVector.y)

    operator fun plusAssign(otherVector: Vector) {
        x += otherVector.x
        y += otherVector.y
    }

    operator fun times(multiplier: Float) = Vector(x * multiplier, y * multiplier)

    operator fun minus(otherVector: Vector) = Vector(x - otherVector.x, y - otherVector.y)
}

fun MotionEvent.toVector() = Vector(x, y)