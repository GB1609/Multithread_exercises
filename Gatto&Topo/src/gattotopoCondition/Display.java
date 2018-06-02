package gattotopoCondition;
public class Display extends Thread
{
	public static void main(String[] args)
	{
		Strip s = new Strip(40);
		Display d = new Display(s);
		Mouse m = new Mouse(s);
		Cat c = new Cat(s);
		d.start();
		m.start();
		c.start();
	}
	Strip s;
	public Display(Strip s)
	{
		this.s = s;
	}
	@Override
	public void run()
	{
		System.out.println("display started");
		this.s.print();
		System.out.println("THe cat eated the mouse, finally!");
	}
}
