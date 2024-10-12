package gemstones;

/**
 * The abstract <code>gemstones.PreciousGemstone</code> class extends the {@link Gemstone} class, representing a type of gemstone that is considered precious.
 * This class is intended to be further extended by specific precious gemstones.
 */
public abstract class PreciousGemstone extends Gemstone {

    /**
     * Constructs a new <code>gemstones.PreciousGemstone</code> with the specified weight, price, and transparency.
     * @param weight       the weight of the gemstone in carats, must be positive
     * @param price        the price of the gemstone, must be positive
     * @param transparency the transparency of the gemstone, must be between 0 and 1 (inclusive)
     * @throws IllegalArgumentException if any parameter is outside its valid range
     * @see Gemstone#Gemstone(double, double, double)  gemstones.Gemstone
     */
    public PreciousGemstone(double weight, double price, double transparency) {
        super(weight, price, transparency);
    }
}
