package com.sp.restaurantlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.sp.restaurantlist.Restaurant;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RestaurantList extends AppCompatActivity {
    EditText name;
    EditText address;
    EditText tel;
    Button bsave;
    RadioGroup type;

    private List<com.sp.restaurantlist.Restaurant> model = new ArrayList<com.sp.restaurantlist.Restaurant>();
    private RestaurantAdapter adapter = null;
    private ListView list;
    private TabHost host;
    View.OnClickListener save = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            host.setCurrentTab(0);
            String restname = name.getText().toString();
            String restadd = address.getText().toString();
            String resttel = tel.getText().toString();
            String msg = restname + "\n" + restadd + "\n" + resttel;
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

            String restType = "";
            //to read selection of restaurantTypes RadioGroup
            switch (type.getCheckedRadioButtonId()) {
                case R.id.Chinese:
                    restType = "Chinese";
                    break;
                case R.id.Western:
                    restType = "Western";
                    break;
                case R.id.Indian:
                    restType = "Indian";
                    break;
                case R.id.Indonesian:
                    restType = "Indonesian";
                    break;
                case R.id.Korean:
                    restType = "Korean";
                    break;
                case R.id.Japanese:
                    restType = "Japanese";
                    break;
                case R.id.Thai:
                    restType = "Thai";
                    break;
            }
            //create restaurant model
            com.sp.restaurantlist.Restaurant restaurant = new Restaurant();
            //update restaurant data model
            restaurant.setName(restname);
            restaurant.setAddress(restadd);
            restaurant.setTelephone(resttel);
            restaurant.setRestaurantType(restType);
            //Pass the record to ArrayAdapter.
            //It will update the ListArray and the ListView
            adapter.add(restaurant);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.restaurant_name);
        address = findViewById(R.id.restaurant_address);
        tel = findViewById(R.id.restaurant_tel);
        bsave = findViewById(R.id.button_save);
        type = findViewById(R.id.restaurant_types);
        bsave.setOnClickListener(save);

        list = findViewById(R.id.restaurants);
        adapter = new RestaurantAdapter();
        list.setAdapter(adapter);

        host = findViewById(R.id.tabhost);
        host.setup();

        //tab1
        TabHost.TabSpec spec = host.newTabSpec("List");
        spec.setContent(R.id.restaurants_tab);
        spec.setIndicator("List");
        host.addTab(spec);

        //tab2
        spec = host.newTabSpec("Details");
        spec.setContent(R.id.details_tab);
        spec.setIndicator("Details");
        host.addTab(spec);
        host.setCurrentTab(1);
        list.setOnItemClickListener(onListClick);
    }

    static class RestaurantHolder {
        private TextView restName = null;
        private TextView addr = null;
        private ImageView icon = null;

        RestaurantHolder(View row) {
            restName = row.findViewById(R.id.restName);
            addr = row.findViewById(R.id.restAddr);
            icon = row.findViewById(R.id.icon);
        }

        void populateFrom(Restaurant r) {
            restName.setText(r.getName());
            addr.setText(r.getAddress() + ", " + r.getTelephone());
            if (r.getRestaurantType().equals("Chinese")) {
                icon.setImageResource(R.drawable.ball_red_1_);
            } else if (r.getRestaurantType().equals("Western")) {
                icon.setImageResource(R.drawable.ball_yellow_1_);
            } else {
                icon.setImageResource(R.drawable.ball_green_1_);
            }
        }
    }

    class RestaurantAdapter extends ArrayAdapter<Restaurant> {
        RestaurantAdapter() {
            super(RestaurantList.this, R.layout.row, model);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            RestaurantHolder holder;
            if (row == null) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.row, parent, false);
                holder = new RestaurantHolder(row);
                row.setTag(holder);
            } else {
                holder = (RestaurantHolder) row.getTag();
            }
            holder.populateFrom(model.get(position));
            return (row);
        }
    }

    AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Restaurant r = model.get(position);

            name.setText(r.getName());
            address.setText(r.getAddress());
            tel.setText(r.getTelephone());

            if (r.getRestaurantType().equals("Chinese")) {
                type.check(R.id.Chinese);
            } else if (r.getRestaurantType().equals("Western")) {
                type.check(R.id.Western);
            } else if (r.getRestaurantType().equals("Indian")) {
                type.check(R.id.Indian);
            } else if (r.getRestaurantType().equals("Indonesian")) {
                type.check(R.id.Indonesian);
            } else if (r.getRestaurantType().equals("Korean")) {
                type.check(R.id.Korean);
            } else if (r.getRestaurantType().equals("Japanese")) {
                type.check(R.id.Japanese);
            } else {
                type.check(R.id.Thai);
            }
            host.setCurrentTab(1);
        }
    };
}