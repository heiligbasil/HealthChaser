package com.lambdaschool.healthchaser.connectivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class NetworkAdapter {
    static final String REQUEST_GET = "GET";
    static final String REQUEST_POST = "POST";
    static final String REQUEST_PUT = "PUT";
    static final String REQUEST_DELETE = "DELETE";
    private static final int READ_TIMEOUT = 3000;
    private static final int CONNECT_TIMEOUT = 3000;

    public static String httpRequest(String url) {
        return httpRequest(url, REQUEST_GET, null, null);
    }

    public static String httpRequest(String url, String requestMethod) {
        return httpRequest(url, requestMethod, null, null);
    }

    public static String httpRequest(String url, String requestMethod, JSONObject requestBody, Map<String, String> headerProperties) {
        String httpResult = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;

        try {
            URL urlObject = new URL(url);
            httpURLConnection = (HttpURLConnection) urlObject.openConnection();
            httpURLConnection.setReadTimeout(READ_TIMEOUT);
            httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT);
            httpURLConnection.setRequestMethod(requestMethod);

            if (headerProperties != null) {
                for (Map.Entry<String, String> property : headerProperties.entrySet()) {
                    httpURLConnection.setRequestProperty(property.getKey(), property.getValue());
                }
            }

            switch (requestMethod) {
                case REQUEST_POST:
                case REQUEST_PUT:
                    if (requestBody != null) {
                        httpURLConnection.setDoInput(true);
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        outputStream.write(requestBody.toString().getBytes());
                        outputStream.close();
                        break;
                    }
                default:
                    httpURLConnection.connect();
                    break;
            }

            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String singleLine;
                    while ((singleLine = bufferedReader.readLine()) != null) {
                        stringBuilder.append(singleLine);
                    }
                    httpResult = stringBuilder.toString();
                }
            } else {
                throw new IOException("Connection failed (" + responseCode + ")");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return httpResult;
    }

    public static Bitmap httpImageRequest(String url) {
        Bitmap httpImageResult = null;
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;

        try {
            URL urlObject = new URL(url);
            httpURLConnection = (HttpURLConnection) urlObject.openConnection();
            httpURLConnection.setReadTimeout(READ_TIMEOUT);
            httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT);
            httpURLConnection.connect();
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                httpImageResult = BitmapFactory.decodeStream(inputStream);
            } else {
                throw new IOException("Connection failed (" + responseCode + ")");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return httpImageResult;
    }

    public static String fileParse(String path, String filename) {
        String fileParseResults = null;
        File file = null;
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            file = new File(path, filename);
            boolean ex=file.exists();
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ((fileParseResults = bufferedReader.readLine()) != null) {
                stringBuilder.append(fileParseResults).append(System.getProperty("line.separator"));
            }
            fileParseResults = stringBuilder.toString();

            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileParseResults;
    }
}
