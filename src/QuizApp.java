
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

    JLabel questionLabel, progressLabel;
    JRadioButton[] buttons = new JRadioButton[4];
    ButtonGroup group;
    JButton nextButton;

    public QuizApp() {
        setTitle("Online Quiz Application");
        setSize(550, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        progressLabel = new JLabel("", JLabel.CENTER);
        questionLabel = new JLabel();

        questionLabel.setFont(new Font("Arial", Font.BOLD, 17));

        JPanel topPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        topPanel.add(progressLabel);
        topPanel.add(questionLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
        group = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            buttons[i] = new JRadioButton();
            group.add(buttons[i]);
            optionsPanel.add(buttons[i]);
        }

        add(optionsPanel, BorderLayout.CENTER);

        nextButton = new JButton("Next");
        nextButton.addActionListener(e -> checkAnswer());

        add(nextButton, BorderLayout.SOUTH);

        showQuestion();
        setVisible(true);
    }

    void showQuestion() {
        progressLabel.setText(
            "Question " + (index + 1) + " of " + questions.length
        );

        questionLabel.setText(questions[index]);

        for (int i = 0; i < 4; i++) {
            buttons[i].setText(options[index][i]);
        }

        group.clearSelection();

        nextButton.setText(
            index == questions.length - 1 ? "Submit" : "Next"
        );
    }

    void checkAnswer() {
        if (group.getSelection() == null) {
            JOptionPane.showMessageDialog(
                this, "Please select an answer!"
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
            int percentage = score * 100 / questions.length;

            JOptionPane.showMessageDialog(
                this,
                "Quiz Completed!\nScore: " + score + "/" + questions.length
                + "\nPercentage: " + percentage + "%"
            );

            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizApp());
    }
}
