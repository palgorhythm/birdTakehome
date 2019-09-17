package com.birdTakehome.db

import com.birdTakehome.model.*
import com.zaxxer.hikari.*
import io.github.cdimascio.dotenv.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*

object DatabaseInitializer {

    fun init(){
        Database.connect(hikari())

        transaction {
            SchemaUtils.create(Events)
            SchemaUtils.create(Birds)
        }
    }
    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = dotenv()["DB_URL"]
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) { transaction {block()}}
}