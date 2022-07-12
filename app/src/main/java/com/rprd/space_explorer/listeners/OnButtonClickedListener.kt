package com.rprd.space_explorer.listeners

interface OnButtonClickedListener {
    fun onDescriptionClicked(description: String)
    fun onLikeButtonClicked(isFavorite: Boolean, imageDate: String)
}