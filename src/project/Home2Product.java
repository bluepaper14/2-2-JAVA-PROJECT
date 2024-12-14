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
    private JLabel hashtagLabel1, hashtagLabel2, hashtagLabel3;

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
    
    private String[][] hashtags = {
            {" #전문가용", " #휴대성좋음", " #사용하기쉽다"},   // Hashtags for SEL1635GM2
            {" #다목적사용가능", " #내구성우수", " #고화질"}, // Hashtags for SEL2470GM
            {" #인물촬영용단렌즈", " #저조도촬영강화", " #뛰어난사용후기"}, // Hashtags for SEL85F18
            {" #전문가용", " #다양한사진", " #고화질"}    // Hashtags for SEL70300G
        };
    
    private String[][] productImages = {
    	    {"src/project/image/product/SEL2470GM_1.jpg", "src/project/image/product/SEL2470GM_2.jpg", "src/project/image/product/SEL2470GM_3.jpg"},
    	    {"src/project/image/product/SEL85F18_1.jpg", "src/project/image/product/SEL85F18_2.jpg", "src/project/image/product/SEL85F18_3.jpg"},
    	    {"src/project/image/product/SEL70300G_1.jpg", "src/project/image/product/SEL70300G_2.jpg", "src/project/image/product/SEL70300G_3.jpg"},
    	    {"src/project/image/product/SEL1635GM2_1.jpg", "src/project/image/product/SEL1635GM2_2.jpg", "src/project/image/product/SEL1635GM2_3.jpg"}
    	};
    
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
        productDetailsArea.setBounds(40, 420, 380, 150);
        
        // 동일한 테두리 적용
        Border border = BorderFactory.createLineBorder(new Color(139, 169, 152), 5);
        productDetailsArea.setBorder(border);
        add(productDetailsArea);

        // "뒤로 가기" 버튼 추가
        JButton backButton = new JButton("HOME");
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

        displayProductDetails();
        addHyperlinkButton();
        addCartButton();
        addHashtagLabels();
        addPreviewButton();
    }

    private void displayProductDetails() {
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
        hyperlinkButton.setBounds(40, 580, 380, 200); // 버튼 위치 및 크기 조정
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

    /**
     * 장바구니 담기 버튼을 추가합니다.
     */
    private void addCartButton() {
        JButton cartButton = new JButton("BUY");
        cartButton.setBackground(new Color(139, 169, 152));  // 색상 변경
        cartButton.setForeground(Color.WHITE);  // 글자 색
        cartButton.setFont(new Font("SansSerif", Font.BOLD, 25));  // 글꼴 설정

        // 버튼 크기를 가로 160, 세로 150으로 조정
        int buttonWidth = 170;  // 버튼의 너비
        int buttonHeight = 150; // 버튼의 높이
        cartButton.setBounds(430, 420, buttonWidth, buttonHeight);  // 위치 및 크기 설정

        // 클릭 이벤트 리스너 추가
        cartButton.addActionListener(e -> {
            if (productIndex < products.length) {
                Product product = products[productIndex];
                // 장바구니에 상품 추가
                CartManager.getInstance().addItem(product.getDescription(), product.getPrice());
                JOptionPane.showMessageDialog(this, "장바구니에 추가되었습니다!");  // 알림 메시지
            }
        });
        
        add(cartButton);  // 버튼을 패널에 추가
    }
    
    private void addHashtagLabels() {
        // 첫 번째 해시태그 레이블 생성
        hashtagLabel1 = new JLabel(hashtags[productIndex][0]);
        hashtagLabel1.setFont(new Font("SansSerif", Font.BOLD, 16));
        hashtagLabel1.setBounds(430, 580, 170, 40);
        hashtagLabel1.setOpaque(true);  // 배경색을 적용하려면 setOpaque(true) 설정
        hashtagLabel1.setBackground(new Color(169, 152, 159));  // 배경색 설정
        add(hashtagLabel1);

        // 두 번째 해시태그 레이블 생성
        hashtagLabel2 = new JLabel(hashtags[productIndex][1]);
        hashtagLabel2.setFont(new Font("SansSerif", Font.BOLD, 16));
        hashtagLabel2.setBounds(430, 630, 170, 40);
        hashtagLabel2.setOpaque(true);  // 배경색을 적용하려면 setOpaque(true) 설정
        hashtagLabel2.setBackground(new Color(168, 152, 169));  // 배경색 설정
        add(hashtagLabel2);

        // 세 번째 해시태그 레이블 생성
        hashtagLabel3 = new JLabel(hashtags[productIndex][2]);
        hashtagLabel3.setFont(new Font("SansSerif", Font.BOLD, 16));
        hashtagLabel3.setBounds(430, 680, 170, 40);
        hashtagLabel3.setOpaque(true);  // 배경색을 적용하려면 setOpaque(true) 설정
        hashtagLabel3.setBackground(new Color(152, 159, 169));  // 배경색 설정
        add(hashtagLabel3);
    }
    private void addPreviewButton() {
        JButton previewButton = new JButton("사진 미리보기");
        previewButton.setBounds(430, 730, 170, 50);
        previewButton.setBackground(new Color(139, 169, 152));
        previewButton.setForeground(Color.WHITE);
        previewButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        previewButton.addActionListener(e -> showImagePreview());
        add(previewButton);
    }

    private void showImagePreview() {
        if (productIndex < productImages.length) {
            String[] images = productImages[productIndex];

            JFrame previewFrame = new JFrame("사진 미리보기");
            previewFrame.setSize(1600, 1000); // 전체 창 크기
            previewFrame.setLocationRelativeTo(this);
            previewFrame.setLayout(new BorderLayout());

            // 이미지가 들어갈 패널 설정 (배경색 검정으로 변경)
            JPanel imagePanel = new JPanel();
            imagePanel.setLayout(new GridLayout(1, images.length, 5, 5)); // 이미지 간 여백을 5로 설정
            imagePanel.setBackground(Color.BLACK); // 배경색 검정색으로 변경

            for (String imagePath : images) {
                ImageIcon icon = new ImageIcon(imagePath);

                // 이미지 크기를 조정하는 부분
                Image scaledImage = icon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));

                // 이미지를 레이블 중앙에 배치
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                imageLabel.setVerticalAlignment(SwingConstants.CENTER);

                imagePanel.add(imageLabel);
            }

            JScrollPane scrollPane = new JScrollPane(imagePanel);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            // 스크롤 패널을 프레임 중앙에 추가
            previewFrame.add(scrollPane, BorderLayout.CENTER);

            // 닫기 버튼 제거 (아무 코드도 추가하지 않음)

            previewFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "이미지가 없습니다!");
        }
    }
 }

