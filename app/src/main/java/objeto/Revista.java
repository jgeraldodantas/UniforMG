package objeto;

/**
 * Created by - on 28/09/2015.
 */
public class Revista extends Material {

    private String mes;
    private String numero;
    private String periodicidade;
    private String codigoRevista;

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPeriodicidade() {
        return periodicidade;
    }

    public void setPeriodicidade(String periodicidade) {
        this.periodicidade = periodicidade;
    }

    public String getCodigoRevista() {
        return codigoRevista;
    }

    public void setCodigoRevista(String codigoRevista) {
        this.codigoRevista = codigoRevista;
    }
}
