package seamountain;
public class Main
{
	public static void main(String[] args)
	{
		Bridge bridge = new Bridge();
		int dim = 14;
		Turist mountainTourists[] = new Turist[dim];
		Turist seaTourists[] = new Turist[dim];
		for (int i = 0; i < dim; i++)
		{
			mountainTourists[i] = new Turist(1, bridge);
			seaTourists[i] = new Turist(0, bridge);
			mountainTourists[i].start();
			seaTourists[i].start();
		}
	}
}
