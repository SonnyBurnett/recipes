package bakker.nick.recipes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import bakker.nick.recipes.RecipeClient;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.recipe_list);
        //String BASE_URL = "https://api.github.com/";
        String BASE_URL = "https://www.food2fork.com/";


        // Specify the Retrofit builder that will take care of the API
        // you have to define the (base) url
        // and a converter. In this case GSON will convert the JSON output to java

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        // create the actual Retrofit object
        Retrofit retrofit = builder.build();

        //Call the API
        RecipeClient client = retrofit.create(RecipeClient.class);
        Call<RecipeList> call = client.recipeList();


        // Consume the output asynchrone
        call.enqueue(new Callback<RecipeList>() {
            @Override
            public void onResponse(Call<RecipeList> call, Response<RecipeList> response) {
                RecipeList recipes = response.body();
                if (recipes == null) return;

                int count = recipes.getCount();
                ArrayList<Recipe> recepten = new ArrayList<>();
                for (int i=0; i<count; i++) {
                    recepten.add(recipes.getRecipes().get(i));
                }

                listView.setAdapter(new RecipeAdapter(MainActivity.this, recepten));
            }

            @Override
            public void onFailure(Call<RecipeList> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error :(", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
