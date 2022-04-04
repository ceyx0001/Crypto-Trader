package utils.api;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DataFetcher {

	protected JsonObject getDataForCrypto(String id) {

		String urlString = String.format(
				"https://api.coingecko.com/api/v3/coins/%s/history?date=%s", id, getDate());
		
		try {
			URL url = new URL(urlString);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
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
			} else if (responsecode == 404) {
				System.out.println("could not find id for " + id);
			}

		} catch (IOException e) {
			System.out.println("Something went wrong with the API call.");
		}
		return null;
	}

	protected double getPriceForCoin(String id) {
		double price = 0.0;

		JsonObject jsonObject = getDataForCrypto(id);
		if (jsonObject != null) {
			JsonObject marketData = jsonObject.get("market_data").getAsJsonObject();
			JsonObject currentPrice = marketData.get("current_price").getAsJsonObject();
			DecimalFormat df = new DecimalFormat("#.##");
			price = currentPrice.get("cad").getAsDouble();
			String temp = df.format(price);
			price = Double.parseDouble(temp);
		}

		return price;
	}

	private String getDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date dateVar = new Date();
		return formatter.format(dateVar);
	}
}