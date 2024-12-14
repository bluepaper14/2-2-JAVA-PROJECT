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
import java.io.File;
import java.io.IOException;

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

/**
 * 애플리케이션의 진입점.
 * MainFrame 객체를 생성하여 프로그램을 시작합니다.
 */
public class main {
    public static void main(String[] args) {
        // Swing GUI는 Event Dispatch Thread에서 실행되어야 하므로 invokeLater 사용
        SwingUtilities.invokeLater(() -> new MainFrame("간단한 JFrame 예제"));
    }
}

/**
 * 메인 프레임 클래스.
 * 프로그램의 주요 윈도우 역할을 수행하며, 배경 패널(CustomPanel)과 메뉴바를 포함합니다.
 */
class MainFrame extends JFrame {
    /**
     * MainFrame 생성자.
     *
     * @param title JFrame의 제목
     */
    public MainFrame(String title) {
        super(title); // 부모 클래스(JFrame)의 생성자를 호출하여 제목 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫기 시 애플리케이션 종료
        setSize(700, 850); // 프레임 크기 설정
        setLocationRelativeTo(null); // 화면 중앙에 창을 표시

        // 사용자 정의 배경 패널 설정
        setContentPane(new CustomPanel());
        //음악재생
        play("sound/jazzpianomusic.wav");
        // 메뉴바 추가
        setJMenuBar(createMenuBar());

        setVisible(true); // 프레임을 화면에 표시
    }
    /*음악 재생하는 매서드
     * String wavFile 음악파일의 주소
     */
    private void play(String wavFile) {
    	try {
    		Clip clip=AudioSystem.getClip();//비어있는 오디오 클립
    		File audioFile=new File(wavFile);//오디오 파일의 경로명
    		//오디오 파일로부터 오디오스트림 가져옴
    		AudioInputStream audioStream=AudioSystem.getAudioInputStream(audioFile);
    		clip.open(audioStream);//재생할 오디오스트림 열기
    		clip.loop(Clip.LOOP_CONTINUOUSLY);//무한 재생
    		clip.start();//음악 시작
    	}
    	catch (UnsupportedAudioFileException e) {
            System.err.println("지원하지 않는 오디오 파일 형식입니다.");
        } catch (IOException e) {
            System.err.println("오디오 파일을 읽는 중 오류가 발생했습니다.");
        } catch (LineUnavailableException e) {
            System.err.println("오디오 라인을 열 수 없습니다.");
        }
    }
    /**
     * 메뉴바를 생성하고 반환하는 메서드.
     * 카메라와 렌즈 관련 팁을 제공하는 메뉴를 추가합니다.
     *
     * @return 생성된 JMenuBar 객체
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar(); // 메뉴바 생성
        JMenu menu = new JMenu("카메라Tip"); // 메뉴 생성

        // 메뉴 아이템 추가
        menu.add(createMenuItem("카메라 사용하는 방법!", "src/project/camera_tip.txt"));
        menu.add(createMenuItem("렌즈 사용하는 방법!", "src/project/lens_tip.txt"));

        menuBar.add(menu); // 메뉴를 메뉴바에 추가
        return menuBar;
    }

    /**
     * 파일 경로에서 내용을 읽어와 메뉴 아이템 클릭 시 팁을 표시하는 메서드.
     *
     * @param itemName 메뉴 아이템의 이름
     * @param filePath 텍스트 파일 경로
     * @return 생성된 JMenuItem 객체
     */
    private JMenuItem createMenuItem(String itemName, String filePath) {
        JMenuItem menuItem = new JMenuItem(itemName); // 메뉴 아이템 생성
        menuItem.addActionListener(e -> {
            try {
                // 파일에서 텍스트 내용을 읽음
                String content = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
                // 팁 내용을 다이얼로그로 표시
                JOptionPane.showMessageDialog(
                    this,
                    content,
                    itemName,
                    JOptionPane.INFORMATION_MESSAGE
                );
            } catch (Exception ex) {
                // 파일 읽기 실패 시 오류 메시지 출력
                JOptionPane.showMessageDialog(
                    this,
                    "파일을 읽을 수 없습니다: " + filePath,
                    "오류",
                    JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
            }
        });
        return menuItem;
    }
}

/**
 * 사용자 정의 패널 클래스.
 * 화면의 배경 및 구성 요소(버튼, 텍스트 등)를 설정합니다.
 */
class CustomPanel extends JPanel {
    public CustomPanel() {
        setLayout(null); // 절대 레이아웃 사용
        addButtons(); // 버튼 추가
        addOutButton(); // "Out" 버튼 추가
    }

