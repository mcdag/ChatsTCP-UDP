import java.net.*;
import java.util.*;
import java.io.*;

public class ClienteUDP2 {
	static int portaCliente1;
	static InetAddress ipPC;
	static DatagramSocket clienteSocket;
	public static void main(String[] args) throws IOException {
		System.out.println("--- Conversa com Maria ---");
		int porta = 8888;
		String endereco = "localhost";
		clienteSocket = new DatagramSocket();
		ipPC = InetAddress.getByName(endereco);
		
		byte[] enviarDados = new byte[1024];
		enviarDados = (" ").getBytes();
		DatagramPacket enviarPacote = new DatagramPacket(enviarDados, enviarDados.length, ipPC, porta);
		clienteSocket.send(enviarPacote);
		
		byte[] receberDados = new byte[1024];
		DatagramPacket receberPacote = new DatagramPacket(receberDados, receberDados.length);
		clienteSocket.receive(receberPacote);
		
		String portaCliente1str = new String(receberDados, "UTF-8");
		portaCliente1 = Integer.parseInt(portaCliente1str.substring(0,5));
		
		new Thread(enviar).start();
		new Thread(receber).start();
	}
	
	private static Runnable enviar = new Runnable() {
		Scanner in = new Scanner(System.in);
		int contador = 1;
		public void run() {
			while (true) {
				byte[] enviarDados = new byte[1024];
				String sentenca = in.nextLine();
				enviarDados = (contador + ": " + sentenca).getBytes();
				DatagramPacket enviarPacote = new DatagramPacket(enviarDados, enviarDados.length, ipPC, portaCliente1);
				contador++;
				try {
					clienteSocket.send(enviarPacote);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	private static Runnable receber = new Runnable() {
		public void run() {
			while (true) {
				byte[] receberDados = new byte[1024];
				DatagramPacket receberPacote = new DatagramPacket(receberDados, receberDados.length);
				try {
					clienteSocket.receive(receberPacote);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				String msgConvertida = "";
				try {
					msgConvertida = new String(receberDados, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				System.out.println(msgConvertida);
			}
		}
	};
}
