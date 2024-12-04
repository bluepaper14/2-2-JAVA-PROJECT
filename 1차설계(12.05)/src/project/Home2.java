package project;

import javax.swing.*;
import java.awt.*;

public class Home2 extends JPanel {
    public Home2(JFrame frame) {
        setLayout(null); // Absolute Layout 사용

        // 라벨
        JLabel label = new JLabel("Home2 화면입니다.", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 20));
        label.setBounds(150, 50, 400, 50); // 중앙에 위치하도록 설정
        add(label);

        // Home1로 이동하는 버튼 (정사각형, 왼쪽 상단)
        JButton toHome1Button = new JButton("Home1");
        toHome1Button.setBounds(10, 10, 100, 100); // 정사각형 크기와 위치 설정
        toHome1Button.addActionListener(e -> {
            frame.setContentPane(new Home1(frame));
            frame.revalidate();
        });
        add(toHome1Button);
    }
}
