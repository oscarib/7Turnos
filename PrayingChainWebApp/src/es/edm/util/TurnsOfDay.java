package es.edm.util;

public enum TurnsOfDay {
	AM_00_00("00:00am"), AM_00_30("00:30am"),
	AM_01_00("01:00am"), AM_01_30("01:30am"),
	AM_02_00("02:00am"), AM_02_30("02:30am"),
	AM_03_00("03:00am"), AM_03_30("03:30am"),
	AM_04_00("04:00am"), AM_04_30("04:30am"),
	AM_05_00("05:00am"), AM_05_30("05:30am"),
	AM_06_00("06:00am"), AM_06_30("06:30am"),
	AM_07_00("07:00am"), AM_07_30("07:30am"),
	AM_08_00("08:00am"), AM_08_30("08:30am"),
	AM_09_00("09:00am"), AM_09_30("09:30am"),
	AM_10_00("10:00am"), AM_10_30("10:30am"),
	AM_11_00("11:00am"), AM_11_30("11:30am"),
	PM_00_00("00:00pm"), PM_00_30("00:30pm"),
	PM_01_00("01:00pm"), PM_01_30("01:30pm"),
	PM_02_00("02:00pm"), PM_02_30("02:30pm"),
	PM_03_00("03:00pm"), PM_03_30("03:30pm"),
	PM_04_00("04:00pm"), PM_04_30("04:30pm"),
	PM_05_00("05:00pm"), PM_05_30("05:30pm"),
	PM_06_00("06:00pm"), PM_06_30("06:30pm"),
	PM_07_00("07:00pm"), PM_07_30("07:30pm"),
	PM_08_00("08:00pm"), PM_08_30("08:30pm"),
	PM_09_00("09:00pm"), PM_09_30("09:30pm"),
	PM_10_00("10:00pm"), PM_10_30("10:30pm"),
	PM_11_00("11:00pm"), PM_11_30("11:30pm");
	
	private final String value;

    /**
     * @param text
     */
    private TurnsOfDay(final String value) {
        this.value = value;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return value;
    }
}
