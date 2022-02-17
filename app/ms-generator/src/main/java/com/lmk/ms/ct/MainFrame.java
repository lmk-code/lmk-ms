package com.lmk.ms.ct;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

import com.lmk.ms.ct.code.config.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lmk.ms.ct.support.PropertyUtils;
import com.lmk.ms.ct.code.bean.Entity;
import com.lmk.ms.ct.support.AppProperties;
import com.lmk.ms.ct.support.CodeUtils;

/**
 * 主窗口
 * @author zhudefu
 * @email laomake@hotmail.com
 */
public class MainFrame {
    /** 日志记录器 */
    private static Logger log = LoggerFactory.getLogger(MainFrame.class);

    /** 全局字体大小 */
    private final Font defaultFont = new Font("Microsoft YaHei", Font.PLAIN, 15);

    /** 小号字体 */
    private final Font smallFont = new Font("Microsoft YaHei", Font.PLAIN, 13);

    /** 按钮字体大小 */
    private final Font btnFont = new Font("Microsoft YaHei", Font.PLAIN, 17);

    /** 单行文本输入框尺寸 */
    private final Dimension inputDimension = new Dimension(190, 26);

    /** 独占一行的单行文本输入框尺寸 */
    private final Dimension inputFullDimension = new Dimension(280, 26);

    /** 文本域尺寸 */
    private final Dimension areaDimension = new Dimension(560, 140);

    /** 系统配置参数 */
    private AppProperties appProperties;

    // 数据绑定组件
    private JTextField dbHostField;     // 数据库主机地址
    private JTextField dbPortField;     // 数据库段课
    private JTextField dbUserField;     // 数据库用户
    private JTextField dbPasswordField; // 数据库密码
    private JTextField dbDataBaseField; // 数据库名称
    private JTextField authorField;     // 作者
    private JTextField emailField;      // 邮箱
    private JTextField packageField;    // 基础包名
    private JTextField versionField;    // 版本号
    private JCheckBox moduleCheck;      // 将表名前缀解析为模块名称
    private JCheckBox m2oCheck;         // 生成多对一代码
    private JCheckBox m2mCheck;         // 生成多对多代码
    private JCheckBox o2mCheck;         // 生成一对多代码
    private JButton generateBtn;        // 生成代码按钮
    private JTextArea outArea;          // 日志输出框

    public void init() throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        /** 主窗体 */
        JFrame jFrame;

        // 创建窗体
        jFrame = new JFrame("代码生成器");

        // 设置风格样式
        String look = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"; // Windows风格
        UIManager.setLookAndFeel(look);

