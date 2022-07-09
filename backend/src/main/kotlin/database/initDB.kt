package database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import schemas.NewWords

val user = System.getenv("DATABASE_USER") ?: "root"
val password = System.getenv("DATABASE_PASSWORD") ?: "root"
val server = System.getenv("DATABASE_SERVER") ?: "localhost:5432"

fun initDB() {
  // use H2 during development
  //    Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;", "org.h2.Driver")

  // use postgres in production
  Database.connect("jdbc:postgresql://$server/dicionarioinclusivo", driver = "org.postgresql.Driver", user, password)

  transaction { SchemaUtils.create(NewWords) }
}
