package project;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class Home2 extends JPanel {
    private JLabel bannerLabel; // 배너 이미지를 표시할 JLabel
    private int currentBannerIndex = 0; // 현재 표시 중인 배너 이미지의 인덱스
    private final String[] bannerImages; // 배너 이미지 경로 배열
    private Thread bannerThread; // 배너를 변경하는 스레드

    public Home2(JFrame frame) {
        setLayout(null); // Absolute Layout 사용
        setBackground(Color.WHITE); // 배경색을 하얀색으로 설정

        // "렌즈" 라벨
        JLabel label = new JLabel("렌즈", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 60));
        label.setBounds(20, 50, 400, 50); // 중앙에 위치하도록 설정
        add(label);

        // 설명 문구 라벨
        JLabel descriptionLabel = new JLabel("이 화면은 렌즈에 대한 상품입니다, 아래에서 확인하세요", SwingConstants.CENTER);
        descriptionLabel.setFont(new Font("Dialog", Font.BOLD, 15));
        descriptionLabel.setBounds(160, 120, 400, 30); // "렌즈" 아래에 위치 설정
        descriptionLabel.setForeground(Color.BLACK); // 검정색 글씨로 설정
        add(descriptionLabel);

        // 테이블 크기 및 위치 설정
        int rows = 2; // 행 개수
        int cols = 2; // 열 개수
        Object[][] data = new Object[rows][cols];

        // 테이블 데이터에 버튼 추가
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int buttonIndex = i * cols + j; // 이미지 인덱스를 위한 계산
                JButton button = new JButton() {
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(400, 400); // 버튼 크기 고정
                    }
                };
                button.setBackground(new Color(245, 245, 250)); // 버튼 배경색을 흰색으로 설정
                button.setForeground(Color.BLACK); // 버튼 텍스트 색상 (검정)
                button.setFont(new Font("SansSerif", Font.BOLD, 16)); // 폰트 설정
                button.setBorder(BorderFactory.createLineBorder(new Color(139, 169, 152), 6)); // 테두리 색상 및 굵기
                button.setFocusPainted(false); // 포커스 페인팅 제거

                // 이미지 경로 설정
                String[] productImages = {
                    "src/project/image/product/SEL2470GM.png",
                    "src/project/image/product/SEL1635GM2.png",
                    "src/project/image/product/SEL85F18.png",
                    "src/project/image/product/SEL70300G.png"
                };

                // 버튼에 이미지 추가
                if (buttonIndex < productImages.length) {
                    String imagePath = productImages[buttonIndex];
                    ImageIcon icon = new ImageIcon(imagePath);
                    Image scaledImage = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH); // 이미지 크기 조정
                    button.setIcon(new ImageIcon(scaledImage)); // 버튼에 아이콘 설정
                }

                // 버튼 클릭 이벤트
                button.addActionListener(e -> JOptionPane.showMessageDialog(this, "Button " + (buttonIndex + 1) + " clicked!"));

                data[i][j] = button;
            }
        }

        // JTable 생성
        String[] columnNames = {"Column 1", "Column 2"};
        JTable table = new JTable(data, columnNames);
        table.setRowHeight(300); // 행 높이를 키움
        table.setDefaultRenderer(Object.class, new ButtonRenderer());
        table.setDefaultEditor(Object.class, new ButtonEditor(new JCheckBox()));
        table.setTableHeader(null); // 테이블 헤더 제거
        table.setShowGrid(false); // 셀 테두리 제거
        table.setIntercellSpacing(new Dimension(20, 20)); // 셀 간격 증가
        table.setBorder(BorderFactory.createEmptyBorder()); // JTable 테두리 제거

        // JScrollPane으로 스크롤 가능하게 설정
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(160, 150, 530, 390); // 테이블 위치와 크기 설정
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // JScrollPane 외부 테두리 제거
        add(scrollPane);

        // Home1로 이동하는 버튼
        JButton toHome1Button = new JButton("본체 이동");
        toHome1Button.setBounds(25, 25, 120, 120); // 크기와 위치 설정
        toHome1Button.setBackground(new Color(139, 169, 152));
        toHome1Button.setForeground(Color.WHITE);
        toHome1Button.setFont(new Font("SansSerif", Font.BOLD, 20));
        toHome1Button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        toHome1Button.setFocusPainted(false);
        toHome1Button.addActionListener(e -> {
            stopBannerThread(); // 스레드 정지
            frame.setContentPane(new Home1(frame));
            frame.revalidate();
        });
        add(toHome1Button);

        // 배너 이미지 로드
        bannerImages = new String[]{
            "src/project/image/banner1.jpg",
            "src/project/image/banner2.jpg"
        };
        setupBannerWithThread(); // 스레드로 배너 설정
    }

    // 배너 설정 메서드 (스레드 사용)
    private void setupBannerWithThread() {
        bannerLabel = new JLabel();
        bannerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bannerLabel.setVerticalAlignment(SwingConstants.CENTER);
        bannerLabel.setBounds(0, 550, 700, 250); // 배너 위치 설정
        add(bannerLabel);

        if (bannerImages.length > 0) {
            bannerThread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        SwingUtilities.invokeLater(this::updateBanner); // EDT에서 배너 업데이트
                        Thread.sleep(3000); // 3초 대기
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // 인터럽트 발생 시 종료
                    }
                }
            });
            bannerThread.start(); // 스레드 시작
            updateBanner(); // 초기 배너 표시
        } else {
            bannerLabel.setText("No banner images found.");
        }
    }

    // 배너 업데이트 메서드
    private void updateBanner() {
        currentBannerIndex = (currentBannerIndex + 1) % bannerImages.length;
        String imagePath = bannerImages[currentBannerIndex];
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(bannerLabel.getWidth(), bannerLabel.getHeight(), Image.SCALE_SMOOTH);
        bannerLabel.setIcon(new ImageIcon(scaledImage));
    }

    // 배너 스레드 정지 메서드
    private void stopBannerThread() {
        if (bannerThread != null && bannerThread.isAlive()) {
            bannerThread.interrupt(); // 스레드 중단 요청
        }
    }

    // 버튼 렌더러
    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof JButton) {
                return (JButton) value;
            }
            return this;
        }
    }

    // 버튼 편집기
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
        super.paintComponent(g); // 기존 컴포넌트 먼저 그리기

        // 1. Graphics2D 객체 생성
        Graphics2D g2 = (Graphics2D) g;

        // 2. 선 스타일 설정 (굵기 10px, 빨간색)
        g2.setStroke(new BasicStroke(7)); // 선의 굵기 설정
        g2.setColor(Color.black); // 선 색상 설정

        // 3. 선 그리기 (x1, y1) -> (x2, y2)
        g2.drawLine(0, 550, 800, 550); // 하단 배너 위에 수평선
        g2.drawLine(173, 150, 655, 150); // 두 번째 수평선
    }
}
