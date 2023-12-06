package br.com.bda.emprestimosgelo;


import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.bda.emprestimosgelo.usuarios.Usuarios;
import br.com.bda.emprestimosgelo.emprestimos.Emprestimos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.InputMismatchException;
import java.util.Scanner;




@SpringBootApplication
	public class EmprestimosGeloApplication {

	public static void main(String[] args) {
		try {
            // Estabelecer a conexão com o banco de dados
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/gelo", "postgres", "fifa16");

            // Criar um Scanner para a entrada do usuário
            Scanner scanner = new Scanner(System.in);

            int escolha;
            do {
                // Exibir o menu
                System.out.println("Escolha uma opção:");
                System.out.println("1. Criar usuário");
                System.out.println("2. Exibir usuário");
                System.out.println("3. Atualizar usuário");
                System.out.println("4. Excluir usuário");
                System.out.println("5. Fazer Empréstimo");
                System.out.println("6. Exibir Empréstimo");
                System.out.println("7. Atualizar Empréstimo");
                System.out.println("8. Excluir Empréstimo");
                System.out.println("0. Sair");

                // Ler a escolha do usuário
                escolha = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer de entrada

                switch (escolha) {
                    case 1:
                        // Criar um novo usuário
                        System.out.println("Digite o nome do usuário:");
                        String nome = scanner.nextLine();
                        System.out.println("Digite o CPF do usuário:");
                        String cpf = scanner.nextLine();
                        System.out.println("Digite a data de nascimento do usuário (no formato YYYY-MM-DD):");
                        String dataNascimentoStr = scanner.nextLine();
                        java.sql.Date dataNascimento = new java.sql.Date(Date.valueOf(dataNascimentoStr).getTime());
                        System.out.println("Digite o e-mail do usuário:");
                        String email = scanner.nextLine();
                        System.out.println("Digite a senha do usuário:");
                        String senha = scanner.nextLine();

                        Usuarios novoUsuario = new Usuarios(nome, cpf, dataNascimento, email, senha);
                        int usuarioId = createUsuario(connection, novoUsuario);
                        System.out.println("Usuário criado com ID: " + usuarioId);
                        break;

                    case 2:
                        // Ler e exibir informações do usuário
                        System.out.println("Digite o ID do usuário:");
                        int idLer = scanner.nextInt();
                        Usuarios usuarioLido = readUsuario(connection, idLer);
                        System.out.println("Usuário lido: " + usuarioLido);
                        break;

                    case 3:
                        // Atualizar o nome do usuário
                        System.out.println("Digite o ID do usuário a ser atualizado:");
                        int idAtualizar = scanner.nextInt();
                        scanner.nextLine(); // Limpar o buffer de entrada
                        Usuarios usuarioAtualizar = readUsuario(connection, idAtualizar);
                        if (usuarioAtualizar != null) {
                            System.out.println("Digite o novo nome do usuário:");
                            String novoNome = scanner.nextLine();
                            usuarioAtualizar.setNome(novoNome);
                            updateUsuario(connection, usuarioAtualizar);
                            System.out.println("Usuário atualizado com sucesso.");
                        } else {
                            System.out.println("Usuário não encontrado.");
                        }
                        break;

                    case 4:
                        // Excluir o usuário
                        System.out.println("Digite o ID do usuário a ser excluído:");
                        int idExcluir = scanner.nextInt();
                        deleteUsuario(connection, idExcluir);
                        System.out.println("Usuário excluído com sucesso.");
                        break;
                    case 5:
                        // Fazer Empréstimo
                        System.out.println("Digite o ID do usuário para o empréstimo:");
                        int usuarioIdEmprestimo = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Digite o valor inicial do empréstimo:");
                        double valorInicialEmprestimo = scanner.nextDouble();
                        scanner.nextLine(); // Limpar o buffer de entrada

                        System.out.println("Digite a data de vencimento do empréstimo (no formato YYYY-MM-DD):");
                        String dataVencimentoEmprestimoStr = scanner.nextLine();
                        java.sql.Date dataVencimentoEmprestimo = new java.sql.Date(Date.valueOf(dataVencimentoEmprestimoStr).getTime());

                        Emprestimos novoEmprestimo = new Emprestimos(0, usuarioIdEmprestimo, BigDecimal.valueOf(valorInicialEmprestimo), BigDecimal.ZERO, BigDecimal.valueOf(valorInicialEmprestimo), dataVencimentoEmprestimo, new java.sql.Date(System.currentTimeMillis()));
                        int emprestimoId = createEmprestimo(connection, novoEmprestimo);
                        System.out.println("Empréstimo criado com ID: " + emprestimoId);
                        break;

                    case 6:
                        // Exibir Empréstimo
                        System.out.println("Digite o ID do empréstimo:");
                        int idExibirEmprestimo = scanner.nextInt();
                        Emprestimos emprestimoExibir = readEmprestimo(connection, idExibirEmprestimo);
                        System.out.println("Empréstimo: " + emprestimoExibir);
                        break;

                    case 7:
                        // Atualizar Empréstimo
                        System.out.println("Digite o ID do empréstimo a ser atualizado:");
                        int idAtualizarEmprestimo = scanner.nextInt();
                        scanner.nextLine(); // Limpar o buffer de entrada
                        Emprestimos emprestimoAtualizar = readEmprestimo(connection, idAtualizarEmprestimo);
                        if (emprestimoAtualizar != null) {
                            System.out.println("Digite o novo valor inicial do empréstimo:");
                            double novoValorInicial = scanner.nextDouble();
                            scanner.nextLine(); // Limpar o buffer de entrada
                            emprestimoAtualizar.setValorInicialEmprestimo(BigDecimal.valueOf(novoValorInicial));
                            updateEmprestimo(connection, emprestimoAtualizar);
                            System.out.println("Empréstimo atualizado com sucesso.");
                        } else {
                            System.out.println("Empréstimo não encontrado.");
                        }
                        break;

                    case 8:
                        // Excluir Empréstimo
                        System.out.println("Digite o ID do empréstimo a ser excluído:");
                        int idExcluirEmprestimo = scanner.nextInt();
                        deleteEmprestimo(connection, idExcluirEmprestimo);
                        System.out.println("Empréstimo excluído com sucesso.");
                        break;    

                    case 0:
                        // Sair do programa
                        System.out.println("Saindo do programa.");
                        break;

                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }

            } while (escolha != 0);

            // Fechar a conexão
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	private static int createUsuario(Connection connection, Usuarios usuario) throws SQLException {
	    String sql = "INSERT INTO Usuarios(nome, cpf, data_nascimento, email, senha) VALUES (?, ?, ?, ?, ?) RETURNING id";
	    try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        statement.setString(1, usuario.getNome());
	        statement.setString(2, usuario.getCpf());
	        statement.setDate(3, usuario.getDataNascimento());
	        statement.setString(4, usuario.getEmail());
	        statement.setString(5, usuario.getSenha());

	        int affectedRows = statement.executeUpdate();

	        if (affectedRows > 0) {
	            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    return generatedKeys.getInt(1);
	                } else {
	                    throw new SQLException("Creating user failed, no ID obtained.");
	                }
	            }
	        } else {
	            throw new SQLException("Creating user failed, no rows affected.");
	        }
	    }
	}

    // Método para ler um usuário por ID
    private static Usuarios readUsuario(Connection connection, int usuarioId) throws SQLException {
        String sql = "SELECT * FROM Usuarios WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, usuarioId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Usuarios(
                        resultSet.getString("nome"),
                        resultSet.getString("cpf"),
                        resultSet.getDate("data_nascimento"),
                        resultSet.getString("email"),
                        resultSet.getString("senha")
                );
            } else {
                return null;
            }
        }
    }

    // Método para atualizar um usuário
    private static void updateUsuario(Connection connection, Usuarios usuario) throws SQLException {
        String sql = "UPDATE Usuarios SET nome = ?, cpf = ?, data_nascimento = ?, email = ?, senha = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getCpf());
            statement.setDate(3, usuario.getDataNascimento());
            statement.setString(4, usuario.getEmail());
            statement.setString(5, usuario.getSenha());
            statement.setInt(6, usuario.getId());

            statement.executeUpdate();
        }
    }

    // Método para excluir um usuário
    private static void deleteUsuario(Connection connection, int usuarioId) throws SQLException {
        String sql = "DELETE FROM Usuarios WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, usuarioId);

            statement.executeUpdate();
        }
    }
    
    private static int createEmprestimo(Connection connection, Emprestimos emprestimo) throws SQLException {
        String sql = "INSERT INTO Emprestimos(usuario_id, valor_inicial_emprestimo, valor_pago, valor_faltante, data_vencimento, data_emprestimo) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, emprestimo.getUsuarioId());
            statement.setBigDecimal(2, emprestimo.getValorInicialEmprestimo());
            statement.setBigDecimal(3, emprestimo.getValorPago());
            statement.setBigDecimal(4, emprestimo.getValorFaltante());
            statement.setDate(5, emprestimo.getDataVencimento());
            statement.setDate(6, emprestimo.getDataEmprestimo());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating loan failed, no ID obtained.");
                    }
                }
            } else {
                throw new SQLException("Creating loan failed, no rows affected.");
            }
        }
    }

    private static Emprestimos readEmprestimo(Connection connection, int emprestimoId) throws SQLException {
        String sql = "SELECT * FROM Emprestimos WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, emprestimoId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Emprestimos(
                        resultSet.getInt("id"),
                        resultSet.getInt("usuario_id"),
                        resultSet.getBigDecimal("valor_inicial_emprestimo"),
                        resultSet.getBigDecimal("valor_pago"),
                        resultSet.getBigDecimal("valor_faltante"),
                        resultSet.getDate("data_vencimento"),
                        resultSet.getDate("data_emprestimo")
                );
            } else {
                return null;
            }
        }
    }

    private static void updateEmprestimo(Connection connection, Emprestimos emprestimo) throws SQLException {
        String sql = "UPDATE Emprestimos SET usuario_id = ?, valor_inicial_emprestimo = ?, valor_pago = ?, valor_faltante = ?, " +
                "data_vencimento = ?, data_emprestimo = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, emprestimo.getUsuarioId());
            statement.setBigDecimal(2, emprestimo.getValorInicialEmprestimo());
            statement.setBigDecimal(3, emprestimo.getValorPago());
            statement.setBigDecimal(4, emprestimo.getValorFaltante());
            statement.setDate(5, emprestimo.getDataVencimento());
            statement.setDate(6, emprestimo.getDataEmprestimo());
            statement.setInt(7, emprestimo.getId());

            statement.executeUpdate();
        }
    }

    private static void deleteEmprestimo(Connection connection, int emprestimoId) throws SQLException {
        String sql = "DELETE FROM Emprestimos WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, emprestimoId);

            statement.executeUpdate();
        }
    }
}