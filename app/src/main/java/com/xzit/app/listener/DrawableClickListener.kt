package com.xzit.app.listener

interface  DrawableClickListener {
    enum class DrawablePosition {
        TOP, BOTTOM, LEFT, RIGHT
    }
    fun onClick(target: DrawablePosition?)
}