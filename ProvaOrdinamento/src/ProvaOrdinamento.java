import java.util.Arrays;

public class ProvaOrdinamento
{
	public static void main(String[] args)
	{
		int vettoreDaOrdinare []=new int [] {1,4,6,32,76,2,5,6,9,22,12};
		ordinaVettore(vettoreDaOrdinare);
		System.out.println(Arrays.toString(vettoreDaOrdinare));
	}

	private static void ordinaVettore(int[] vettoreDaOrdinare)
	{
		boolean ordinato=false;
		int temp;
		int ultimoOrdinato=vettoreDaOrdinare.length;
		while	(!ordinato)
		{
			ordinato=true;
			int lastI=-1;
			for (int i=1;i<ultimoOrdinato;i++)
			{
				if(vettoreDaOrdinare[i-1]>vettoreDaOrdinare[i])
				{
					ordinato=false;
					temp=vettoreDaOrdinare[i];
					vettoreDaOrdinare[i]=vettoreDaOrdinare[i-1];
					vettoreDaOrdinare[i-1]=temp;
					lastI=i;
				}
			}
			ultimoOrdinato=lastI;
		}
	}
}
