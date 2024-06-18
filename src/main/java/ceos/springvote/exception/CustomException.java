package ceos.springvote.exception;

import ceos.springvote.exception.error.MemberErrorCode;
import ceos.springvote.exception.error.VoteErrorCode;

public class CustomException extends ResponseException{

    public CustomException (MemberErrorCode memberErrorCode){
        super(memberErrorCode.getMessage(), memberErrorCode.getHttpStatus());
    }

    public CustomException (VoteErrorCode voteErrorCode){
        super(voteErrorCode.getMessage(), voteErrorCode.getHttpStatus());
    }
}
