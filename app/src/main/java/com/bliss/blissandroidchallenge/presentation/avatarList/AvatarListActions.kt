package com.bliss.blissandroidchallenge.presentation.avatarList

sealed class AvatarListActions {
    data object GetAvatars : AvatarListActions()
    data object ClearError : AvatarListActions()
    data class DeleteAvatar(val avatarId: Int) : AvatarListActions()
}