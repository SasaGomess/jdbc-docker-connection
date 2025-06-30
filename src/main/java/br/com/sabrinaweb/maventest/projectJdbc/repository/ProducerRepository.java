package br.com.sabrinaweb.maventest.projectJdbc.repository;

import br.com.sabrinaweb.maventest.projectJdbc.connection.ConnectionFactory;
import br.com.sabrinaweb.maventest.projectJdbc.domain.Producer;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Log4j2
public class ProducerRepository {
    public static Set<Producer> findByName(String name) {
        log.info("Finding Producer by name '{}'", name);
        Set<Producer> producers = new TreeSet<>(Comparator.comparing(Producer::getId));

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = createPreparedStatementFindByName(conn, name);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                producers.add(Producer
                        .builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build());
            }
        } catch (SQLException e) {
            log.error("Error while trying to find producers by name ", e);
        }
        return producers;
    }

    private static PreparedStatement createPreparedStatementFindByName(Connection conn, String name) throws SQLException {
        String sql = "SELECT * FROM `loja`.`producer` WHERE `name` LIKE ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, String.format("%%%s%%", name));
        return ps;
    }
}
