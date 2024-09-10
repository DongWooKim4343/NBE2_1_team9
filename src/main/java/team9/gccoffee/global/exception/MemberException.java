package team9.gccoffee.global.exception;

public enum MemberException {
    NOT_FOUND("NOT_FOUND", 404),
    NOT_REMOVED("Member NOT Removed", 400),
    NOT_REGISTERED("Member NOT Registered", 400),
    NOT_MODIFIED("Product NOT Modified", 400),
    BAD_CREDENTIALS("BAD_CREDENTIALS", 401);

    private MemberTaskException memberTaskException;

    MemberException(String message, int code) {
        // 예외 등록해두고 예외 발생하면 넘기게 한다.
        // MemberException 4가지로 정의했다.
        memberTaskException = new MemberTaskException(message, code);
    }

    public MemberTaskException get() {
        return memberTaskException;
    }

}
