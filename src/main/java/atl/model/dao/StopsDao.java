package atl.model.dao;

import atl.model.config.ConfigManager;
import atl.model.dto.StopsDto;
import atl.model.exception.StibException;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StopsDao implements Dao<Integer, StopsDto> {

    private final String dbUrl;
    private final Connection connexion;

    public StopsDao(String dbUrl) throws StibException {
        try {
            ConfigManager.getInstance().load();
            this.dbUrl = dbUrl;
            this.connexion = DriverManager.getConnection("jdbc:sqlite:" + dbUrl);
        } catch (IOException | SQLException exception) {
            throw new StibException(exception);
        }

    }

    public StopsDao() throws StibException {
        try {
            ConfigManager.getInstance().load();
            dbUrl = ConfigManager.getInstance().getProperties("db.url");
            this.connexion = DriverManager.getConnection("jdbc:sqlite:" + dbUrl);
        } catch (IOException | SQLException exception) {
            throw new StibException(exception);
        }

    }
    public StopsDto select(Integer id) throws StibException {
        StopsDto stopsDto = null;
        String query = "SELECT s.id_line, s.id_station, s.id_order, st.name " +
                "FROM STOPS s " +
                "JOIN STATIONS st ON s.id_station = st.id " +
                "WHERE s.id_station = ?";

        try (PreparedStatement pstmt = connexion.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    int idLine = resultSet.getInt(1);
                    int idStation = resultSet.getInt(2);
                    int idOrder = resultSet.getInt(3);
                    String stationName = resultSet.getString(4);

                    stopsDto = new StopsDto(idLine, idStation, idOrder, stationName);

                    List<Integer> lines = new ArrayList<>();
                    String linesQuery = "SELECT id_line FROM STOPS WHERE id_station = ?";
                    try (PreparedStatement linesStmt = connexion.prepareStatement(linesQuery)) {
                        linesStmt.setInt(1, idStation);
                        try (ResultSet linesResult = linesStmt.executeQuery()) {
                            while (linesResult.next()) {
                                lines.add(linesResult.getInt("id_line"));
                            }
                        }
                    }
                    // Ajout des lignes à l'objet StopsDto
                    stopsDto.addLines(lines);
                }
            }
        } catch (SQLException ex) {
            throw new StibException(ex);
        }
        return stopsDto;
    }





    @Override
    public List<StopsDto> selectAll() throws StibException {
        List<StopsDto> stopsDtoList = new ArrayList<>();
        try {
            Statement stmt = connexion.createStatement();


            String query = "SELECT id_line, id_station, id_order, name " +
                    "FROM STOPS JOIN STATIONS ON id_station = id";

            ResultSet result = stmt.executeQuery(query);

            while (result.next()) {

                StopsDto stopsDto;
                int id_line = result.getInt(1);
                int id_station = result.getInt(2);
                int id_order = result.getInt(3);
                String name = result.getString(4);

                stopsDto = new StopsDto(id_line, id_station, id_order, name);

                query = "SELECT id_line " +
                        "FROM STOPS " +
                        "WHERE id_station = ?";

                PreparedStatement stmt2 = connexion.prepareStatement(query);
                stmt2.setInt(1, id_station);
                ResultSet result2 = stmt2.executeQuery();
                List<Integer> ids_line = new ArrayList<>();

                while (result2.next()) {
                    int id = result2.getInt(1);
                    ids_line.add(id);
                }
                stopsDto.addLines(ids_line);

                stopsDtoList.add(stopsDto);
            }
        } catch (SQLException ex) {
            throw new StibException(ex);
        }

        return stopsDtoList;

    }

    public List<StopsDto> selectAllWithOutDouble() throws StibException {
        List<StopsDto> stopsDtoList = new ArrayList<>();
        try {
            Connection connexion = DriverManager.getConnection("jdbc:sqlite:" + dbUrl);

            Statement stmt = connexion.createStatement();
            String query = "SELECT id_line, id_station, MIN(id_order) AS id_order, MIN(name) AS name " +
                    "FROM STOPS JOIN STATIONS ON id_station = id " +
                    "GROUP BY id_station";

            ResultSet result = stmt.executeQuery(query);

            while (result.next()) {

                StopsDto stopsDto;
                int id_line = result.getInt(1);
                int id_station = result.getInt(2);
                int id_order = result.getInt(3);
                String name = result.getString(4);

                stopsDto = new StopsDto(id_line, id_station, id_order, name);

                query = "SELECT id_line " +
                        "FROM STOPS " +
                        "WHERE id_station = ?";

                PreparedStatement stmt2 = connexion.prepareStatement(query);
                stmt2.setInt(1, id_station);
                ResultSet result2 = stmt2.executeQuery();
                List<Integer> ids_line = new ArrayList<>();

                while (result2.next()) {
                    int id = result2.getInt(1);
                    ids_line.add(id);
                }
                stopsDto.addLines(ids_line);

                stopsDtoList.add(stopsDto);
            }
        } catch (SQLException ex) {
            throw new StibException(ex);
        }

        return stopsDtoList;

    }
}
