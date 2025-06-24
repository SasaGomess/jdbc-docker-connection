package br.com.sabrinaweb.maventest.repository;

import br.com.sabrinaweb.maventest.dominio.Producer;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Log4j2
public class ProducerRepository {
    public static void save(Producer producer) {
        String sql = "INSERT INTO `loja`.`producer` (`name`) VALUES ('%s')".formatted(producer.getName());
        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement()) {
            int rowsAffected = st.executeUpdate(sql);
            log.info("Inserted producer '{}' in database rows affected '{}'", producer.getName(), rowsAffected);
        } catch (SQLException e) {
            log.error("Error while trying to insert producer '{}'", producer.getName());
        }
    }

    public static void deleteBetween(int idInicio, int idFinal) {
        String sql = "DELETE FROM `loja`.`producer` WHERE `id` BETWEEN '%d' AND '%d'".formatted(idInicio, idFinal);
        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement()) {
            int rowsAffected = st.executeUpdate(sql);
            log.info("Deleted producer from the database rows affected '{}'", rowsAffected);
        } catch (SQLException e) {
            log.error("Error while trying to delete producer");
        }
    }

    public static void delete(int id) {
        String sql = "DELETE FROM `loja`.`producer` WHERE (`id` = '%d') ".formatted(id);
        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement()) {

            int rowsAffected = st.executeUpdate(sql);
            log.info("Deleted producer '{}' from the database rows affected '{}'", id, rowsAffected);
        } catch (SQLException e) {
            log.error("Error while trying to delete producer '{}'", id);
        }
    }

    public static void update(Producer producer) {
        String sql = "UPDATE `loja`.`producer` SET `name` = '%s' WHERE (`id` = '%d')".formatted(producer.getName(), producer.getId());
        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement()) {

            int rowsAffected = st.executeUpdate(sql);
            log.info("Updated producer '{}' from the database, rowsaffected '{}'", producer.getId(), rowsAffected);

        } catch (SQLException e) {
            log.error("Error while trying to update '{}' procucer '{}'", producer.getName(), producer.getId());
        }
    }

    public static Set<Producer> findAll() {
        log.info("Finding all producers");
        Set<Producer> producers = new TreeSet<>(Comparator.comparing(Producer::getId));
        String sql = "SELECT id, name FROM `loja`.`producer`";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                producers.add(Producer
                        .builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build());
//                rs.getInt(1);
//                rs.getString(2);
            }

        } catch (SQLException e) {
            log.error("Error while trying to findAll procucers ", e);
        }
        return producers;
    }

    public static Set<Producer> findByName(String name) {
        log.info("Finding by producer name");
        Set<Producer> producers = new TreeSet<>(Comparator.comparing(Producer::getId));
        String sql = "SELECT * FROM `loja`.`producer` WHERE `name` LIKE '%%%s%%'".formatted(name);
        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                producers.add(Producer
                        .builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build());
            }

        } catch (SQLException e) {
            log.error("Error while trying to findAll procucers ", e);
        }
        return producers;
    }
}