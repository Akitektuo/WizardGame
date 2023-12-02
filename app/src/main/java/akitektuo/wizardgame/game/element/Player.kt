package akitektuo.wizardgame.game.element

import akitektuo.wizardgame.game.element.unit.CircleGameUnit
import akitektuo.wizardgame.game.model.Boundary
import androidx.compose.ui.graphics.Color

class Player(
    positionX: Float = 0f,
    positionY: Float = 0f,
    private val boundary: Boundary,
    radius: Float = 30f
) : CircleGameUnit(positionX, positionY, 300f, Color.Blue, radius) {
    override fun update() {
        super.update()
        if (positionX < boundary.left) {
            positionX = boundary.left
        } else if (positionX > boundary.right) {
            positionX = boundary.right
        }

        if (positionY < boundary.top) {
            positionY = boundary.top
        } else if (positionY > boundary.bottom) {
            positionY = boundary.bottom
        }
    }
}