    /**
     * 화면 중앙에 두 개의 버튼을 추가합니다.
     * 버튼 클릭 시 패널을 전환합니다.
     */
    private void addButtons() {
        int panelWidth = 700;
        int panelHeight = 800;
        int buttonWidth = 200;
        int buttonHeight = 250;
        int gap = 40;

        // 버튼 위치 계산
        int x1 = (panelWidth - (buttonWidth * 2 + gap)) / 2;
        int y1 = (panelHeight - buttonHeight) / 2 + 160;
        int x2 = x1 + buttonWidth + gap;

        // 버튼 생성
        PositionedButton button1 = new PositionedButton("camera", x1, y1, buttonWidth, buttonHeight, "src/project/image/camera.png");
        PositionedButton button2 = new PositionedButton("lens", x2, y1, buttonWidth, buttonHeight, "src/project/image/lens.png");

        // 버튼 클릭 시 동작 정의
        button1.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this); // 부모 프레임 참조
            SwingUtilities.invokeLater(() -> {
                frame.setContentPane(new Home1(frame)); // Home1 패널로 전환
                frame.revalidate();
            });
        });

        button2.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            SwingUtilities.invokeLater(() -> {
                frame.setContentPane(new Home2(frame)); // Home2 패널로 전환
                frame.revalidate();
            });
        });

        // 패널에 버튼 추가
        add(button1);
        add(button2);
    }

    /**
     * 화면 하단에 "Out" 버튼을 추가하여 프로그램 종료 기능을 제공합니다.
     */
    private void addOutButton() {
        JButton outButton = new JButton("Out"); // "Out" 버튼 생성
        outButton.setBounds(310, 730, 80, 25); // 버튼 위치 및 크기 설정
        outButton.setBackground(new Color(139, 169, 152)); // 버튼 배경색
        outButton.setForeground(Color.WHITE); // 텍스트 색상
        outButton.setFont(new Font("SansSerif", Font.BOLD, 12)); // 텍스트 폰트
        outButton.addActionListener(e -> System.exit(0)); // 버튼 클릭 시 애플리케이션 종료
        add(outButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 배경 색상
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // 상단 헤더 배경
        g.setColor(new Color(139, 169, 152));
        g.fillRect(0, 0, 800, 200);

        // 하단 텍스트 박스 배경
        g.fillRect(150, 330, 400, 60);
        g.setColor(Color.BLACK);
        g.drawRect(150, 330, 400, 60);

        // 텍스트 추가
        drawCenteredText(g, "화면을 터치하여 주문해보세요", new Font("SansSerif", Font.BOLD, 25), Color.WHITE, 350, 360);
        drawCenteredText(g, "Sony", new Font("SansSerif", Font.BOLD, 100), Color.WHITE, getWidth() / 2, 100);
        drawCenteredText(g, "Order Here", new Font("SansSerif", Font.BOLD, 100), new Color(139, 169, 152), getWidth() / 2, 250);
    }

    /**
     * 텍스트를 중앙에 정렬하여 그립니다.
     *
     * @param g      Graphics 객체
     * @param text   그릴 텍스트
     * @param font   텍스트 폰트
     * @param color  텍스트 색상
     * @param x      텍스트 기준 x 좌표
     * @param y      텍스트 기준 y 좌표
     */
    private void drawCenteredText(Graphics g, String text, Font font, Color color, int x, int y) {
        g.setColor(color);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        int textX = x - metrics.stringWidth(text) / 2; // 텍스트 중앙 정렬
        int textY = y + metrics.getAscent() - metrics.getHeight() / 2; // 텍스트 세로 중앙 정렬
        g.drawString(text, textX, textY);
    }
}

/**
 * PositionedButton 클래스.
 * 둥근 테두리와 이미지를 포함한 커스텀 버튼을 생성합니다.
 */
class PositionedButton extends JButton {
    public PositionedButton(String text, int x, int y, int width, int height, String imagePath) {
        super(text); // 버튼 텍스트 설정
        setBounds(x, y, width, height); // 버튼 위치 및 크기 설정
        setIcon(getScaledImageIcon(imagePath, (int) (width * 0.8), (int) (height * 0.8))); // 이미지 아이콘 설정
        setHorizontalTextPosition(CENTER); // 텍스트 위치
        setVerticalTextPosition(BOTTOM); // 텍스트 위치
        setContentAreaFilled(false); // 버튼 배경 제거
        setFocusPainted(false); // 포커스 표시 제거
        setBorderPainted(false); // 테두리 제거
    }

    /**
     * 이미지 경로에서 지정된 크기로 스케일링된 ImageIcon을 반환합니다.
     *
     * @param path        이미지 경로
     * @param targetWidth 스케일링할 폭
     * @param targetHeight 스케일링할 높이
     * @return ImageIcon 객체
     */
    private ImageIcon getScaledImageIcon(String path, int targetWidth, int targetHeight) {
        try {
            Image originalImage = new ImageIcon(path).getImage(); // 원본 이미지 로드
            BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB); // 크기 변경
            Graphics2D g2 = resizedImage.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
            g2.dispose();
            return new ImageIcon(resizedImage);
        } catch (Exception e) {
            e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
            return null; // 기본적으로 null 반환
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create(); // 그래픽 객체 생성
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 안티앨리어싱 활성화

        // 버튼 배경
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        // 버튼 테두리
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);

        g2.dispose(); // 그래픽 객체 해제
        super.paintComponent(g); // 기본 버튼 그리기
    }
}
