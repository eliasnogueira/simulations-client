package exception;

import java.util.Map;

public class UnprocessableEntityException {

    private Map<String, String> errors;

    public UnprocessableEntityException() {}

    public UnprocessableEntityException(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
