package objeto;

/**
 * Created by josegeraldo on 16/09/2015.
 */
public class Material {

    private String titulo;
    private String volume;
    private String pagina;
    private String ano;
    private String editora;
    private String local;
    private String unitermo;
    private String classificacao;
    private String referencia;
    private String url;
    private String codigo;

    public String getCodigoLivro() {
        return codigo;
    }

    public void setCodigoLivro(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getUnitermo() {
        return unitermo;
    }

    public void setUnitermo(String unitermo) {
        this.unitermo = unitermo;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
