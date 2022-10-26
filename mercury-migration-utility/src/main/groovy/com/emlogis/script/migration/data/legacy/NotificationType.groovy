package com.emlogis.script.migration.data.legacy;

/**
 * Created by rjackson on 6/4/2015.
 */
public enum NotificationType {
    TIME_OFF_REQUEST("PTO_REQUEST_CHANGE", 32768L),
    SWAP_REQUEST("SHIFT_SWAP_REQUEST_RECEIVED",2147483648L ),
    WIP_REQUEST("WORK_IN_PLACE_REQUEST_RECEIVED", 34359738368L),
    AVAILABILITY_REQUEST("AVAILABILITY", 8589934592L),
    SCHEDULE_POSTED("NEW_SCHEDULE_POSTED", 33554432L),
    SCHEDULE_DELETED("SCHEDULE_DELETED", 67108864L),
    OPEN_SHIFT_POSTED("OPEN_SHIFTS_POSTED", 8388608L),
    OPEN_SHIFT_REQUEST("OPEN_SHIFT_REQUEST_RECEIVED", 1073741824L),
    MY_SCHEDULE_CHANGE("NEW", 1L),
    MANAGER_APPROVAL("NEW", 1L),
    SCHEDULE_MANAGEMENT("NEW", 1L);
/*    ("WORK_IN_PLACE_REQUEST_CHANGE_ORIGINATOR", 17179869184L),
    ("WORK_IN_PLACE_REQUEST_CHANGE_ORIGINATOR", 17179869184L),
    ("OPEN_SHIFT_REQUEST_CHANGE", 65536L),
    ("OPEN_SHIFTS_POSTED_PSS",16777216L),
    ("PTO_DAY_REMOVED", 2097152L),
    ("PTO_DAY_REMOVED", 2097152L),
    ("PTO_REQUEST_RECEIVED", 536870912L),
    ("PTO_REQUEST_RECEIVED", 536870912L),
    ("PTO_REQUEST_RECEIVED", 536870912L),
    ("SHIFT_SWAP_REQUEST_CHANGE_ORIGINATOR", 131072L),
    ("AVAILABILITY", 536870912L);	*/

    private final String legacyValue;
    private long bitMask = 0L;

    NotificationType(String legacyValue, long bitMask) {
        this.legacyValue = legacyValue;
        this.bitMask = bitMask;
    }

    public String getLegacyValue() {
        return legacyValue;
    }

    public long getBitMask() {
        return bitMask;
    }

}
