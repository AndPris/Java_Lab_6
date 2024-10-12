package gemstones.imp;

import gemstones.PreciousGemstone;

/**
 * The <code>gemstones.imp.Emerald</code> class represents a specific type of precious gemstone, gemstones.imp.Emerald.
 * It extends the {@link PreciousGemstone} class, inheriting the attributes and behavior for weight, price, and transparency.
 */
public class Emerald extends PreciousGemstone {
    /**
     * Constructs a new <code>gemstones.imp.Emerald</code> gemstone with the specified weight, price, and transparency.
     *
     * @param weight       the weight of the gemstone in carats, must be positive
     * @param price        the price of the gemstone, must be positive
     * @param transparency the transparency of the gemstone, must be between 0 and 1 (inclusive)
     * @throws IllegalArgumentException if any parameter is outside its valid range
     * @see PreciousGemstone#PreciousGemstone(double, double, double) gemstones.PreciousGemstone
     */
    public Emerald(double weight, double price, double transparency) {
        super(weight, price, transparency);
    }
}
