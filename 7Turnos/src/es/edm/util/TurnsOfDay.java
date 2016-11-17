package es.edm.util;
//TODO: Eliminar los enumerados de toda la aplicación
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
	PM_00_00("12:00pm"), PM_00_30("12:30pm"),
	PM_01_00("13:00pm"), PM_01_30("13:30pm"),
	PM_02_00("14:00pm"), PM_02_30("14:30pm"),
	PM_03_00("15:00pm"), PM_03_30("15:30pm"),
	PM_04_00("16:00pm"), PM_04_30("16:30pm"),
	PM_05_00("17:00pm"), PM_05_30("17:30pm"),
	PM_06_00("18:00pm"), PM_06_30("18:30pm"),
	PM_07_00("19:00pm"), PM_07_30("19:30pm"),
	PM_08_00("20:00pm"), PM_08_30("20:30pm"),
	PM_09_00("21:00pm"), PM_09_30("21:30pm"),
	PM_10_00("22:00pm"), PM_10_30("22:30pm"),
	PM_11_00("23:00pm"), PM_11_30("23:30pm");
	
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
