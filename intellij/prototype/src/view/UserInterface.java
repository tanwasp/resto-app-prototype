package view;
import model.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface{
    public void welcome(){
        System.out.println("Welcome to Lunch Box!");
    }
    public String[] getSearchData(){

        Scanner in = new Scanner(System.in);
        System.out.println("Please enter a search term to find a restaurant by name.");
        String term = in.nextLine();

        String priceFilter = askPriceFilter();
        String locFilter = askLocationFilter();
        String sort = askSort();

        String[] res = {term, priceFilter, locFilter, sort};
        return res;
    }

    public String askPriceFilter(){
        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.println("Would you like to filter by price? (y,n)");
            String resp = in.nextLine();
            if (resp.equals("y")) {
                System.out.println("Please enter a valid price category: $, $$, or $$$");
                String price = in.nextLine();
                if (price.equals("$") || price.equals("$$") || price.equals("$$$")){
                    return price;
                }
            } else if (resp.equals("n")) {
                return null;
            }
            System.out.println("Invalid input. Please try again.");
        }
    }

    public String askLocationFilter(){
        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.println("Would you like to filter by distance? (y,n)");
            String resp = in.nextLine();
            if (resp.equals("y")) {
                System.out.println("Please enter the max distance (in miles) the restaurant can be from you.");
                String dist = in.nextLine();
                try{
                    Float.parseFloat(dist);
                    return dist;
                } catch (NumberFormatException e){}
            } else if (resp.equals("n")) {
                return null;
            }
            System.out.println("Invalid input. Please try again.");
        }
    }

    public String askSort(){
        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.println("Would you like to sort your results? (y,n)");
            String resp = in.nextLine();
            if (resp.equals("y")) {
                System.out.println("How would you like to sort? (proximity, rating)");
                String sort = in.nextLine();
                if (sort.equalsIgnoreCase("proximity")){
                    return "prox";
                } else if (sort.equalsIgnoreCase("rating")){
                    return "rating";
                }
            } else if (resp.equals("n")) {
                return null;
            }
            System.out.println("Invalid input. Please try again.");
        }
    }

    public void displayRestaurants(ArrayList<Restaurant> results){
        StringBuilder output = new StringBuilder();

        if (results.isEmpty()) {
            System.out.println("No restaurants match the given criteria.");
            return;
        }

        output.append("=====================================\n");
        output.append("           RESTAURANT LIST           \n");
        output.append("=====================================\n");

        int count = 1;

        for (Restaurant restaurant : results) {
            output.append("Restaurant ").append(count).append("\n");
            output.append("Name: ").append(restaurant.name).append("\n");
            output.append("Rating: ").append(restaurant.rating).append("\n");
            output.append("Price: ").append(getDollarSigns(restaurant.priceRange)).append("\n");
            output.append("Address: ").append(restaurant.address).append("\n");
            output.append("City: ").append(restaurant.city).append("\n");
            output.append("State: ").append(restaurant.state).append("\n");
            if (restaurant.distanceToUser < 10) {
                output.append("Distance: ")
                        .append(String.format("%.1f", restaurant.distanceToUser));
            } else {
                output.append("Distance: ")
                        .append(String.format("%.0f", restaurant.distanceToUser));

            }
            output.append(" miles\n");

            output.append("-------------------------------------\n");
            count++;
        }

        System.out.println(output.toString());
    }

    private String getDollarSigns(int priceRange) {
        StringBuilder dollarSigns = new StringBuilder();
        for (int i = 0; i < priceRange; i++) {
            dollarSigns.append("$");
        }
        return dollarSigns.toString();

    }

    public String[] getReviewData(){
        Scanner in = new Scanner(System.in);
        System.out.println("Would you like to post a review? (y,n)");
        String resp = in.nextLine();
        if (!resp.equals("y")){
            return null;
        }
        System.out.println("Please enter a rating for this restaurant (1-5).");
        String rating = in.nextLine();
        System.out.println("Please enter a review for this restaurant.");
        String reviewText = in.nextLine();
        String[] res = {rating, reviewText};
        return res;
    }

    public void displayRestaurantInfo(Restaurant r){
        System.out.println(r.toString());
    }

    public int selectRestaurant(ArrayList<Restaurant> results){
        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter the number of the restaurant you would like to select.");
            try {
                int num = in.nextInt();
                if (num > 0 && num <= results.size()) {
                    return num;
                }
            } catch (NumberFormatException e) {
                System.out.println("Not a valid number. Please try again.");
            }
            System.out.println("Invalid input. Please try again.");
        }
    }


}