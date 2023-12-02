package akitektuo.wizardgame.game

import akitektuo.wizardgame.game.element.Enemy
import akitektuo.wizardgame.game.element.Joystick
import akitektuo.wizardgame.game.element.Player
import akitektuo.wizardgame.game.element.Spell
import akitektuo.wizardgame.game.model.Boundary
import akitektuo.wizardgame.game.model.Vector

class GameState {
    private var isInitialized = false
    lateinit var boundary: Boundary
    lateinit var player: Player
    lateinit var joystick: Joystick
    val enemies = mutableListOf<Enemy>()
    val spells = mutableListOf<Spell>()

    fun initialize(width: Float, height: Float) {
        if (isInitialized)
            return

        boundary = Boundary(0f, width, height, 0f)
        player = Player(Vector(width / 2, height / 2), boundary)
        joystick = Joystick(
            Vector(Game.JOYSTICK_MARGIN, height - Game.JOYSTICK_MARGIN),
            100f,
            50f,
            player::setVelocity
        )

        isInitialized = true
    }
}

val state = GameState()