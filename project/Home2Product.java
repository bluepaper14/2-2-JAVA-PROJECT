package project;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import java.net.URI;

public class Home2Product extends JPanel {
    private int productIndex;
    private JLabel productImageLabel;
    private JTextArea productDetailsArea;
    private JLabel hashtagLabel1, hashtagLabel2, hashtagLabel3;

    private Product[] products = {
        new Product(
            "/resources/image/product/SEL1635GM2.png",
            "제품명:SEL1635GM2\n타입: 풀프레임 광각 줌 렌즈\n초점 거리: 16-35mm\n조리개: 최대 F2.8\n용도: 풍경, 건축물 촬영 및 넓은 공간",
            "2,900,000원"
        ),
        new Product(
            "/resources/image/product/SEL2470GM.png",
            "제품명:SEL2470GM\n타입: 풀프레임 표준 줌 렌즈\n초점 거리: 24-70mm\n조리개: 최대 F2.8\n용도: 인물, 여행, 웨딩 촬영 등 다양한 용도",
            "3,200,000원"
        ),
        new Product(
            "/resources/image/product/SEL85F18.png",
            "제품명:SEL85F18\n타입: 풀프레임 인물 촬영용 단렌즈\n초점 거리: 85mm\n조리개: 최대 F1.8\n용도: 인물 촬영, 포트레이트 촬영에 적합",
            "800,000원"
        ),
        new Product(
            "/resources/image/product/SEL70300G.png",
            "제품명:SEL70300G\n타입: 풀프레임 망원 줌 렌즈\n초점 거리: 70-300mm\n조리개: F4.5-5.6\n용도: 스포츠, 야생동물 촬영 먼 거리의 피사체 촬영",
            "1,400,000원"
        )
    };
    
    private String[][] hashtags = {
            {" #전문가용", " #휴대성좋음", " #사용하기쉽다"},
            {" #다목적사용가능", " #내구성우수", " #고화질"},
            {" #인물촬영용단렌즈", " #저조도촬영강화", " #뛰어난사용후기"},
            {" #전문가용", " #다양한사진", " #고화질"}
    };
    
    private String[][] productImages = {
        {"/resources/image/product/SEL2470GM_1.jpg", "/resources/image/product/SEL2470GM_2.jpg", "/resources/image/product/SEL2470GM_3.jpg"},
        {"/resources/image/product/SEL85F18_1.jpg", "/resources/image/product/SEL85F18_2.jpg", "/resources/image/product/SEL85F18_3.jpg"},
        {"/resources/image/product/SEL70300G_1.jpg", "/resources/image/product/SEL70300G_2.jpg", "/resources/image/product/SEL70300G_3.jpg"},
        {"/resources/image/product/SEL1635GM2_1.jpg", "/resources/image/product/SEL1635GM2_2.jpg", "/resources/image/product/SEL1635GM2_3.jpg"}
    };
    
    public Home2Product(JFrame frame, int productIndex) {
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
        backButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        backButton.addActionListener(e -> {
            stopBannerThread();
            frame.setContentPane(new Home2(frame));
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
            ImageIcon icon = new ImageIcon(getClass().getResource(product.getImagePath()));
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
            "/resources/image/SEL1635GM2_hyper.jpg",
            "/resources/image/SEL2470GM_hyper.jpg",
            "/resources/image/SEL85F18_hyper.jpg",
            "/resources/image/SEL70300G_hyper.jpg"
        };
        String[] urls = {
            "https://www.youtube.com/watch?v=_JON6OjL03s",
            "https://www.youtube.com/watch?v=yYQHLrhE_Gw",
            "https://www.youtube.com/watch?v=u6_IB86CnyQ",
            "https://www.youtube.com/watch?v=5cTAfdw0mog"
        };

        if (productIndex < hyperlinkImages.length && productIndex < urls.length) {
            String imagePath = hyperlinkImages[productIndex];
            ImageIcon hyperlinkIcon = new ImageIcon(getClass().getResource(imagePath));
            Image scaledImage = hyperlinkIcon.getImage().getScaledInstance(
                adjustedWidth, adjustedHeight, Image.SCALE_SMOOTH
            );
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
        cartButton.setBackground(new Color(139, 169, 152));
        cartButton.setForeground(Color.WHITE);
        cartButton.setFont(new Font("SansSerif", Font.BOLD, 25));

        int buttonWidth = 170;
        int buttonHeight = 150;
        cartButton.setBounds(430, 420, buttonWidth, buttonHeight);

        cartButton.addActionListener(e -> {
            if (productIndex < products.length) {
                Product product = products[productIndex];
                CartManager.getInstance().addItem(product.getDescription(), product.getPrice());
                JOptionPane.showMessageDialog(this, "장바구니에 추가되었습니다!");
            }
        });
        
        add(cartButton);
    }

    private void addHashtagLabels() {
        hashtagLabel1 = new JLabel(hashtags[productIndex][0]);
        hashtagLabel1.setFont(new Font("SansSerif", Font.BOLD, 16));
        hashtagLabel1.setBounds(430, 580, 170, 40);
        hashtagLabel1.setOpaque(true);
        hashtagLabel1.setBackground(new Color(169, 152, 159));
        add(hashtagLabel1);

        hashtagLabel2 = new JLabel(hashtags[productIndex][1]);
        hashtagLabel2.setFont(new Font("SansSerif", Font.BOLD, 16));
        hashtagLabel2.setBounds(430, 630, 170, 40);
        hashtagLabel2.setOpaque(true);
        hashtagLabel2.setBackground(new Color(168, 152, 169));
        add(hashtagLabel2);

        hashtagLabel3 = new JLabel(hashtags[productIndex][2]);
        hashtagLabel3.setFont(new Font("SansSerif", Font.BOLD, 16));
        hashtagLabel3.setBounds(430, 680, 170, 40);
        hashtagLabel3.setOpaque(true);
        hashtagLabel3.setBackground(new Color(152, 159, 169));
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
            previewFrame.setSize(1600, 1000);
            previewFrame.setLocationRelativeTo(this);
            previewFrame.setLayout(new BorderLayout());

            JPanel imagePanel = new JPanel();
            imagePanel.setLayout(new GridLayout(1, images.length, 5, 5));
            imagePanel.setBackground(Color.BLACK);

            for (String imagePath : images) {
                ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));

                Image scaledImage = icon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));

                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                imageLabel.setVerticalAlignment(SwingConstants.CENTER);

                imagePanel.add(imageLabel);
            }

            JScrollPane scrollPane = new JScrollPane(imagePanel);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            previewFrame.add(scrollPane, BorderLayout.CENTER);
            previewFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "이미지가 없습니다!");
        }
    }
}
