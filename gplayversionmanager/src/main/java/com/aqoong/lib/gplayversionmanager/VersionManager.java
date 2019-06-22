package com.aqoong.lib.gplayversionmanager;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class VersionManager {
    public static String getGooglePlayVersionString(Context context){
        String mData = null;
        String mVer = null;

        HttpURLConnection mConnection = null;
        BufferedReader mReader = null;
        String packageName = context.getPackageName();

        try{
            URL mUrl = new URL("https://play.google.com/store/apps/details?id="+packageName);

            mConnection = (HttpURLConnection)mUrl.openConnection();

            if(mConnection == null) return null;

            mConnection.setConnectTimeout(5000);
            mConnection.setUseCaches(false);
            mConnection.setDoOutput(true);

            if(mConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                mReader = new BufferedReader(
                        new InputStreamReader(mConnection.getInputStream()));

                while(true){
                    String line = mReader.readLine();
                    if(line == null) break;
                    mData += line;
                }
                mReader.close();

            }
            mConnection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }finally {
            if(mConnection != null){
                mConnection.disconnect();
            }
            if(mReader != null){
                try {
                    mReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        String startToken = "softwareVersion\">";
        String endToken = "<";
        int index = 0;

        try {
            index = mData.indexOf(startToken);
        }catch (Exception e){
            index = -1;
        }

        if (index == -1) {
            mVer = null;
        } else {
            mVer = mData.substring(index + startToken.length(), index + startToken.length() + 100);
            mVer = mVer.substring(0, mVer.indexOf(endToken)).trim();
        }

        return mVer;
    }

    public static int[] getGooglePlayVersion(Context context) throws NullPointerException{

        String googlePlayVersion = getGooglePlayVersionString(context);

        String[] verSplit = googlePlayVersion.split("\\.");
        int[] result = new int[verSplit.length];

        for(int i = 0 ; i < verSplit.length ; i++){
            result[i] = Integer.parseInt(verSplit[i]);
        }

        return result;
    }
}
