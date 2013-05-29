package nl.tue.fingerpaint.client.json;

import java.util.HashMap;

import nl.tue.fingerpaint.client.MixingProtocol;
import nl.tue.fingerpaint.client.MixingProtocol.MixingProtocolJsonizer;

import org.jsonmaker.gwt.client.Jsonizer;
import org.jsonmaker.gwt.client.JsonizerException;
import org.jsonmaker.gwt.client.base.Defaults;
import org.jsonmaker.gwt.client.base.HashMapJsonizer;

import com.google.gwt.core.client.GWT;

public class ProtocolMap {
	private HashMap<String, MixingProtocol> protocols = new HashMap<String, MixingProtocol>();

	public HashMap<String, MixingProtocol> getProtocols() {
		return protocols;
	}

	public void addProtocol(String name, MixingProtocol protocol) {
		protocols.put(name, protocol);
	}

	public String jsonize() {
		HashMapJsonizer hj = new HashMapJsonizer(Defaults.STRING_JSONIZER,
				(MixingProtocolJsonizer) GWT
						.create(MixingProtocolJsonizer.class));

		return hj.asString(protocols);
	}

	public interface ProtocolMapJsonizer extends Jsonizer {
		@Override
		public String asString(Object javaValue) throws JsonizerException;
	}
}