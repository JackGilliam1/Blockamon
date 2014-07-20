package blockamon.items;

public enum Item {
    HEALVIAL(100, true, 10, "Heal Vial"), BLOCKABALL(250, "Blocka Ball");

    private String _name;
    private boolean heals;
    private double healAmount;
    private double price;

    Item(double price, String name) {
        this(price, false, 0, name);
    }

    Item(boolean heals, double healAmount, String name) {
        this(0, heals, healAmount, name);
    }

    Item(double price, boolean heals, double healAmount, String name) {
        this.heals = heals;
        this.healAmount = healAmount;
        this.price = price;
        this._name = name;
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

    public String getName() {
        return _name;
    }

    public String getWellFormattedString() { return _name + ", " + price; }
}