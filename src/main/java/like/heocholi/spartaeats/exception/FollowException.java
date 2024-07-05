package like.heocholi.spartaeats.exception;

import like.heocholi.spartaeats.constants.ErrorType;

public class FollowException extends CustomException{
    public FollowException(ErrorType errorType) {
        super(errorType);
    }
}