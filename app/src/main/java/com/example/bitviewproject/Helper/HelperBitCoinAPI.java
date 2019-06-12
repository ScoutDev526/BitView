package com.example.bitviewproject.Helper;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.bitviewproject.Model.APIObjects;
import com.example.bitviewproject.Model.CryptoCurrency;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.realm.Case;
import io.realm.Realm;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HelperBitCoinAPI {

    private Realm realm;
    private final Handler handler;
    String JSON_LIST_CURRENCIES;
    static int globalCounter = 1;
    ArrayList<APIObjects> apiObjects = new ArrayList<>();
    private static OkHttpClient client = new OkHttpClient();

    public HelperBitCoinAPI(Context context) {
        realm = Realm.getDefaultInstance();
        handler = new Handler(context.getMainLooper());
    }

    private void changeAPIUrl() {
        switch (globalCounter) {
            case 1:
                JSON_LIST_CURRENCIES = "https://api.cryptonator.com/api/full/btc-usd";
                break;
            case 2:
                JSON_LIST_CURRENCIES = "https://api.cryptonator.com/api/full/eth-usd";
                break;
            case 3:
                JSON_LIST_CURRENCIES = "https://api.cryptonator.com/api/full/xrp-usd";
                break;
            case 4:
                JSON_LIST_CURRENCIES = "https://api.cryptonator.com/api/full/bch-usd";
                break;
            case 5:
                JSON_LIST_CURRENCIES = "https://api.cryptonator.com/api/full/eos-usd";
                break;
            case 6:
                JSON_LIST_CURRENCIES = "https://api.cryptonator.com/api/full/ltc-usd";
                break;
            case 7:
                JSON_LIST_CURRENCIES = "https://api.cryptonator.com/api/full/bnb-usd";
                break;
            case 8:
                JSON_LIST_CURRENCIES = "https://api.cryptonator.com/api/full/bsv-usd";
                break;
            case 9:
                JSON_LIST_CURRENCIES = "https://api.cryptonator.com/api/full/zec-usd";
                //TODO: NO EXISTE
                break;
            case 10:
                JSON_LIST_CURRENCIES = "https://api.cryptonator.com/api/full/trx-usd";
                break;
            case 11:
                JSON_LIST_CURRENCIES = "https://api.cryptonator.com/api/full/ada-usd";
                break;
            case 12:
                JSON_LIST_CURRENCIES = "https://api.cryptonator.com/api/full/xmr-usd";
                break;
            case 13:
                JSON_LIST_CURRENCIES = "https://api.cryptonator.com/api/full/dash-usd";
                break;
            case 14:
                JSON_LIST_CURRENCIES = "https://api.cryptonator.com/api/full/miota-usd";
                break;
            case 15:
                JSON_LIST_CURRENCIES = "https://api.cryptonator.com/api/full/etc-usd";
                break;
            case 16:
                JSON_LIST_CURRENCIES = "https://api.cryptonator.com/api/full/neo-usd";
                break;
            case 17:
                JSON_LIST_CURRENCIES = "https://api.cryptonator.com/api/full/xem-usd";
                break;
        }
    }

    public void load() throws IOException, JSONException {
        while (globalCounter < 18) {
            changeAPIUrl();
            Request request = new Request.Builder()
                    .url(JSON_LIST_CURRENCIES)
                    .build();
            System.out.println(globalCounter + " ----> " + JSON_LIST_CURRENCIES);
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("ERROR LOOP", "No ejecuta bien loop api");
                }
                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    final String body = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                parseBpiResponse(body);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
            globalCounter++;
        }
    }

    public void parseBpiResponse(String body) throws JSONException {
        StringBuilder base = new StringBuilder();
        StringBuilder price = new StringBuilder();
        JSONObject jsonObject = new JSONObject(body);
        JSONObject tickerObject = jsonObject.getJSONObject("ticker");
        base.append(tickerObject.getString("base"));
        price.append(tickerObject.getString("price"));
        String name = base.toString();
        int value = (int) Double.parseDouble(price.toString());
        System.out.println(name + " ------- " + value);
        updateCurrency(name, value);
    }

    private synchronized void updateCurrency(final String name, final int value) {
        try {
            realm = Realm.getDefaultInstance();
            System.out.println("UPDATECURREN");
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm bgRealm) {
                    CryptoCurrency first = bgRealm.where(CryptoCurrency.class).like("name", "*MONEDA", Case.INSENSITIVE).findFirst();
                    first.setName(name);
                    first.setValue(value);
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    // Transaction was a success.
                    System.out.println("ok");
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    // Transaction failed and was automatically canceled.
                    System.out.println("puta");
                }
            });
        } finally {
            realm.close();
        }
    }

    private void runOnUiThread(Runnable r) {
        handler.post(r);
    }

}
