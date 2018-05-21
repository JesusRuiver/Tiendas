package bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Conexion {

	private Connection conexion;
	private String usuario;
	private String pass;
	private String baseDatos;
	private String servidor;
	private String forName;

	private Statement sentencia;

	public Conexion() {

	}

	public Conexion(Connection conexion, String usuario, String pass, String baseDatos, String servidor,
			String forName) {
		this.conexion = conexion;
		this.usuario = usuario;
		this.pass = pass;
		this.baseDatos = baseDatos;
		this.servidor = servidor;
		this.forName = forName;
	}

	public void conectar() {

		this.forName = "com.mysql.jdbc.Driver";
		this.servidor = "jdbc:mysql://localhost/";
		this.baseDatos = "tiendas";
		this.usuario = "tiendas";
		this.pass = "tiendas";

		try {

			Class.forName(getForName());

			this.conexion = DriverManager.getConnection(getServidor() + getBaseDatos(), getUsuario(), getPass());

			this.sentencia = conexion.createStatement();

			System.out.println("Conectado");

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block

			System.out.println("Error... No Conectado");

			e.printStackTrace();
		}

	}

	public void cerrarConexion() {

		try {
			conexion.close();

			System.out.println("La conexión se ha cerrado correctamente");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR al cerrar al conexión");
			e.printStackTrace();
		}
	}

	public ArrayList<String> dameTiendas() {

		ArrayList<String> tiendas = new ArrayList<String>();

		String consulta = "SELECT * FROM TIENDAS";

		try {
			ResultSet resultado = sentencia.executeQuery(consulta);

			while (resultado.next()) {
				tiendas.add("NOMBRE: " + resultado.getString(2) + " CIF: " + resultado.getString(1));
			}
			resultado.close();
		} catch (Exception e) {

		}

		return tiendas;
	}

	public Connection getConexion() {
		return conexion;
	}

	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getBaseDatos() {
		return baseDatos;
	}

	public void setBaseDatos(String baseDatos) {
		this.baseDatos = baseDatos;
	}

	public String getServidor() {
		return servidor;
	}

	public void setServidor(String servidor) {
		this.servidor = servidor;
	}

	public String getForName() {
		return forName;
	}

	public void setForName(String forName) {
		this.forName = forName;
	}

}
