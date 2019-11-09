package utt.if26.androidtask.persistance.entity.categoryEnum;

public enum TypeCategory {
    PROJECT(1),
    PERSONAL(2),
    OTHER(3);

    private final int value;

    public int getValue() {
        return value;
    }

    TypeCategory(int value){
        this.value = value;
    }

    public static TypeCategory valueOf(int value) {
        for (TypeCategory e : values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return null;
    }
}
