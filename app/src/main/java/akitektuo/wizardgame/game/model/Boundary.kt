package akitektuo.wizardgame.game.model

import akitektuo.wizardgame.game.definition.ScreenSizeDependency
import kotlin.random.Random

data class Boundary(val top: Float, var right: Float, var bottom: Float, val left: Float) :
    ScreenSizeDependency {
    fun getCloseRandomVector(): Vector {
        return when (BoundaryType.entries.random()) {
            BoundaryType.TOP -> Vector(nextFloat(left, right), top)
            BoundaryType.RIGHT -> Vector(right, nextFloat(top, bottom))
            BoundaryType.BOTTOM -> Vector(nextFloat(left, right), bottom)
            BoundaryType.LEFT -> Vector(left, nextFloat(top, bottom))
        }
    }

    private fun nextFloat(start: Float, end: Float) =
        Random.nextInt(start.toInt(), end.toInt() + 1).toFloat()

    override fun updateScreenSize(width: Float, height: Float) {
        right = width
        bottom = height
    }
}