package akitektuo.wizardgame.game.element

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource

class HealthBar(private val player: Player) : GameObject {
    companion object {
        private const val DISTANCE_TO_PLAYER = 30
        private const val WIDTH = 100
        private const val HEIGHT = 20
        private const val MARGIN = 2
    }

    private val borderPaint = Paint().apply {
        color = Color.Gray.toArgb()
    }
    private val healthPaint = Paint().apply {
        color = Color.Green.toArgb()
    }

    override fun update() {
        TODO("Not yet implemented")
    }

    override fun draw(canvas: Canvas) {
        val halfOfWidth = WIDTH / 2

        val borderRectangle = RectF(
            player.position.x - halfOfWidth,
            player.position.y - DISTANCE_TO_PLAYER - HEIGHT,
            player.position.x + halfOfWidth,
            player.position.y - DISTANCE_TO_PLAYER
        )
        canvas.drawRect(borderRectangle, borderPaint)

        val healthWidth = WIDTH - 2 * MARGIN
        val healthPointsPercentage = player.healthPoints.toFloat() / Player.MAXIMUM_HEALTH_POINTS

        val healthRectangle = RectF(
            borderRectangle.left + MARGIN,
            borderRectangle.top + MARGIN,
            borderRectangle.left + MARGIN + healthWidth * healthPointsPercentage,
            borderRectangle.bottom - MARGIN
        )
        canvas.drawRect(healthRectangle, healthPaint)
    }

}
