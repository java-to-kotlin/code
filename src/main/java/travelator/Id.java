package travelator;

import java.util.Objects;
import java.util.UUID;

public class Id<T> {
    private final String raw;

    private Id(String raw) {
        this.raw = raw;
    }

    public static <T> Id<T> of(String raw) {
        return new Id<T>(raw);
    }

    public static <T> String raw(Id<T> id) {
        return id.raw;
    }

    public static <T> Id<T> mint() {
        return Id.of(UUID.randomUUID().toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Id<?> id = (Id<?>) o;
        return raw.equals(id.raw);
    }

    @Override
    public int hashCode() {
        return Objects.hash(raw);
    }

    @Override
    public String toString() {
        return raw;
    }
}