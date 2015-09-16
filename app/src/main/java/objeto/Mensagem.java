package objeto;

/**
 * Created by - on 13/09/2015.
 */
public class Mensagem {

    private Integer codigo;
    private String mensagem;
    private String dataInicio;
    private String dataFim;
    private String visivel;
    private String tipo;
    private String horario;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String data) {
        this.dataInicio = data;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String data) {
        this.dataFim = data;
    }

    public String getVisivel() {
        return visivel;
    }

    public void setVisivel(String visivel) {
        this.visivel = visivel;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
