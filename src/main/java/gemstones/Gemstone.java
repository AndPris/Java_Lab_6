package gemstones;

/**
 * The abstract <code>gemstones.Gemstone</code> class represents a gemstone with weight, price, and transparency attributes.
 * It provides methods to set and get these attributes, with validation to ensure values are within valid ranges.
 */
public abstract class Gemstone {

    /** The weight of the gemstone in carats. Must be positive. */
    protected double weight;

    /** The price of the gemstone in dollars. Must be positive. */
    protected double price;

    /** The transparency of the gemstone, a value between 0 (opaque) and 1 (fully transparent). */
    protected double transparency;

    /**
     * Constructs a new <code>gemstones.Gemstone</code> with the specified weight, price, and transparency.
     * @param weight       the weight of the gemstone in carats, must be positive
     * @param price        the price of the gemstone, must be positive
     * @param transparency the transparency of the gemstone, must be between 0 and 1 (inclusive)
     * @throws IllegalArgumentException if any parameter is outside its valid range
     */
    public Gemstone(double weight, double price, double transparency) {
        setWeight(weight);
        setPrice(price);
        setTransparency(transparency);
    }

    /**
     * Sets the weight of the gemstone.
     * @param weight the weight in carats, must be positive
     * @throws IllegalArgumentException if the weight is negative or zero
     */
    public void setWeight(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Invalid weight");
        }
        this.weight = weight;
    }

    /**
     * Sets the price of the gemstone per carat.
     * @param price the price in dollars per carat, must be positive
     * @throws IllegalArgumentException if the price is negative or zero
     */
    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Invalid price");
        }
        this.price = price;
    }

    /**
     * Sets the transparency of the gemstone.
     * @param transparency the transparency value, must be between 0 and 1 (inclusive)
     * @throws IllegalArgumentException if the transparency is not between 0 and 1
     */
    public void setTransparency(double transparency) {
        if (transparency < 0 || transparency > 1) {
            throw new IllegalArgumentException("Invalid transparency");
        }
        this.transparency = transparency;
    }

    /**
     * Returns the weight of the gemstone.
     * @return the weight of the gemstone in carats
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Returns the price of the gemstone.
     * @return the price of the gemstone in dollars
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the transparency of the gemstone.
     * @return the transparency of the gemstone, where 0 is opaque and 1 is fully transparent
     */
    public double getTransparency() {
        return transparency;
    }

    /**
     * Returns a string representation of the gemstone, including its class name, weight, price, and transparency.
     * @return a string describing the gemstone
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " is a " + this.getClass().getSuperclass().getSimpleName()
                + " with weight " + weight + " carat, price " + price + "$ and transparency " + transparency + ".";
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if(!(obj instanceof Gemstone))
            return false;

        if(this.getClass() != obj.getClass())
            return false;

        Gemstone gemstone = (Gemstone) obj;

        if(price != gemstone.price)
            return false;

        if(transparency != gemstone.transparency)
            return false;

        if(weight != gemstone.weight)
            return false;

        return true;
    }
}
