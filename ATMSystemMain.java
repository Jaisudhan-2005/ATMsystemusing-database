import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;

class BankAccount {
    private final String accountNumber;
    private final String accountHolderName;
    private double balance;
    private String pin;
    
    public BankAccount(String accountNumber, String accountHolderName, double initialBalance, String pin) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.pin = pin;
    }
  
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String getAccountHolderName() {
        return accountHolderName;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public String getPin() {
        return pin;
    }
    
    public void setPin(String pin) {
        this.pin = pin;
    }
 
    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
    
    public boolean hasSufficientBalance(double amount) {
        return balance >= amount;
    }
}

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class ATMSystemMain extends JFrame implements ActionListener {
    private final HashMap<String, BankAccount> accounts;
    private BankAccount currentAccount;
    
    private JPanel loginPanel;
    private JPanel mainPanel;
    private JTextField accountField;
    private JPasswordField pinField;
    private JLabel statusLabel;
    private JLabel balanceLabel;
    private JLabel welcomeLabel;
    
    private JButton loginBtn;
    private JButton balanceBtn;
    private JButton withdrawBtn;
    private JButton depositBtn;
    private JButton statementBtn;
    private JButton changePinBtn;
    private JButton logoutBtn;
    private JButton exitBtn;
    
    private static final double MIN_WITHDRAWAL_AMOUNT = 100.0;
    private static final double MAX_WITHDRAWAL_AMOUNT = 10000.0;
    private static final double MIN_DEPOSIT_AMOUNT = 10.0;
    private static final double MAX_DEPOSIT_AMOUNT = 50000.0;
    
    public ATMSystemMain() {
        accounts = new HashMap<>();
        initializeAccounts();
        initializeGUI();
    }
    
    private void initializeAccounts() {
        accounts.put("123456789", new BankAccount("123456789", "JAI SUDHAN", 501.0, "1234"));
        accounts.put("987654321", new BankAccount("987654321", "RONALDO", 75002200999999.0, "5678"));
        accounts.put("555666777", new BankAccount("555666777", "MAHI", 12888575420.0, "9999"));
    }
    
    private void initializeGUI() {
        setTitle("Secure Bank ATM System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        createLoginPanel();
        createMainPanel();
        
        setContentPane(loginPanel);
        setVisible(true);
    }
    
    private void createLoginPanel() {
        loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBackground(new Color(236, 240, 241));
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setPreferredSize(new Dimension(800, 100));
        
        JLabel titleLabel = new JLabel("SECURE BANK ATM SERVICES");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel);
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel loginTitle = new JLabel("USER AUTHENTICATION");
        loginTitle.setFont(new Font("Arial", Font.BOLD, 18));
        loginTitle.setForeground(new Color(41, 128, 185));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        formPanel.add(loginTitle, gbc);
        
        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        formPanel.add(new JLabel("Account Number:"), gbc);
        
        accountField = new JTextField(15);
        accountField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        formPanel.add(accountField, gbc);
        
        gbc.gridy = 2; gbc.gridx = 0;
        formPanel.add(new JLabel("PIN:"), gbc);
        
        pinField = new JPasswordField(15);
        pinField.setFont(new Font("Arial", Font.PLAIN, 14));
        pinField.addActionListener(this);
        gbc.gridx = 1;
        formPanel.add(pinField, gbc);
        
        loginBtn = new JButton("LOGIN");
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.setBackground(new Color(39, 174, 96));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.addActionListener(this);
        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        formPanel.add(loginBtn, gbc);
        
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 4;
        formPanel.add(statusLabel, gbc);
        
        loginPanel.add(headerPanel, BorderLayout.NORTH);
        loginPanel.add(formPanel, BorderLayout.CENTER);
    }
    
    private void createMainPanel() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(236, 240, 241));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setPreferredSize(new Dimension(800, 80));
        
        welcomeLabel = new JLabel();
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        balanceLabel = new JLabel();
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        headerPanel.add(balanceLabel, BorderLayout.EAST);
        
        JPanel buttonPanel = new JPanel(new GridLayout(3, 3, 20, 20));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        balanceBtn = createStyledButton("CHECK BALANCE");
        withdrawBtn = createStyledButton("WITHDRAW MONEY");
        depositBtn = createStyledButton("DEPOSIT MONEY");
        statementBtn = createStyledButton("MINI STATEMENT");
        changePinBtn = createStyledButton("CHANGE PIN");
        logoutBtn = createStyledButton("LOGOUT");
        exitBtn = createStyledButton("EXIT");
        
        balanceBtn.addActionListener(this);
        withdrawBtn.addActionListener(this);
        depositBtn.addActionListener(this);
        statementBtn.addActionListener(this);
        changePinBtn.addActionListener(this);
        logoutBtn.addActionListener(this);
        exitBtn.addActionListener(this);
        
        buttonPanel.add(balanceBtn);
        buttonPanel.add(withdrawBtn);
        buttonPanel.add(depositBtn);
        buttonPanel.add(statementBtn);
        buttonPanel.add(changePinBtn);
        buttonPanel.add(new JLabel());
        buttonPanel.add(logoutBtn);
        buttonPanel.add(exitBtn);
        buttonPanel.add(new JLabel());
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setPreferredSize(new Dimension(150, 50));
        return button;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == loginBtn || source == pinField) {
            handleLogin();
        } else if (source == balanceBtn) {
            showBalance();
        } else if (source == withdrawBtn) {
            withdrawMoney();
        } else if (source == depositBtn) {
            depositMoney();
        } else if (source == statementBtn) {
            showStatement();
        } else if (source == changePinBtn) {
            changePin();
        } else if (source == logoutBtn) {
            logout();
        } else if (source == exitBtn) {
            exitApp();
        }
    }
    
    private void handleLogin() {
        String accountNum = accountField.getText().trim();
        String pin = new String(pinField.getPassword());
        
        if (accountNum.isEmpty() || pin.isEmpty()) {
            showMessage("Please enter both account number and PIN", Color.RED);
            return;
        }
        
        BankAccount account = accounts.get(accountNum);
        if (account != null && account.getPin().equals(pin)) {
            currentAccount = account;
            showMessage("Login successful!", Color.GREEN);
            
            Timer timer = new Timer(1000, e -> switchToMainScreen());
            timer.setRepeats(false);
            timer.start();
        } else {
            showMessage("Invalid account number or PIN!", Color.RED);
            pinField.setText("");
        }
    }
    
    private void switchToMainScreen() {
        welcomeLabel.setText("Welcome, " + currentAccount.getAccountHolderName());
        updateBalanceDisplay();
        
        setContentPane(mainPanel);
        revalidate();
        repaint();
    }
    
    private void updateBalanceDisplay() {
        balanceLabel.setText("Balance: Rs." + String.format("%.2f", currentAccount.getBalance()));
    }
    
    private void showBalance() {
        String msg = """
                    Account Information:
                    
                    Account Number: %s
                    Account Holder: %s
                    Current Balance: Rs.%.2f""".formatted(
                    maskAccount(currentAccount.getAccountNumber()),
                    currentAccount.getAccountHolderName(),
                    currentAccount.getBalance());
        
        JOptionPane.showMessageDialog(this, msg, "Balance Inquiry", JOptionPane.INFORMATION_MESSAGE);
        updateBalanceDisplay();
    }
    
    private void withdrawMoney() {
        String input = JOptionPane.showInputDialog(this,
            """
            Current Balance: Rs.%.2f
            Withdrawal Limits: Rs.%.0f - Rs.%.0f
            
            Enter withdrawal amount:""".formatted(
            currentAccount.getBalance(), MIN_WITHDRAWAL_AMOUNT, MAX_WITHDRAWAL_AMOUNT),
            "Cash Withdrawal", JOptionPane.QUESTION_MESSAGE);
        
        if (input != null && !input.trim().isEmpty()) {
            try {
                double amount = Double.parseDouble(input.trim());
                
                if (amount < MIN_WITHDRAWAL_AMOUNT) {
                    JOptionPane.showMessageDialog(this, "Minimum withdrawal amount is Rs." + MIN_WITHDRAWAL_AMOUNT, 
                        "Error", JOptionPane.ERROR_MESSAGE);
                } else if (amount > MAX_WITHDRAWAL_AMOUNT) {
                    JOptionPane.showMessageDialog(this, "Maximum withdrawal amount is Rs." + MAX_WITHDRAWAL_AMOUNT, 
                        "Error", JOptionPane.ERROR_MESSAGE);
                } else if (amount % 100 != 0) {
                    JOptionPane.showMessageDialog(this, "Please enter amount in multiples of Rs.100", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!currentAccount.hasSufficientBalance(amount)) {
                    JOptionPane.showMessageDialog(this, "Insufficient balance!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    currentAccount.withdraw(amount);
                    JOptionPane.showMessageDialog(this, 
                        """
                        Withdrawal Successful!
                        
                        Amount: Rs.%.2f
                        Remaining Balance: Rs.%.2f
                        
                        Please collect your cash!""".formatted(amount, currentAccount.getBalance()),
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    updateBalanceDisplay();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void depositMoney() {
        String input = JOptionPane.showInputDialog(this,
            """
            Current Balance: Rs.%.2f
            Deposit Limits: Rs.%.0f - Rs.%.0f
            
            Enter deposit amount:""".formatted(
            currentAccount.getBalance(), MIN_DEPOSIT_AMOUNT, MAX_DEPOSIT_AMOUNT),
            "Cash Deposit", JOptionPane.QUESTION_MESSAGE);
        
        if (input != null && !input.trim().isEmpty()) {
            try {
                double amount = Double.parseDouble(input.trim());
                
                if (amount < MIN_DEPOSIT_AMOUNT) {
                    JOptionPane.showMessageDialog(this, "Minimum deposit amount is Rs." + MIN_DEPOSIT_AMOUNT, 
                        "Error", JOptionPane.ERROR_MESSAGE);
                } else if (amount > MAX_DEPOSIT_AMOUNT) {
                    JOptionPane.showMessageDialog(this, "Maximum deposit amount is Rs." + MAX_DEPOSIT_AMOUNT, 
                        "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    currentAccount.deposit(amount);
                    JOptionPane.showMessageDialog(this, 
                        """
                        Deposit Successful!
                        
                        Amount: Rs.%.2f
                        New Balance: Rs.%.2f
                        
                        Please collect your receipt!""".formatted(amount, currentAccount.getBalance()),
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    updateBalanceDisplay();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showStatement() {
        String statement = """
                          Mini Statement
                          
                          Account Number: %s
                          Account Holder: %s
                          Current Balance: Rs.%.2f
                          
                          Transaction Information:
                          Last Login: Today
                          Account Status: Active
                          Daily Limit: Rs.30,000""".formatted(
                          maskAccount(currentAccount.getAccountNumber()),
                          currentAccount.getAccountHolderName(),
                          currentAccount.getBalance());
        
        JTextArea textArea = new JTextArea(statement);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(350, 250));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Mini Statement", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void changePin() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        
        JPasswordField currentPinField = new JPasswordField();
        JPasswordField newPinField = new JPasswordField();
        JPasswordField confirmPinField = new JPasswordField();
        
        panel.add(new JLabel("Current PIN:"));
        panel.add(currentPinField);
        panel.add(new JLabel("New PIN:"));
        panel.add(newPinField);
        panel.add(new JLabel("Confirm PIN:"));
        panel.add(confirmPinField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Change PIN", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String currentPin = new String(currentPinField.getPassword());
            String newPin = new String(newPinField.getPassword());
            String confirmPin = new String(confirmPinField.getPassword());
            
            if (!currentPin.equals(currentAccount.getPin())) {
                JOptionPane.showMessageDialog(this, "Current PIN is incorrect!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            } else if (newPin.length() != 4 || !newPin.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "PIN must be exactly 4 digits!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!newPin.equals(confirmPin)) {
                JOptionPane.showMessageDialog(this, "New PINs do not match!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                currentAccount.setPin(newPin);
                JOptionPane.showMessageDialog(this, "PIN changed successfully!\nPlease remember your new PIN.", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void logout() {
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", 
            "Logout", JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            currentAccount = null;
            accountField.setText("");
            pinField.setText("");
            statusLabel.setText(" ");
            
            setContentPane(loginPanel);
            revalidate();
            repaint();
            
            JOptionPane.showMessageDialog(this, "Thank you for using our ATM service!", 
                "Logged Out", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void exitApp() {
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", 
            "Exit", JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Thank you for banking with us!\nHave a great day!", 
                "Goodbye", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
    
    private String maskAccount(String accountNumber) {
        if (accountNumber.length() <= 4) return accountNumber;
        return "****" + accountNumber.substring(accountNumber.length() - 4);
    }
    
    private void showMessage(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ATMSystemMain());
    }
}