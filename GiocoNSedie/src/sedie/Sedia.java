package sedie;
public class Sedia
{
	private boolean occupata;
	private int numSedia;
	public Sedia(int i)
	{
		this.numSedia = i;
		this.occupata = false;
	}
	public int getNumSedia()
	{
		return this.numSedia;
	}
	public boolean isOccupata()
	{
		return this.occupata;
	}
	public void setOccupata(boolean occupata)
	{
		this.occupata = occupata;
	}
}
