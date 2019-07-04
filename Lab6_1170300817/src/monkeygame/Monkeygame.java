package monkeygame;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import monkey.MonkeyGenerator;
import monkey.monkeyPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JScrollPane;

/**
 * Monkeygame继承自JFrame，通过windowbuilder设计， 主要是实现GUI，面板，按钮，按钮响应，参数文件读取
 */
public class Monkeygame extends JFrame {

  private int n;
  private int h;
  private int t;
  private int N;
  private int k;
  private int MV;

  private static final long serialVersionUID = 1L;
  private JPanel contentPane;

  /**
   * 启动应用
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          Monkeygame frame = new Monkeygame();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
  public Monkeygame() {
    final JFileChooser fileChooser = new JFileChooser();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 1038, 614);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);

    JButton chooseConfig = new JButton("选择配置文件");
    chooseConfig.setBounds(10, 184, 137, 23);
    chooseConfig.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        int returnvalue = fileChooser.showOpenDialog(contentPane);
        if (returnvalue == JFileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();

          try {
            readFile(file);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    });


    contentPane.setLayout(null);
    contentPane.add(chooseConfig);

    JTextArea textArea_data1 = new JTextArea();
    textArea_data1.setBounds(629, 9, 385, 74);
    contentPane.add(textArea_data1);
    JTextArea textArea_data2 = new JTextArea();
    textArea_data2.setBounds(629, 118, 385, 74);
    contentPane.add(textArea_data2);
    JScrollPane scrollPane_info = new JScrollPane();
    scrollPane_info.setBounds(221, 9, 364, 562);
    contentPane.add(scrollPane_info);

    JTextArea textArea_info = new JTextArea();
    textArea_info.setBounds(221, 45, 357, 526);
    scrollPane_info.add(textArea_info);
    scrollPane_info.setViewportView(textArea_info);

    JComboBox<String> comboBox = new JComboBox<String>();
    comboBox.setEditable(true);
    comboBox.setBounds(10, 98, 161, 23);
    comboBox.addItem("随机选择策略");
    comboBox.addItem("空梯子优先策略");
    comboBox.addItem("同向优先策略");

    contentPane.add(comboBox);
    ItemListener itemListener = new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent arg0) {
        if (ItemEvent.SELECTED == arg0.getStateChange()) {
        }
      }
    };
    comboBox.addItemListener(itemListener);
    JLabel label = new JLabel("选择策略");
    label.setBounds(10, 65, 81, 23);
    contentPane.add(label);

    JButton beginButton = new JButton("开始");
    beginButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        int strategyFlag = 0;
        String selectedItemString = (String) comboBox.getSelectedItem();
        System.out.println(selectedItemString);
        if (selectedItemString.equals("随机选择策略")) {
          strategyFlag = 0;
        } else if (selectedItemString.equals("空梯子优先策略")) {
          strategyFlag = 1;
        } else if (selectedItemString.equals("同向优先策略")) {
          strategyFlag = 2;
        }

        MonkeyGenerator generator =
            new MonkeyGenerator(n, h, N, t, k, MV, textArea_info, textArea_data1, strategyFlag);
        Thread generatorThread = new Thread(generator);
        generatorThread.start();

        JFrame monkeyFrame = new JFrame();
        monkeyPanel monketPanel = new monkeyPanel(n, h);
        monkeyFrame.getContentPane().add(monketPanel);
        monkeyFrame.setSize(1200, 800);
        monkeyFrame.setVisible(true);
      }
    });
    beginButton.setBounds(10, 372, 137, 23);
    contentPane.add(beginButton);


    JButton btnNewButton = new JButton("计算公平性");
    btnNewButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        MonkeyGenerator.getFairness(textArea_data2);
      }
    });
    btnNewButton.setBounds(10, 433, 137, 23);
    contentPane.add(btnNewButton);
    JFileChooser fileChooser_file = new JFileChooser();
    JButton button = new JButton("文件直接写入模式");
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int returnvalue2 = fileChooser_file.showOpenDialog(contentPane);
        if (returnvalue2 == JFileChooser.APPROVE_OPTION) {
          File file = fileChooser_file.getSelectedFile();

          try {
            Map<Integer, Set<MonkeyDataKeeper>> moneyMap = beginFromFile(file, textArea_info);
            MonkeyGenerator generator =
                new MonkeyGenerator(n, h, N, textArea_info, textArea_data1, moneyMap);
            Thread generatorThread = new Thread(generator);
            generatorThread.start();
          } catch (IOException | InterruptedException e1) {
            e1.printStackTrace();
          }
          JFrame monkeyFrame = new JFrame();
          monkeyPanel monketPanel = new monkeyPanel(n, h);
          monkeyFrame.getContentPane().add(monketPanel);
          monkeyFrame.setSize(1200, 800);
          monkeyFrame.setVisible(true);

        }

      }
    });
    button.setBounds(10, 289, 137, 23);
    contentPane.add(button);
  }


  /**
   * 读取参数文件的函数
   * 
   * @param file 参数文件
   * @throws IOException
   */
  public void readFile(File file) throws IOException {
    BufferedReader in = new BufferedReader(new FileReader(file));

    String fileline;
    String pattern_n = "n=(\\d+)";
    String pattern_h = "h=(\\d+)";
    String pattern_t = "t=(\\d+)";
    String pattern_N = "N=(\\d+)";
    String pattern_k = "k=(\\d+)";
    String pattern_MV = "MV=(\\d+)";

    while ((fileline = in.readLine()) != null) {
      Matcher pattern_nMatcher = Pattern.compile(pattern_n).matcher(fileline);
      Matcher pattern_hMatcher = Pattern.compile(pattern_h).matcher(fileline);
      Matcher pattern_tMatcher = Pattern.compile(pattern_t).matcher(fileline);
      Matcher pattern_NMatcher = Pattern.compile(pattern_N).matcher(fileline);
      Matcher pattern_kMatcher = Pattern.compile(pattern_k).matcher(fileline);
      Matcher pattern_MVMatcher = Pattern.compile(pattern_MV).matcher(fileline);
      if (pattern_nMatcher.find()) {
        n = Integer.valueOf(pattern_nMatcher.group(1));
      } else if (pattern_hMatcher.find()) {
        h = Integer.valueOf(pattern_hMatcher.group(1));
      } else if (pattern_tMatcher.find()) {
        t = Integer.valueOf(pattern_tMatcher.group(1));
      } else if (pattern_NMatcher.find()) {
        N = Integer.valueOf(pattern_NMatcher.group(1));
      } else if (pattern_kMatcher.find()) {
        k = Integer.valueOf(pattern_kMatcher.group(1));
      } else if (pattern_MVMatcher.find()) {
        MV = Integer.valueOf(pattern_MVMatcher.group(1));
      }
    }
    in.close();
  }

