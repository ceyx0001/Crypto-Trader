package utils.api;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DataFetcher {
	private HashMap<String, Double> priceMap;

	protected DataFetcher() {
		priceMap = new HashMap<String, Double>();
	}

	protected HashMap<String, Double> getPriceMap() {
		return priceMap;
	}

	protected void populate() {
		CryptoList list = new CryptoList();
		for (int i = 0; i < list.getAvailableCryptos().length; i++) {
			String name = list.getAvailableCryptos()[i];
			priceMap.put(name, getPriceForCoin(name, today()));
		}
	}

	protected String today() {
		SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
		return f.format( new Date());
	}

	private JsonObject getDataForCrypto(String id, String date) {

		String urlString = String.format(
				"https://api.coingecko.com/api/v3/coins/%s/history?date=%s", id, date);
		
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
				JsonObject jsonObject = new JsonParser().parse(inline).getAsJsonObject();
				return jsonObject;
			}

		} catch (IOException e) {
			System.out.println("Something went wrong with the API call.");
		}
		return null;
	}
	
	private double getPriceForCoin(String id, String date) {
		double price = 0.0;
		
		JsonObject jsonObject = getDataForCrypto(id, date);
		if (jsonObject != null) {
			JsonObject marketData = jsonObject.get("market_data").getAsJsonObject();
			JsonObject currentPrice = marketData.get("current_price").getAsJsonObject();
			price = currentPrice.get("cad").getAsDouble();
		}
		
		return price;
	}
	
	/*
	private double getMarketCapForCoin(String id, String date) {
		double marketCap = 0.0;
		
		JsonObject jsonObject = getDataForCrypto(id, date);
		if (jsonObject != null) {
			JsonObject marketData = jsonObject.get("market_data").getAsJsonObject();
			JsonObject currentPrice = marketData.get("market_cap").getAsJsonObject();
			marketCap = currentPrice.get("cad").getAsDouble();
		}
		
		return marketCap;
	}
	
	private double getVolumeForCoin(String id, String date) {
		double volume = 0.0;
		
		JsonObject jsonObject = getDataForCrypto(id, date);
		if (jsonObject != null) {
			JsonObject marketData = jsonObject.get("market_data").getAsJsonObject();
			JsonObject currentPrice = marketData.get("total_volume").getAsJsonObject();
			volume = currentPrice.get("cad").getAsDouble();
		}
		
		return volume;
	}
	*/
}
