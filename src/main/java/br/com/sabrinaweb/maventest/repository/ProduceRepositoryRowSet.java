package br.com.sabrinaweb.maventest.repository;

import br.com.sabrinaweb.maventest.conn.ConnectionFactory;
import br.com.sabrinaweb.maventest.dominio.Producer;
import lombok.extern.log4j.Log4j2;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Log4j2
public class ProduceRepositoryRowSet {

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
}
