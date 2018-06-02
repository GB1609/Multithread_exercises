package hopitalDayNight;
public class Visitator extends Thread
{
	Room r;
	public Visitator(Room r)
	{
		super();
		this.r = r;
	}
	@Override
	public void run()
	{
		try
		{
			sleep((long) (Math.random() * 1000));
			this.r.takeVisitatorLock();
			sleep(1500);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		this.r.leaveVisitatorLock();
	}
}
