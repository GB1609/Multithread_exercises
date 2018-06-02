package campionato;
import java.util.Arrays;
import java.util.Collections;
public class Main
{
	public static void main(String[] args)
	{
		System.out.println("begin");
		String squadre[] = { "domani", "crotone", "ale", "zaino", "lucertola" };
		for (String element : squadre)
			System.out.print(element + "  ");
		System.out.println();
		Collections.sort(Arrays.asList(squadre));
		for (String element : squadre)
			System.out.print(element + "  ");
		System.out.println("fine");
	}
}
