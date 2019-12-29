/*
 * Nextcloud Talk application
 *
 * @author Mario Danic
 * Copyright (C) 2017-2019 Mario Danic <mario@lovelyhq.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nextcloud.talk.newarch.data.repository.online

import com.nextcloud.talk.models.json.conversations.Conversation
import com.nextcloud.talk.models.json.conversations.RoomOverall
import com.nextcloud.talk.models.json.generic.GenericOverall
import com.nextcloud.talk.newarch.data.source.remote.ApiService
import com.nextcloud.talk.newarch.domain.repository.online.NextcloudTalkRepository
import com.nextcloud.talk.newarch.local.models.UserNgEntity
import com.nextcloud.talk.newarch.local.models.getCredentials
import com.nextcloud.talk.utils.ApiUtils

class NextcloudTalkRepositoryImpl(private val apiService: ApiService) : NextcloudTalkRepository {
    override suspend fun deleteConversationForUser(
            user: UserNgEntity,
            conversation: Conversation
    ): GenericOverall {
        return apiService.deleteConversation(
                user.getCredentials(), ApiUtils.getRoom(user.baseUrl, conversation.token)
        )
    }

    override suspend fun leaveConversationForUser(
            user: UserNgEntity,
            conversation: Conversation
    ): GenericOverall {
        return apiService.leaveConversation(
                user.getCredentials(), ApiUtils.getUrlForRemoveSelfFromRoom(
                user
                        .baseUrl, conversation.token
        )
        )
    }

    override suspend fun getConversationForUser(userEntity: UserNgEntity, conversationToken: String): RoomOverall {
        return apiService.getConversation(userEntity.getCredentials(), conversationToken)
    }

    override suspend fun joinConversationForUser(userNgEntity: UserNgEntity, conversationToken: String, conversationPassword: String?): RoomOverall {
        return apiService.joinConversation(userNgEntity.getCredentials(), ApiUtils.getUrlForSettingMyselfAsActiveParticipant(userNgEntity.baseUrl, conversationToken), conversationPassword)
    }

    override suspend fun exitConversationForUser(userNgEntity: UserNgEntity, conversationToken: String): GenericOverall {
        return apiService.exitConversation(userNgEntity.getCredentials(), ApiUtils.getUrlForSettingMyselfAsActiveParticipant(userNgEntity.baseUrl, conversationToken))
    }

    override suspend fun setFavoriteValueForConversation(
            user: UserNgEntity,
            conversation: Conversation,
            favorite: Boolean
    ): GenericOverall {
        return if (favorite) {
            apiService.addConversationToFavorites(
                    user.getCredentials(),
                    ApiUtils.getUrlForConversationFavorites(user.baseUrl, conversation.token)
            )
        } else {
            apiService.removeConversationFromFavorites(
                    user.getCredentials(),
                    ApiUtils.getUrlForConversationFavorites(user.baseUrl, conversation.token)
            )
        }
    }

    override suspend fun getConversationsForUser(user: UserNgEntity): List<Conversation> {
        return apiService.getConversations(
                user.getCredentials(),
                ApiUtils.getUrlForGetRooms(user.baseUrl)
        )
                .ocs.data
    }
}