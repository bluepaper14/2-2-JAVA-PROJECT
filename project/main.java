package project;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class main {
    public static void main(String[] args) {
        // Swing GUI는 Event Dispatch Thread에서 실행되어야 하므로 invokeLater 사용
        SwingUtilities.invokeLater(() -> new MainFrame("간단한 JFrame 예제"));
    }
}

class MainFrame extends JFrame {
    public MainFrame(String title) {
        super(title); // 부모 클래스(JFrame)의 생성자를 호출하여 제목 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫기 시 애플리케이션 종료
        setSize(700, 850); // 프레임 크기 설정
        setLocationRelativeTo(null); // 화면 중앙에 창을 표시

        // 사용자 정의 배경 패널 설정
        setContentPane(new CustomPanel());
        //음악재생
        play("resources/sound/jazzpianomusic.wav");
        // 메뉴바 추가
        setJMenuBar(createMenuBar());

        setVisible(true); // 프레임을 화면에 표시
    }

    private void play(String wavFile) {
        try {
            // JAR 내부 리소스 로드
            URL resourceUrl = getClass().getClassLoader().getResource(wavFile);
            if (resourceUrl == null) {
                System.err.println("오디오 파일을 찾을 수 없습니다: " + wavFile);
                return;
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(resourceUrl);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // 무한 반복 재생
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("오디오 재생 중 오류 발생: " + e.getMessage());
        }
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("카메라Tip");

        menu.add(createMenuItem("카메라 사용하는 방법!", "resources/text/camera_tip.txt"));
        menu.add(createMenuItem("렌즈 사용하는 방법!", "resources/text/lens_tip.txt"));

        menuBar.add(menu);
        return menuBar;
    }

    private JMenuItem createMenuItem(String itemName, String filePath) {
        JMenuItem menuItem = new JMenuItem(itemName);
        menuItem.addActionListener(e -> {
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
                if (inputStream == null) {
                    JOptionPane.showMessageDialog(this, "파일을 읽을 수 없습니다: " + filePath, "오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String content = new String(inputStream.readAllBytes());
                JOptionPane.showMessageDialog(this, content, itemName, JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "파일을 읽을 수 없습니다: " + filePath, "오류", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        return menuItem;
    }
}

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

        PositionedButton button1 = new PositionedButton("camera", x1, y1, buttonWidth, buttonHeight, "resources/image/camera.png");
        PositionedButton button2 = new PositionedButton("lens", x2, y1, buttonWidth, buttonHeight, "resources/image/lens.png");

        button1.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            SwingUtilities.invokeLater(() -> {
                frame.setContentPane(new Home1(frame));
                frame.revalidate();
            });
        });

        button2.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            SwingUtilities.invokeLater(() -> {
                frame.setContentPane(new Home2(frame));
                frame.revalidate();
            });
        });

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

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(new Color(139, 169, 152));
        g.fillRect(0, 0, 800, 200);

        g.fillRect(150, 330, 400, 60);
        g.setColor(Color.BLACK);
        g.drawRect(150, 330, 400, 60);

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
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                System.err.println("이미지 파일을 찾을 수 없습니다: " + path);
                return null;
            }

            Image originalImage = new ImageIcon(inputStream.readAllBytes()).getImage();
            BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resizedImage.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
            g2.dispose();
            return new ImageIcon(resizedImage);
        } catch (IOException e) {
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
