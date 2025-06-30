package br.com.sabrinaweb.maventest.projectJdbc.repository;

import br.com.sabrinaweb.maventest.projectJdbc.connection.ConnectionFactory;
import br.com.sabrinaweb.maventest.projectJdbc.domain.Producer;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.*;

@Log4j2
public class ProducerRepository {
    public static List<Producer> findByName(String name) {
        log.info("Finding Producer by name '{}'", name);
        List<Producer> producers = new ArrayList<>();

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
    public static void delete(int id) {
        String sql = "DELETE FROM `loja`.`producer` WHERE (`id` = '%d') ".formatted(id);
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = createPreparedStatementDelete(conn, id)) {
            ps.execute();
            log.info("Deleted producer '{}' from the database", id);
        } catch (SQLException e) {
            log.error("Error while trying to delete producer '{}'", id);
        }
    }
    private static PreparedStatement createPreparedStatementDelete(Connection conn, Integer id) throws SQLException {
        String sql = "DELETE FROM `loja`.`producer` WHERE (`id` = ?) ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }
}
