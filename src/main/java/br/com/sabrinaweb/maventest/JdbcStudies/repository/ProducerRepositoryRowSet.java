package br.com.sabrinaweb.maventest.JdbcStudies.repository;

import br.com.sabrinaweb.maventest.JdbcStudies.conn.ConnectionFactory;
import br.com.sabrinaweb.maventest.JdbcStudies.dominio.Producer;
import br.com.sabrinaweb.maventest.JdbcStudies.listener.CustomRowSetListener;
import lombok.extern.log4j.Log4j2;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Log4j2
public class ProducerRepositoryRowSet {

    public static Set<Producer> findByNameJdbcRowSet(String name){
        String sql = "SELECT * FROM `loja`.`producer` WHERE `name` LIKE ?;";
        Set<Producer> producers = new TreeSet<>(Comparator.comparing(Producer::getId));

        try(JdbcRowSet jrs = ConnectionFactory.getjdbcRowSet()) {
            jrs.addRowSetListener(new CustomRowSetListener());
            jrs.setCommand(sql);
            jrs.setString(1, String.format("%%%s%%",name));
            jrs.execute();
            while (jrs.next()){
                producers.add(Producer
                        .builder()
                        .id(jrs.getInt("id"))
                        .name(jrs.getString("name"))
                        .build());
            }
        }catch (SQLException e){
            log.error("Error while trying to find producers by name ", e);
        }
        return producers;
    }
    public static void updateJdbcRowSet(Producer producer) {
        String sql = "SELECT * FROM `loja`.`producer` WHERE (`id` = ?)";
        try(JdbcRowSet jrs = ConnectionFactory.getjdbcRowSet()) {
            jrs.addRowSetListener(new CustomRowSetListener());
            jrs.setCommand(sql);
            jrs.setInt(1,  producer.getId());
            jrs.execute();
            if (!jrs.next()) return;

            jrs.updateString("name", producer.getName());
            jrs.updateRow();
            log.info("Updated producer '{}' from the database ", producer);
        }catch (SQLException e){
            log.error("Error while trying to update producers by name ", e);
        }
    }
    public static void updateCachedRowSet(Producer producer) {
        String sql = "SELECT * FROM producer WHERE (`id` = ?)";
        try(CachedRowSet crs = ConnectionFactory.getCachedRowSet();
            Connection conn = ConnectionFactory.getConnection()) {

            conn.setAutoCommit(false);
            crs.setCommand(sql);
            crs.setInt(1,  producer.getId());
            crs.execute(conn);
            if (!crs.next()) return;

            crs.updateString("name", producer.getName());
            crs.updateRow();
            crs.acceptChanges();

        }catch (SQLException e){
            log.error("Error while trying to update producers by name ", e);
        }
    }
}
