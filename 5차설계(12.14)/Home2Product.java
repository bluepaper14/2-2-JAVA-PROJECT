package project;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import java.net.URI;

/**
 * Home2Product 클래스.
 * 선택한 렌즈의 상세 정보를 표시하는 화면.
 */
public class Home2Product extends JPanel {
    private int productIndex;
    private JLabel productImageLabel;
    private JTextArea productDetailsArea;

    // Product 배열 정의 (클래스 내부에서 접근 가능하도록 선언)
    private Product[] products = {
        new Product(
            "src/project/image/product/SEL1635GM2.png",
            "제품명:SEL1635GM2\n타입: 풀프레임 광각 줌 렌즈\n초점 거리: 16-35mm\n조리개: 최대 F2.8\n용도: 풍경, 건축물 촬영 및 넓은 공간",
            "2,900,000원"
        ),
        new Product(
            "src/project/image/product/SEL2470GM.png",
            "제품명:SEL2470GM\n타입: 풀프레임 표준 줌 렌즈\n초점 거리: 24-70mm\n조리개: 최대 F2.8\n용도: 인물, 여행, 웨딩 촬영 등 다양한 용도",
            "3,200,000원"
        ),
        new Product(
            "src/project/image/product/SEL85F18.png",
            "제품명:SEL85F18\n타입: 풀프레임 인물 촬영용 단렌즈\n초점 거리: 85mm\n조리개: 최대 F1.8\n용도: 인물 촬영, 포트레이트 촬영에 적합",
            "800,000원"
        ),
        new Product(
            "src/project/image/product/SEL70300G.png",
            "제품명:SEL70300G\n타입: 풀프레임 망원 줌 렌즈\n초점 거리: 70-300mm\n조리개: F4.5-5.6\n용도: 스포츠, 야생동물 촬영 먼 거리의 피사체 촬영",
            "1,400,000원"
        )
    };

    // Product 클래스 정의
    public class Product {
        private String imagePath;  // 이미지 경로
        private String description;  // 제품 설명
        private String price;  // 가격

        public Product(String imagePath, String description, String price) {
            this.imagePath = imagePath;
            this.description = description;
            this.price = price;
        }

        public String getImagePath() {
            return imagePath;
        }

        public String getDescription() {
            return description;
        }

        public String getPrice() {
            return price;
        }
    }

    public Home2Product(JFrame frame, int productIndex) {
        this.productIndex = productIndex;
        setLayout(null);
        setBackground(new Color(245, 245, 250));

        // 렌즈 이미지
        productImageLabel = new JLabel();
        int imageWidth = 400;
        int imageHeight = 400;
        productImageLabel.setBounds(((getWidth() - imageWidth) / 2) + 340, 10, imageWidth, imageHeight);
        productImageLabel.setBorder(BorderFactory.createLineBorder(new Color(139, 169, 152), 5));
        add(productImageLabel);

        // 렌즈 상세 정보
        productDetailsArea = new JTextArea();
        productDetailsArea.setEditable(false);
        productDetailsArea.setFont(new Font("Dialog", Font.BOLD, 14));
        productDetailsArea.setBounds(40, 420, 350, 150);
        
        // 동일한 테두리 적용
        Border border = BorderFactory.createLineBorder(new Color(139, 169, 152), 5);
        productDetailsArea.setBorder(border);
        add(productDetailsArea);

        // "뒤로 가기" 버튼 추가
        JButton backButton = new JButton("홈으로");
        backButton.setBounds(10, 10, 120, 120);
        backButton.setBackground(new Color(139, 169, 152));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // 테두리 설정
        backButton.addActionListener(e -> {
            stopBannerThread();
            frame.setContentPane(new Home2(frame)); // 렌즈 목록 화면으로 돌아가기
            frame.revalidate();
        });
        add(backButton);

        // 렌즈 정보 표시
        displayProductDetails();

        // 하이퍼링크 버튼 추가
        addHyperlinkButton();
    }

    private void displayProductDetails() {
        // 선택된 렌즈에 맞는 이미지, 설명, 가격 표시
        if (productIndex < products.length) {
            Product product = products[productIndex];
            ImageIcon icon = new ImageIcon(product.getImagePath());
            Image scaledImage = icon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
            productImageLabel.setIcon(new ImageIcon(scaledImage));

            String productInfo = product.getDescription() + "\n가격: " + product.getPrice();
            productDetailsArea.setText(productInfo);
        }
    }

    private void stopBannerThread() {
        // Home1에서 사용하는 배너 스레드가 있다면 여기서 중지
    }

    // 이미지 버튼 생성 및 URL 연결
    private void addHyperlinkButton() {
        // 버튼 생성
        JButton hyperlinkButton = new JButton();
        int borderThickness = 5; // 테두리 두께
        hyperlinkButton.setBounds(40, 580, 380, 180); // 버튼 위치 및 크기 조정
        hyperlinkButton.setLayout(null); // 레이아웃 관리자를 null로 설정
        hyperlinkButton.setBackground(Color.WHITE);
        hyperlinkButton.setBorder(BorderFactory.createLineBorder(new Color(139, 169, 152), borderThickness)); // 테두리 추가

        // 버튼 내부에 JLabel 추가하여 이미지 표시
        JLabel imageLabel = new JLabel();
        // 테두리 안쪽에 맞춰 이미지 배치
        int adjustedWidth = hyperlinkButton.getWidth() - 2 * borderThickness;
        int adjustedHeight = hyperlinkButton.getHeight() - 2 * borderThickness;
        imageLabel.setBounds(borderThickness, borderThickness, adjustedWidth, adjustedHeight);

        // 동적으로 이미지와 URL 로드
        String[] hyperlinkImages = {
            "src/project/image/SEL1635GM2_hyper.jpg",
            "src/project/image/SEL2470GM_hyper.jpg",
            "src/project/image/SEL85F18_hyper.jpg",
            "src/project/image/SEL70300G_hyper.jpg"
        };
        String[] urls = {
            "https://www.youtube.com/watch?v=_JON6OjL03s",
            "https://www.youtube.com/watch?v=yYQHLrhE_Gw",
            "https://www.youtube.com/watch?v=u6_IB86CnyQ",
            "https://www.youtube.com/watch?v=5cTAfdw0mog"
        };

        if (productIndex < hyperlinkImages.length && productIndex < urls.length) {
            // 이미지 설정
            String imagePath = hyperlinkImages[productIndex];
            ImageIcon hyperlinkIcon = new ImageIcon(imagePath);
            Image scaledImage = hyperlinkIcon.getImage().getScaledInstance(
                adjustedWidth, adjustedHeight, Image.SCALE_SMOOTH
            );
            imageLabel.setIcon(new ImageIcon(scaledImage));
            hyperlinkButton.add(imageLabel);

            // URL 연결
            String productUrl = urls[productIndex];
            hyperlinkButton.addActionListener(e -> {
                try {
                    Desktop.getDesktop().browse(new URI(productUrl));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }

        // 버튼을 패널에 추가
        add(hyperlinkButton);
    }
}
