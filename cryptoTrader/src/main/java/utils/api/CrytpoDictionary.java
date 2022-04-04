package utils.api;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * This class contains implements the mapping of a coin symbol to its name
 * which are retrieved from CoinGecko's API
 * 
 * @author Ernest Li, Simone Sequeira
 * @date 2022-03-30
 */
public class CrytpoDictionary {
	private HashMap<String, String> cryptoDictionary;

	/**
	 * Constructor for CryptoDictionary object
	 *
	 * @return CryptoDictionary the CryptoDictionary object
	 */
	protected CrytpoDictionary() {
		cryptoDictionary = new HashMap<String, String>();
		findAvailableCryptos();
	}
	
	/**
	 * Maps the symbol of coins to its name
	 *
	 * @return void
	 */
	private void findAvailableCryptos() {
		String urlString = 
				"https://api.coingecko.com/api/v3/coins/markets" + 
						"?vs_currency=cad&order=market_cap_desc&per_page=100&page=1&sparkline=false";
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			if (responsecode == 200) {
				String inline = "";
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				sc.close();
				JsonArray jsonArray = new JsonParser().parse(inline).getAsJsonArray();
				int size = jsonArray.size();
				
				for (int i = 0; i < size; i++) {
					JsonObject object = jsonArray.get(i).getAsJsonObject();
					String name = object.get("id").getAsString().toLowerCase();
					String symbol = object.get("symbol").getAsString().toUpperCase();
					cryptoDictionary.put(symbol, name);
				}
			}

		} catch (IOException e) {
			System.out.println("Failed connecting to CoinGecko API: " + e.getMessage());
		}
	}
	
	/**
	 * Gets the HashMap mapping the symbol of a coin to its name
	 *
	 * @return HashMap<String, String> the mapping of a coin symbol to its name
	 */
	protected HashMap<String, String> getCryptoDictionary() {
		return cryptoDictionary;
	}
}
