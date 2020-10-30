import java.io.*;
import java.net.*;

public class ServidorTCP {
	public static void main(String[] args) {
		int porta = 8877;
		long inicial = System.currentTimeMillis();
		
		try {
			ServerSocket tmpsocket = new ServerSocket(porta);
			System.out.println("Aguardando cliente");
			Socket socket = tmpsocket.accept();
			
			while((System.currentTimeMillis() - inicial) < 10000) {
				InputStreamReader entrada = new InputStreamReader(socket.getInputStream());
				BufferedReader le = new BufferedReader(entrada);
				String resposta = le.readLine();
				System.out.println("Cliente: " + resposta);
			}
			socket.close();
			
			
		} catch (BindException e) {
			System.out.println("Endereço em uso");
		} catch (Exception e) {
			System.out.println("Erro " + e);
		}
	}
}

