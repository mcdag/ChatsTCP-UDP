import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;

import java.net.*;
import java.util.*;
import java.io.*;
import javax.swing.JLabel;



public class Cliente2 extends JFrame {
	static int portaCliente1;
	static InetAddress ipPC;
	static DatagramSocket clienteSocket;
	private JPanel contentPane;
	private static JTextField textFieldMensagem;
	private static JTextPane textPane;
	String mensagem;
	int contador = 1;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cliente2 frame = new Cliente2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void addMensagensLabel (String a) {
		textPane.setText( textPane.getText() +  a + '\n');
	}
	
	public Cliente2() {
		int porta = 8888;
		String endereco = "localhost";
		try {
			clienteSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		try {
			ipPC = InetAddress.getByName(endereco);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		byte[] enviarDados = new byte[1024];
		enviarDados = (" ").getBytes();
		DatagramPacket enviarPacote = new DatagramPacket(enviarDados, enviarDados.length, ipPC, porta);
		try {
			clienteSocket.send(enviarPacote);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		byte[] receberDados = new byte[1024];
		DatagramPacket receberPacote = new DatagramPacket(receberDados, receberDados.length);
		try {
			clienteSocket.receive(receberPacote);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String portaCliente1str = null;
		try {
			portaCliente1str = new String(receberDados, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		portaCliente1 = Integer.parseInt(portaCliente1str.substring (0, 5));

		new Thread(receber).start();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 319, 465);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		textFieldMensagem = new JTextField();
		textFieldMensagem.setBounds(23, 232, 274, 19);
		textFieldMensagem.setColumns(10);
		
		textPane = new JTextPane();
		textPane.setFont(new Font("DialogInput", Font.BOLD, 12));
		textPane.setEditable(false);
		
		JButton btnEnviar = new JButton(">");
		btnEnviar.setFont(new Font("Dialog", Font.BOLD, 10));
		btnEnviar.setBackground(new Color(169, 169, 169));
		btnEnviar.setBounds(309, 229, 113, 25);
		
		scrollBar = new JScrollBar();
		
		lblConversaComMaria = new JLabel("Conversa com Maria:");
		lblConversaComMaria.setForeground(Color.WHITE);
		lblConversaComMaria.setFont(new Font("DialogInput", Font.BOLD, 12));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(textFieldMensagem, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnEnviar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(scrollBar, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(22)
							.addComponent(lblConversaComMaria)))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblConversaComMaria)
					.addPreferredGap(ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(scrollBar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(textFieldMensagem, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnEnviar, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addGap(18))
		);
		contentPane.setLayout(gl_contentPane);
		
		btnEnviar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mensagem = textFieldMensagem.getText();
				if(!mensagem.trim().equals("")) {
					enviar(contador);
					contador++;
					textFieldMensagem.setText("");
				}
			}
		});
		
		textFieldMensagem.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					mensagem = textFieldMensagem.getText();
					if(!mensagem.trim().equals("")) {
						enviar(contador);
						contador++;
						textFieldMensagem.setText("");
					}
				}
			}
		});
	}
	
	
	static int enviar(int contador) {
		String sentenca = textFieldMensagem.getText();
		byte[] enviarDados = new byte[1024];
		textPane.setText(textPane.getText() + "(" + contador + ") " + "Eu: " + sentenca + '\n');
		enviarDados = ("(" + contador + ") " + "João: " + sentenca).getBytes();
		DatagramPacket enviarPacote = new DatagramPacket(enviarDados, enviarDados.length, ipPC, portaCliente1);
		try {
			clienteSocket.send(enviarPacote);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contador;
	};
	
	public static Runnable receber = new Runnable() {
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
					textPane.setText(textPane.getText() + msgConvertida + '\n');
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
	};
	private JScrollBar scrollBar;
	private JLabel lblConversaComMaria;
}

