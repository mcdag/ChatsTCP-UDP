import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServidorTCP {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int porta = 8877;
		
		try {
			ServerSocket tmpsocket = new ServerSocket(porta);
			Socket socket = tmpsocket.accept();
			System.out.println("Conversa com Maria");
			System.out.println();
			
			while(true) {
				InputStreamReader entrada = new InputStreamReader(socket.getInputStream());
				BufferedReader le = new BufferedReader(entrada);
				String resposta = le.readLine();
				System.out.println("Maria: " + resposta);
				
				System.out.print("Você: ");
				String mensagem = in.nextLine();
				mensagem = mensagem + '\n';
				DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
				saida.write(mensagem.getBytes());
				
			}
			
		} catch (BindException e) {
			System.out.println("Endereço em uso");
		} catch (Exception e) {
			System.out.println("Erro " + e);
		}
	}
}
