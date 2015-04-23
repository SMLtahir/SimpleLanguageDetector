import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UKPtask {	
	
	public static void main(String[] args) throws IOException, FileNotFoundException {

		String File= "Spanish.txt";
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		Worker work = new Worker();
		
		String Solution;
		
		//To be used when input is taken from what the user types.
		//System.out.println("Results of version: " + dateFormat.format(date) + " over user input.");
		//String[] InpWords = work.readUserInput();
		
		//To be used when input is taken from a saved corpus.
		System.out.println("Results of version: " + dateFormat.format(date) + " over " + File);
		String[] InpWords = work.Read(File);
		
		System.err.println("Beginning quick evaluation...\n");
		Solution = work.checkLang(InpWords);
		
		if (work.MaxFreq == 0) 
		{
			System.out.println("Sorry. The input language is currently not supported.");
		}
		else
		{
			System.err.println("\nThe input text is in " + Solution + "!");
			System.err.printf("Amongst the currently supported languages, there is " + "%.2f", work.Efficiency);
			System.err.print("% probability that the text is in " + Solution + ".\n");
			System.err.println("There are " + work.MaxFreq + "/" + work.LangSWFq[work.HighLang] + " = "  + (float)(100*work.MaxFreq)/(float)(work.LangSWFq[work.HighLang]) +"% " + Solution + " STOP words detected.");
			System.err.println("Out of " + InpWords.length + " input words in total and " + work.uniqueInput.size() + " unique words of input, " + work.MaxFreq + " " + Solution + " STOP words have been detected.");
		}
		
		//The method below is used to weed out duplicates between STOP words lists.
		//work.Unique();
		
	}
	

}
