package vehiculo;

public class Vehiculo {

    private long matricula;
    private long numBastidor;
    private int cv;
    private String descripcion;
    private double precioCompra;
    private double precioVenta;
    private String marca;
    private String modelo;

    public Vehiculo(long matricula, long numBastidor, int cv, String descripcion,
            double precioCompra, double precioVenta, String marca, String modelo) {
        this.matricula = matricula;
        this.numBastidor = numBastidor;
        this.cv = cv;
        this.descripcion = descripcion;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.marca = marca;
        this.modelo = modelo;
    }

    public long getMatricula() {
        return matricula;
    }

    public void setMatricula(long matricula) {
        this.matricula = matricula;
    }

    public long getNumBastidor() {
        return numBastidor;
    }

    public void setNumBastidor(long numBastidor) {
        this.numBastidor = numBastidor;
    }

    public int getCv() {
        return cv;
    }

    public void setCv(int cv) {
        this.cv = cv;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String serializar() {
        return matricula + "," + numBastidor + "," + cv + "," + descripcion + "," +
                precioCompra + "," + precioVenta + "," + marca + "," + modelo;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "matricula=" + matricula +
                ", numBastidor=" + numBastidor +
                ", cv=" + cv +
                ", descripcion='" + descripcion + '\'' +
                ", precioCompra=" + precioCompra +
                ", precioVenta=" + precioVenta +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                '}';
    }
}