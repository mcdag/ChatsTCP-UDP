import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteTCP {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int porta = 8877;
		String endereco = "localhost";
		
		try {
			Socket socket = new Socket(endereco, porta);
			System.out.println("Conversa com João");
			System.out.println();
			
			while(true) {
				System.out.print("Você: ");
				String mensagem = in.nextLine();
				mensagem = mensagem + '\n';
				DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
				saida.write(mensagem.getBytes());
				
				InputStreamReader entrada = new InputStreamReader(socket.getInputStream());
				BufferedReader le = new BufferedReader(entrada);
				String resposta = le.readLine();
				System.out.println("João: " + resposta);

			}
			
		} catch(ConnectException e) {
			System.out.println("Não foi possível chegar ao destinatário");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
