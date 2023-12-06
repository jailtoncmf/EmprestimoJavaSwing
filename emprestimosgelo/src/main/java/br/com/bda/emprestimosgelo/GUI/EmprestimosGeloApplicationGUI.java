package br.com.bda.emprestimosgelo.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.com.bda.emprestimosgelo.usuarios.Usuarios;
import br.com.bda.emprestimosgelo.emprestimos.Emprestimos;

public class EmprestimosGeloApplicationGUI {
    private JFrame frame;
    private Connection connection;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new EmprestimosGeloApplicationGUI().start();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public EmprestimosGeloApplicationGUI() throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost/gelo", "postgres", "fifa16");
    }

    public void start() {
        frame = new JFrame("EmprestimosGelo Application");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));

        JButton createUserButton = new JButton("Criar Usuário");
        createUserButton.setPreferredSize(new Dimension(200, 50));
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUsuarioDialog();
            }
        });

        

        JButton readUserButton = new JButton("Exibir Usuário");
        readUserButton.setPreferredSize(new Dimension(200, 50));
        readUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readUsuarioDialog();
            }
        });

        JButton updateUserButton = new JButton("Atualizar Usuário");
        updateUserButton.setPreferredSize(new Dimension(200, 50));
        updateUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUsuarioDialog();
            }
        });

        JButton deleteUserButton = new JButton("Excluir Usuário");
        deleteUserButton.setPreferredSize(new Dimension(200, 50));
        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUsuarioDialog();
            }
        });
        
        JButton createLoanButton = new JButton("Criar Empréstimo");
        createLoanButton.setPreferredSize(new Dimension(200, 50));
        createLoanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createEmprestimoDialog();
            }
        });

        JButton readLoanButton = new JButton("Exibir Empréstimo");
        readLoanButton.setPreferredSize(new Dimension(200, 50));
        readLoanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readEmprestimoDialog();
            }
        });

        JButton updateLoanButton = new JButton("Atualizar Empréstimo");
        updateLoanButton.setPreferredSize(new Dimension(200, 50));
        updateLoanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEmprestimoDialog();
            }
        });

        JButton deleteLoanButton = new JButton("Excluir Empréstimo");
        deleteLoanButton.setPreferredSize(new Dimension(200, 50));
        deleteLoanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEmprestimoDialog();
            }
        });

        JButton exitButton = new JButton("Sair");
        exitButton.setPreferredSize(new Dimension(200, 50));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });

        panel.add(createUserButton);
        panel.add(createLoanButton);
        panel.add(readUserButton);
        panel.add(updateUserButton);
        panel.add(deleteUserButton);
        panel.add(readLoanButton);
        panel.add(updateLoanButton);
        panel.add(deleteLoanButton);
        panel.add(exitButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }


    private void createUsuarioDialog() {
        JDialog dialog = new JDialog(frame, "Criar Usuário", true);
        dialog.setSize(300, 200);

        JPanel panel = new JPanel(new GridLayout(6, 2));

        panel.add(new JLabel("Nome:"));
        JTextField nomeField = new JTextField();
        panel.add(nomeField);

        panel.add(new JLabel("CPF:"));
        JTextField cpfField = new JTextField();
        panel.add(cpfField);

        panel.add(new JLabel("Data de Nascimento (YYYY-MM-DD):"));
        JTextField dataNascimentoField = new JTextField();
        panel.add(dataNascimentoField);

        panel.add(new JLabel("E-mail:"));
        JTextField emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Senha:"));
        JTextField senhaField = new JTextField();
        panel.add(senhaField);

        JButton confirmButton = new JButton("Confirmar");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = nomeField.getText();
                    String cpf = cpfField.getText();
                    String dataNascimentoStr = dataNascimentoField.getText();
                    Date dataNascimento = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dataNascimentoStr).getTime());
                    String email = emailField.getText();
                    String senha = senhaField.getText();

                    Usuarios novoUsuario = new Usuarios(nome, cpf, dataNascimento, email, senha);
                    int usuarioId = createUsuario(connection, novoUsuario);
                    JOptionPane.showMessageDialog(dialog, "Usuário criado com ID: " + usuarioId);
                    dialog.dispose();
                } catch (SQLException | ParseException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(dialog, "Erro ao criar usuário. Verifique os dados e tente novamente.");
                }
            }
        });

        panel.add(confirmButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        panel.add(cancelButton);

        dialog.getContentPane().add(panel);
        dialog.setVisible(true);
    }
    
    private void readUsuarioDialog() {
        JDialog dialog = new JDialog(frame, "Exibir Usuário", true);
        dialog.setSize(300, 200);

        JTextField usuarioIdField = new JTextField();
        JButton confirmButton = new JButton("Confirmar");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int usuarioId = Integer.parseInt(usuarioIdField.getText());
                    Usuarios usuarioLido = readUsuario(connection, usuarioId);
                    if (usuarioLido != null) {
                        JOptionPane.showMessageDialog(dialog, "Usuário lido:\n" + usuarioLido);
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Usuário não encontrado.");
                    }
                    dialog.dispose();
                } catch (SQLException | NumberFormatException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(dialog, "Erro ao exibir usuário. Verifique os dados e tente novamente.");
                }
            }
        });

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("ID do Usuário:"));
        panel.add(usuarioIdField);
        panel.add(confirmButton);

        dialog.getContentPane().add(panel);
        dialog.setVisible(true);
    }

    private void updateUsuarioDialog() {
        JDialog dialog = new JDialog(frame, "Atualizar Usuário", true);
        dialog.setSize(300, 200);

        JTextField usuarioIdField = new JTextField();
        JButton confirmButton = new JButton("Confirmar");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int usuarioId = Integer.parseInt(usuarioIdField.getText());
                    Usuarios usuarioAtualizar = readUsuario(connection, usuarioId);
                    if (usuarioAtualizar != null) {
                        String novoNome = JOptionPane.showInputDialog(dialog, "Digite o novo nome do usuário:", usuarioAtualizar.getNome());
                        if (novoNome != null && !novoNome.isEmpty()) {
                            usuarioAtualizar.setNome(novoNome);
                            updateUsuario(connection, usuarioAtualizar);
                            JOptionPane.showMessageDialog(dialog, "Usuário atualizado com sucesso.");
                        } else {
                            JOptionPane.showMessageDialog(dialog, "Nome inválido. Tente novamente.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Usuário não encontrado.");
                    }
                    dialog.dispose();
                } catch (SQLException | NumberFormatException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(dialog, "Erro ao atualizar usuário. Verifique os dados e tente novamente.");
                }
            }
        });

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("ID do Usuário:"));
        panel.add(usuarioIdField);
        panel.add(confirmButton);

        dialog.getContentPane().add(panel);
        dialog.setVisible(true);
    }

    private void deleteUsuarioDialog() {
        JDialog dialog = new JDialog(frame, "Excluir Usuário", true);
        dialog.setSize(300, 200);

        JTextField usuarioIdField = new JTextField();
        JButton confirmButton = new JButton("Confirmar");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int usuarioId = Integer.parseInt(usuarioIdField.getText());
                    deleteUsuario(connection, usuarioId);
                    JOptionPane.showMessageDialog(dialog, "Usuário excluído com sucesso.");
                    dialog.dispose();
                } catch (SQLException | NumberFormatException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(dialog, "Erro ao excluir usuário. Verifique os dados e tente novamente.");
                }
            }
        });

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("ID do Usuário:"));
        panel.add(usuarioIdField);
        panel.add(confirmButton);

        dialog.getContentPane().add(panel);
        dialog.setVisible(true);
    }

    private void createEmprestimoDialog() {
        JDialog dialog = new JDialog(frame, "Criar Empréstimo", true);
        dialog.setSize(300, 200);

        JPanel panel = new JPanel(new GridLayout(5, 2));

        panel.add(new JLabel("ID do Usuário:"));
        JTextField usuarioIdField = new JTextField();
        panel.add(usuarioIdField);

        panel.add(new JLabel("Valor Inicial do Empréstimo:"));
        JTextField valorInicialField = new JTextField();
        panel.add(valorInicialField);

        panel.add(new JLabel("Data de Vencimento (YYYY-MM-DD):"));
        JTextField dataVencimentoField = new JTextField();
        panel.add(dataVencimentoField);

        JButton confirmButton = new JButton("Confirmar");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int usuarioId = Integer.parseInt(usuarioIdField.getText());
                    double valorInicial = Double.parseDouble(valorInicialField.getText());
                    String dataVencimentoStr = dataVencimentoField.getText();
                    Date dataVencimento = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dataVencimentoStr).getTime());

                    Emprestimos novoEmprestimo = new Emprestimos(0, usuarioId, BigDecimal.valueOf(valorInicial),
                            BigDecimal.ZERO, BigDecimal.valueOf(valorInicial), dataVencimento, new java.sql.Date(System.currentTimeMillis()));

                    int emprestimoId = createEmprestimo(connection, novoEmprestimo);
                    JOptionPane.showMessageDialog(dialog, "Empréstimo criado com ID: " + emprestimoId);
                    dialog.dispose();
                } catch (SQLException | ParseException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(dialog, "Erro ao criar empréstimo. Verifique os dados e tente novamente.");
                }
            }
        });

        panel.add(confirmButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        panel.add(cancelButton);

        dialog.getContentPane().add(panel);
        dialog.setVisible(true);
    }
    
    private void readEmprestimoDialog() {
        JDialog dialog = new JDialog(frame, "Exibir Empréstimo", true);
        dialog.setSize(300, 200);

        JTextField emprestimoIdField = new JTextField();
        JButton confirmButton = new JButton("Confirmar");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int emprestimoId = Integer.parseInt(emprestimoIdField.getText());
                    Emprestimos emprestimoExibir = readEmprestimo(connection, emprestimoId);
                    if (emprestimoExibir != null) {
                        JOptionPane.showMessageDialog(dialog, "Empréstimo:\n" + emprestimoExibir);
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Empréstimo não encontrado.");
                    }
                    dialog.dispose();
                } catch (SQLException | NumberFormatException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(dialog, "Erro ao exibir empréstimo. Verifique os dados e tente novamente.");
                }
            }
        });

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("ID do Empréstimo:"));
        panel.add(emprestimoIdField);
        panel.add(confirmButton);

        dialog.getContentPane().add(panel);
        dialog.setVisible(true);
    }

    private void updateEmprestimoDialog() {
        JDialog dialog = new JDialog(frame, "Atualizar Empréstimo", true);
        dialog.setSize(300, 200);

        JTextField emprestimoIdField = new JTextField();
        JButton confirmButton = new JButton("Confirmar");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int emprestimoId = Integer.parseInt(emprestimoIdField.getText());
                    Emprestimos emprestimoAtualizar = readEmprestimo(connection, emprestimoId);
                    if (emprestimoAtualizar != null) {
                        String novoValorInicialStr = JOptionPane.showInputDialog(dialog, "Digite o novo valor inicial do empréstimo:", emprestimoAtualizar.getValorInicialEmprestimo());
                        if (novoValorInicialStr != null && !novoValorInicialStr.isEmpty()) {
                            double novoValorInicial = Double.parseDouble(novoValorInicialStr);
                            emprestimoAtualizar.setValorInicialEmprestimo(BigDecimal.valueOf(novoValorInicial));
                            updateEmprestimo(connection, emprestimoAtualizar);
                            JOptionPane.showMessageDialog(dialog, "Empréstimo atualizado com sucesso.");
                        } else {
                            JOptionPane.showMessageDialog(dialog, "Valor inicial inválido. Tente novamente.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Empréstimo não encontrado.");
                    }
                    dialog.dispose();
                } catch (SQLException | NumberFormatException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(dialog, "Erro ao atualizar empréstimo. Verifique os dados e tente novamente.");
                }
            }
        });

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("ID do Empréstimo:"));
        panel.add(emprestimoIdField);
        panel.add(confirmButton);

        dialog.getContentPane().add(panel);
        dialog.setVisible(true);
    }

    private void deleteEmprestimoDialog() {
        JDialog dialog = new JDialog(frame, "Excluir Empréstimo", true);
        dialog.setSize(300, 200);

        JTextField emprestimoIdField = new JTextField();
        JButton confirmButton = new JButton("Confirmar");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int emprestimoId = Integer.parseInt(emprestimoIdField.getText());
                    deleteEmprestimo(connection, emprestimoId);
                    JOptionPane.showMessageDialog(dialog, "Empréstimo excluído com sucesso.");
                    dialog.dispose();
                } catch (SQLException | NumberFormatException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(dialog, "Erro ao excluir empréstimo. Verifique os dados e tente novamente.");
                }
            }
        });

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("ID do Empréstimo:"));
        panel.add(emprestimoIdField);
        panel.add(confirmButton);

        dialog.getContentPane().add(panel);
        dialog.setVisible(true);
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

    private static Usuarios readUsuario(Connection connection, int usuarioId) throws SQLException {
        String sql = "SELECT * FROM Usuarios WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, usuarioId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Usuarios(
                        resultSet.getInt("id"),
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