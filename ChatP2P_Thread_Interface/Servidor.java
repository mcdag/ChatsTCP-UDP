import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Servidor {
	public static void main(String [] args) throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(8888);
        int portaCliente1, portaCliente2;
        InetAddress ipPC = InetAddress.getByName("localhost");
        
        byte[] receberDados1 = new byte[1024];
        byte[] receberDados2 = new byte[1024];
        byte[] enviarDados1 = new byte[1024];
        byte[] enviarDados2 = new byte[1024];
        
        DatagramPacket receberPacote1 = new DatagramPacket(receberDados1, receberDados1.length);
        serverSocket.receive(receberPacote1);
        portaCliente1 = receberPacote1.getPort();
        
        DatagramPacket receberPacote2 = new DatagramPacket(receberDados2, receberDados2.length);
        serverSocket.receive(receberPacote2);
        portaCliente2 = receberPacote2.getPort();
        
        enviarDados1 = Integer.toString(portaCliente2).getBytes();
        DatagramPacket enviarPacote1 = new DatagramPacket(enviarDados1, enviarDados1.length, ipPC, portaCliente1);
        serverSocket.send(enviarPacote1);
        
        enviarDados2 = Integer.toString(portaCliente1).getBytes();
        DatagramPacket enviarPacote2 = new DatagramPacket(enviarDados2, enviarDados2.length, ipPC, portaCliente2);
        serverSocket.send(enviarPacote2);
        
    }
}
