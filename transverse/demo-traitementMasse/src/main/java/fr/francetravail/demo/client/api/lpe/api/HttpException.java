package fr.francetravail.demo.client.api.lpe.api;

public class HttpException extends RuntimeException {
    private final int statusCode;
    private final String url;

    public HttpException(int statusCode, String url, String message) {
        super("HTTP " + statusCode + " on " + url + " : " + message);
        this.statusCode = statusCode;
        this.url = url;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getUrl() {
        return url;
    }
}




