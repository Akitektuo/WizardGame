package akitektuo.wizardgame.game

import akitektuo.wizardgame.game.element.Enemy
import akitektuo.wizardgame.game.hud.GameOver
import akitektuo.wizardgame.game.hud.Joystick
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
    lateinit var gameOver: GameOver

    fun initialize(width: Float, height: Float) {
        if (isInitialized) {
            updateScreenSize(width, height)
            return
        }

        boundary = Boundary(0f, width, height, 0f)
        player = Player(Vector(width / 2, height / 2), boundary)
        joystick = Joystick(
            Vector(Game.JOYSTICK_MARGIN, height - Game.JOYSTICK_MARGIN),
            100f,
            50f,
            player::setVelocity
        )
        gameOver = GameOver(width, height)

        isInitialized = true
    }

    fun updateScreenSize(width: Float, height: Float) {
        boundary.updateScreenSize(width, height)
        gameOver.updateScreenSize(width, height)
    }
}

val state = GameState()