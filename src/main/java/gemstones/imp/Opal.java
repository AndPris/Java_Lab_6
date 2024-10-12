package gemstones.imp;

import gemstones.SemiPreciousGemstone;

/**
 * The <code>gemstones.imp.Opal</code> class represents a specific type of semi-precious gemstone, gemstones.imp.Opal.
 * It extends the {@link SemiPreciousGemstone} class, inheriting the attributes and behavior for weight, price, and transparency.
 */
public class Opal extends SemiPreciousGemstone {
    /**
     * Constructs a new <code>gemstones.imp.Opal</code> gemstone with the specified weight, price, and transparency.
     *
     * @param weight       the weight of the gemstone in carats, must be positive
     * @param price        the price of the gemstone, must be positive
     * @param transparency the transparency of the gemstone, must be between 0 and 1 (inclusive)
     * @throws IllegalArgumentException if any parameter is outside its valid range
     * @see SemiPreciousGemstone#SemiPreciousGemstone(double, double, double) gemstones.SemiPreciousGemstone
     */
    public Opal(double weight, double price, double transparency) {
        super(weight, price, transparency);
    }
}
