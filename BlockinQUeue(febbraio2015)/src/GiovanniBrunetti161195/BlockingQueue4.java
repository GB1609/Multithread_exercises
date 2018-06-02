package GiovanniBrunetti161195;
import java.util.concurrent.ArrayBlockingQueue;
public class BlockingQueue4<T> extends ArrayBlockingQueue<T>
{
	private static final long serialVersionUID = 1L;
	public BlockingQueue4()
	{
		super(4);
	}
}