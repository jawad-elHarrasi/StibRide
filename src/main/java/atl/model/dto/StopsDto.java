package atl.model.dto;

import java.util.ArrayList;
import java.util.List;

public class StopsDto extends Dto<Integer> {

    private final Integer id_Line;
    private final Integer id_station;
    private final Integer id_order;
    private final String nameStation;

    private List<Integer> lines;

    public StopsDto(Integer id_Line, Integer id_station, Integer id_order, String nameStation) {
        super(id_station);
        this.id_Line = id_Line;
        this.id_station = id_station;
        this.id_order = id_order;
        this.nameStation = nameStation;
        lines = new ArrayList<>();
    }

    public void addLines(List<Integer> linesId){
        lines = linesId;
    }

    public Integer getId_Line() {
        return id_Line;
    }
    public List<Integer> getIds_Line() {
        return lines;
    }

    public String getNameStation() {
        return nameStation;
    }

    public Integer getId_station() {
        return id_station;
    }

    public Integer getId_order() {
        return id_order;
    }

    @Override
    public String toString() {
        return nameStation.toUpperCase();

    }
}
