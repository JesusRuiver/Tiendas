package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bbdd.Conexion;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;

public class EjercicioComboTabla extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EjercicioComboTabla frame = new EjercicioComboTabla();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EjercicioComboTabla() {
		
		Conexion miConexion = new Conexion();
		
		miConexion.conectar();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 866, 461);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox cboxTiendas = new JComboBox();
		cboxTiendas.setBounds(27, 24, 287, 20);
		contentPane.add(cboxTiendas);
		
		JRadioButton rbtnVentas = new JRadioButton("Ventas");
		rbtnVentas.setSelected(true);
		rbtnVentas.setBounds(61, 68, 66, 23);
		contentPane.add(rbtnVentas);
		
		JRadioButton rbtnPedidos = new JRadioButton("Pedidos");
		rbtnPedidos.setBounds(186, 68, 66, 23);
		contentPane.add(rbtnPedidos);
		
		rellenaComboTiendas(miConexion, cboxTiendas);
		
		
		
	}

	public void rellenaComboTiendas(Conexion miConexion, JComboBox cboxTiendas) {
		ArrayList <String> tiendas = new ArrayList();
		
		tiendas = miConexion.dameTiendas();
		
		for (int i = 0; i < tiendas.size(); i++){
			cboxTiendas.addItem(tiendas.get(i));
		}
	}
}
