package mygroup.web_final_back_end.exceptions;

public class TransactionNotFoundByIdException extends Exception {
    public TransactionNotFoundByIdException(String message) {
        super(message);
    }
}
