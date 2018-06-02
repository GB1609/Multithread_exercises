package cyclicBarrier;
import java.util.Arrays;
import java.util.Random;
public class Main
{
	public static void main(String[] args)
	{
		long begin = System.nanoTime();
		Random r = new Random();
		int dim = r.nextInt(30);
		System.out.println("DIMENSIONE ARRAY:" + dim);
		int v1[] = new int[dim];
		int v2[] = new int[dim];
		for (int i = 0; i < dim; i++)
		{
			v1[i] = r.nextInt(100);
			v2[i] = r.nextInt(100);
		}
		System.out.println(Arrays.toString(v1));
		System.out.println(Arrays.toString(v2));
		int result[];
		if (r.nextBoolean())
		{
			result = new Operazioni().somma(v1, v2);
			System.out.println("SOMMA");
		}
		else
		{
			result = new Operazioni().sottrai(v1, v2);
			System.out.println("DIFFERENZA");
		}
		System.out.println(Arrays.toString(result));
		long end = System.nanoTime();
		System.out.println((end - begin) / 1000000000);
	}
}
