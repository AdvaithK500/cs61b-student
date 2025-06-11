public class Dessert {
    int flavor;
    int price;
    static int numDesserts;
    public Dessert(int flavor, int price) {
        this.flavor = flavor;
        this.price = price;
        this.numDesserts += 1;
    }

    public void printDessert() {
        System.out.print(this.flavor + " " + this.price +  " " + numDesserts);
    }

    public static void main(String[] args) {
        System.out.println("I love dessert!");
    }


}
