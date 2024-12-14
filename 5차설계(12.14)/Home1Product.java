package project;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import java.net.URI;

/**
 * Home1Product 클래스.
 * 선택한 상품의 상세 정보를 표시하는 화면.
 */
public class Home1Product extends JPanel {
    private int productIndex;
    private JLabel productImageLabel;	
    private JTextArea productDetailsArea;

    // Product 배열 생성 (상품의 이미지, 설명, 가격 등)
    private Product[] products = {
        new Product(
            "src/project/image/product/ZV-E10.png",
            "제품:Sony ZV-E10\n센서: 24.2MP APS-C CMOS 센서.\n용도: 브이로그 및 콘텐츠 제작에 최적화.\n비디오: 4K UHD(30fps), 1080p(120fps) 지원.\n포커스: 425포인트 위상차 및 콘트라스트 검출 AF.\n디스플레이: 회전 가능한 3인치 터치스크린 LCD.\n특징: 고급 마이크, 실시간 스트리밍, 제품 쇼케이스 모드.\n무게: 약 343g",
            "$799"
        ),
        new Product(
            "src/project/image/product/ILME-FX30.png",
            "제품:Sony ILME-FX30\n센서: 26MP APS-C (Super 35mm) CMOS.\n용도: 영화 제작과 고화질 영상 촬영용 시네마 라인 카메라.\n비디오: 4K 120fps, 10-bit 4:2:2, RAW 출력 지원.\n컬러 과학: S-Cinetone 및 로그 촬영 지원.\n포커스: 빠른 하이브리드 AF와 리얼타임 Eye AF.\n기능: 5축 손떨림 보정, 풀사이즈 HDMI, USB-C등.\n무게: 약 562g",
            "$1299"
        ),
        new Product(
            "src/project/image/product/ILCE-9M3.png",
            "제품:Sony ILCE-9M3\n센서: 24.2MP Exmor RS CMOS 센서.\n용도: 스포츠 및 야생동물 촬영에 최적화.\n비디오: 4K 30fps, 1080p 120fps.\n포커스: 693포인트 하이브리드 AF.\n디스플레이: 3인치 LCD 터치스크린.\n특징: 초고속 연사, 20fps 연속 촬영.\n무게: 약 650g",
            "$4499"
        ),
        new Product(
            "src/project/image/product/ILCE-7CR.png",
            "제품:Sony ILCE-7CR\n센서: 33MP Full-frame Exmor R CMOS.\n용도: 전문가용 고화질 촬영.\n비디오: 4K UHD(60fps) 촬영 지원.\n포커스: 693포인트 하이브리드 AF.\n디스플레이: 3인치 LCD 터치스크린.\n기능: IBIS, 10fps 연속 촬영.\n무게: 약 507g",
            "$2799"
        )
    };

    public Home1Product(JFrame frame, int productIndex) {
        this.productIndex = productIndex;
        setLayout(null);
        setBackground(new Color(245, 245, 250));

        // 상품 이미지
        productImageLabel = new JLabel();
        int imageWidth = 400;
        int imageHeight = 400;
        productImageLabel.setBounds(((getWidth() - imageWidth) / 2) + 340, 10, imageWidth, imageHeight);
        productImageLabel.setBorder(BorderFactory.createLineBorder(new Color(139, 169, 152), 5));
        add(productImageLabel);

        // 상품 상세 정보
        productDetailsArea = new JTextArea();
        productDetailsArea.setEditable(false);
        productDetailsArea.setFont(new Font("Dialog", Font.BOLD, 14)); // 글꼴 설정
        productDetailsArea.setBounds(40, 420, 380, 150);

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
            frame.setContentPane(new Home1(frame)); // Home1 화면으로 돌아가기
            frame.revalidate();
        });
        add(backButton);

        // 상품 정보 표시
        displayProductDetails();

        // 하이퍼링크 버튼 추가
        addHyperlinkButton();
    }

    private void displayProductDetails() {
        // 선택된 상품에 맞는 이미지, 설명, 가격 표시
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
