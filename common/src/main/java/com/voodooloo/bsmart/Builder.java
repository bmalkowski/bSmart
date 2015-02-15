package com.voodooloo.bsmart;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.jooq.util.GenerationTool;
import org.jooq.util.jaxb.Configuration;
import org.jooq.util.jaxb.Database;
import org.jooq.util.jaxb.Generator;
import org.jooq.util.jaxb.Target;
import org.pmw.tinylog.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Builder {
    final DataSource dataSource;

    public Builder(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void buildDatabase() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setLocations("database/migration");
        flyway.migrate();
    }

    public void buildCode() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();

            Configuration configuration = new Configuration()
                    .withGenerator(new Generator()
                                           .withName("org.jooq.util.DefaultGenerator")
                                           .withDatabase(new Database()
                                                                 .withName("org.jooq.util.h2.H2Database")
                                                                 .withIncludes(".*")
                                                                 .withExcludes("")
                                                                 .withInputSchema("PUBLIC"))
                                           .withTarget(new Target()
                                                               .withPackageName(getClass().getPackage().getName() + ".generated")
                                                               .withDirectory("common/src/main/java")));

            GenerationTool tool = new GenerationTool();
            tool.setConnection(connection);
            tool.run(configuration);
        } catch (Exception e) {
            Logger.error(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    Logger.error(e);
                }
            }
        }
    }

    public static void main(String[] args) {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:bsmart;DB_CLOSE_DELAY=-1");

        Builder builder = new Builder(dataSource);
        builder.buildDatabase();
        builder.buildCode();
    }
}
