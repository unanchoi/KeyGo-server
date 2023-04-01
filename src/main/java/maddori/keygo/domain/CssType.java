package maddori.keygo.domain;

public enum CssType {
    Continue("Continue"),
    Stop("Stop");

    private String value;

    CssType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
