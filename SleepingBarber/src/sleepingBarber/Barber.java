package sleepingBarber;
public class Barber extends Thread
{
	BarberShop bs;
	public Barber(BarberShop bs)
	{
		this.bs = bs;
	}
	@Override
	public void run()
	{
		System.out.println("il barbiere inizia a lavorare");
		while (true)
			this.bs.actionBarber();
	}
}
