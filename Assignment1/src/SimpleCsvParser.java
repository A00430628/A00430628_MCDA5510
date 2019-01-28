import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class SimpleCsvParser extends DirWalker{
	public static  String fileName = "";
	public static final String outputFile = "/home/kshitijnetwork/Documents/SMU_MCDA5510_Assignments/MCDA5510_Assignments/Assignment1/Output/Output.csv";
	public static final String outputErrorFile = "/home/kshitijnetwork/Documents/SMU_MCDA5510_Assignments/MCDA5510_Assignments/Assignment1/Output/OutputError.csv";
	
	/*Calculation of date for putting in date column*/  
    public String calcDate(String fileRead){
		String dateReturned = "";
		List<Integer> list = new ArrayList<>();
		int index = fileRead.indexOf('/');
		list.add(index);
		while (index >= 0) {
		    index = fileRead.indexOf('/', index + 1);
		    list.add(index);
		}
		dateReturned = fileRead.substring(list.get(list.size() - 5)+1,list.get(list.size() - 2));
		return dateReturned;
	}
	
    /* Parsing all .csv files for reading and writing valid and invalid rows in output files */
    public void readCsv(ArrayList<String> files) {

		Reader in;
		Logger logger = Logger.getLogger("Main");
		
			try {
				PrintWriter pw = new PrintWriter(new File(outputFile));
				PrintWriter pwError = new PrintWriter(new File(outputErrorFile));
				String entries = "First Name,Last Name,Street No.,Street Number,City,Province, Postal Code, Canada, Phone Number, Email Address, \n";
				int header_length = 10;
				pw.write(entries);
				pwError.write(entries);
				int countOfSkippedRows = 0;
				int countOfValidRows = 0;
				for ( String f : files ) {
				fileName = f;
				in = new FileReader(f);
			    BufferedReader bufferedReader = new BufferedReader(in);
			    bufferedReader.readLine();
			    final CSVFormat format = CSVFormat.DEFAULT.withDelimiter(',');
			    CSVParser parser = new CSVParser(bufferedReader, format);
				Iterable<CSVRecord> records = parser.getRecords();
				parser.close();
				for (CSVRecord record : records) {
					boolean skipped = false;
					Iterator<String> iterCols = record.iterator();
					int size = 0;
					StringBuilder sb = new StringBuilder();
					String iteratedValue = "";
					while(iterCols.hasNext()) { 
						iteratedValue =  iterCols.next().trim();
						/* Check if there is a null or empty value in a column */
						 if (iteratedValue != null && !iteratedValue.isEmpty()) {
					         sb.append(iteratedValue);
							 sb.append(",");
					            
					        }
						 else{
							 skipped = true;
							 sb.append(iteratedValue);
							 sb.append(",");
						 }
						 size++;
					}
					/* Check if the filled columns length is less than the length of headers */
					 if(size < header_length-1){
						 skipped = true;
						 size = 0;
					 }
					
                    String date = calcDate(f);
                    sb.append(date);
					sb.append(",");
					if(!skipped){
						countOfValidRows++;
						sb.append('\n');
						pw.write(sb.toString());
					}
					else{
						countOfSkippedRows++;
						sb.append('\n');
						pwError.write(sb.toString());
						continue;
					}
				}			
				
			}
				logger.log(Level.INFO, "Total number of skipped rows is " + countOfSkippedRows);
				logger.log(Level.INFO, "Total number of valid rows is " + countOfValidRows);
				pw.close();
				pwError.close();
			} catch ( IOException e) {
				e.printStackTrace();
				logger.log(Level.SEVERE, "Exception "+e+"occured in file " + fileName);
			}
			
	       
		
}

}
