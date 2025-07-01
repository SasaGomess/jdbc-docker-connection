package br.com.sabrinaweb.maventest.projectJdbc.repository;

import br.com.sabrinaweb.maventest.projectJdbc.connection.ConnectionFactory;
import br.com.sabrinaweb.maventest.projectJdbc.domain.Anime;
import br.com.sabrinaweb.maventest.projectJdbc.domain.Producer;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public class AnimeRepository {
    public static void save(Anime anime) {
        log.info("Saving Anime '{}'", anime.getName());

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = createPreparedStatementInsert(conn, anime)) {
            ps.execute();
        } catch (SQLException e) {
            log.error("Error while trying to update '{}' anime's name '{}'", anime.getName(), anime.getId());
        }
    }
    private static PreparedStatement createPreparedStatementInsert(Connection conn, Anime anime) throws SQLException {
        String sql = "INSERT INTO `loja`.`anime` (name, episodes, id_producer) VALUES (?, ?, ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, anime.getName());
        ps.setInt(2, anime.getEpisodes());
        ps.setInt(3, anime.getProducer().getId());
        return ps;
    }

    public static Optional<Anime> findById(Integer id) {
        log.info("Finding Anime by id '{}'", id);
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = createPreparedStatementFindById(conn, id);
             ResultSet rs = ps.executeQuery()) {

            if (!rs.next()) return Optional.empty();
            Producer producer = Producer
                    .builder()
                    .name(rs.getString("producer_name"))
                    .id(rs.getInt("id_producer"))
                    .build();
            Anime anime = Anime
                    .builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .episodes(rs.getInt("episodes"))
                    .producer(producer)
                    .build();
            return Optional.of(anime);

        } catch (SQLException e) {
            log.error("Error while trying to find animes by id ", e);
        }
        return Optional.empty();
    }

    private static PreparedStatement createPreparedStatementFindById(Connection conn, Integer id) throws SQLException {
        String sql = """
                SELECT a.id, a.name, a.episodes, a.id_producer, p.name AS 'producer_name' FROM anime a
                INNER JOIN producer p ON a.id_producer = p.id
                WHERE (a.id = ?);
                """;;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }

    public static void update(Anime anime) {
        log.info("Updating anime '{}' ", anime);
        try (Connection conn = br.com.sabrinaweb.maventest.JdbcStudies.conn.ConnectionFactory.getConnection();
             PreparedStatement ps = createPreparedStatementUpdate(conn, anime)) {
            ps.execute();
        } catch (SQLException e) {
            log.error("Error while trying to update '{}'", anime.getId(), e);
        }
    }

    private static PreparedStatement createPreparedStatementUpdate(Connection conn, Anime anime) throws SQLException {
        String sql = "UPDATE `loja`.`anime` SET `name` = ?, `episodes` = ? WHERE (`id` = ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, anime.getName());
        ps.setInt(2, anime.getEpisodes());
        ps.setInt(3, anime.getId());
        return ps;
    }


    public static List<Anime> findByName(String name) {
        log.info("Finding Anime by name '{}'", name);
        List<Anime> animes = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = createPreparedStatementFindByName(conn, name);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producer producer = Producer
                        .builder()
                        .name(rs.getString("producer_name"))
                        .id(rs.getInt("id_producer"))
                        .build();
                Anime anime = Anime
                        .builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .episodes(rs.getInt("episodes"))
                        .producer(producer)
                        .build();
                animes.add(anime);
            }
        } catch (SQLException e) {
            log.error("Error while trying to find animes by name ", e);
        }
        return animes;
    }

    private static PreparedStatement createPreparedStatementFindByName(Connection conn, String name) throws SQLException {
        String sql = """
                SELECT a.id, a.name, a.episodes, a.id_producer, p.name AS 'producer_name' FROM anime a
                INNER JOIN producer p ON a.id_producer = p.id
                WHERE a.name LIKE ?;
                """;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, String.format("%%%s%%", name));
        return ps;
    }

    public static void delete(int id) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = createPreparedStatementDelete(conn, id)) {
            ps.execute();
            log.info("Deleted anime '{}' from the database", id);
        } catch (SQLException e) {
            log.error("Error while trying to delete anime '{}'", id);
        }
    }

    private static PreparedStatement createPreparedStatementDelete(Connection conn, Integer id) throws SQLException {
        String sql = "DELETE FROM `loja`.`anime` WHERE (`id` = ?) ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }
}
