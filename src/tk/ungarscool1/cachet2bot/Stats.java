package tk.ungarscool1.cachet2bot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonObject;

public class Stats {

	private boolean active;
	private String key;
    private String url;
    private String metricId;
    private String componentId;
	
	public Stats(String[] args) {
		if (args[0].equalsIgnoreCase("true")) {
			this.active = true;
            this.key = args[1];
            this.url = args[2]+"/api/v1/";
            this.metricId = args[3];
            this.componentId = args[4];
            // Set User Agent
            System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36");
        } else {
        	this.active = false;
            return;
        }
	}
	
	public void sendStats() {
        if (active) {
        	try{
                URL url = new URL(this.url+"metrics/"+this.metricId+"/points");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("X-Cachet-Token", this.key);
                connection.setRequestMethod("POST");
                long timestamp = System.currentTimeMillis() / 1000L;
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("value",1);
                jsonObject.addProperty("timestamp",timestamp+"");
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(jsonObject.toString());
                writer.flush();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer();
                String res ;
                while ((res = in.readLine()) != null) {
                    stringBuffer.append(res);
                }
                in.close();
                System.out.println("res: "+stringBuffer.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
    }
	
	
	public void setBotStatus(Status status) {
		if (active) {
			if(!status.equals(Status.OPERATIONAL)&&!status.equals(Status.PERFORMANCE_ISSUES)&&!status.equals(Status.PARTIAL_OUTAGE)&&!status.equals(Status.MAJOR_OUTAGE)) {
				System.err.println("L'argument "+status.name()+" n'est pas valable");
			}

	        try{
	            URL url = new URL(this.url+"components/"+this.componentId);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setDoOutput(true);
	            connection.setDoInput(true);
	            connection.setRequestProperty("Content-Type", "application/json");
	            connection.setRequestProperty("Accept", "application/json");
	            connection.setRequestProperty("X-Cachet-Token", this.key);
	            connection.setRequestMethod("PUT");
	            JsonObject jsonObject = new JsonObject();
	            jsonObject.addProperty("status",status.getCode());
	            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
	            writer.write(jsonObject.toString());
	            writer.flush();
	            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            StringBuffer stringBuffer = new StringBuffer();
	            String res ;
	            while ((res = in.readLine()) != null) {
	                stringBuffer.append(res);
	            }
	            in.close();
	            System.out.println("res: "+stringBuffer.toString());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}

    }
	
	
}
