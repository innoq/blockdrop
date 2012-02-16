package de.innoq.blockdrop.http;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;

import de.innoq.blockdrop.TetrisListener;
import de.innoq.blockdrop.TetrisServer;
import de.innoq.blockdrop.model.Point;

/**
 * A TetrisListener that exposed the currrent board and tile via http:
 * 
 */
public class HttpServerTetrisListener implements TetrisListener {

	private TetrisServer server;

	public HttpServerTetrisListener() {
		try {
			startServer();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * Http Uri to get current board as json
	 * 
	 * @return
	 */
	private URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost/").port(9998).build();
	}

	protected HttpServer startServer() throws IOException {
		final URI BASE_URI = getBaseURI();
		System.out.println("Starting grizzly...");
		// ResourceConfig rc = new
		// PackagesResourceConfig("com.sun.jersey.samples.helloworld.resources");
		final BlockDropResource resource = new BlockDropResource();
		ResourceConfig rc = new DefaultResourceConfig() {

			@Override
			public Object getProperty(String propertyName) {
				if ("board".equals(propertyName)) {
					return concat(server.getFixed(), server.getCurrentPiece());
				}
				return null;
			}

			@Override
			public Set<Object> getSingletons() {
				Set<Object> result = new HashSet<Object>();
				result.add(resource);
				return result;
			}
		};
		return GrizzlyServerFactory.createHttpServer(BASE_URI, rc);
	}

	@Override
	public void setServer(TetrisServer server) {
		this.server = server;
	}

	@Override
	public void next(Point[] currentBlock, Point[] fixed) {
		// NoOp
	}

	public static <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}
}
