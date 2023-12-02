package akitektuo.wizardgame.game.element

import akitektuo.wizardgame.game.element.unit.CircleGameUnit
import akitektuo.wizardgame.game.model.Vector
import androidx.compose.ui.graphics.Color

class Enemy(position: Vector, private val player: Player) :
    CircleGameUnit(position, 250f, Color.Red) {

    override fun update() {
        followPlayer()
        super.update()
    }

    private fun followPlayer() {
        setVelocity(position.getVelocityTowards(player.position))
    }
}