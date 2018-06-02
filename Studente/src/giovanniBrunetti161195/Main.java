package giovanniBrunetti161195;
import java.util.Calendar;
import java.util.GregorianCalendar;
public class Main
{
	public static void main(String[] args)
	{
		GregorianCalendar gc = new GregorianCalendar();
		System.out.println("Giorno: " + gc.get(Calendar.DAY_OF_MONTH));
		System.out.println("Mese: " + gc.get(Calendar.MONTH));
		System.out.println("Anno: " + gc.get(Calendar.YEAR));
	}
}
