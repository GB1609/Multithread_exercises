import java.util.Arrays;
public class Operazioni
{
	public int[] somma(int[] v1, int[] v2)
	{
		if (v1.length != v2.length)
			throw new RuntimeException("errore dimensione");
		int nThread = Runtime.getRuntime().availableProcessors();
		System.out.println(nThread);
		int[] toReturn = new int[v1.length];
		int portion = v1.length / nThread;
		while (portion == 0)
		{
			nThread--;
			portion = v1.length / nThread;
		}
		System.out.println("ogni Thread calcolera " + portion
				+ " elementi,TRANNE L'ULTIMO CHE CALCOLERA QUELLI IN PIU");
		Barrier b = new Barrier(nThread + 1);
		int start = 0;
		int end = portion;
		SommatoriParziali sommatoriParziali[] = new SommatoriParziali[nThread];
		for (int i = 0; i < (nThread - 1); i++)
		{
			sommatoriParziali[i] = new SommatoriParziali(true, start, end, b, v1, v2);
			sommatoriParziali[i].start();
			start = end;
			end += portion;
			if ((end + portion) > v1.length)
				end = v1.length;
		}
		sommatoriParziali[nThread - 1] = new SommatoriParziali(true, start, end, b, v1, v2);
		sommatoriParziali[nThread - 1].start();
		b.await();
		int sum = 0;
		for (SommatoriParziali element : sommatoriParziali)
		{
			for (int k = 0; k < element.getToReturn().length; k++)
				toReturn[k + sum] = element.getToReturn()[k];
			sum += element.getToReturn().length;
		}
		return toReturn;
	}
	public int[] sottrai(int[] v1, int[] v2)
	{
		if (v1.length != v2.length)
			throw new RuntimeException("errore dimensione");
		int nThread = Runtime.getRuntime().availableProcessors();
		System.out.println(nThread);
		int[] toReturn = new int[v1.length];
		int portion = v1.length / nThread;
		while (portion == 0)
		{
			nThread--;
			portion = v1.length / nThread;
		}
		System.out.println("ogni Thread calcolera " + portion
				+ " elementi,TRANNE L'ULTIMO CHE CALCOLERA QUELLI IN PIU");
		Barrier b = new Barrier(nThread + 1);
		int start = 0;
		int end = portion;
		SommatoriParziali sommatoriParziali[] = new SommatoriParziali[nThread];
		for (int i = 0; i < (nThread - 1); i++)
		{
			sommatoriParziali[i] = new SommatoriParziali(false, start, end, b, v1, v2);
			sommatoriParziali[i].start();
			start = end;
			end += portion;
			if ((end + portion) > v1.length)
				end = v1.length;
		}
		sommatoriParziali[nThread - 1] = new SommatoriParziali(false, start, end, b, v1, v2);
		sommatoriParziali[nThread - 1].start();
		b.await();
		int sum = 0;
		for (SommatoriParziali element : sommatoriParziali)
		{
			System.out.println(Arrays.toString(element.getToReturn()));
			for (int k = 0; k < element.getToReturn().length; k++)
				toReturn[k + sum] = element.getToReturn()[k];
			sum += element.getToReturn().length;
		}
		return toReturn;
	}
}
