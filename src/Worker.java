import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Worker {

	//To add a new language simply add its name in the array Languages[] and copy it's STOPWORDS file in the source folder.
	private String[] Languages = {"Danish", "English", "French", "German", "Hindi", "Italian", 
			"Portuguese", "Russian", "Spanish", "Swedish"};
		
	private int[] IDFq = new int[Languages.length];
	int[] LangSWFq = new int[Languages.length];  
	int HighLang, MaxFreq;
	private float TotalFq = 0;
	float Efficiency;
	float[] indivEff= new float[Languages.length];
	Set<String> uniqueInput = new TreeSet<String>();

	//Weeding out duplicates of input text and keeping only 1 instance of every unique input word.
	private Set<String> UniqueInput(String[] Input) {
		
		for(int n=0; n < Input.length; n++)
		{
			uniqueInput.add(Input[n]);
		}
		uniqueInput.remove("");
		uniqueInput.remove("(");
		uniqueInput.remove(")");
		uniqueInput.remove("-");
		System.out.println("Unique input words are: \n" + uniqueInput); 
		//Remove comment the above line if you want to view all the unique words of input.
		return(uniqueInput);
	}
	
	
	//Method to read file.
 	protected String[] Read(String FileName) throws FileNotFoundException, IOException {

		BufferedReader br = new BufferedReader(new FileReader(FileName));
		
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			
			while(line != null) {
				sb.append(line.replaceAll("(\\p{Punct})|[0-9]", " ").toLowerCase());
				sb.append("\n");
				line = br.readLine();
			}
			
			//\\P{L}+ can be used too.
			//(\\p{Punct})-'|[0-9]

			String[] LangArr = sb.toString().split(" |\n|\t");
			
			return(LangArr);
			
		}

		finally {
			br.close();
		}
	}
	
	
	//Method to check language of input text.
	protected String checkLang(String[] InpWords) throws IOException
	{

		uniqueInput = UniqueInput(InpWords); 
		
		int i=0, flag =0, IDNum;
		MaxFreq = 0;
		HighLang = 0;
		String[] tempArray = uniqueInput.toArray(new String[0]);
		
		while(flag<2)
		{
			
			IDNum = 0; 
			TotalFq =0;
			
			while(IDNum < Languages.length)
			{
				String[] LangArr = Read(Languages[IDNum] + ".txt");
				LangSWFq[IDNum] = LangArr.length; 
			
				int j=0, k;

				// The input is broken into 3 parts: 0-10, 45-55 and 90-100 percent of the input text.
				double[] size = {0, 0.1*tempArray.length, 0.45*tempArray.length, 0.55*tempArray.length, 0.9*tempArray.length, tempArray.length };
				
				for(i=flag; i<=4; i=i+2)
				{
					for(j= (int)(size[i]); j< (int)(size[i+1]); j++)
					{
						k=0;
						
						while(k < LangArr.length) 
						{

							if( tempArray[j].equals(LangArr[k]) ) 
							{
								IDFq[IDNum]++;
								k = LangArr.length; //Exit Loop
							}
							
							k++;
						}
						
					}
				}
			
			indivEff[IDNum] = 100*((float)(IDFq[IDNum]))/((float)(LangSWFq[IDNum]));
			System.out.println(Languages[IDNum] + " STOP words matched: " + IDFq[IDNum] + "/" + LangSWFq[IDNum] + " = " + indivEff[IDNum] + "%");
	
			if (MaxFreq < IDFq[IDNum])
			{
				MaxFreq = IDFq[IDNum];
				HighLang = IDNum;
			}
			
			IDNum++;
			
		}
			for(i=0; i<IDNum; i++)
			{
				TotalFq += indivEff[i]; 
			}
			Efficiency = 100*((float)(indivEff[HighLang]))/(float)(TotalFq);
			
			if(Efficiency > 100)
			{
				flag=2; //exit out of loop
			}
			
			else
			{
				flag++;
				if(flag == 1)
				{	
					System.err.println("\nUnsatisfactory results after preliminary evaluation. Starting deeper evaluation... \n");
				//If on the first round efficiency is less than 50%, then the remaining portions of the text are evaluated.
				}
			}
		}
		
		return(Languages[HighLang]);
		
	}
	
	//Method to keep only singles of each word if they occur multiple times in different lists
		protected void Unique() throws IOException, FileNotFoundException
		{
			int i,j,k,l;
			System.out.println("Repeated Words are: ");
			
			for(i=0; i<Languages.length; i++)
			{
				String[] FirstList = Read(Languages[i]+ ".txt");
				for(j=0; j<Languages.length; j++)
				{
					String[] SecondList = Read(Languages[j] + ".txt");
					
					if(i==j)
					{
						j++;
					}
					else
					{
						System.out.println(Languages[i] + "And" + Languages[j]);
						
						for(k=0; k<FirstList.length; k++)
						{
							for(l=0; l<SecondList.length; l++)
							{
								if(FirstList[k].equals(SecondList[l]) && FirstList[k]!="\n")
								{
									System.out.println(FirstList[k]);
									FirstList[k]= "\n";
									SecondList[l] = "\n";
								}
							}
						}
						
						
					}
				}
				
				System.out.println(Languages[i] + " list after deletion: ");
				for(k=0; k<FirstList.length; k++)
				{
					if(FirstList[k]!="\n")
					{
						System.out.println(FirstList[k]);
					}
					
				}
			}
		}

	//Method to read input from user input.
		protected String[] readUserInput() 
		{
			System.out.println("Please enter input string: ");
			Scanner scanner = new Scanner(System.in);
			String input = scanner.nextLine();
			String[] InpWords = input.replaceAll("(\\p{Punct})-'|[0-9]", " ").split(" ");
			scanner.close();
			
			return(InpWords);
			
		}

}
