package productos;

public class Producto {

    public static final String RED_BRIGHT = "\033[0;91m"; // RED
    public static final String GREEN_BRIGHT = "\033[0;92m"; // GREEN
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String RESET = "\033[0m"; // Text Reset

    private long codigo;
    private String descripcion;
    private double precioCompra;
    private double precioVenta;
    private int stock;

    
    

    public Producto(long codigo, String descripcion, double precioCompra, double precioVenta, int stock) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.stock = stock;
    }

    public long getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public int getStock() {
        return stock;
    }

    
    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String seralizar(){
        System.out.println("Codigo del producto: "+this.codigo+" Descripcion: "+this.descripcion+" PrecioCompra: "+ this.precioCompra+" PrecioVenta: "+this.precioVenta+" Stock: "+this.stock);
        return "";
    }
    @Override
    public String toString() {
        return String.format("TODO");
    }
}