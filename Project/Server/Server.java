import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static final int PORT_NUMBER = 4321;
	
	public Server() {

		
		try {

			ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
			Socket socket = serverSocket.accept();

			InputStream fromClient = socket.getInputStream();
			OutputStream toClient = socket.getOutputStream();
				
			byte[] welcomeMessageBytes =
					("Welcome!"
					+ "\nChoose an option:"
					+ "\n1- Prime number checker."
					+ "\n2- Prime factors finder."
					+ "\n3- Factorial finder."
					+ "\n4- Termination of connection.").getBytes();
			
			toClient.write(welcomeMessageBytes.length);
			toClient.write(welcomeMessageBytes);

			switch (fromClient.read()) {
			
			case 1:
				toClient.write(checkPrime(fromClient.read()));
				break;
			case 2:
				byte[] listOfPrimeFactors = getPrimeFactors(fromClient.read());
				toClient.write(listOfPrimeFactors.length);
				toClient.write(listOfPrimeFactors);
				for (int i = 0; i < listOfPrimeFactors.length; i++)
					System.out.print((char) listOfPrimeFactors[i]);
				
				break;
			case 3:
				int input = fromClient.read();
				int result = factorial(input);
				System.out.println(result);
				toClient.write(result);
				break;
			case 4:
				break;
			default:
				break;
			
			}
			
			toClient.flush();
			socket.close();
			serverSocket.close();
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public static int checkPrime(int number) {
		
		if(number == 1)
			return 0;
		
		for (int i = 2; i < number; i++)
			if (number % i == 0)
				return 0;
		
		return 1;
		
	}
	
	public static byte[] getPrimeFactors(int number) {
		
		String result = "";
		
		for (int i = 2; i <= number; i++)
			if (number % i == 0 && checkPrime(i) == 1)
					result += i + " ";
		
		return result.trim().getBytes();
		
	}
	
	public static int factorial(int number) {
	
		int result = 1;
		for (int i = number; i > 1; i--)
			result *= i;
		
		return result;
		
	}
	
	public static void main(String[] args) {
		Server server = new Server(); // programming is in the constructor, creating new Server does all the job
	}

}
