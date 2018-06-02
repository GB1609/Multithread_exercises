package GiovanniBrunetti161195;
import java.util.Random;
public class Main
{
	public static void main(String[] args)
	{
		Tavolo t = new Tavolo(new Random().nextInt(60) + 20);
		t.startGame();
	}
}
