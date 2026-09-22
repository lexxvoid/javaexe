import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/** 随机抽取人 —— 希沃一体机 / Java 17 + Swing */
public class Main extends JFrame {

  // ==================== 名单（以后换成您的真实名单） ====================
  // ==================== 名单（52人） ====================
  private static final List<String> NAMES =
      new ArrayList<>(
          List.of(
              "洪梓木",
              "江宇恒",
              "江瑞林",
              "江承轩",
              "王东文",
              "姜梅朵",
              "葛振坤",
              "周子棋",
              "卢政嘉",
              "胡鹭洋",
              "姜星壕",
              "袁烁",
              "卢孝焱",
              "闵宇泽",
              "刘文特",
              "李振扬",
              "左欣怡",
              "张锦逸",
              "刘畅",
              "陈宣文",
              "徐勋铨",
              "周楷宸",
              "汪铭博",
              "陈诗语",
              "石依民",
              "胡子渔",
              "吴雨豪(豪情在天)",
              "王若骐",
              "涂云予",
              "李懿轩",
              "郭郑昊",
              "朱相橙",
              "蒋函汐",
              "贾智淋",
              "陈仪",
              "冯柯锦",
              "伐陈泽",
              "梁文昊",
              "傅立航",
              "周子涵",
              "胡俊熙",
              "董明睿",
              "柯铭轩",
              "黄凯熙",
              "李温雅",
              "罗子涵(机长)",
              "纪炫宇",
              "曹政",
              "郭沛辰",
              "王丽君",
              "吕学武",
              "赵紫祺"));
  // ==================================================
  // =====================================================================

  // 配色
  private static final Color BG_COLOR = new Color(0xFD, 0xE9, 0xC5);
  private static final Color BTN_COLOR = new Color(0xFF, 0xF9, 0xEC);
  private static final Color BTN_BORDER = new Color(0xF0, 0xDB, 0xC0);
  private static final Color BTN_TEXT = new Color(0x8B, 0x5E, 0x2C);
  private static final Color NAME_COLOR = new Color(0x4A, 0x2E, 0x0E);
  private static final Color SLOGAN_COLOR = new Color(0x7A, 0x4D, 0x1A);
  private static final Color COUNT_COLOR = new Color(0x9B, 0x7B, 0x5C);

  // 组件
  private JLabel nameLabel;
  private JLabel countLabel;
  private JButton drawButton;

  // 状态
  private int drawCount = 0;
  private final Random random = new Random();
  private Timer slideTimer;

  public Main() {
    setTitle("随机抽取人 · 希沃一体机");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setLocationRelativeTo(null);

    JPanel root = new JPanel(new GridBagLayout());
    root.setBackground(BG_COLOR);
    setContentPane(root);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.BOTH;

    // ==================== 上方区域（约 2/3） ====================
    JPanel topPanel = new JPanel(new GridBagLayout());
    topPanel.setOpaque(false);

    // 标语：占顶部一点点
    GridBagConstraints tc = new GridBagConstraints();
    tc.gridx = 0;
    tc.gridy = 0;
    tc.fill = GridBagConstraints.HORIZONTAL;
    tc.weightx = 1.0;
    tc.insets = new Insets(30, 20, 0, 20);

    JLabel slogan = new JLabel("（我来自那个高手如云的时代）", SwingConstants.CENTER);
    slogan.setFont(new Font("微软雅黑", Font.BOLD | Font.ITALIC, 40));
    slogan.setForeground(SLOGAN_COLOR);
    topPanel.add(slogan, tc);

    // 名字：占满剩余全部空间
    tc.gridy = 1;
    tc.weighty = 1.0;
    tc.fill = GridBagConstraints.BOTH;
    tc.insets = new Insets(0, 0, 0, 0);

    nameLabel = new JLabel(" ", SwingConstants.CENTER);
    nameLabel.setFont(new Font("微软雅黑", Font.BOLD, 300)); // 初始给一个超大字号
    nameLabel.setForeground(NAME_COLOR);
    nameLabel.setOpaque(false);
    nameLabel.setVerticalAlignment(SwingConstants.CENTER);
    topPanel.add(nameLabel, tc);

    gbc.gridy = 0;
    gbc.weighty = 2.0;
    root.add(topPanel, gbc);

    // ==================== 下方区域（约 1/3） ====================
    JPanel bottomPanel = new JPanel();
    bottomPanel.setOpaque(false);
    bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

    drawButton = new JButton("点我抽取");
    drawButton.setFont(new Font("微软雅黑", Font.BOLD, 80));
    drawButton.setForeground(BTN_TEXT);
    drawButton.setBackground(BTN_COLOR);
    drawButton.setOpaque(true);
    drawButton.setFocusPainted(false);

    drawButton.setBorder(
        BorderFactory.createCompoundBorder(
            new LineBorder(BTN_BORDER, 8), new EmptyBorder(25, 90, 25, 90)));

    drawButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    drawButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

    drawButton.addActionListener((ActionEvent e) -> onDraw());

    countLabel = new JLabel("已抽取 0 次", SwingConstants.CENTER);
    countLabel.setFont(new Font("微软雅黑", Font.PLAIN, 26));
    countLabel.setForeground(COUNT_COLOR);
    countLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    countLabel.setBorder(new EmptyBorder(20, 0, 40, 0));

    bottomPanel.add(Box.createVerticalGlue());
    bottomPanel.add(drawButton);
    bottomPanel.add(countLabel);
    bottomPanel.add(Box.createVerticalGlue());

    gbc.gridy = 1;
    gbc.weighty = 1.0;
    root.add(bottomPanel, gbc);
  }

