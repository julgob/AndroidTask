package utt.if26.androidtask.persistance.entity.categoryEnum;

public enum RatingCategory {
    LOW(1),
    NORMAL(2),
    HIGH(3);

    private final int value;

    public int getValue() {
        return value;
    }

    RatingCategory(int value){
        this.value = value;
    }

    public static RatingCategory valueOf(int value) {
        for (RatingCategory e : values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return null;
    }
}
