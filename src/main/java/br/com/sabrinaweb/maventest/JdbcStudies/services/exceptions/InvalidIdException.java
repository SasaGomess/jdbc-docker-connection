package br.com.sabrinaweb.maventest.JdbcStudies.services.exceptions;

public class InvalidIdException extends IllegalArgumentException {
    public InvalidIdException(String s) {
        super(s);
    }
}
