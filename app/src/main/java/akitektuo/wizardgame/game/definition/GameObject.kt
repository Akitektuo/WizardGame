package akitektuo.wizardgame.game.definition

import android.graphics.Canvas

interface GameObject {
    fun update()

    fun draw(canvas: Canvas)
}