package fr.francetravail.demo.client.api.lpe.api;

public class TooManyRequestsException extends HttpException{
    public TooManyRequestsException(int statusCode, String url, String message) {
        super(statusCode, url, message);
    }
}
