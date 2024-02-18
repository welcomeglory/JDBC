package DB;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class MainPOS extends JFrame implements ActionListener {

    private JComboBox<String> itemList; // ��ǰ ����� ǥ���ϴ� �޺� ����
    private JLabel nameLabel; // ��ǰ�� ��
    private JLabel stockLabel; // ��� ��
    private JLabel priceLabel; // ���� ��
    private JComboBox<String> productComboBox; // ���õ� ��ǰ�� ǥ���ϴ� �޺� ����
    private JLabel quantityLabel; // ���� ���� ��
    private JTextField quantityTextField; // ���� ������ �Է��ϴ� �ؽ�Ʈ �ʵ�
    private JLabel totalPriceLabel; // �� ���� ��
    private JButton addToCartButton; // ��ٱ��Ͽ� �߰��ϴ� ��ư
    private JButton refreshButton; // ��ǰ��� ���ΰ�ħ ��ư
    private JButton checkoutButton; // ���� ��ư
    private JButton cancelButton; // �ֹ� ��� ��ư
    private ItemDAO itemDAO; // ��ǰ ������ �׼��� ��ü
    private Cart cart; // ��ٱ��� ��ü

    public MainPOS() {
        setTitle("������ POS �ý���"); // ������ Ÿ��Ʋ ����
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // �ݱ� ��ư ���� ����

        itemDAO = ItemDAO.getInstance(); // ��ǰ ������ �׼��� ��ü �ʱ�ȭ
        cart = new Cart(); // ��ٱ��� ��ü �ʱ�ȭ

        Vector<String> items; // ��ǰ ����� ������ ����
        try {
            items = itemDAO.getItem(); // �����ͺ��̽����� ��ǰ ����� ������
        } catch (SQLException e) {
            items = new Vector<>(); // ���� �߻� �� �� ���� ����
            e.printStackTrace();
        }
        itemList = new JComboBox<>(items); // ��ǰ ����� ǥ���ϴ� �޺� ���� ����

        // �� ������Ʈ �ʱ�ȭ
        nameLabel = new JLabel("��ǰ��:");
        stockLabel = new JLabel("���:");
        priceLabel = new JLabel("����:");
        productComboBox = new JComboBox<>();
        quantityLabel = new JLabel("���� ����:");
        quantityTextField = new JTextField(5);
        totalPriceLabel = new JLabel("�� ����:");
        addToCartButton = new JButton("��ٱ��� �߰�");
        addToCartButton.addActionListener(this);
        refreshButton = new JButton("��ǰ��� ���ΰ�ħ");
        refreshButton.addActionListener(this);
        checkoutButton = new JButton("����");
        checkoutButton.addActionListener(this);
        cancelButton = new JButton("�ֹ� ���");
        cancelButton.addActionListener(this);

        // �г� ���� �� ����
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(new JLabel("��ǰ ����: "), gbc);
        gbc.gridx++;
        panel.add(itemList, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(nameLabel, gbc);
        gbc.gridx++;
        panel.add(productComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(stockLabel, gbc);
        gbc.gridx++;
        panel.add(new JLabel(), gbc); // ��� ���� ���⿡ ǥ�õ�
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(priceLabel, gbc);
        gbc.gridx++;
        panel.add(new JLabel(), gbc); // ���� ���� ���⿡ ǥ�õ�
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(quantityLabel, gbc);
        gbc.gridx++;
        panel.add(quantityTextField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(totalPriceLabel, gbc);
        gbc.gridx++;
        panel.add(new JLabel(), gbc); // �� ���� ���� ���⿡ ǥ�õ�
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(addToCartButton, gbc);
        gbc.gridx++;
        panel.add(refreshButton, gbc);
        gbc.gridx++;
        panel.add(checkoutButton, gbc);
        gbc.gridx++;
        panel.add(cancelButton, gbc);

        add(panel); // �г��� �����ӿ� �߰�
        pack(); // �ּ� ũ��� ������ ũ�� ����
        setLocationRelativeTo(null); // ȭ�� �߾ӿ� ��ġ
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainPOS mainPOS = new MainPOS();
            mainPOS.setVisible(true); // �������� ǥ��
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addToCartButton) {
            // ��ٱ��� �߰� ��ư Ŭ�� ��
            // ���õ� ��ǰ�� ��ٱ��Ͽ� �߰�
            String productName = (String) productComboBox.getSelectedItem();
            int quantity = Integer.parseInt(quantityTextField.getText());
            cart.addToCart(productName, quantity);
            // �� ���� �� ������Ʈ
            totalPriceLabel.setText("�� ����: " + cart.getTotalPrice());
        } else if (e.getSource() == refreshButton) {
            // ��ǰ��� ���ΰ�ħ ��ư Ŭ�� ��
            // ��ǰ ��� ������Ʈ
            refreshProductList();
        } else if (e.getSource() == checkoutButton) {
            // ���� ��ư Ŭ�� ��
            // ���� �۾� ����
            // �䱸 ���׿� �°� �� �κ��� �����Ͻø� �˴ϴ�.
        } else if (e.getSource() == cancelButton) {
            // �ֹ� ��� ��ư Ŭ�� ��
            // ���� �ֹ� ���
            cart.clearCart();
            // �� ���� �� ������Ʈ
            totalPriceLabel.setText("�� ����:");
        }
    }

    private void refreshProductList() {
        // �޺� ������ ��ǰ ��� ������Ʈ
        String selectedItem = (String) itemList.getSelectedItem();
        try {
            Vector<String> products = itemDAO.getProductsByItem(selectedItem);
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(products);
            productComboBox.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
