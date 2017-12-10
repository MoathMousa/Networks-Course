import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public static final int PORT_NUMBER = 4321;

	public Client() {

		try {

			Socket clientSocket = new Socket("localhost", PORT_NUMBER);

			InputStream fromServer = clientSocket.getInputStream();
			OutputStream toServer = clientSocket.getOutputStream();
		
			byte[] welcomeMessage = new byte[fromServer.read()];
			fromServer.read(welcomeMessage);
			
			for (int i = 0; i < welcomeMessage.length; i++)
				System.out.print((char) welcomeMessage[i]);
			
			Scanner scan = new Scanner(System.in);
			int choice = -1;
			
			System.out.println();
			System.out.print("Enter your choice: ");
			
			// Ensure choice be between 1 and 4 inclusive
							
			
			while (choice < 1 || choice > 4)
				choice = scan.nextInt();
			
			toServer.write(choice);
			
			System.out.println();
			
			switch (choice) {
			
			case 1:
				System.out.print("Enter the number to check if prime: ");
				toServer.write(scan.nextInt());
				if (fromServer.read() == 1)
					System.out.println("Yes a prime!");
				else
					System.out.println("No not a prime!");
				break;
			case 2:
				System.out.print("Enter the number to get list of prime factors: ");
				toServer.write(scan.nextInt());
				byte[] listOfPrimeFactors = new byte[fromServer.read()];
				fromServer.read(listOfPrimeFactors);
				
				for (int i = 0; i < listOfPrimeFactors.length; i++)
					System.out.print((char) listOfPrimeFactors[i]);
				
				break;
			case 3:
				System.out.print("Enter the number to get factorial: ");
				toServer.write(scan.nextInt());
				int result = fromServer.read();
				System.out.println("Factorial is: " + result);
				break;
			case 4:
				break;
			default:
				break;
			
			}
			
			clientSocket.close();
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Client client = new Client(); // programming is in the constructor,
										// creating new Client does all the job
	}

}
