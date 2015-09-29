package objeto;

/**
 * Created by - on 28/09/2015.
 */
public class Livro extends Material{

    private String autor;
    private String cutter;
    private String cidade;
    private String editora;
    private String quantidade;
    private String numeroTombo;
    private String codigoLivro;

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCutter() {
        return cutter;
    }

    public void setCutter(String cutter) {
        this.cutter = cutter;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Override
    public String getEditora() {
        return editora;
    }

    @Override
    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getNumeroTombo() {
        return numeroTombo;
    }

    public void setNumeroTombo(String numeroTombo) {
        this.numeroTombo = numeroTombo;
    }

    @Override
    public String getCodigoLivro() {
        return codigoLivro;
    }

    @Override
    public void setCodigoLivro(String codigoLivro) {
        this.codigoLivro = codigoLivro;
    }
}
