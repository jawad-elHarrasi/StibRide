package atl.model.dao;

import atl.model.dto.Dto;
import atl.model.exception.StibException;

import java.util.List;

public interface Dao<K, T extends Dto<K>> {
    List<T> selectAll() throws StibException;
}
