package ventanas;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bbdd.Conexion;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class EjercicioExportarImportar extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	private String[] ventas;
	private String[] pedidos;

	private Conexion miConexion = new Conexion();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EjercicioExportarImportar frame = new EjercicioExportarImportar();
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
	public EjercicioExportarImportar() {

		miConexion.conectar();

		/*------------------------COMPONENTES DE LA VENTANA----------------------------*/

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 365, 403);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JComboBox cboxTiendas = new JComboBox();
		cboxTiendas.setBounds(42, 28, 272, 23);
		contentPane.add(cboxTiendas);

		JRadioButton rbtnVentas = new JRadioButton("Ventas");
		buttonGroup.add(rbtnVentas);
		rbtnVentas.setBounds(109, 62, 109, 23);
		rbtnVentas.setSelected(true);
		contentPane.add(rbtnVentas);

		JRadioButton rbtnPedidos = new JRadioButton("Pedidos");
		buttonGroup.add(rbtnPedidos);
		rbtnPedidos.setBounds(109, 95, 109, 23);
		contentPane.add(rbtnPedidos);

		JButton btnExportarBinarioSecuencial = new JButton("Exportar Fichero Binario");
		btnExportarBinarioSecuencial.setBounds(133, 148, 181, 23);
		contentPane.add(btnExportarBinarioSecuencial);

		JButton btnExportarXML = new JButton("Exportar Fichero XML");
		btnExportarXML.setBounds(133, 193, 181, 23);
		contentPane.add(btnExportarXML);

		JButton btnImportarBinarioSecuencial = new JButton("Importar Fichero Binario");
		btnImportarBinarioSecuencial.setBounds(133, 240, 181, 23);
		contentPane.add(btnImportarBinarioSecuencial);

		JButton btnImportarFicheroXML = new JButton("Importar Fichero XML");
		btnImportarFicheroXML.setBounds(133, 292, 181, 23);
		contentPane.add(btnImportarFicheroXML);

		// Primero rellenamos el comboBox de Tiendas con NIF

		rellenaComboTiendas(miConexion, cboxTiendas);

		/*---------------------------------ACCIONES DE LOS BOTONES----------------------*/

		rbtnVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String nif = troceaNif(cboxTiendas);

				ventas = miConexion.dameVentas(nif);

				for (String i : ventas) {
					System.out.println(i);
				}

			}
		});

		rbtnPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String nif = troceaNif(cboxTiendas);

				pedidos = miConexion.damePedidos(nif);

				for (String i : pedidos) {
					System.out.println(i);
				}

			}
		});

		cboxTiendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (rbtnVentas.isSelected() == true) {

					String nif = troceaNif(cboxTiendas);

					ventas = miConexion.dameVentas(nif);

					for (String i : ventas) {
						System.out.println(i);
					}
				} else {

					String nif = troceaNif(cboxTiendas);

					pedidos = miConexion.damePedidos(nif);

					for (String i : pedidos) {
						System.out.println(i);
					}

				}

			}
		});

		btnExportarBinarioSecuencial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (rbtnVentas.isSelected() == true) {

					try {
						File fichero = new File("FicheroDatosVentas.dat");

						FileOutputStream fileout = new FileOutputStream(fichero);

						DataOutputStream dataOS = new DataOutputStream(fileout);

						for (int i = 0; i < ventas.length; i++) {

							dataOS.writeUTF(ventas[i]); // inserta nombre
						}
						dataOS.close(); // cerrar stream

						System.out.println("Exportado Fichero Ventas");

					} catch (Exception ex) {
						// TODO: handle exception
					}

				} else {

					try {
						File fichero = new File("FicheroDatosPedidos.dat");

						FileOutputStream fileout = new FileOutputStream(fichero);

						DataOutputStream dataOS = new DataOutputStream(fileout);

						for (int i = 0; i < pedidos.length; i++) {

							dataOS.writeUTF(pedidos[i]); // inserta nombre

						}

						dataOS.close(); // cerrar stream

						System.out.println("Exportado Fichero Pedidos");
					} catch (Exception ex) {
						// TODO: handle exception
					}

				}

			}
		});

		btnImportarBinarioSecuencial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (rbtnVentas.isSelected() == true) {
					try {
						File fichero = new File("FicheroDatosVentas.dat");

						FileInputStream filein = new FileInputStream(fichero);

						DataInputStream dataIS = new DataInputStream(filein);

						String ventas;

						while (true) {

							ventas = dataIS.readUTF();

							System.out.println(ventas);
						}
						// Funcion pero no me deja cerrar el DataInputStream
						// dataIS.close();

					} catch (Exception ex) {
						// TODO: handle exception
					}

				} else {

					try {

						File fichero = new File("FicheroDatosPedidos.dat");

						FileInputStream filein = new FileInputStream(fichero);

						DataInputStream dataIS = new DataInputStream(filein);

						String pedidos;

						while (true) {

							pedidos = dataIS.readUTF();

							System.out.println(pedidos);
						}
						// Funcion pero no me deja cerrar el DataInputStream
						// dataIS.close();

					} catch (Exception ex) {
						// TODO: handle exception
					}

				}
			}
		});

		btnExportarXML.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

			}

		});
	}

	/*-------------------------------------METODOS-----------------------------------*/

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
}
