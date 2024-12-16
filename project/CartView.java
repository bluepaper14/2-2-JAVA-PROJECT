package project;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CartView extends JFrame {
    private JTable cartTable;
    private JLabel totalPriceLabel;  // 가격 합계를 표시할 레이블

    public CartView() {
        setTitle("장바구니");
        setLayout(new BorderLayout());
        setSize(500, 400);

        // 장바구니 아이템 목록 가져오기
        CartManager cartManager = CartManager.getInstance();
        List<CartItem> cartItems = cartManager.getCartItems();

        // 테이블 데이터 준비
        String[][] data = new String[cartItems.size()][2];
        for (int i = 0; i < cartItems.size(); i++) {
            data[i][0] = cartItems.get(i).getName();  // 상품 이름
            data[i][1] = cartItems.get(i).getPrice(); // 상품 가격
        }

        String[] columnNames = {"상품 이름", "가격"};

        // JTable 생성
        cartTable = new JTable(data, columnNames);
        
        // 셀 편집 방지
        cartTable.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(cartTable);
        add(scrollPane, BorderLayout.CENTER);

        // 하단에 총합을 표시할 레이블 추가
        totalPriceLabel = new JLabel("총합: 0원", SwingConstants.RIGHT);  // 초기 값 설정
        totalPriceLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        totalPriceLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // 여백 설정
        add(totalPriceLabel, BorderLayout.SOUTH);

        // 총합 계산
        updateTotalPrice(cartItems);

        setVisible(true);
    }

    /**
     * 장바구니 아이템들의 가격 합계를 계산하여 레이블에 표시합니다.
     */
    private void updateTotalPrice(List<CartItem> cartItems) {
        long total = 0;
        for (CartItem item : cartItems) {
            // 가격에서 콤마, 원 제거 후 long으로 변환하여 합산
            try {
                String price = item.getPrice().replace("원", "").replace(",", "").trim();
                total += Long.parseLong(price);
            } catch (NumberFormatException e) {
                // 예외가 발생하면 0으로 처리
                total += 0;
            }
        }

        // 총합을 포맷팅하여 레이블에 표시
        totalPriceLabel.setText(String.format("총합: %,d원", total));
    }
}
