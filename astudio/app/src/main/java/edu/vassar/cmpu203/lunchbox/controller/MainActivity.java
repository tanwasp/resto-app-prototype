package edu.vassar.cmpu203.lunchbox.controller;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashSet;

import edu.vassar.cmpu203.lunchbox.model.IFilter;
import edu.vassar.cmpu203.lunchbox.model.LocFilter;
import edu.vassar.cmpu203.lunchbox.model.PriceFilter;
import edu.vassar.cmpu203.lunchbox.model.Restaurant;
import edu.vassar.cmpu203.lunchbox.model.RestaurantLibrary;
import edu.vassar.cmpu203.lunchbox.model.ReviewsLibrary;
import edu.vassar.cmpu203.lunchbox.model.User;
import edu.vassar.cmpu203.lunchbox.view.HomeFragment;
import edu.vassar.cmpu203.lunchbox.view.IHomeView;
import edu.vassar.cmpu203.lunchbox.view.IMainView;
import edu.vassar.cmpu203.lunchbox.view.MainView;
import edu.vassar.cmpu203.lunchbox.view.ISearchView;
import edu.vassar.cmpu203.lunchbox.view.SearchFragment;

public class MainActivity extends AppCompatActivity implements IHomeView.Listener, ISearchView.Listener {
    private RestaurantLibrary lib;
    private ReviewsLibrary revLib;
    private User curUser;
    IMainView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        curUser = new User("Me", 30, -90);
        lib = new RestaurantLibrary();
        revLib = new ReviewsLibrary();

        // Initialize HomeView and SearchView
        this.mainView = new MainView(this);
        HomeFragment homeFragment = new HomeFragment(this);
        this.mainView.displayFragment(homeFragment, false, "home");

        setContentView(this.mainView.getRootView());
    }

    // HomeView.Listener methods
    @Override
    public void onNavigateToSearch() {
        SearchFragment searchFragment = new SearchFragment(this);
        this.mainView.displayFragment(searchFragment, false, "search");
    }

    // SearchView.Listener methods
    @Override
    public void onPerformSearch(String searchTerm, String priceFilter, String distanceFilter, String sortOption) {
        HashSet<IFilter> filters = new HashSet<IFilter>();
        if(priceFilter != null){
            PriceFilter pf = new PriceFilter(priceFilter);
            filters.add(pf);
        }
        if(distanceFilter != null){
            try {
                LocFilter lf = new LocFilter(Integer.parseInt(distanceFilter), curUser);
                filters.add(lf);
            } catch (NumberFormatException e){
                System.out.println("Something went wrong. Invalid integer.");
            }
        }
        ArrayList<Restaurant> matches = lib.search(searchTerm, filters, sortOption, curUser);
//        TODO

//        ui.displayRestaurants(matches);
//        return matches;



    }
}
