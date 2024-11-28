package com.bliss.blissandroidchallenge.presentation.avatarList

import com.bliss.blissandroidchallenge.domain.usersAvatar.model.Avatar
import com.google.common.collect.ImmutableList

data class AvatarListState(
    val isLoading: Boolean = true,
    val avatarList: ImmutableList<Avatar> = ImmutableList.of(),
    val error: String? = null
)