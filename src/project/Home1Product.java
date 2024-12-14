package project;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import java.net.URI;

public class Home1Product extends JPanel {
    private int productIndex;
    private JLabel productImageLabel;
    private JTextArea productDetailsArea;
    private JLabel hashtagLabel1, hashtagLabel2, hashtagLabel3;  // New labels for hashtags
    
    // 상품 데이터 배열
    private Product[] products = {
        new Product(
            "src/project/image/product/ZV-E10.png",
            "제품:Sony ZV-E10\n센서: 24.2MP APS-C CMOS 센서.\n용도: 브이로그 및 콘텐츠 제작에 최적화.\n비디오: 4K UHD(30fps), 1080p(120fps) 지원.\n무게: 약 343g",
            "1340000원"
        ),
        new Product(
            "src/project/image/product/ILME-FX30.png",
            "제품:Sony ILME-FX30\n센서: 26MP APS-C (Super 35mm) CMOS.\n용도: 영화 제작과 고화질 영상 촬영용 시네마 라인 카메라.\n비디오: 4K 120fps, 10-bit 4:2:2, RAW 출력 지원.\n컬러 과학: S-Cinetone 및 로그 촬영 지원.\n기능: 5축 손떨림 보정, 풀사이즈 HDMI, USB-C등.\n무게: 약 562g",
            "2391340원"
        ),
        new Product(
            "src/project/image/product/ILCE-9M3.png",
            "제품:Sony ILCE-9M3\n센서: 24.2MP Exmor RS CMOS 센서.\n용도: 스포츠 및 야생동물 촬영에 최적화.\n비디오: 4K 30fps, 1080p 120fps.\n디스플레이: 3인치 LCD 터치스크린.\n특징: 초고속 연사, 20fps 연속 촬영.\n무게: 약 650g",
            "7980000원"
        ),
        new Product(
            "src/project/image/product/ILCE-7CR.png",
            "제품:Sony ILCE-7CR\n센서: 33MP Full-frame Exmor R CMOS.\n용도: 전문가용 고화질 촬영.\n비디오: 4K UHD(60fps) 촬영 지원.\n디스플레이: 3인치 LCD 터치스크린.\n기능: IBIS, 10fps 연속 촬영.\n무게: 약 507g",
            "3790000원"
        )
    };

    private String[][] hashtags = {
        {" #입문자에게좋아요", " #휴대성좋음", " #고화질"},   // Hashtags for ZV-E10
        {" #전문가용", " #저조도촬영강화", " #AI기능포함"}, // Hashtags for ILME-FX30
        {" #다목적사용가능", " #내구성우수", " #빠른사진전송속도"}, // Hashtags for ILCE-9M3
        {" #고화질", " #AI기능포함", " #터치스크린지원"}    // Hashtags for ILCE-7CR
    };
    
    private String[][] productImages = {
            {"src/project/image/product/ZV-E10_1.jpg", "src/project/image/product/ZV-E10_2.jpg", "src/project/image/product/ZV-E10_3.jpg"},
            {"src/project/image/product/ILME-FX30_1.jpg", "src/project/image/product/ILME-FX30_2.jpg", "src/project/image/product/ILME-FX30_3.jpg"},
            {"src/project/image/product/ILCE-9M3_1.jpg", "src/project/image/product/ILCE-9M3_2.jpg", "src/project/image/product/ILCE-9M3_3.jpg"},
            {"src/project/image/product/ILCE-7CR_1.jpg", "src/project/image/product/ILCE-7CR_2.jpg", "src/project/image/product/ILCE-7CR_3.jpg"}
        };
    public Home1Product(JFrame frame, int productIndex) {
        this.productIndex = productIndex;
        setLayout(null);
        setBackground(new Color(245, 245, 250));

        productImageLabel = new JLabel();
        int imageWidth = 400;
        int imageHeight = 400;
        productImageLabel.setBounds(((getWidth() - imageWidth) / 2) + 340, 10, imageWidth, imageHeight);
        productImageLabel.setBorder(BorderFactory.createLineBorder(new Color(139, 169, 152), 5));
        add(productImageLabel);

        productDetailsArea = new JTextArea();
        productDetailsArea.setEditable(false);
        productDetailsArea.setFont(new Font("Dialog", Font.BOLD, 14));
        productDetailsArea.setBounds(40, 420, 380, 150);

        Border border = BorderFactory.createLineBorder(new Color(139, 169, 152), 5);
        productDetailsArea.setBorder(border);
        add(productDetailsArea);

        JButton backButton = new JButton("HOME");
        backButton.setBounds(10, 10, 120, 120);
        backButton.setBackground(new Color(139, 169, 152));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        backButton.addActionListener(e -> {
            stopBannerThread();
            frame.setContentPane(new Home1(frame));
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

    private void addHyperlinkButton() {
        JButton hyperlinkButton = new JButton();
        int borderThickness = 5;
        hyperlinkButton.setBounds(40, 580, 380, 200);
        hyperlinkButton.setLayout(null);
        hyperlinkButton.setBackground(Color.WHITE);
        hyperlinkButton.setBorder(BorderFactory.createLineBorder(new Color(139, 169, 152), borderThickness));

        JLabel imageLabel = new JLabel();
        int adjustedWidth = hyperlinkButton.getWidth() - 2 * borderThickness;
        int adjustedHeight = hyperlinkButton.getHeight() - 2 * borderThickness;
        imageLabel.setBounds(borderThickness, borderThickness, adjustedWidth, adjustedHeight);

        String[] hyperlinkImages = {
            "src/project/image/ZV-E10_hyper.jpg",
            "src/project/image/ILME-FX30_hyper.jpg",
            "src/project/image/ILCE-9M3_hyper.jpg",
            "src/project/image/ILCE-7CR_hyper.jpg"
        };
        String[] urls = {
            "https://www.youtube.com/watch?v=OlAwaKkmhNY",
            "https://www.youtube.com/watch?v=GFLL_IZWd70",
            "https://www.youtube.com/watch?v=BNMo4d4jiME",
            "https://www.youtube.com/watch?v=2e5NDyXu7dY"
        };

        if (productIndex < hyperlinkImages.length && productIndex < urls.length) {
            String imagePath = hyperlinkImages[productIndex];
            ImageIcon hyperlinkIcon = new ImageIcon(imagePath);
            Image scaledImage = hyperlinkIcon.getImage().getScaledInstance(adjustedWidth, adjustedHeight, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
            hyperlinkButton.add(imageLabel);

            String productUrl = urls[productIndex];
            hyperlinkButton.addActionListener(e -> {
                try {
                    Desktop.getDesktop().browse(new URI(productUrl));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }

        add(hyperlinkButton);
    }

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
    
 // 해시태그 레이블 추가
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
