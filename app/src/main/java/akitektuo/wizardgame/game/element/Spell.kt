package akitektuo.wizardgame.game.element

import akitektuo.wizardgame.game.element.unit.CircleGameUnit
import akitektuo.wizardgame.game.model.Boundary
import androidx.compose.ui.graphics.Color

class Spell(boundary: Boundary, caster: Player) :
    CircleGameUnit(caster.position.copy(), 800f, COLOR, 20f) {
    companion object {
        private val COLOR = Color(255, 165, 0)
        var spellsToCast = 0
    }

    init {
        setVelocity(caster.direction.copy())
    }
}