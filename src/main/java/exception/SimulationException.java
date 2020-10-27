package exception;


public class SimulationException {

    private String message;

    public SimulationException() {}

    public SimulationException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return message;
    }
}
