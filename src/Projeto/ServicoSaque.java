package projeto;

public class ServicoSaque {
    
    public String efetuarSaque(Integer valor, int[][] cedulas, int cotaMinima) {
        //Verifica o saldo atual somando tudo
        int saldoAtual = 0;
        for (int i = 0; i < cedulas.length; i++) {
            saldoAtual += (cedulas[i][0] * cedulas[i][1]);
        }
        
        //Trava da Cota Mínima
        if ((saldoAtual - valor) < cotaMinima) {
            return "Caixa Vazio: Chame o Operador";
        }

        // Esse array vai guardar temporariamente quantas notas estamos tentando usar
        int[] notasParaSacar = new int[cedulas.length]; 
        
        //Recursão
        boolean sucesso = tentarSaqueBack(valor, 0, cedulas, notasParaSacar, 0);

        if (sucesso) {
            //Desconta da matriz oficial e montamos o seu relatório.
            String relatorioNotas = "";
            for (int i = 0; i < cedulas.length; i++) {
                int qtdSacada = notasParaSacar[i];
                if (qtdSacada > 0) {
                    relatorioNotas += qtdSacada + " nota(s) de R$ " + cedulas[i][0] + "\n";
                    cedulas[i][1] -= qtdSacada; // Desconta fisicamente da matriz
                }
            }
            return "SAQUE BEM SUCEDIDO:\n" + relatorioNotas;
        } else {
            return "Saque não realizado por falta de cédulas";
        }
    }

    private boolean tentarSaqueBack(int valorRestante, int indiceAtual, int[][] cedulas, int[] notasParaSacar, int totalCedulasUsadas) {
        
        // Condição de Parada
        if (valorRestante == 0) {
            return true;
        }
        
        // Condições de Falha
        if (totalCedulasUsadas >= 30 || valorRestante < 0 || indiceAtual >= cedulas.length) {
            return false;
        }

        int valorNota = cedulas[indiceAtual][0];
        int qtdDisponivel = cedulas[indiceAtual][1];

        // Descobre o máximo de notas desse tipo que podemos pegar
        int maxNotas = Math.min(valorRestante / valorNota, qtdDisponivel);
        
        // Garante que esse máximo não estoure o limite de 30 notas
        maxNotas = Math.min(maxNotas, 30 - totalCedulasUsadas);

       
        for (int qtd = maxNotas; qtd >= 0; qtd--) {
            notasParaSacar[indiceAtual] = qtd; 
            
            //Chama a si mesmo
            boolean caminhoDeuCerto = tentarSaqueBack(valorRestante - (qtd * valorNota), indiceAtual + 1, cedulas, notasParaSacar, totalCedulasUsadas + qtd);
            
            if (caminhoDeuCerto) {
                return true; 
            }
        }

        notasParaSacar[indiceAtual] = 0;
        return false;
    }
}