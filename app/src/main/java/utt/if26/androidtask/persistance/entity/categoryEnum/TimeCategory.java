package utt.if26.androidtask.persistance.entity.categoryEnum;

public enum TimeCategory {
    TODAY(1),
    TOMORROW(2),
    NEXT_SEVEN_DAYS(3),
    AFTER_NEXT_SEVEN_DAYS(4);

    private final int value;

    TimeCategory(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TimeCategory valueOf(int value) {
        for (TimeCategory e : values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return null;
    }
}
