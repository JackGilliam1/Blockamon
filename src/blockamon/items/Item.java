package blockamon.items;

public enum Item {
    HEALVIAL(100, true, 10), BLOCKABALL(250);
    
    private boolean heals;
    private double healAmount;
    private double price;
    
    Item(double price) {
        this(price, false, 0);
    }
    
    Item(boolean heals, double healAmount) {
        this(0, heals, healAmount);
    }
    
    Item(double price, boolean heals, double healAmount) {
        this.heals = heals;
        this.healAmount = healAmount;
        this.price = price;
    }
    
    public boolean heals() {
        return heals;
    }
    
    public double getPrice() {
        return price;
    }
    
    public double getHealAmount() {
        return healAmount;
    }
}