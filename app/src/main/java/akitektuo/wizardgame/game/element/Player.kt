package akitektuo.wizardgame.game.element

import akitektuo.wizardgame.game.element.unit.CircleGameUnit
import akitektuo.wizardgame.game.model.Boundary
import akitektuo.wizardgame.game.model.Vector
import androidx.compose.ui.graphics.Color

class Player(
    position: Vector = Vector(),
    private val boundary: Boundary,
    radius: Float = 30f
) : CircleGameUnit(position, 300f, Color.Blue, radius) {
    override fun update() {
        super.update()
        position.boundTo(boundary)
    }
}
