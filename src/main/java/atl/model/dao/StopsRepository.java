package atl.model.dao;

import atl.model.dto.StopsDto;
import atl.model.exception.StibException;

import java.util.List;

public class StopsRepository implements Repository<Integer, StopsDto> {
    private final StopsDao stopsDao;


    public StopsRepository() throws StibException {
        this.stopsDao = new StopsDao();
    }
    public  StopsRepository(StopsDao stopsDao){
        this.stopsDao = stopsDao;
    }


    @Override
    public List<StopsDto> selectAll() throws StibException {
        return stopsDao.selectAll();
    }

    public List<StopsDto> selectAllWithOutDouble() throws StibException {
        return stopsDao.selectAllWithOutDouble();
    }

    public StopsDto select(Integer id)throws StibException {
        if (id == null){
            throw new StibException("args null");
        }
        return stopsDao.select(id);
    }
}
