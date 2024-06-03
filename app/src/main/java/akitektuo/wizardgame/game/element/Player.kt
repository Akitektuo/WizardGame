package akitektuo.wizardgame.game.element

import akitektuo.wizardgame.game.element.unit.CircleGameUnit
import akitektuo.wizardgame.game.hud.HealthBar
import akitektuo.wizardgame.game.model.Boundary
import akitektuo.wizardgame.game.model.Vector
import android.graphics.Canvas
import androidx.compose.ui.graphics.Color
import kotlin.math.max

class Player(
    position: Vector = Vector(),
    private val boundary: Boundary,
    radius: Float = 30f
) : CircleGameUnit(position, 300f, Color.Blue, radius) {
    companion object {
        const val MAXIMUM_HEALTH_POINTS = 10
    }

    private val healthBar = HealthBar(this)
    var healthPoints = MAXIMUM_HEALTH_POINTS
        set(value) {
            field = max(0, value)
        }

    val isDead get() = healthPoints <= 0

    override fun update() {
        super.update()
        position.boundTo(boundary)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        healthBar.draw(canvas)
    }
}
