package com.birdTakehome.db

import com.birdTakehome.model.*
import com.zaxxer.hikari.*
import io.github.cdimascio.dotenv.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*

object DatabaseInitializer {
    val needToInitialize = true

    fun init(){
        Database.connect(hikari()) //connects exposed to hikaricp

        transaction {
            SchemaUtils.create(Events) // CREATE TABLE IF NOT EXISTS
            SchemaUtils.create(Birds)
        }
    }
    private fun hikari(): HikariDataSource { // hikari config, including DB url specification
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = dotenv()["DB_URL"]
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: () -> T): T = withContext(Dispatchers.IO) { transaction {block()}}
    // dispatches a coroutine for each transaction so they can run in a non-blocking manner
}