package travelator.http;

import java.util.Objects;

public class Request {

    private final String body;

    public Request(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return body.equals(request.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }
}