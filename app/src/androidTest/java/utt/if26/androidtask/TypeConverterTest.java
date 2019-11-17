package utt.if26.androidtask;

import org.junit.Test;

import java.time.OffsetDateTime;

import utt.if26.androidtask.persistance.entity.TypeConverter.DateTimeConverter;

public class TypeConverterTest {
    @Test
    public void test(){
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        String timestamp =  DateTimeConverter.dateToTimestamp(offsetDateTime);

        OffsetDateTime t = DateTimeConverter.fromTimeStamp(timestamp);
        assert t.isEqual(offsetDateTime);
        assert t.getYear() == offsetDateTime.getYear();
        assert t.getMonth() == offsetDateTime.getMonth();
        assert t.getDayOfMonth() == offsetDateTime.getDayOfMonth();
    }
}
