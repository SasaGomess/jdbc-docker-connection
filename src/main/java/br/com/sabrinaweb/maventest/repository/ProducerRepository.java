package br.com.sabrinaweb.maventest.repository;

import br.com.sabrinaweb.maventest.dominio.Producer;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
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

    public static void deleteBetween(int firstId, int lastId) {
        String sql = "DELETE FROM `loja`.`producer` WHERE `id` BETWEEN '%d' AND '%d'".formatted(firstId, lastId);
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
        return findByName("");
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
            log.error("Error while trying to find producers by name ", e);
        }
        return producers;
    }
    public static Set<Producer> findByNamePreparedStatment(String name) {
        log.info("Finding by producer name with preparedStatment");
        Set<Producer> producers = new TreeSet<>(Comparator.comparing(Producer::getId));

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = preparedStatmentFindByName(conn, name);
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
    private static PreparedStatement preparedStatmentFindByName(Connection conn, String name) throws SQLException {
        String sql = "SELECT * FROM `loja`.`producer` WHERE `name` LIKE ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, String.format("%%%s%%", name));
        return ps;
    }
    public static void updatePreparedStatment(Producer producer) {

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = preparedStatmentUpdate(conn, producer)) {

            ps.executeUpdate();
            log.info("Updated producer '{}' from the database ", producer.getId());

        } catch (SQLException e) {
            log.error("Error while trying to update '{}' producer's name '{}'", producer.getName(), producer.getId());
        }

    }

    private static PreparedStatement preparedStatmentUpdate(Connection conn, Producer producer) throws SQLException {
        String sql = "UPDATE `loja`.`producer` SET `name` = ? WHERE (`id` = ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,  producer.getName());
        ps.setInt(2, producer.getId());
        return ps;
    }
    public static Set<Producer> findByNameAndUpdateToUpperCase(String name) {
        log.info("Finding by producer name and updating");
        Set<Producer> producers = new TreeSet<>(Comparator.comparing(Producer::getId));
        String sql = "SELECT * FROM `loja`.`producer` WHERE `name` LIKE '%%%s%%'".formatted(name);
        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                rs.updateString("name", rs.getString("name").toUpperCase());
                rs.updateRow();
                producers.add(Producer
                        .builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build());
            }
        } catch (SQLException e) {
            log.error("Error while trying to update producer by name ", e);
        }
        return producers;
    }

    public static Set<Producer> findByNameAndInsertWhenNotFound(String name) {
        log.info("Finding by producer name if not founding insert new register");
        Set<Producer> producers = new TreeSet<>(Comparator.comparing(Producer::getId));
        String sql = "SELECT * FROM `loja`.`producer` WHERE `name` LIKE '%%%s%%'".formatted(name);
        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) return producers;

            insertNewProducer(name, rs);

            producers.add(getProducer(rs));

        } catch (SQLException e) {
            log.error("Error while trying to insert the producer by that name ", e);
        }
        return producers;
    }

    public static void findByNameAndDelete(String name) {
        log.info("Finding by producer name to delete");
        String sql = "SELECT * FROM `loja`.`producer` WHERE `name` LIKE '%%%s%%'".formatted(name);
        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                log.info("Deleting the producer '{}' from database ", rs.getString("name"));
                rs.deleteRow();
            }
        } catch (SQLException e) {
            log.error("Error while trying to delete the producer by name ", e);
        }
    }

    private static void insertNewProducer(String name, ResultSet rs) throws SQLException {
        rs.moveToInsertRow();
        rs.updateString("name", name);
        rs.insertRow();
    }

    public static void showProducerMetaData() {
        log.info("Showing Producer Metadata");
        String sql = "SELECT * FROM `loja`.`producer`";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            ResultSetMetaData rsmetaData = rs.getMetaData();
            int columnCount = rsmetaData.getColumnCount();
            log.info("Columns count '{}'", columnCount);
            log.info("Tables name: '{}'", rsmetaData.getTableName(columnCount));

            for (int i = 1; i <= columnCount; i++) {
                log.info("Column name: '{}'", rsmetaData.getColumnName(i));
                log.info("Column size data: '{}'", rsmetaData.getColumnDisplaySize(i));
                log.info("Column nameType: '{}'", rsmetaData.getColumnTypeName(i));
            }
        } catch (SQLException e) {
            log.error("Error while trying to show producer's metadata ", e);
        }
    }

    public static void showDriverMetaData() {
        log.info("Showing Driver Metadata");

        try (Connection conn = ConnectionFactory.getConnection()) {
            DatabaseMetaData dbmetaData = conn.getMetaData();
            if (dbmetaData.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY)) {
                log.info("Supports TYPE_FORWARD_ONLY");
                if (dbmetaData.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)) {
                    log.info("And supports CONCUR_UPDATABLE");
                }
            }
            if (dbmetaData.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE)) {
                log.info("Supports TYPE_SCROLL_INSENSITIVE");
                if (dbmetaData.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)) {
                    log.info("And supports CONCUR_UPDATABLE");
                }
            }
            if (dbmetaData.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE)) {
                log.info("Supports TYPE_SCROLL_SENSITIVE");
                if (dbmetaData.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)) {
                    log.info("And supports CONCUR_UPDATABLE");
                }
            }

        } catch (SQLException e) {
            log.error("Error while trying to show driver metadata ", e);
        }
    }

    public static void showTypeScrollWorking() {
        String sql = "SELECT * FROM `loja`.`producer`";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet rs = st.executeQuery(sql)) {
            int especifRow = 4;

            log.info("Last row? '{}'", rs.last());
            log.info("Row number? '{}'", rs.getRow());
            log.info(Producer.builder().id(rs.getInt("id")).name(rs.getString("name")).build());
            log.info("------------------------------------------");
            log.info("Row relative? '{}'", rs.relative(-especifRow));
            log.info("Row number? '{}'", rs.getRow());
            log.info(Producer.builder().id(rs.getInt("id")).name(rs.getString("name")).build());
            log.info("------------------------------------------");
            log.info("First row? '{}'", rs.first());
            log.info("Row number? '{}'", rs.getRow());
            log.info(Producer.builder().id(rs.getInt("id")).name(rs.getString("name")).build());
            log.info("------------------------------------------");
            log.info("Row absolute? '{}'", rs.absolute(especifRow));
            log.info("Row number? '{}'", rs.getRow());
            log.info(Producer.builder().id(rs.getInt("id")).name(rs.getString("name")).build());
            log.info("------------------------------------------");
            log.info("Is last? '{}'", rs.isLast());
            log.info("Row number? '{}'", rs.getRow());
            log.info("------------------------------------------");
            log.info("Is first? '{}'", rs.isFirst());
            log.info("Row number? '{}'", rs.getRow());
            log.info("Last row? '{}'", rs.last());
            log.info("-------------------------------");


            rs.next();
            log.info("Is after last? '{}'", rs.isAfterLast());
            log.info("Row number -> after last '{}'", rs.getRow());
            while (rs.previous()) {
                log.info(Producer.builder().id(rs.getInt("id")).name(rs.getString("name")).build());
            }
            log.info("------------------------------------------");
            log.info("Is before first? '{}'", rs.isBeforeFirst());
            log.info("Row number? '{}'", rs.getRow());
        } catch (SQLException e) {
            log.error("Error while trying to show the rows informations ", e);
        }
    }

    private static Producer getProducer(ResultSet rs) throws SQLException {
        rs.beforeFirst();
        rs.next();
        return Producer
                .builder()
                .id(rs.getInt("id"))
                .name("name")
                .build();
    }
}