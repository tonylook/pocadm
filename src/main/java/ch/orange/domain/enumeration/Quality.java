package ch.orange.domain.enumeration;

/**
 * The Quality enumeration.
 */
public enum Quality {
    BAD("bad"),
    GOOD("good"),
    TOP("top");

    private final String value;

    Quality(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
