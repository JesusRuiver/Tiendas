package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import bbdd.Conexion;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ButtonGroup;

public class EjercicioComboTabla extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTable tablaVentas;
	private JScrollPane scrollPaneVentas;
	
	private Conexion miConexion = new Conexion();

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
		buttonGroup.add(rbtnVentas);
		rbtnVentas.setSelected(true);
		rbtnVentas.setBounds(61, 68, 66, 23);
		contentPane.add(rbtnVentas);

		JRadioButton rbtnPedidos = new JRadioButton("Pedidos");
		buttonGroup.add(rbtnPedidos);
		rbtnPedidos.setBounds(186, 68, 66, 23);
		contentPane.add(rbtnPedidos);

		rellenaComboTiendas(miConexion, cboxTiendas);
		
		rbtnVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String nif = troceaNif(cboxTiendas);

				construirTablaVentas(nif);

			}
		});

	}

	public void rellenaComboTiendas(Conexion miConexion, JComboBox cboxTiendas) {
		ArrayList<String> tiendas = new ArrayList();

		tiendas = miConexion.dameTiendas();

		for (int i = 0; i < tiendas.size(); i++) {
			cboxTiendas.addItem(tiendas.get(i));
		}
	}

	private String troceaNif(JComboBox cboxTiendas) {
		String tiendaYnif;

		tiendaYnif = cboxTiendas.getSelectedItem().toString().trim();

		String[] parteNif = tiendaYnif.trim().split(": ");

		String nif = parteNif[2];

		return nif;
	}
	
	private DefaultTableModel construirTablaVentas() {

		DefaultTableModel modelo = new DefaultTableModel();
		
		modelo.addColumn("NIF");
		modelo.addColumn("ARTICULO");
		modelo.addColumn("FABRICANTE");
		modelo.addColumn("PESO");
		modelo.addColumn("CATEGORIA");
		modelo.addColumn("FECHA VENTA");
		modelo.addColumn("UNIDADES VENDIDAS");
		modelo.addColumn("PRECIO VENTA");

		tablaVentas = new JTable(modelo);
		scrollPaneVentas.setViewportView(tablaVentas);
		
		return modelo;

	}
	
	private void obtenerDatosVentas(DefaultTableModel modelo, String nif) {

		ArrayList<String> ventas = new ArrayList<String>();

		ventas = miConexion.dameVentas(nif);

		for (int i = 0; i < ventas.size(); i++) {

		modelo.addRow(ventas.get(i));

		}

	}
}
