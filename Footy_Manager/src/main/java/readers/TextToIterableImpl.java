package readers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class TextToIterableImpl implements TextToIterable{
	
	private String nf;

	public TextToIterableImpl(String f) {
		nf = f;
	}

	@Override
	public Iterator<String> iterator() {
		return new IteradorFlujoEntrada();
	}

	private class IteradorFlujoEntrada implements Iterator<String> {
		private BufferedReader bf;
		private String linea;

		public IteradorFlujoEntrada() {
			try {
				bf = new BufferedReader(new FileReader(nf));
				linea = bf.readLine();
			} catch (IOException e) {
				throw new IllegalArgumentException("No se puede acceder al fichero de entrada");
			}
		}

		public boolean hasNext() {
			return linea != null;
		}

		public String next() {
			if (!hasNext())
				throw new IllegalArgumentException("No se puede acceder a next");
			String pal = linea;
			try {
				linea = bf.readLine();
			} catch (IOException e) {
				throw new IllegalArgumentException("No se puede acceder al fichero de entrada");
			}
			return pal;
		}

		public void remove() {

		}
	}
}
