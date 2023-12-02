package akitektuo.wizardgame.game.element

import akitektuo.wizardgame.game.element.unit.CircleGameUnit
import androidx.compose.ui.graphics.Color

class Enemy(positionX: Float, positionY: Float, private val player: Player) :
    CircleGameUnit(positionX, positionY, 250f, Color.Red) {

    override fun update() {
        followPlayer()
        super.update()
    }

    private fun followPlayer() {
        val distanceToPlayerX = player.positionX - positionX
        val distanceToPlayerY = player.positionY - positionY

        val distanceToPlayer = getDistanceToObject(player)
        if (distanceToPlayer <= 0) {
            return setVelocity(0f, 0f)
        }

        setVelocity(distanceToPlayerX / distanceToPlayer, distanceToPlayerY / distanceToPlayer)
    }
}