package DB;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<String, Integer> products; // 제품명과 수량을 저장하는 맵

    public Cart() {
        products = new HashMap<>();
    }

    // 장바구니에 제품을 추가하는 메서드
    public void addToCart(String productName, int quantity) {
        // 기존에 해당 제품이 장바구니에 있는지 확인
        if (products.containsKey(productName)) {
            // 기존에 있는 경우 수량을 더함
            int currentQuantity = products.get(productName);
            products.put(productName, currentQuantity + quantity);
        } else {
            // 기존에 없는 경우 새로 추가
            products.put(productName, quantity);
        }
    }

    // 장바구니에서 제품을 삭제하는 메서드
    public void removeFromCart(String productName) {
        products.remove(productName);
    }

    // 장바구니 비우는 메서드
    public void clearCart() {
        products.clear();
    }

    // 총 가격을 계산하는 메서드
    public int getTotalPrice() {
        int totalPrice = 0;
        // 모든 제품에 대해 가격을 계산하여 더함
        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            // 여기서는 가격을 DB에서 가져오지 않고 가정으로 계산함
            // 실제로는 DB에서 제품 가격을 가져와야 함
            int price = 10000; // 예시 가격
            totalPrice += price * quantity;
        }
        return totalPrice;
    }

    // 장바구니에 담긴 제품 정보를 문자열로 반환하는 메서드
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
