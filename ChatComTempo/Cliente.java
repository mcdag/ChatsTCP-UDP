import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteTCP {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		long inicial = System.currentTimeMillis();
		int porta = 8877;
		String endereco = "localhost";
		
		try {
			Socket socket = new Socket(endereco, porta);
			
			while((System.currentTimeMillis() - inicial) < 9000) {
				System.out.println("Digite uma mensagem: ");
				String mensagem = in.nextLine();
				mensagem = mensagem + '\n';
				DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
				saida.write(mensagem.getBytes());
				System.out.println();
			}
			
			System.out.println("Desculpe, conexão encerrada, 10 segundos se passaram.");
			socket.close();
			
		} catch(ConnectException e) {
			System.out.println("Não foi possível chegar ao destinatário");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
