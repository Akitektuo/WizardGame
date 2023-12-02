package akitektuo.wizardgame.game.element

import android.graphics.Canvas

interface GameObject {
    fun update()

    fun draw(canvas: Canvas)
}