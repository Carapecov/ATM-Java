package projeto;

import javax.swing.*;
import java.awt.*;

public class InterfaceCaixa extends JFrame {

    // Instancia o nosso back-end 
    private CaixaEletronico meuCaixa = new CaixaEletronico();

    public InterfaceCaixa() {
        setTitle("Caixa eletronico");
        setSize(300, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Módulo do Cliente 
        add(new JLabel("Modulo do Cliente:"));
        JButton btnSaque = criarBotao("Efetuar Saque");
        add(btnSaque);

        // Módulo do Administrador 
        add(new JLabel("Modulo do Administrador:"));
        JButton btnRelatorio = criarBotao("Relatorio de Cedulas");
        JButton btnValorTotal = criarBotao("Valor total disponivel");
        JButton btnReposicao = criarBotao("Reposicao de Cedulas");
        JButton btnCotaMinima = criarBotao("Cota Minima");
        
        add(btnRelatorio);
        add(btnValorTotal);
        add(btnReposicao);
        add(btnCotaMinima);

        //Módulo de Ambos
        add(new JLabel("Modulo de Ambos:"));
        JButton btnSair = criarBotao("Sair");
        add(btnSair);

        //Efetuar Saque
        btnSaque.addActionListener(e -> {
            String valorStr = JOptionPane.showInputDialog(this, "Digite o valor do saque:");
            if (valorStr != null && !valorStr.trim().isEmpty()) {
                try {
                    Integer valor = Integer.parseInt(valorStr);
                    String resposta = meuCaixa.sacar(valor);
                    JOptionPane.showMessageDialog(this, resposta, "Comprovante de Saque", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ERRO: Digite apenas números válidos!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Relatório de Cédulas
        btnRelatorio.addActionListener(e -> {
            String relatorio = meuCaixa.pegaRelatorioCedulas();
            JOptionPane.showMessageDialog(this, relatorio, "Relatório do Cofre", JOptionPane.INFORMATION_MESSAGE);
        });

        //Valor Total Disponível
        btnValorTotal.addActionListener(e -> {
            String total = meuCaixa.pegaValorTotalDisponivel();
            JOptionPane.showMessageDialog(this, total, "Saldo do Caixa", JOptionPane.INFORMATION_MESSAGE);
        });

        //Reposição de Cédulas
        btnReposicao.addActionListener(e -> {
            String notaStr = JOptionPane.showInputDialog(this, "Qual nota deseja repor? (2, 5, 10, 20, 50, 100)");
            String qtdStr = JOptionPane.showInputDialog(this, "Quantas notas deseja colocar na gaveta?");
            
            if (notaStr != null && qtdStr != null && !notaStr.trim().isEmpty() && !qtdStr.trim().isEmpty()) {
                try {
                    Integer nota = Integer.parseInt(notaStr);
                    Integer quantidade = Integer.parseInt(qtdStr);
                    String resposta = meuCaixa.reposicaoCedulas(nota, quantidade);
                    JOptionPane.showMessageDialog(this, resposta, "Reposição", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ERRO: Digite apenas números!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Cota Mínima
        btnCotaMinima.addActionListener(e -> {
            String cotaStr = JOptionPane.showInputDialog(this, "Digite o valor da nova cota mínima:");
            if (cotaStr != null && !cotaStr.trim().isEmpty()) {
                try {
                    Integer minimo = Integer.parseInt(cotaStr);
                    String resposta = meuCaixa.armazenaCotaMinima(minimo);
                    JOptionPane.showMessageDialog(this, resposta, "Configuração", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ERRO: Digite apenas números válidos!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Botão Sair
        btnSair.addActionListener(e -> {
            // texto do histórico
            String extratoFinal = meuCaixa.auditoria.compilarExtrato();
            
            JTextArea textArea = new JTextArea(extratoFinal);
            textArea.setEditable(false);
            textArea.setFont(new Font("Helvetica", Font.PLAIN, 12)); 
            
      
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(350, 250)); 
            
            
            JOptionPane.showMessageDialog(this, scrollPane, "Extrato Final de Encerramento", JOptionPane.PLAIN_MESSAGE);
            
           
            System.exit(0);
        });
    }

    // Método auxiliar para criar botões padronizados
    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setPreferredSize(new Dimension(250, 30));
        return botao;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InterfaceCaixa().setVisible(true);
        });
    }

}