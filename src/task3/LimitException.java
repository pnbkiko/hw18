package task3;

public class LimitException extends RuntimeException {
    int attemps;
    public LimitException(final String message, final int attemps){
        super(message);
        this.attemps = attemps;
    }
    public String getDetailMessage() {
        return getMessage() + " "+ attemps;
    }

}