  public Map<Integer, Set<MonkeyDataKeeper>> beginFromFile(File file, JTextArea textArea_info)
      throws IOException, InterruptedException {
    BufferedReader in = new BufferedReader(new FileReader(file));
    String fileline;
    String pattern_n = "n=(\\d+)";
    String pattern_h = "h=(\\d+)";
    String pattern_monkey = "monkey=<([0-9]+),([0-9]+),(R->L|L->R),([1-9]+)>";
    Map<Integer, Set<MonkeyDataKeeper>> moneyMap = new HashMap<>();
    N = 0;
    while ((fileline = in.readLine()) != null) {
      Matcher Matcher_n = Pattern.compile(pattern_n).matcher(fileline);
      Matcher Matcher_h = Pattern.compile(pattern_h).matcher(fileline);
      Matcher Matcher_monkey = Pattern.compile(pattern_monkey).matcher(fileline);
      if (Matcher_n.find()) {
        n = Integer.valueOf(Matcher_n.group(1));
        System.out.println(n);
      } else if (Matcher_h.find()) {
        h = Integer.valueOf(Matcher_h.group(1));
        System.out.println(h);
      } else if (Matcher_monkey.find()) {
        N++;
        int bornTime = Integer.valueOf(Matcher_monkey.group(1));
        int id = Integer.valueOf(Matcher_monkey.group(2));
        String direction = Matcher_monkey.group(3);
        int velocity = Integer.valueOf(Matcher_monkey.group(4));
        MonkeyDataKeeper newDataKeeper = new MonkeyDataKeeper(bornTime, id, direction, velocity);
        if (moneyMap.containsKey(bornTime)) {
          moneyMap.get(bornTime).add(newDataKeeper);
        } else {
          Set<MonkeyDataKeeper> newSet = new HashSet<>();
          newSet.add(newDataKeeper);
          moneyMap.put(bornTime, newSet);
        }
      }
    }
    in.close();
    return moneyMap;
  }
}
