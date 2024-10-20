package atl.model.dto;

import java.util.Objects;

public class FavoriteDto extends Dto<Integer> {
    private StopsDto source;
    private StopsDto destination;
    private String name;


    public FavoriteDto(Integer id, String name, StopsDto source, StopsDto destination) {
        super(id);
        this.source = source;
        this.destination = destination;
        this.name = name;
    }
    public FavoriteDto(String name, StopsDto source, StopsDto destination) {
        super();
        this.source = source;
        this.destination = destination;
        this.name = name;
    }

    public StopsDto getSource() {
        return source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FavoriteDto that = (FavoriteDto) o;
        return Objects.equals(source, that.source) && Objects.equals(destination, that.destination) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), source, destination, name);
    }

    @Override
    public String toString() {
        return name;
    }

    public StopsDto getDestination() {
        return destination;
    }

    public String getName() {
        return name;
    }
}
