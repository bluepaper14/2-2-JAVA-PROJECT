package project;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class Home2 extends JPanel {
    private JLabel bannerLabel;
    private int currentBannerIndex = 0;
    private final String[] bannerImages;
    private Thread bannerThread;
    private JFrame frame;

    public Home2(JFrame frame) {
        this.frame = frame;
        setLayout(null);
        setBackground(Color.WHITE);

        // "렌즈" 라벨
        JLabel label = new JLabel("렌즈", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 50));
        label.setBounds(20, 50, 400, 50);
        add(label);

        // 설명 문구 라벨
        JLabel descriptionLabel = new JLabel("이 화면은 렌즈에 대한 상품입니다, 아래에서 확인하세요", SwingConstants.CENTER);
        descriptionLabel.setFont(new Font("Dialog", Font.BOLD, 15));
        descriptionLabel.setBounds(160, 120, 400, 30);
        descriptionLabel.setForeground(Color.BLACK);
        add(descriptionLabel);

        // 상품 테이블 설정
        setupProductTable();

        // 본체 화면으로 이동하는 버튼 추가
        JButton toHome1Button = createHome1Button();
        add(toHome1Button);

        // 가격표 확인 버튼 추가
        JButton priceCheckButton = createPriceCheckButton();
        add(priceCheckButton);

        // 배너 이미지 초기화 및 배너 스레드 시작
        bannerImages = new String[] {
            "src/project/image/banner1.jpg",
            "src/project/image/banner2.jpg"
        };
        setupBannerWithThread();
    }

    /**
     * 상품 목록을 표시하는 JTable을 생성하고 추가합니다.
     */
    private void setupProductTable() {
        int rows = 2; // 테이블의 행 수 (2행으로 변경)
        int cols = 2; // 테이블의 열 수
        Object[][] data = new Object[rows][cols];

        // 상품 이미지 경로 배열
        String[] productImages = {
            "src/project/image/product/SEL1635GM2.png",
            "src/project/image/product/SEL2470GM.png",
            "src/project/image/product/SEL85F18.png",
            "src/project/image/product/SEL70300G.png"
        };

        // 상품명 및 가격 배열
        String[] productNames = {
            "SEL1635GM2",
            "SEL2470GM",
            "SEL85F18",
            "SEL70300G"
        };
        String[] productPrices = {
            "3,090,000",
            "2,890,000",
            "699,000",
            "1,149,000"
        };

        // 각 셀에 버튼 추가
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int buttonIndex = i * cols + j; // 버튼 인덱스 계산
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(400, 400)); 
                button.setBackground(new Color(245, 245, 250)); 
                button.setBorder(BorderFactory.createLineBorder(new Color(139, 169, 152), 6));

                if (buttonIndex < productImages.length) {
                    String imagePath = productImages[buttonIndex];
                    ImageIcon icon = new ImageIcon(imagePath);
                    Image scaledImage = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH); 
                    button.setIcon(new ImageIcon(scaledImage));
                    
                    // 제품명과 가격을 텍스트로 설정
                    String productInfo = "<html>" + "<br>제품: " + productNames[buttonIndex] + "<br>가격: " + productPrices[buttonIndex] + "</html>";
                    button.setText(productInfo);
                    button.setHorizontalTextPosition(SwingConstants.CENTER); // 텍스트를 중앙에 배치
                    button.setVerticalTextPosition(SwingConstants.BOTTOM); // 텍스트를 이미지 아래에 배치
                }

                // 상품 정보를 넘기면서 화면 전환
                int finalButtonIndex = buttonIndex;
                button.addActionListener(e -> {
                    stopBannerThread(); // 배너 스레드 종료
                    frame.setContentPane(new Home2Product(frame, finalButtonIndex)); // Home2Product로 전환
                    frame.revalidate(); // 화면 갱신
                });

                data[i][j] = button;
            }
        }

        // 테이블 생성
        String[] columnNames = {"Column 1", "Column 2"}; // 열 이름
        JTable table = new JTable(data, columnNames);
        table.setRowHeight(300); // 행 높이 설정
        table.setDefaultRenderer(Object.class, new ButtonRenderer());
        table.setDefaultEditor(Object.class, new ButtonEditor(new JCheckBox()));
        table.setTableHeader(null); // 테이블 헤더 숨기기
        table.setShowGrid(false); // 테두리 숨기기
        table.setIntercellSpacing(new Dimension(20, 20)); // 셀 간격 설정
        table.setBorder(BorderFactory.createEmptyBorder()); // JTable 테두리 제거
        
        // 스크롤 가능한 테이블로 설정
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(160, 150, 530, 390); // 위치 및 크기 설정
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // JScrollPane 외부 테두리 제거
        add(scrollPane);
    }

    /**
     * 본체 화면으로 이동하는 버튼을 생성합니다.
     *
     * @return 생성된 JButton
     */
    private JButton createHome1Button() {
        JButton toHome1Button = new JButton("본체 이동");
        toHome1Button.setBounds(25, 25, 120, 120);
        toHome1Button.setBackground(new Color(139, 169, 152));
        toHome1Button.setForeground(Color.WHITE);
        toHome1Button.setFont(new Font("SansSerif", Font.BOLD, 20));
        toHome1Button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        toHome1Button.addActionListener(e -> {
            stopBannerThread(); // 배너 스레드 종료
            frame.setContentPane(new Home1(frame)); // Home1으로 화면 전환
            frame.revalidate();
        });
        return toHome1Button;
    }

    /**
     * 가격표 확인 버튼을 생성합니다.
     *
     * @return 생성된 JButton
     */
    private JButton createPriceCheckButton() {
        JButton priceCheckButton = new JButton("가격표 확인");
        priceCheckButton.setBounds(530, 80, 130, 30);
        priceCheckButton.setBackground(new Color(156, 152, 169));
        priceCheckButton.setForeground(Color.WHITE);
        priceCheckButton.setFont(new Font("SansSerif", Font.BOLD, 15));
        priceCheckButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        priceCheckButton.addActionListener(e -> {
            new CartView().setVisible(true); // 가격표 창 표시
        });
        return priceCheckButton;
    }

    /**
     * 배너를 설정하고 변경하는 스레드를 시작합니다.
     */
    private void setupBannerWithThread() {
        bannerLabel = new JLabel();
        bannerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bannerLabel.setVerticalAlignment(SwingConstants.CENTER);
        bannerLabel.setBounds(0, 550, 700, 250);
        add(bannerLabel);

        if (bannerImages.length > 0) {
            bannerThread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        SwingUtilities.invokeLater(this::updateBanner); // 배너 업데이트
                        Thread.sleep(3000); // 3초 간격으로 배너 변경
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // 인터럽트 발생 시 스레드 종료
                    }
                }
            });
            bannerThread.start();
            updateBanner(); // 초기 배너 표시
        } else {
            bannerLabel.setText("No banner images found.");
        }
    }

    /**
     * 현재 배너를 업데이트합니다.
     */
    private void updateBanner() {
        currentBannerIndex = (currentBannerIndex + 1) % bannerImages.length;
        String imagePath = bannerImages[currentBannerIndex];
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(bannerLabel.getWidth(), bannerLabel.getHeight(), Image.SCALE_SMOOTH);
        bannerLabel.setIcon(new ImageIcon(scaledImage));
    }

    /**
     * 배너 변경 스레드를 중지합니다.
     */
    private void stopBannerThread() {
        if (bannerThread != null && bannerThread.isAlive()) {
            bannerThread.interrupt();
        }
    }

    /**
     * JButton을 JTable 셀에 렌더링하기 위한 클래스.
     */
    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof JButton) {
                return (JButton) value;
            }
            return this;
        }
    }

    /**
     * JTable 셀에서 버튼 클릭 이벤트를 처리하기 위한 클래스.
     */
    private static class ButtonEditor extends DefaultCellEditor {
        private final JButton button;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            this.button = new JButton();
            this.button.setOpaque(true);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (value instanceof JButton) {
                return (JButton) value;
            }
            return button;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 하단 배너 위 선 그리기
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setStroke(new BasicStroke(9));
        g2.setColor(new Color(156, 152, 169));
        g2.drawLine(0, 550, 800, 550); // 배너 위 선
        g2.drawLine(173, 150, 655, 150); // 두 번째 선
        g2.dispose();
    }
}
