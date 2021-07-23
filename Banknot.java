
public class Banknot {
    private double varyans;
    private double carpiklik;
    private double basiklik;
    private double entropi;
    private int tur;



    public Banknot(double varyans, double carpiklik, double basiklik, double entropi, int tur){ //yapıcı metod
        this.setVaryans(varyans);
        this.setCarpiklik(carpiklik);
        this.setBasiklik(basiklik);
        this.setEntropi(entropi);
        this.setTur(tur);
    }



    public String toString(){
        return String.format("Varyans Değeri:%10f   Çarpıklık Değeri:%10f   Basıklık Değeri:%10f   Entropi Değeri:%10f   Tür:%4d", getVaryans(),getCarpiklik(),getBasiklik(),getEntropi(),getTur());
    }


    public double getVaryans() {
        return varyans;
    }

    public void setVaryans(double varyans) {
        this.varyans = varyans;
    }

    public double getCarpiklik() {
        return carpiklik;
    }

    public void setCarpiklik(double carpiklik) {
        this.carpiklik = carpiklik;
    }

    public double getBasiklik() {
        return basiklik;
    }

    public void setBasiklik(double basiklik) {
        this.basiklik = basiklik;
    }

    public double getEntropi() {
        return entropi;
    }

    public void setEntropi(double entropi) {
        this.entropi = entropi;
    }

    public int getTur() {
        return tur;
    }

    public void setTur(int tur) {
        this.tur = tur;
    }
}
