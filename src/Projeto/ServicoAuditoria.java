package projeto;

public class ServicoAuditoria {
    private String historico = "== OPERAÇÕES ==\n\n";

    public void registrar(String operacao) {
        this.historico += operacao + "\n";
    }

    public String compilarExtrato() {
        return this.historico;
    }
}