package akitektuo.wizardgame.game.element

import akitektuo.wizardgame.game.GameLoop
import akitektuo.wizardgame.game.element.unit.CircleGameUnit
import akitektuo.wizardgame.game.model.Boundary
import akitektuo.wizardgame.game.model.Vector
import androidx.compose.ui.graphics.Color

class Enemy(position: Vector, private val player: Player) :
    CircleGameUnit(position, 100f, Color.Red) {
    constructor(boundary: Boundary, player: Player) : this(boundary.getCloseRandomVector(), player)

    companion object {
        private const val SPAWNS_PER_MINUTE = 20
        private const val UPDATES_PER_SPAWN =
            GameLoop.MAXIMUM_UPDATES_PER_SECOND / (SPAWNS_PER_MINUTE / 60f)
        private var updatesUntilNextSpawn = UPDATES_PER_SPAWN
        fun isReadyToSpawn(): Boolean {
            if (updatesUntilNextSpawn > 0) {
                updatesUntilNextSpawn--
                return false
            }

            updatesUntilNextSpawn += UPDATES_PER_SPAWN
            return true
        }
    }

    override fun update() {
        followPlayer()
        super.update()
    }

    private fun followPlayer() {
        setVelocity(position.getVelocityTowards(player.position))
    }

    fun isCollidingWithPlayer(): Boolean {
        if (!isColliding(player))
            return false

        player.healthPoints--
        return true
    }
}