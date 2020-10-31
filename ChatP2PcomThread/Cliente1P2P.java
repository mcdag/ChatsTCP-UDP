import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClienteUDP1 {
	static int portaCliente2;
	static InetAddress ipPC;
	static DatagramSocket clienteSocket;
	public static void main(String[] args) throws IOException {
		System.out.println("--- Conversa com João ---");
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
		
		String portaCliente2str = new String(receberDados, "UTF-8");
		portaCliente2 = Integer.parseInt(portaCliente2str.substring (0, 5));
		
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
				DatagramPacket enviarPacote = new DatagramPacket(enviarDados, enviarDados.length, ipPC, portaCliente2);
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
