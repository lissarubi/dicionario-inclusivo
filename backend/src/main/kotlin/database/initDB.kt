package database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import schemas.NewWords

val production = System.getenv("PRODUCTION") ?: true
val user = System.getenv("DATABASE_USER") ?: "root"
val password = System.getenv("DATABASE_PASSWORD") ?: "root"
val server = System.getenv("DATABASE_SERVER") ?: "localhost:5432"

fun initDB() {
  if (production == "true") {
    Database.connect(
        "jdbc:postgresql://$server/dicionarioinclusivo",
        driver = "org.postgresql.Driver",
        user,
        password
    )
  } else {
    Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;", "org.h2.Driver")
  }
  transaction { SchemaUtils.create(NewWords) }
}
