package utils.api;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CryptoList {
	private List<String> cryptoListSym;

	public CryptoList() {
		cryptoListSym = new ArrayList<>();
		findAvailableCryptos();
	}
	
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
					String symbol = object.get("symbol").getAsString().toUpperCase();
					cryptoListSym.add(symbol);
				}
			}

		} catch (IOException e) {
			System.out.println("Failed connecting to CoinGecko API: " + e.getMessage());
		}
	}
	
	protected String[] getAvailableCryptos() {
		return cryptoListSym.toArray(new String[cryptoListSym.size()]);
	}
}
