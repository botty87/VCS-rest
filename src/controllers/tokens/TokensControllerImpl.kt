package com.vcs.controllers.tokens

import com.vcs.data.db.TokenItem
import com.vcs.data.db.UserItem
import com.vcs.data.db.isOld
import com.vcs.data.db.notActive
import com.vcs.data.dbTables.Tokens
import com.vcs.tools.Crypt
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.sql.BatchUpdateException
import java.time.LocalDateTime

class TokensControllerImpl: TokensController {
    override fun create(user: UserItem) : TokenItem {
        fun invalidateOldUserTokens() {
            Tokens.update({ (Tokens.user eq user.id) and (Tokens.active eq true) }) {
                it[active] = false
            }
        }

        var retryCounter = 0
        fun createToken(): TokenItem {
            try {
                return transaction {
                    invalidateOldUserTokens()
                    TokenItem.new(Crypt.createRandomToken()) {
                        this.user = user
                        this.start = LocalDateTime.now()
                    }
                }
            } catch (e: ExposedSQLException) {
                if(e.cause is BatchUpdateException) {
                    if(retryCounter++ < 5) {
                        return createToken()
                    }
                }
                throw e
            }
        }

        return createToken()
    }

    override fun checkToken(token: String): Boolean {
        val tokenItem = transaction {
            TokenItem.find {
                Tokens.id eq token
            }.firstOrNull()
        } ?: return false

        if(tokenItem.isOld()) {
            transaction {
                tokenItem.active = false
            }
        }

        if(tokenItem.notActive) {
            return false
        }

        return true
    }
}