package dominio.excepcion;

public class ParqueaderoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ParqueaderoException(String message) {
		super(message);
	}
}
