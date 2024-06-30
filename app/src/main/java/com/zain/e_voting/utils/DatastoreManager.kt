package com.zain.e_voting.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatastoreManager(@ApplicationContext val context: Context) {

    val getNipd: Flow<String> = context.dataStore.data.map {
        it[NIPD_KEY] ?: ""
    }
    val getRole: Flow<String> = context.dataStore.data.map {
        it[ROLE_KEY] ?: ""
    }


    val getIsLogin: Flow<Boolean> = context.dataStore.data.map {
        it[IS_LOGIN_KEY] ?: false
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit {
            it[TOKEN_KEY] = token
        }
    }

    suspend fun saveNipd(nipd: String) {
        context.dataStore.edit {
            it[NIPD_KEY] = nipd
        }
    }

    suspend fun saveRole(role: String) {
        context.dataStore.edit {
            it[ROLE_KEY] = role
        }
    }


    suspend fun saveIsLoginStatus(paramIsLogin: Boolean) {
        context.dataStore.edit {
            it[IS_LOGIN_KEY] = paramIsLogin
        }
    }

    suspend fun removeIsLoginStatus() {
        context.dataStore.edit {
            it.remove(IS_LOGIN_KEY)
        }
    }

    suspend fun removeRole() {
        context.dataStore.edit {
            it.remove(ROLE_KEY)
        }
    }
    suspend fun removeNipd() {
        context.dataStore.edit {
            it.remove(NIPD_KEY)
        }
    }

    companion object {
        private const val DATASTORE_NAME = "preferences"
        private val TOKEN_KEY = stringPreferencesKey("token_key")
        private val NIPD_KEY = stringPreferencesKey("nipd_key")
        private val ROLE_KEY = stringPreferencesKey("role_key")
        private val IS_LOGIN_KEY = booleanPreferencesKey("is_login_key")
        private val Context.dataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }
}