object Versions {
    val springBootVersion = "3.0.2"
    val springBootDependencyManagement = "1.1.0"
    val springDocVersion = "2.0.2"
    val guavaVersion = "31.1-jre"
    val commonsIoVersion = "2.11.0"
    val commonsLang3Version = "3.12.0"
    val commonsCollections4Version = "4.4"
}

object Libs {

}

enum class DatabaseType(val dependency: String) {
    MySQL("com.mysql:mysql-connector-j"),
    Mariadb("org.mariadb.jdbc:mariadb-java-client"),
    PostgreSql("org.postgresql:postgresql"),
    Oracle("com.oracle.database.jdbc:ojdbc8")
}
