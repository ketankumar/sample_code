import java.util.*;

//Ketan Kumar
//This was written in Java
public class PageRank
{
	int dimension = 18;
	double[][] inLinks = {
		{0, 0, 0, (double)1/3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, .5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, (double)1/3, 0, 0, 0, 0, (double)1/3, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, (double)1/3, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, (double)1/3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, .5, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
		{1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
		{0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, (double)1/3, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, (double)1/3, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, (double)1/3, 0, 1, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, (double)1/3, 0, 0, 0}
		};
		
	double[] ranks = {(double)1/dimension, (double)1/dimension, (double)1/dimension, (double)1/dimension, (double)1/dimension, (double)1/dimension, (double)1/dimension, (double)1/dimension, (double)1/dimension, 
					(double)1/dimension, (double)1/dimension, (double)1/dimension, (double)1/dimension, (double)1/dimension, (double)1/dimension,	(double)1/dimension, (double)1/dimension, (double)1/dimension};
				
	double[] newRanks = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	
	//this method calculates the page ranks without teleporting
	//this keeps computing results until the results converge
	//it stores the answer in the newRanks array
	public void pageRankWithoutTeleporting()
	{
		boolean converges = this.converges();
		while(!converges)
		{
			this.clearNewRanks();
			for(int i = 0; i < dimension; i++)
			{
				for(int j = 0; j < dimension; j++)
				{
					newRanks[i] += ranks[j] * inLinks[i][j];
				}
			}
			converges = this.converges();
			this.transferNewRanksToRanks();
		}
	}
	
	//computes results with teleporting
	public void pageRankWithTeleporting()
	{
		boolean converges = this.converges();
		while(!converges)
		{
			this.clearNewRanks();
			for(int i = 0; i < dimension; i++)
			{
				for(int j = 0; j < dimension; j++)
				{
					newRanks[i] += .8 * ranks[j] * inLinks[i][j];
				}
			}
			for(int i = 0; i < dimension; i++)
			{
				newRanks[i] += .2 * (double)1/dimension;
			}
			converges = this.converges();
			this.transferNewRanksToRanks();
		}
	}

	//moves the answer from newRanks to ranks so the next iteration's results can be stored in newRanks
	public void transferNewRanksToRanks()
	{
		for(int i = 0; i < dimension; i++)
		{
			ranks[i] = newRanks[i];
		}
	}
	
	//clears the newRanks array.
	public void clearNewRanks()
	{
		for(int i = 0; i < dimension; i++)
		{
			newRanks[i] = 0;
		}
	}
	
	//checks to see if the results converge
	public boolean converges()
	{
		for(int i = 0; i < dimension; i++)
		{
			if(Math.abs(ranks[i] - newRanks[i]) > .001)
				return false;
		}
		return true;
	}

	public void printNewRanks()
	{
		for(int i = 0; i < dimension; i++)
		{
			System.out.println("Rank " + i + 1 + ": " + newRanks[i]);
		}
	}
	
	
	public static void main(String[] args)
	{	
		PageRank p = new PageRank();
		
		p.pageRankWithTeleporting();
		p.printNewRanks();
		
		p.pageRankWithoutTeleporting();
		p.printNewRanks();
	}
}
