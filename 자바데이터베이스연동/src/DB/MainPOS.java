package DB;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class MainPOS extends JFrame implements ActionListener {

    private JComboBox<String> itemList; // 상품 목록을 표시하는 콤보 상자
    private JLabel nameLabel; // 상품명 라벨
    private JLabel stockLabel; // 재고 라벨
    private JLabel priceLabel; // 가격 라벨
    private JComboBox<String> productComboBox; // 선택된 상품을 표시하는 콤보 상자
    private JLabel quantityLabel; // 구매 수량 라벨
    private JTextField quantityTextField; // 구매 수량을 입력하는 텍스트 필드
    private JLabel totalPriceLabel; // 총 가격 라벨
    private JButton addToCartButton; // 장바구니에 추가하는 버튼
    private JButton refreshButton; // 상품목록 새로고침 버튼
    private JButton checkoutButton; // 결제 버튼
    private JButton cancelButton; // 주문 취소 버튼
    private ItemDAO itemDAO; // 상품 데이터 액세스 객체
    private Cart cart; // 장바구니 객체

    public MainPOS() {
        setTitle("편의점 POS 시스템"); // 프레임 타이틀 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 버튼 동작 설정

        itemDAO = ItemDAO.getInstance(); // 상품 데이터 액세스 객체 초기화
        cart = new Cart(); // 장바구니 객체 초기화

        Vector<String> items; // 상품 목록을 저장할 벡터
        try {
            items = itemDAO.getItem(); // 데이터베이스에서 상품 목록을 가져옴
        } catch (SQLException e) {
            items = new Vector<>(); // 에러 발생 시 빈 벡터 생성
            e.printStackTrace();
        }
        itemList = new JComboBox<>(items); // 상품 목록을 표시하는 콤보 상자 생성

        // 각 컴포넌트 초기화
        nameLabel = new JLabel("상품명:");
        stockLabel = new JLabel("재고:");
        priceLabel = new JLabel("가격:");
        productComboBox = new JComboBox<>();
        quantityLabel = new JLabel("구매 수량:");
        quantityTextField = new JTextField(5);
        totalPriceLabel = new JLabel("총 가격:");
        addToCartButton = new JButton("장바구니 추가");
        addToCartButton.addActionListener(this);
        refreshButton = new JButton("상품목록 새로고침");
        refreshButton.addActionListener(this);
        checkoutButton = new JButton("결제");
        checkoutButton.addActionListener(this);
        cancelButton = new JButton("주문 취소");
        cancelButton.addActionListener(this);

        // 패널 생성 및 구성
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(new JLabel("상품 선택: "), gbc);
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
        panel.add(new JLabel(), gbc); // 재고 값은 여기에 표시됨
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(priceLabel, gbc);
        gbc.gridx++;
        panel.add(new JLabel(), gbc); // 가격 값은 여기에 표시됨
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(quantityLabel, gbc);
        gbc.gridx++;
        panel.add(quantityTextField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(totalPriceLabel, gbc);
        gbc.gridx++;
        panel.add(new JLabel(), gbc); // 총 가격 값은 여기에 표시됨
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(addToCartButton, gbc);
        gbc.gridx++;
        panel.add(refreshButton, gbc);
        gbc.gridx++;
        panel.add(checkoutButton, gbc);
        gbc.gridx++;
        panel.add(cancelButton, gbc);

        add(panel); // 패널을 프레임에 추가
        pack(); // 최소 크기로 윈도우 크기 조절
        setLocationRelativeTo(null); // 화면 중앙에 위치
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainPOS mainPOS = new MainPOS();
            mainPOS.setVisible(true); // 프레임을 표시
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addToCartButton) {
            // 장바구니 추가 버튼 클릭 시
            // 선택된 상품을 장바구니에 추가
            String productName = (String) productComboBox.getSelectedItem();
            int quantity = Integer.parseInt(quantityTextField.getText());
            cart.addToCart(productName, quantity);
            // 총 가격 라벨 업데이트
            totalPriceLabel.setText("총 가격: " + cart.getTotalPrice());
        } else if (e.getSource() == refreshButton) {
            // 상품목록 새로고침 버튼 클릭 시
            // 상품 목록 업데이트
            refreshProductList();
        } else if (e.getSource() == checkoutButton) {
            // 결제 버튼 클릭 시
            // 결제 작업 수행
            // 요구 사항에 맞게 이 부분을 구현하시면 됩니다.
        } else if (e.getSource() == cancelButton) {
            // 주문 취소 버튼 클릭 시
            // 현재 주문 취소
            cart.clearCart();
            // 총 가격 라벨 업데이트
            totalPriceLabel.setText("총 가격:");
        }
    }

    private void refreshProductList() {
        // 콤보 상자의 상품 목록 업데이트
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
