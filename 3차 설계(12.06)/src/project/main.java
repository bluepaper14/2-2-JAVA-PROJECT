package project;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 애플리케이션의 진입점.
 * MainFrame을 생성하여 프로그램을 실행합니다.
 */
public class main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame("간단한 JFrame 예제"));
    }
}

/**
 * 메인 프레임 클래스.
 * 프로그램의 메인 윈도우를 생성하고, 배경 패널과 메뉴바를 추가합니다.
 */
class MainFrame extends JFrame {
    public MainFrame(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 900);
        setLocationRelativeTo(null); // 화면 중앙에 표시

        // 사용자 정의 배경 패널 설정
        setContentPane(new CustomPanel());

        // 메뉴바 설정
        setJMenuBar(createMenuBar());

        setVisible(true);
    }

    /**
     * 메뉴바 생성 메서드.
     * 카메라 관련 팁을 제공하는 메뉴와 메뉴 아이템을 추가합니다.
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("카메라Tip");

        // 메뉴 아이템 추가
        menu.add(createMenuItem("카메라 사용하는 방법!", "src/project/camera_tip.txt"));
        menu.add(createMenuItem("렌즈 사용하는 방법!", "src/project/lens_tip.txt"));

        menuBar.add(menu);
        return menuBar;
    }

    /**
     * 메뉴 아이템 생성 메서드.
     * 메뉴 아이템 클릭 시 팁 내용을 로드합니다.
     *
     * @param itemName 메뉴 아이템 이름
     * @param filePath 파일 경로
     * @return 생성된 JMenuItem 객체
     */
    private JMenuItem createMenuItem(String itemName, String filePath) {
        JMenuItem menuItem = new JMenuItem(itemName);
        menuItem.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "팁 로드: " + filePath,
                itemName,
                JOptionPane.INFORMATION_MESSAGE
        ));
        return menuItem;
    }
}

/**
 * 사용자 정의 패널 클래스.
 * 배경과 텍스트를 그리고, 버튼을 추가합니다.
 */
class CustomPanel extends JPanel {
	public CustomPanel() {
        setLayout(null);
        addButtons();
        addOutButton();
    }

    private void addButtons() {
        int panelWidth = 700;
        int panelHeight = 800;
        int buttonWidth = 200;
        int buttonHeight = 250;
        int gap = 40;

        int x1 = (panelWidth - (buttonWidth * 2 + gap)) / 2;
        int y1 = (panelHeight - buttonHeight) / 2 + 160;
        int x2 = x1 + buttonWidth + gap;

        PositionedButton button1 = new PositionedButton("camera", x1, y1, buttonWidth, buttonHeight, "src/project/image/camera.png");
        PositionedButton button2 = new PositionedButton("lens", x2, y1, buttonWidth, buttonHeight, "src/project/image/lens.png");
        
        
        button1.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(new Home1(frame));
            frame.revalidate();
        });

        button2.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(new Home2(frame));
            frame.revalidate();
        });
        
        // 버튼 추가 (ActionListener 없음)
        add(button1);
        add(button2);
    }

    private void addOutButton() {
        JButton outButton = new JButton("Out");
        outButton.setBounds(310, 730, 80, 25);
        outButton.setBackground(new Color(139, 169, 152));
        outButton.setForeground(Color.WHITE);
        outButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        outButton.addActionListener(e -> System.exit(0));
        add(outButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 배경색
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // 상단 사각형
        g.setColor(new Color(139, 169, 152));
        g.fillRect(0, 0, 800, 200);

        // 하단 사각형
        g.fillRect(150, 330, 400, 60);
        g.setColor(Color.BLACK);
        g.drawRect(150, 330, 400, 60);

        // 텍스트 그리기
        drawCenteredText(g, "화면을 터치하여 주문해보세요", new Font("SansSerif", Font.BOLD, 25), Color.WHITE, 350, 360);
        drawCenteredText(g, "Sony", new Font("SansSerif", Font.BOLD, 100), Color.WHITE, getWidth() / 2, 100);
        drawCenteredText(g, "Order Here", new Font("SansSerif", Font.BOLD, 100), new Color(139, 169, 152), getWidth() / 2, 250);
    }

    private void drawCenteredText(Graphics g, String text, Font font, Color color, int x, int y) {
        g.setColor(color);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        int textX = x - metrics.stringWidth(text) / 2;
        int textY = y + metrics.getAscent() - metrics.getHeight() / 2;
        g.drawString(text, textX, textY);
    }
}

/**
 * PositionedButton 클래스.
 * 둥근 테두리와 이미지를 적용한 커스텀 버튼입니다.
 */
class PositionedButton extends JButton {
    public PositionedButton(String text, int x, int y, int width, int height, String imagePath) {
        super(text);
        setBounds(x, y, width, height);
        setIcon(getScaledImageIcon(imagePath, (int) (width * 0.8), (int) (height * 0.8)));
        setHorizontalTextPosition(CENTER);
        setVerticalTextPosition(BOTTOM);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
    }

    private ImageIcon getScaledImageIcon(String path, int targetWidth, int targetHeight) {
        try {
            Image originalImage = new ImageIcon(path).getImage();
            BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resizedImage.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
            g2.dispose();
            return new ImageIcon(resizedImage);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);

        g2.dispose();
        super.paintComponent(g);
    }
}
