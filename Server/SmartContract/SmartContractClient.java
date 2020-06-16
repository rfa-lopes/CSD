import java.io.Serializable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Security;

public class SmartContractClient implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SmartContractClient() {
			
			try{
		File f = new File("lol.txt");

		System.out.println(f.canWrite());

		FileReader reader = new FileReader("lol.txt");

		BufferedReader bufferedReader = new BufferedReader(reader);



		String line;



		while ((line = bufferedReader.readLine()) != null) {

			System.out.println(line);

		}

		reader.close();
			}catch(Exception e){

			}

	}	

}
