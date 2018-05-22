package ventanas;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
	private JTable tablaPedidos;
	
	private JScrollPane scrollPaneTabla;

	
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

		scrollPaneTabla = new JScrollPane();
		scrollPaneTabla.setBounds(27, 98, 813, 314);
		contentPane.add(scrollPaneTabla);
		


		rbtnVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String nif = troceaNif(cboxTiendas);

				construirTablaVentas(nif);

			}
		});

		rbtnPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String nif = troceaNif(cboxTiendas);

				construirTablaPedidos(nif);

			}

		});

	}

	public void rellenaComboTiendas(Conexion miConexion, JComboBox cboxTiendas) {
		ArrayList<String> tiendas = new ArrayList<String>();

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

	private void construirTablaVentas(String nif) {

		String titulosColumnas[] = { "NIF", "ARTICULO", "FABRICANTE", "PESO", "CATEGORIA", "FECHA VENTA",
				"UNIDADES VENDIDAS", "PRECIO VENTA" };
		String informacionTablaVentas[][] = obtenerDatosVentas(nif);

		tablaVentas = new JTable(informacionTablaVentas, titulosColumnas);
		scrollPaneTabla.setViewportView(tablaVentas);

	}

	private String[][] obtenerDatosVentas(String nif) {

		/*
		 * Obtiene la longitud de la consulta a traves de un metodo que cuenta
		 * los campos de la consulra
		 */
		int numFilasVentas = miConexion.dameNumeroFilasVentas(nif);

		/*
		 * Tenemos un metodo que nos devuelve un resultset con los campos de la
		 * consulta
		 */

		ResultSet resultado = miConexion.dameResultadosVentas(nif);

		// Iniciamo el contador de filas
		int i = 0;

		// matriz [fila][columna]
		String matrizInfo[][] = new String[numFilasVentas][8];

		try {
			while (resultado.next()) {

				matrizInfo[i][0] = resultado.getString(1);
				matrizInfo[i][1] = resultado.getString(2);
				matrizInfo[i][2] = resultado.getString(3);
				matrizInfo[i][3] = resultado.getString(4);
				matrizInfo[i][4] = resultado.getString(5);
				matrizInfo[i][5] = resultado.getString(6);
				matrizInfo[i][6] = resultado.getString(7);
				matrizInfo[i][7] = resultado.getString(8);

				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return matrizInfo;
	}

	private void construirTablaPedidos(String nif) {
		String titulosColumnas[] = { "NIF", "ARTICULO", "FABRICANTE", "PESO", "CATEGORIA", "FECHA VENTA",
				"UNIDADES PEDIDAS", "PRECIO COSTO" };
		String informacionTablaPedidos[][] = obtenerDatosPedidos(nif);

		tablaPedidos = new JTable(informacionTablaPedidos, titulosColumnas);
		scrollPaneTabla.setViewportView(tablaPedidos);

	}

	private String[][] obtenerDatosPedidos(String nif) {
		
		/*
		 * Obtiene la longitud de la consulta a traves de un metodo que cuenta
		 * los campos de la consulra
		 */
		int numFilasPedidos = miConexion.dameNumeroFilasPedidos(nif);

		/*
		 * Tenemos un metodo que nos devuelve un resultset con los campos de la
		 * consulta
		 */

		ResultSet resultado = miConexion.dameResultadosPedidos(nif);

		// Iniciamo el contador de filas
		int i = 0;

		// matriz [fila][columna]
		String matrizInfo[][] = new String[numFilasPedidos][8];

		try {
			while (resultado.next()) {

				matrizInfo[i][0] = resultado.getString(1);
				matrizInfo[i][1] = resultado.getString(2);
				matrizInfo[i][2] = resultado.getString(3);
				matrizInfo[i][3] = resultado.getString(4);
				matrizInfo[i][4] = resultado.getString(5);
				matrizInfo[i][5] = resultado.getString(6);
				matrizInfo[i][6] = resultado.getString(7);
				matrizInfo[i][7] = resultado.getString(8);

				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return matrizInfo;
		
	}
}
