package sleepingBarber;
public class Main
{
	public static void main(String[] args)
	{
		BarberShop bs = new BarberShop();
		new Barber(bs).start();
		for (int i = 0; i < 40; i++)
			new Client(bs).start();
	}
}
