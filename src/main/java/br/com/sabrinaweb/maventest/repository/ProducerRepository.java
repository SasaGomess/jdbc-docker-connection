package br.com.sabrinaweb.maventest.repository;

import br.com.sabrinaweb.maventest.dominio.Producer;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
}