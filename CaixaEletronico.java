package projeto;

public class CaixaEletronico implements ICaixaEletronico {

    
    private int[][] cedulas = {
        {100, 100}, // Coluna 0: Valor | Coluna 1: Quantidade
        {50,  200},
        {20,  300},
        {10,  350},
        {5,   450},
        {2,   500}
    };

    private int cotaMinima = 0; // Inicia zerada até o admin configurar
    private ServicoSaque servicoSaque;
    public ServicoAuditoria auditoria;

    public CaixaEletronico() {
        this.servicoSaque = new ServicoSaque();
        this.auditoria = new ServicoAuditoria();
    }

    @Override
    public String sacar(Integer valor) {
        String resposta = servicoSaque.efetuarSaque(valor, this.cedulas, this.cotaMinima);
        
        //Pega o saldo após o saque para o extrato 
        int saldoAtualizado = 0;
        for (int i = 0; i < cedulas.length; i++) {
            saldoAtualizado += (cedulas[i][0] * cedulas[i][1]);
        }
        
        // Registra no extrato com a atualização de saldo
        auditoria.registrar("Saque R$ " + valor + " | Resultado: " + resposta.replace("\n", " ") + " | Saldo Atualizado: R$ " + saldoAtualizado);
        
        return resposta;
    }

    //Devolve a soma total do valor do caixa 
    @Override
    public String pegaValorTotalDisponivel() {
        int total = 0;
        for (int i = 0; i < cedulas.length; i++) {
            total += (cedulas[i][0] * cedulas[i][1]);
        }
        return "Valor total em caixa: R$ " + total;
    }

    //Devolve o registro de quantas cédulas tem no caixa pro admin
    @Override
    public String pegaRelatorioCedulas() {
        String relatorio = "=== Relatório de Cédulas ===\n";
        for (int i = 0; i < cedulas.length; i++) {
            relatorio += "R$ " + cedulas[i][0] + ": " + cedulas[i][1] + " unidades disponíveis\n";
        }
        return relatorio;
    }

    
    //Reposição de cédulas feito pelo admin
    @Override
    public String reposicaoCedulas(Integer cedula, Integer quantidade) {
        for (int i = 0; i < cedulas.length; i++) {
            if (cedulas[i][0] == cedula) {
                cedulas[i][1] += quantidade;
                auditoria.registrar("Reposição: " + quantidade + " notas de R$ " + cedula);
                return "Reposição efetuada com sucesso!";
            }
        }
        return "Erro: Cédula de R$ " + cedula + " não existe no sistema.";
    }

    @Override
    public String armazenaCotaMinima(Integer minimo) {
        this.cotaMinimaPendente = minimo;
        auditoria.registrar("Nova Cota Mínima: R$ " + minimo);
        return "Cota mínima de R$ " + minimo;
    }
    
}

