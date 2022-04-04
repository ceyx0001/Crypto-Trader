package utils.api;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * This class implements the fetching of a coin's data and price
 * from CoinGecko's API
 * 
 * @author Ernest Li, Simone Sequeira
 * @date 2022-03-30
 */
public class DataFetcher {
	/**
	 *  Gets the data of a cryptocoin by querying CoinGecko API
	 * @param id the coin's name
	 * @return JsonObject the coin's data
	 */
	protected JsonObject getDataForCrypto(String id, HttpURLConnection conn, URL url) {

			int responsecode = 0;
			try {
				responsecode = conn.getResponseCode();
			} catch (IOException e) {
				System.out.println("Error getting response code");
			}
			if (responsecode == 200) {
				String inline = "";
				Scanner sc = null;
				try {
					sc = new Scanner(url.openStream());
				} catch (IOException e) {
					System.out.println("Error opening stream: " + e.getMessage());
				}
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				sc.close();
				JsonObject jsonObject = new JsonParser().parse(inline).getAsJsonObject();
				return jsonObject;
			} else if (responsecode == 404) {
				System.out.println("could not find id for " + id);
			}

		
		return null;
	}

	/**
	 * Gets the price of a coin by parsing the JsonObject from
	 * CoinGecko's api
	 * @param id the coin's name
	 * @return double the coin's price
	 */
	protected double getPriceForCoin(String id) {
		double price = 0.0;
		String urlString = String.format(
				"https://api.coingecko.com/api/v3/coins/%s/history?date=%s", id, getDate());
		URL url = null;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			System.out.println("Malformed url while getting price for coin");
		}
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			System.out.println("IO exception while opening connection");
		}
		try {
			conn.setRequestMethod("GET");
		} catch (ProtocolException e) {
			System.out.println("Protocol exception while setting request method");
		}

		JsonObject jsonObject = getDataForCrypto(id, conn, url);
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

	/**
	 * Gets today's calendar day
	 * @return String today's date
	 */
	private String getDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date dateVar = new Date();
		return formatter.format(dateVar);
	}
}