package br.com.sabrinaweb.maventest.repository;

import br.com.sabrinaweb.maventest.conn.ConnectionFactory;
import br.com.sabrinaweb.maventest.dominio.Producer;
import lombok.extern.log4j.Log4j2;

import javax.sql.rowset.JdbcRowSet;
import java.sql.ResultSet;
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
            jrs.setCommand(sql);
            jrs.setInt(1,  producer.getId());
            jrs.execute();
            if (!jrs.next()) return;

            jrs.updateString("name", producer.getName());
            jrs.updateRow();
            log.info("Updated producer '{}' from the database ", producer.getId());
        }catch (SQLException e){
            log.error("Error while trying to update producers by name ", e);
        }
    }
}
