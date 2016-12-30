package rtc.nuttamon.kantana.monopolygame;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Created by ILLUTION on 28/11/2559.
 */

public class synQuestion extends AsyncTask<Void, Void, String>{

    //Explicit
    private static final String urLJSON ="http://swiftcodingthai.com/nut/get_question.php";
    private Context context;

    public synQuestion(Context context) {
        this.context = context;
    }


    @Override
    protected String doInBackground(Void... voids) {
        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(urLJSON).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
} // Main Class
