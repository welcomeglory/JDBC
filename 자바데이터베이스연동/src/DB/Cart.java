package DB;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<String, Integer> products; // ��ǰ��� ������ �����ϴ� ��

    public Cart() {
        products = new HashMap<>();
    }

    // ��ٱ��Ͽ� ��ǰ�� �߰��ϴ� �޼���
    public void addToCart(String productName, int quantity) {
        // ������ �ش� ��ǰ�� ��ٱ��Ͽ� �ִ��� Ȯ��
        if (products.containsKey(productName)) {
            // ������ �ִ� ��� ������ ����
            int currentQuantity = products.get(productName);
            products.put(productName, currentQuantity + quantity);
        } else {
            // ������ ���� ��� ���� �߰�
            products.put(productName, quantity);
        }
    }

    // ��ٱ��Ͽ��� ��ǰ�� �����ϴ� �޼���
    public void removeFromCart(String productName) {
        products.remove(productName);
    }

    // ��ٱ��� ���� �޼���
    public void clearCart() {
        products.clear();
    }

    // �� ������ ����ϴ� �޼���
    public int getTotalPrice() {
        int totalPrice = 0;
        // ��� ��ǰ�� ���� ������ ����Ͽ� ����
        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            // ���⼭�� ������ DB���� �������� �ʰ� �������� �����
            // �����δ� DB���� ��ǰ ������ �����;� ��
            int price = 10000; // ���� ����
            totalPrice += price * quantity;
        }
        return totalPrice;
    }

    // ��ٱ��Ͽ� ��� ��ǰ ������ ���ڿ��� ��ȯ�ϴ� �޼���
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            sb.append(productName).append(": ").append(quantity).append("\n");
        }
        return sb.toString();
    }
}
