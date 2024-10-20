package atl.model.dto;

import java.util.Objects;

/**
 * Generic class to build a Data transfer object. The data carried is known by
 * its key.
 */
public class Dto<K> {

    /**
     * Key of the data.
     */
    private final K key;

    /**
     * Creates a new instance of <code>Dto</code> with the key of the data.
     *
     * @param key key of the data.
     */
    public Dto(K key) {
        this.key = key;
    }

    public Dto() {
        this.key = null;
    }

    /**
     * Returns the key of the data.
     *
     * @return the key of the data.
     */
    public K getKey() {
        return key;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dto<?> other = (Dto<?>) obj;
        return Objects.equals(this.key, other.key);
    }

}
