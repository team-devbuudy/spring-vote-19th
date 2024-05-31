package ceos.springvote.exception;

public class MemberException extends ResponseException{

    public MemberException (MemberErrorCode memberErrorCode){
        super(memberErrorCode.getMessage(), memberErrorCode.getHttpStatus());
    }
}