  private void onDraw() {
    System.out.println("点击抽取，当前次数 = " + drawCount);

    if (NAMES.isEmpty()) {
      nameLabel.setText("暂无名单");
      return;
    }

    drawCount++;
    countLabel.setText("已抽取 " + drawCount + " 次");

    String name = NAMES.get(random.nextInt(NAMES.size()));
    slideNameIn(name);
  }

  private void slideNameIn(String name) {
    if (slideTimer != null && slideTimer.isRunning()) {
      slideTimer.stop();
    }

    // 关键：字号直接按屏幕大小算，尽量占满上方
    nameLabel.setFont(new Font("微软雅黑", Font.BOLD, pickFontSize(name)));
    nameLabel.setText(name);

    final int startOffset = 300;
    final int totalSteps = 22;
    final int delay = 15;
    final int[] step = {0};

    slideTimer =
        new Timer(
            delay,
            e -> {
              step[0]++;
              float t = Math.min(1f, step[0] / (float) totalSteps);
              float eased = easeOutBack(t);
              int offset = (int) (startOffset * (1 - eased));
              nameLabel.setBorder(new EmptyBorder(offset, 0, -offset, 0));

              if (step[0] >= totalSteps) {
                nameLabel.setBorder(new EmptyBorder(0, 0, 0, 0));
                slideTimer.stop();
              }
            });
    slideTimer.start();
  }

  /** 根据屏幕大小 + 名字长度，给出尽可能大的字号 让名字占满上方 2/3 空间，有张力 */
  private int pickFontSize(String name) {
    // 屏幕尺寸
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int screenH = screen.height;
    int screenW = screen.width;

    // 上方区域约占 2/3 高，名字要占其中的大部分
    // 高度上限：屏幕高 * 0.45（留出上下空间）
    int maxByHeight = (int) (screenH * 0.45);
    // 宽度上限：屏幕宽 * 0.9 除以字数
    int maxByWidth = (int) (screenW * 0.9 / Math.max(1, name.length()));

    int size = Math.min(maxByHeight, maxByWidth);

    // 再给一个硬上下限，避免太小或太大
    size = Math.max(size, 100); // 最小 100pt
    size = Math.min(size, 400); // 最大 400pt（再大也放不下）

    return size;
  }

  private static float easeOutBack(float t) {
    float c1 = 1.70158f;
    float c3 = c1 + 1;
    return 1 + c3 * (float) Math.pow(t - 1, 3) + c1 * (float) Math.pow(t - 1, 2);
  }

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ignored) {
    }

    SwingUtilities.invokeLater(
        () -> {
          Main frame = new Main();
          frame.setVisible(true);
        });
  }
}

