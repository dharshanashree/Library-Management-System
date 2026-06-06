import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class LibraryGUI extends JFrame implements ActionListener {

    JTextField txtId;
    JTextField txtName;
    JTextField txtAuthor;

    JTextArea area;

    JButton btnAdd;
    JButton btnView;
    JButton btnSearch;
    JButton btnIssue;
    JButton btnReturn;

    public LibraryGUI() {

        setTitle("Library Management System");

        setSize(700, 500);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();

        topPanel.setLayout(new GridLayout(3, 2, 10, 10));

        topPanel.setBackground(new Color(220, 240, 255));

        topPanel.add(new JLabel("Book ID"));
        txtId = new JTextField();
        topPanel.add(txtId);

        topPanel.add(new JLabel("Book Name"));
        txtName = new JTextField();
        topPanel.add(txtName);

        topPanel.add(new JLabel("Author"));
        txtAuthor = new JTextField();
        topPanel.add(txtAuthor);

        add(topPanel, BorderLayout.NORTH);

        area = new JTextArea();

        area.setBackground(new Color(255, 255, 220));

        area.setFont(new Font("Arial", Font.PLAIN, 15));

        add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();

        bottomPanel.setBackground(new Color(220, 255, 220));

        btnAdd = new JButton("Add Book");
        btnView = new JButton("View Books");
        btnSearch = new JButton("Search");
        btnIssue = new JButton("Issue");
        btnReturn = new JButton("Return");

        bottomPanel.add(btnAdd);
        bottomPanel.add(btnView);
        bottomPanel.add(btnSearch);
        bottomPanel.add(btnIssue);
        bottomPanel.add(btnReturn);

        add(bottomPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(this);
        btnView.addActionListener(this);
        btnSearch.addActionListener(this);
        btnIssue.addActionListener(this);
        btnReturn.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        try {

            Connection con = Main.getConnection();

            if (e.getSource() == btnAdd) {

                PreparedStatement ps =
                        con.prepareStatement(
                                "INSERT INTO books VALUES(?,?,?,?)");

                ps.setInt(
                        1,
                        Integer.parseInt(txtId.getText()));

                ps.setString(
                        2,
                        txtName.getText());

                ps.setString(
                        3,
                        txtAuthor.getText());

                ps.setBoolean(
                        4,
                        false);

                ps.executeUpdate();

                area.setText("Book Added Successfully");
            }

            else if (e.getSource() == btnView) {

                Statement st = con.createStatement();

                ResultSet rs =
                        st.executeQuery(
                                "SELECT * FROM books");

                area.setText("");

                while (rs.next()) {

                    area.append(
                            "ID : " + rs.getInt("id")
                                    + " | Name : " + rs.getString("name")
                                    + " | Author : " + rs.getString("author")
                                    + " | Issued : " + rs.getBoolean("issued")
                                    + "\n");
                }
            }

            else if (e.getSource() == btnSearch) {

                PreparedStatement ps =
                        con.prepareStatement(
                                "SELECT * FROM books WHERE id=?");

                ps.setInt(
                        1,
                        Integer.parseInt(txtId.getText()));

                ResultSet rs =
                        ps.executeQuery();

                if (rs.next()) {

                    area.setText(
                            "ID : " + rs.getInt("id")
                                    + "\nName : " + rs.getString("name")
                                    + "\nAuthor : " + rs.getString("author")
                                    + "\nIssued : " + rs.getBoolean("issued"));
                }

                else {

                    area.setText("Book Not Found");
                }
            }

            else if (e.getSource() == btnIssue) {

                PreparedStatement ps =
                        con.prepareStatement(
                                "UPDATE books SET issued=true WHERE id=?");

                ps.setInt(
                        1,
                        Integer.parseInt(txtId.getText()));

                int rows =
                        ps.executeUpdate();

                if (rows > 0)
                    area.setText("Book Issued Successfully");
                else
                    area.setText("Book Not Found");
            }

            else if (e.getSource() == btnReturn) {

                PreparedStatement ps =
                        con.prepareStatement(
                                "UPDATE books SET issued=false WHERE id=?");

                ps.setInt(
                        1,
                        Integer.parseInt(txtId.getText()));

                int rows =
                        ps.executeUpdate();

                if (rows > 0)
                    area.setText("Book Returned Successfully");
                else
                    area.setText("Book Not Found");
            }

            con.close();

        } catch (Exception ex) {

            area.setText(ex.getMessage());
        }
    }
}