
import javax.swing.*;
import java.awt.*;

public class QuizApp extends JFrame {

    String[] questions = {
        "Which language is used for Android development?",
        "Which keyword creates an object in Java?",
        "Which data type stores true or false?",
        "Which method is the starting point of Java?"
    };

    String[][] options = {
        {"HTML", "Java", "CSS", "SQL"},
        {"class", "new", "void", "static"},
        {"int", "String", "boolean", "char"},
        {"start()", "run()", "main()", "begin()"}
    };

    int[] answers = {1, 1, 2, 2};

    int index = 0;
    int score = 0;

    Color blue = new Color(37, 99, 235);
    Color background = new Color(245, 248, 255);

    JLabel questionLabel, progressLabel;
    JRadioButton[] buttons = new JRadioButton[4];
    ButtonGroup group;
    JButton nextButton;

    public QuizApp() {
        setTitle("Online Quiz Application");
        setSize(600, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(background);

        JLabel header = new JLabel(
            "ONLINE QUIZ APPLICATION", JLabel.CENTER
        );
        header.setFont(new Font("Arial", Font.BOLD, 21));
        header.setForeground(Color.WHITE);
        header.setOpaque(true);
        header.setBackground(blue);
        header.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(header, BorderLayout.NORTH);

        progressLabel = new JLabel("", JLabel.LEFT);
        progressLabel.setForeground(blue);
        progressLabel.setFont(new Font("Arial", Font.BOLD, 14));

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        questionLabel.setForeground(new Color(30, 41, 59));

        JPanel questionPanel = new JPanel(new GridLayout(2, 1, 5, 12));
        questionPanel.setBackground(background);
        questionPanel.setBorder(
            BorderFactory.createEmptyBorder(20, 25, 10, 25)
        );
        questionPanel.add(progressLabel);
        questionPanel.add(questionLabel);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 8, 8));
        optionsPanel.setBackground(background);
        optionsPanel.setBorder(
            BorderFactory.createEmptyBorder(5, 25, 15, 25)
        );

        group = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            buttons[i] = new JRadioButton();
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            buttons[i].setBackground(Color.WHITE);
            buttons[i].setFocusPainted(false);
            buttons[i].setBorder(
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
            );
            group.add(buttons[i]);
            optionsPanel.add(buttons[i]);
        }

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(background);
        centerPanel.add(questionPanel, BorderLayout.NORTH);
        centerPanel.add(optionsPanel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        nextButton = new JButton("Next Question");
        nextButton.setFont(new Font("Arial", Font.BOLD, 15));
        nextButton.setForeground(Color.WHITE);
        nextButton.setBackground(blue);
        nextButton.setFocusPainted(false);
        nextButton.setOpaque(true);
        nextButton.setBorderPainted(false);
        nextButton.setBorder(
            BorderFactory.createEmptyBorder(12, 25, 12, 25)
        );
        nextButton.addActionListener(e -> checkAnswer());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
        bottomPanel.add(nextButton);
        add(bottomPanel, BorderLayout.SOUTH);

        showQuestion();
        setVisible(true);
    }

    void showQuestion() {
        progressLabel.setText(
            "QUESTION " + (index + 1) + " OF " + questions.length
        );

        questionLabel.setText(questions[index]);

        for (int i = 0; i < 4; i++) {
            buttons[i].setText(options[index][i]);
        }

        group.clearSelection();

        nextButton.setText(
            index == questions.length - 1 ? "Finish Quiz" : "Next Question"
        );
    }

    void checkAnswer() {
        if (group.getSelection() == null) {
            JOptionPane.showMessageDialog(
                this,
                "Please select an answer first!",
                "No Answer Selected",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        for (int i = 0; i < 4; i++) {
            if (buttons[i].isSelected() && i == answers[index]) {
                score++;
            }
        }

        index++;

        if (index < questions.length) {
            showQuestion();
        } else {
            showResult();
        }
    }

    void showResult() {
        int percentage = score * 100 / questions.length;

        JOptionPane.showMessageDialog(
            this,
            "Quiz Completed!\n\n"
            + "Your Score: " + score + "/" + questions.length
            + "\nPercentage: " + percentage + "%",
            "Quiz Results",
            JOptionPane.INFORMATION_MESSAGE
        );

        int choice = JOptionPane.showConfirmDialog(
            this,
            "Would you like to try again?",
            "Restart Quiz",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            index = 0;
            score = 0;
            showQuestion();
        } else {
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizApp());
    }
}
