package dev.chiyanram.ledger

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.jdbc.core.simple.JdbcClient
import spock.lang.Specification

@SpringBootTest
@Import(PostgresContainer)
class LedgerApplicationIT extends Specification {

    @Autowired
    JdbcClient jdbc

    def "the application starts on PostgreSQL 18 with every migration applied"() {
        when:
        def server = jdbc.sql('SHOW server_version').query(String).single()
        def applied = jdbc.sql('SELECT count(*) FROM flyway_schema_history WHERE success').query(Integer).single()

        then:
        server.startsWith('18')
        applied == 1
    }
}
