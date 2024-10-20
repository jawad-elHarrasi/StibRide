package atl.model.dao;

import atl.model.dto.FavoriteDto;
import atl.model.exception.StibException;

import java.util.List;

public class FavoriteRepository implements Repository<Integer, FavoriteDto> {
    private final FavoriteDao favoriteDao;

    public FavoriteRepository() throws StibException {
        favoriteDao = new FavoriteDao();
    }

    //@Override
    public List<FavoriteDto> selectAll() throws StibException {
        return favoriteDao.selectAll();
    }

    public FavoriteDto select(String name) throws StibException {
        return favoriteDao.select(name);
    }

    public void insert(FavoriteDto favoriteDto) throws StibException {
        favoriteDao.insert(favoriteDto);
    }

    public void delete(String id) throws StibException {
        favoriteDao.delete(id);
    }
}