        // 设置关闭窗口时退出程序
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Image image= ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResource("config/img/logo.png")));
        jFrame.setIconImage(image);

        // 主面板
        JPanel mainPanel =new JPanel();

        // 设置组件
        setComponents(mainPanel);

        jFrame.add(mainPanel);

        // 设置尺寸
        jFrame.setSize(600, 600);
        jFrame.setResizable(false);


        // 自动窗口的大小，以适合其子组件的首选大小和布局
        // 单独使用setSize()时，不能使用pack()
        // jFrame.pack();

        // 设置居中显示
        jFrame.setLocationRelativeTo(null);

        // 显示窗体
        jFrame.setVisible(true);
    }

    /**
     * 设置组件
     * @param panel
     */
    private void setComponents(JPanel panel){
        Box box = Box.createVerticalBox();

        box.add(Box.createVerticalStrut(15));
        box.add(getDbPanel());

        box.add(Box.createVerticalStrut(15));
        box.add(getCodePanel());

        box.add(Box.createVerticalStrut(15));
        box.add(getActionPanel());
        initComponents();
        setListener();

        panel.add(box);
    }

    /**
     * 数据库参数面板
     * @return
     */
    private JPanel getDbPanel(){
        JPanel dbPanel = new JPanel();
        TitledBorder dbPanelBorder = new TitledBorder("数据库参数");
        dbPanelBorder.setTitleFont(smallFont);
        dbPanelBorder.setTitleColor(Color.GRAY);
        dbPanel.setBorder(dbPanelBorder);

        Box dbBox = Box.createVerticalBox();

        ////  第1行
        Box rowBox = Box.createHorizontalBox();
        // 1.主机IP
        JLabel dbHostLabel = new JLabel("主    机:");
        dbHostLabel.setFont(defaultFont);
        dbHostField = new JTextField();
        dbHostField.setFont(defaultFont);
        dbHostField.setPreferredSize(inputDimension);
        rowBox.add(dbHostLabel);
        rowBox.add(Box.createHorizontalStrut(10));
        rowBox.add(dbHostField);

        // 2.主机端口
        rowBox.add(Box.createHorizontalStrut(40));
        JLabel dbPortLabel = new JLabel("端    口:");
        dbPortLabel.setFont(defaultFont);
        dbPortField = new JTextField();
        dbPortField.setFont(defaultFont);
        dbPortField.setPreferredSize(inputDimension);
        rowBox.add(dbPortLabel);
        rowBox.add(Box.createHorizontalStrut(10));
        rowBox.add(dbPortField);

        dbBox.add(rowBox);
        dbBox.add(Box.createVerticalStrut(10));

        //// 第2行
        rowBox = Box.createHorizontalBox();
        // 3.用户名
        JLabel dbUserLabel = new JLabel("用    户:");
        dbUserLabel.setFont(defaultFont);
        dbUserField = new JTextField();
        dbUserField.setFont(defaultFont);
        dbUserField.setPreferredSize(inputDimension);
        rowBox.add(dbUserLabel);
        rowBox.add(Box.createHorizontalStrut(10));
        rowBox.add(dbUserField);

        // 4.密码
        rowBox.add(Box.createHorizontalStrut(40));
        JLabel dbPasswordLabel = new JLabel("密    码:");
        dbPasswordLabel.setFont(defaultFont);
        dbPasswordField = new JTextField();
        dbPasswordField.setFont(defaultFont);
        dbPasswordField.setPreferredSize(inputDimension);
        rowBox.add(dbPasswordLabel);
        rowBox.add(Box.createHorizontalStrut(10));
        rowBox.add(dbPasswordField);

        dbBox.add(rowBox);
        dbBox.add(Box.createVerticalStrut(10));

        //// 第3行
        rowBox = Box.createHorizontalBox();
        // 5.数据库
        JLabel dbDataBaseLabel = new JLabel("数据库:");
        dbDataBaseLabel.setFont(defaultFont);
        dbDataBaseField = new JTextField();
        dbDataBaseField.setFont(defaultFont);
        dbDataBaseField.setPreferredSize(inputFullDimension);
        rowBox.add(dbDataBaseLabel);
        rowBox.add(Box.createHorizontalStrut(10));
        rowBox.add(dbDataBaseField);

        dbBox.add(rowBox);
        dbBox.add(Box.createVerticalStrut(10));

        dbPanel.add(dbBox);
        return dbPanel;
    }

    /**
     * 代码生成面板
     * @return
     */
    private JPanel getCodePanel(){
        JPanel codePanel = new JPanel();
        TitledBorder codePanelBorder = new TitledBorder("源码参数");
        codePanelBorder.setTitleFont(smallFont);
        codePanelBorder.setTitleColor(Color.GRAY);
        codePanel.setBorder(codePanelBorder);

        Box dbBox = Box.createVerticalBox();

        ////  第1行
        Box rowBox = Box.createHorizontalBox();
        // 1.主机IP
        JLabel authorLabel = new JLabel("作    者:");
        authorLabel.setFont(defaultFont);
        authorField = new JTextField();
        authorField.setFont(defaultFont);
        authorField.setPreferredSize(inputDimension);
        rowBox.add(authorLabel);
        rowBox.add(Box.createHorizontalStrut(10));
        rowBox.add(authorField);

        // 2.邮箱
        rowBox.add(Box.createHorizontalStrut(40));
        JLabel emailLabel = new JLabel("邮    箱:");
        emailLabel.setFont(defaultFont);
        emailField = new JTextField();
        emailField.setFont(defaultFont);
        emailField.setPreferredSize(inputDimension);
        rowBox.add(emailLabel);
        rowBox.add(Box.createHorizontalStrut(10));
        rowBox.add(emailField);

        dbBox.add(rowBox);
        dbBox.add(Box.createVerticalStrut(10));

        //// 第2行
        rowBox = Box.createHorizontalBox();
        // 3.包名
        JLabel packageLabel = new JLabel("包    名:");
        packageLabel.setFont(defaultFont);
        packageField = new JTextField();
        packageField.setFont(defaultFont);
        packageField.setPreferredSize(inputDimension);
        rowBox.add(packageLabel);
        rowBox.add(Box.createHorizontalStrut(10));
        rowBox.add(packageField);

        // 4.版本
        rowBox.add(Box.createHorizontalStrut(40));
        JLabel versionLabel = new JLabel("版    本:");
        versionLabel.setFont(defaultFont);
        versionField = new JTextField();
        versionField.setFont(defaultFont);
        versionField.setPreferredSize(inputDimension);
        rowBox.add(versionLabel);
        rowBox.add(Box.createHorizontalStrut(10));
        rowBox.add(versionField);

        dbBox.add(rowBox);
        dbBox.add(Box.createVerticalStrut(20));

        //// 第3行
        rowBox = Box.createHorizontalBox();
        // 5.解析基础包名
        moduleCheck = new JCheckBox("解析模块");
        moduleCheck.setFocusPainted(false);
        moduleCheck.setFont(defaultFont);
        rowBox.add(moduleCheck);

        // 6.多对一
        rowBox.add(Box.createHorizontalStrut(40));
        m2oCheck = new JCheckBox("多对一");
        m2oCheck.setFocusPainted(false);
        m2oCheck.setFont(defaultFont);
        rowBox.add(m2oCheck);

        // 7.多对多
        rowBox.add(Box.createHorizontalStrut(40));
        m2mCheck = new JCheckBox("多对多");
        m2mCheck.setFocusPainted(false);
        m2mCheck.setFont(defaultFont);
        rowBox.add(m2mCheck);

        // 8.一对多
        rowBox.add(Box.createHorizontalStrut(40));
        o2mCheck = new JCheckBox("一对一");
        o2mCheck.setFocusPainted(false);
        o2mCheck.setFont(defaultFont);
        rowBox.add(o2mCheck);

        dbBox.add(rowBox);
        dbBox.add(Box.createVerticalStrut(10));

        codePanel.add(dbBox);
        return codePanel;
    }

    /**
     * 生成按钮面板
     * @return
     */
    private Box getActionPanel(){
        Box actionBox = Box.createVerticalBox();

        Box rowBox = Box.createHorizontalBox();
        generateBtn = new JButton("生 成 代 码");
        generateBtn.setFont(btnFont);
        generateBtn.setBackground(Color.WHITE);
        generateBtn.setFocusPainted(false);
        rowBox.add(generateBtn);

        actionBox.add(rowBox);
        actionBox.add(Box.createVerticalStrut(20));

        rowBox = Box.createHorizontalBox();
        outArea = new JTextArea();
        outArea.setPreferredSize(areaDimension);
        outArea.setFont(smallFont);
        // outArea.setEditable(false);
        DefaultCaret caret = (DefaultCaret)outArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.OUT_BOTTOM);

        JScrollPane scrollPane = new JScrollPane(outArea);
        scrollPane.setPreferredSize(new Dimension(560, 142));
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rowBox.add(scrollPane);
        actionBox.add(rowBox);

        return actionBox;
    }

    /**
     * 初始化组件数据
     */
    private void initComponents(){
        log.info("加载配置文件...");
        appProperties = new AppProperties();

        PropertyUtils pu = new PropertyUtils();
        pu.load(appProperties.getClassPathRoot() + "/config/app.properties");//加载配置文件

        //初始化数据库配置
        appProperties.setDbType(pu.getString("db.type")); // 咱们不支持手动更改

        appProperties.setDbHost(pu.getString("db.host"));
        appProperties.setDbPort(pu.getString("db.port"));
        appProperties.setDbUser(pu.getString("db.user"));
        appProperties.setDbPassword(pu.getString("db.password"));
        appProperties.setDbName(pu.getString("db.database"));

        appProperties.setAuthor(pu.getString("code.author"));
        appProperties.setEmail(pu.getString("code.email"));
        appProperties.setPackageName(pu.getString("code.packageName"));
        appProperties.setVersion(pu.getString("code.version"));

        appProperties.setParseModule(pu.getBoolean("code.tablePrefix", true));
        appProperties.setManyToOne(pu.getBoolean("code.manyToOne", false));
        appProperties.setManyToMany(pu.getBoolean("code.manyToMany", true));
        appProperties.setOneToMany(pu.getBoolean("code.oneToMany", false));

        dbHostField.setText(appProperties.getDbHost());
        dbPortField.setText(appProperties.getDbPort());
        dbUserField.setText(appProperties.getDbUser());
        dbPasswordField.setText(appProperties.getDbPassword());
        dbDataBaseField.setText(appProperties.getDbName());
        authorField.setText(appProperties.getAuthor());
        emailField.setText(appProperties.getEmail());
        packageField.setText(appProperties.getPackageName());
        versionField.setText(appProperties.getVersion());
        moduleCheck.setSelected(appProperties.getParseModule());
        m2oCheck.setSelected(appProperties.getManyToOne());
        m2mCheck.setSelected(appProperties.getManyToMany());
        o2mCheck.setSelected(appProperties.getOneToMany());
    }

    /**
     * 设置监听器
     */
    private void setListener(){
        generateBtn.addActionListener( e -> {
            appProperties.setDbHost(dbHostField.getText());
            appProperties.setDbPort(dbPortField.getText());
            appProperties.setDbUser(dbUserField.getText());
            appProperties.setDbPassword(dbPasswordField.getText());
            appProperties.setDbName(dbDataBaseField.getText());

            String urlTemplate = DbTools.getUrlTemplate(appProperties.getDbType());
            appProperties.setDbUrl(String.format(urlTemplate, appProperties.getDbHost(), appProperties.getDbPort(), appProperties.getDbName()));

            appProperties.setAuthor(authorField.getText());
            appProperties.setEmail(emailField.getText());
            appProperties.setPackageName(packageField.getText());
            appProperties.setVersion(versionField.getText());

            appProperties.setParseModule(moduleCheck.isSelected());
            appProperties.setManyToOne(m2oCheck.isSelected());
            appProperties.setManyToMany(m2mCheck.isSelected());
            appProperties.setOneToMany(o2mCheck.isSelected());

            try {
                generateCode();
                JOptionPane.showMessageDialog(null, "生成成功！", "生成代码", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception) {
                exception.printStackTrace();
                JOptionPane.showMessageDialog(null, "生成失败！", "生成代码", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * 输出日志
     * @param text
     */
    public void outputText(String text){
        if(StringUtils.isBlank(outArea.getText())){
            outArea.setText(text);
        }else{
            outArea.append("\n" + text);
        }
        outArea.setRows(outArea.getRows() + 1);
    }

    /**
     * 生成代码
     */
    private void generateCode(){
        // 初始化配置
        AppConfig.init(appProperties);

        //读取表名
        Map<String, String> tableMap = CodeUtils.getTableNames(this, appProperties.getDbName());

        // 解析表结构
        List<Entity> entitys = new ArrayList<Entity>();
        Set<String> modules = new HashSet<String>();
        Set<String> tableNames = tableMap.keySet();
        for(String tableName : tableNames){
            entitys.add(CodeUtils.parseEntity(this, tableName, tableMap.get(tableName), modules));
        }


        // 解析外键关联
        CodeUtils.parseForeignKey(this, entitys, appProperties.getDbName());

        // 初始化所有模块
        Map<String, Object> data = AppConfig.parseMoudle(modules);

        // 输出代码文件
        for(Entity entity : entitys){
            data.put("entity", entity);
            CodeUtils.makeFile(this, entity, data);
        }
    }
}
