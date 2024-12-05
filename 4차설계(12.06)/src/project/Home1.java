package project;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.File;

/**
 * Home1 클래스.
 * "본체" 화면을 표시하는 JPanel으로, 배너 이미지, 상품 목록, 버튼 등을 포함합니다.
 */
public class Home1 extends JPanel {
    private JLabel bannerLabel; // 배너 이미지를 표시할 JLabel
    private int currentBannerIndex = 0; // 현재 표시 중인 배너 이미지의 인덱스
    private final String[] bannerImages; // 배너 이미지 경로 배열
    private Thread bannerThread; // 배너를 변경하는 스레드

    /**
     * Home1 생성자.
     *
     * @param frame JFrame 참조. 화면 전환 및 동작 정의에 사용됩니다.
     */
    public Home1(JFrame frame) {
        setLayout(null); // 절대 레이아웃 사용
        setBackground(Color.WHITE); // 배경색을 흰색으로 설정

        // "본체" 라벨 추가
        JLabel label = new JLabel("본체", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 60));
        label.setBounds(20, 50, 400, 50); // 중앙 상단에 배치
        add(label);

        // 설명 문구 라벨 추가
        JLabel descriptionLabel = new JLabel("이 화면은 본체에 대한 상품입니다, 아래에서 확인하세요", SwingConstants.CENTER);
        descriptionLabel.setFont(new Font("Dialog", Font.BOLD, 15));
        descriptionLabel.setBounds(160, 120, 400, 30); // "본체" 라벨 아래에 배치
        descriptionLabel.setForeground(Color.BLACK);
        add(descriptionLabel);

        // 상품 테이블 설정
        setupProductTable();

        // 렌즈 화면으로 이동하는 버튼 추가
        JButton toHome1Button = createLensButton(frame);
        add(toHome1Button);

        // 배너 이미지 초기화 및 배너 스레드 시작
        bannerImages = new String[]{
            "src/project/image/banner1.jpg",
            "src/project/image/banner2.jpg"
        };
        setupBannerWithThread();
    }

    /**
     * 상품 목록을 표시하는 JTable을 생성하고 추가합니다.
     */
    private void setupProductTable() {
        int rows = 3; // 테이블의 행 수
        int cols = 2; // 테이블의 열 수
        Object[][] data = new Object[rows][cols];

        // 상품 이미지 경로 배열
        String[] productImages = {
            "src/project/image/product/ZV-E10.png",
            "src/project/image/product/ILME-FX30.png",
            "src/project/image/product/ILCE-9M3.png",
            "src/project/image/product/ILCE-7CR.png"
        };

        // 각 셀에 버튼 추가
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int buttonIndex = i * cols + j; // 버튼 인덱스 계산
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(400, 400)); // 버튼 크기 설정
                button.setBackground(new Color(245, 245, 250)); // 버튼 배경색 설정
                button.setBorder(BorderFactory.createLineBorder(new Color(139, 169, 152), 6)); // 버튼 테두리 설정

                // 버튼에 이미지 추가
                if (buttonIndex < productImages.length) {
                    String imagePath = productImages[buttonIndex];
                    ImageIcon icon = new ImageIcon(imagePath);
                    Image scaledImage = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH); // 이미지 크기 조정
                    button.setIcon(new ImageIcon(scaledImage));
                }

                // 버튼 클릭 이벤트 추가
                button.addActionListener(e -> JOptionPane.showMessageDialog(this, "Button " + (buttonIndex + 1) + " clicked!"));

                data[i][j] = button; // 데이터를 테이블 셀에 추가
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

        // 스크롤 가능한 테이블로 설정
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(160, 150, 530, 390); // 위치 및 크기 설정
        add(scrollPane);
    }

    /**
     * 렌즈 화면으로 이동하는 버튼을 생성합니다.
     *
     * @param frame JFrame 참조
     * @return 생성된 JButton
     */
    private JButton createLensButton(JFrame frame) {
        JButton toHome1Button = new JButton("렌즈 이동");
        toHome1Button.setBounds(25, 25, 120, 120); // 크기와 위치 설정
        toHome1Button.setBackground(new Color(139, 169, 152)); // 배경색 설정
        toHome1Button.setForeground(Color.WHITE); // 텍스트 색상
        toHome1Button.setFont(new Font("SansSerif", Font.BOLD, 20)); // 폰트 설정
        toHome1Button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // 테두리 설정
        toHome1Button.addActionListener(e -> {
            stopBannerThread(); // 배너 스레드 종료
            frame.setContentPane(new Home2(frame)); // Home2로 화면 전환
            frame.revalidate();
        });
        return toHome1Button;
    }

    /**
     * 배너를 설정하고 변경하는 스레드를 시작합니다.
     */
    private void setupBannerWithThread() {
        bannerLabel = new JLabel(); // 배너 이미지를 표시할 JLabel 생성
        bannerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bannerLabel.setVerticalAlignment(SwingConstants.CENTER);
        bannerLabel.setBounds(0, 550, 700, 250); // 배너 위치 설정
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
            bannerThread.start(); // 스레드 시작
            updateBanner(); // 초기 배너 표시
        } else {
            bannerLabel.setText("No banner images found."); // 배너 이미지가 없을 때 표시
        }
    }

    /**
     * 현재 배너를 업데이트합니다.
     */
    private void updateBanner() {
        currentBannerIndex = (currentBannerIndex + 1) % bannerImages.length; // 다음 배너로 이동
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
            bannerThread.interrupt(); // 스레드에 인터럽트 신호 전달
        }
    }

    /**
     * JButton을 JTable 셀에 렌더링하기 위한 클래스.
     */
    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof JButton) {
                return (JButton) value; // 버튼 반환
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
            this.button = new JButton(); // 버튼 생성
            this.button.setOpaque(true);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (value instanceof JButton) {
                return (JButton) value; // 버튼 반환
            }
            return button;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 하단 배너 위 선 그리기
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setStroke(new BasicStroke(7)); // 선 굵기
        g2.setColor(Color.BLACK); // 선 색상
        g2.drawLine(0, 550, 800, 550); // 배너 위 선
        g2.drawLine(173, 150, 655, 150); // 두 번째 선
        g2.dispose();
    }
}
