package atl.model.dao;

import atl.model.config.ConfigManager;
import atl.model.dto.FavoriteDto;
import atl.model.dto.StopsDto;
import atl.model.exception.StibException;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDao implements Dao<Integer, FavoriteDto> {
    private final String dbUrl;
    private final Connection connexion;

    public FavoriteDao() throws StibException {
        try {
            ConfigManager.getInstance().load();
            dbUrl = ConfigManager.getInstance().getProperties("db.url");
            this.connexion = DriverManager.getConnection("jdbc:sqlite:" + dbUrl);
        } catch (IOException | SQLException exception) {
            throw new StibException(exception);
        }
    }

    public FavoriteDto select(String name) throws StibException {
        StopsRepository stopsRepository = new StopsRepository();
        FavoriteDto favoriteDto = null;
        String query = "SELECT id, route_name, source_id, destination_id " +
                "FROM FAVORITE_ROUTES " +
                "WHERE route_name = ?";

        try (PreparedStatement pstmt = connexion.prepareStatement(query)) {
            pstmt.setString(1, name);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    Integer favId = resultSet.getInt(1);
                    String favName = resultSet.getString(2);
                    int id_source = resultSet.getInt(3);
                    int id_destination = resultSet.getInt(4);
                    StopsDto source = stopsRepository.select(id_source);
                    StopsDto destionation = stopsRepository.select(id_destination);

                    favoriteDto = new FavoriteDto(favId, favName, source, destionation);

                }
            }
        } catch (SQLException e) {
            throw new StibException(e);
        }
        return favoriteDto;
    }

    public void insert(FavoriteDto favoriteDto) throws StibException {
        String query = "INSERT INTO FAVORITE_ROUTES (route_name, source_id, destination_id) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connexion.prepareStatement(query)) {
            pstmt.setString(1, favoriteDto.getName());
            pstmt.setInt(2, favoriteDto.getSource().getId_station());
            pstmt.setInt(3, favoriteDto.getDestination().getId_station());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new StibException(e);
        }
    }

    public void delete(String routeName) throws StibException {
        System.out.println("dans delete et nom = " + routeName);
        String query = "DELETE FROM FAVORITE_ROUTES WHERE route_name = ?";

        try (PreparedStatement pstmt = connexion.prepareStatement(query)) {
            pstmt.setString(1, routeName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new StibException(e);
        }
    }

    public List<FavoriteDto> selectAll() throws StibException {
        List<FavoriteDto> favorites = new ArrayList<>();

        String query = "SELECT route_name, source_id, destination_id " +
                "FROM FAVORITE_ROUTES";

        try (PreparedStatement pstmt = connexion.prepareStatement(query);
             ResultSet resultSet = pstmt.executeQuery()) {

            StopsRepository stopsRepository = new StopsRepository();

            while (resultSet.next()) {
                String favName = resultSet.getString("route_name");
                int id_source = resultSet.getInt("source_id");
                int id_destination = resultSet.getInt("destination_id");

                StopsDto source = stopsRepository.select(id_source);
                StopsDto destination = stopsRepository.select(id_destination);

                FavoriteDto favoriteDto = new FavoriteDto(favName, source, destination);
                favorites.add(favoriteDto);
            }
        } catch (SQLException e) {
            throw new StibException(e);
        }

        return favorites;
    }


}
