import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteUDP {
	public static void main(String[] args) throws IOException {

		Scanner in = new Scanner(System.in);
		
		int porta = 8888;
		String endereco = "localhost";
		String sentenca = "";
		DatagramSocket clienteSocket = new DatagramSocket();
		InetAddress ipServidor = InetAddress.getByName(endereco);

		while (!sentenca.contains("Tchau")) {
			byte[] enviarDados = new byte[1024];
			sentenca = in.nextLine();
			enviarDados = sentenca.getBytes();
			DatagramPacket enviarPacote = new DatagramPacket(enviarDados, enviarDados.length, ipServidor, porta);
			clienteSocket.send(enviarPacote);
		}

		byte[] receberDados = new byte[1024];
		DatagramPacket receberPacote = new DatagramPacket(receberDados, receberDados.length);
		clienteSocket.receive(receberPacote);
		ipServidor = receberPacote.getAddress();
		porta = receberPacote.getPort();

		String msgConvertida = new String(receberDados, "UTF-8");
		System.out.println(msgConvertida);
		clienteSocket.close();
	}
}
