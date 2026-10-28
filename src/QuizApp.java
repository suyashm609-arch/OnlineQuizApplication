
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class QuizApp extends JFrame {

    
String[] questions = {
    "Which language is used for Android development?",
    "Which keyword creates an object in Java?",
    "Which data type stores true or false?",
    "Which method is the starting point of Java?",
    "Which symbol ends a Java statement?",
    "Which keyword is used to inherit a class?",
    "Which loop runs while a condition is true?",
    "Which collection stores unique elements?",
    "Which keyword is used to create a subclass object?",
    "Which company developed Java?"
};

String[][] options = {
    {"HTML", "Java", "CSS", "SQL"},
    {"class", "new", "void", "static"},
    {"int", "String", "boolean", "char"},
    {"start()", "run()", "main()", "begin()"},
    {".", ";", ":", ","},
    {"this", "super", "extends", "implements"},
    {"for", "while", "switch", "break"},
    {"ArrayList", "Array", "HashSet", "String"},
    {"new", "class", "extends", "static"},
    {"Google", "Microsoft", "Sun Microsystems", "Apple"}
};

int[] answers = {1, 1, 2, 2, 1, 2, 1, 2, 0, 2};


    int index = 0;
    int score = 0;
    int highScore = loadHighScore();

    Color blue = new Color(37, 99, 235);
    Color background = new Color(245, 248, 255);

   
JLabel questionLabel, progressLabel, timerLabel;
JRadioButton[] buttons = new JRadioButton[4];
ButtonGroup group;
JButton nextButton;

JProgressBar progressBar;

Timer timer;
int timeLeft = 15;
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

        
timerLabel = new JLabel("Time left: 15 seconds", JLabel.CENTER);
timerLabel.setFont(new Font("Arial", Font.BOLD, 15));
timerLabel.setForeground(blue);

progressBar = new JProgressBar(0, questions.length);
progressBar.setValue(0);
progressBar.setStringPainted(true);
progressBar.setString("Quiz Progress");
progressBar.setForeground(blue);
progressBar.setBackground(Color.WHITE);

JPanel statusPanel = new JPanel(new GridLayout(2, 1, 5, 5));
statusPanel.setBackground(Color.WHITE);
statusPanel.setBorder(
    BorderFactory.createEmptyBorder(10, 25, 10, 25)
);
statusPanel.add(timerLabel);
statusPanel.add(progressBar);

add(statusPanel, BorderLayout.SOUTH);


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
centerPanel.add(statusPanel, BorderLayout.SOUTH);

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
        add(bottomPanel, BorderLayout.PAGE_END);

        showWelcomeScreen();
        setVisible(true);
    }

    
void showWelcomeScreen() {
    getContentPane().removeAll();
    setLayout(new BorderLayout());

    JLabel title = new JLabel("ONLINE QUIZ", JLabel.CENTER);
    title.setFont(new Font("Arial", Font.BOLD, 28));
    title.setForeground(Color.WHITE);
    title.setOpaque(true);
    title.setBackground(blue);
    title.setBorder(
        BorderFactory.createEmptyBorder(25, 10, 25, 10)
    );

    JLabel welcome = new JLabel(
        "<html><center>Welcome to the Quiz!<br><br>"
        + "Test your knowledge and challenge yourself."
        + "</center></html>",
        JLabel.CENTER
    );
    welcome.setFont(new Font("Arial", Font.BOLD, 20));
    welcome.setForeground(new Color(30, 41, 59));

    JLabel instructions = new JLabel(
        "<html><center>"
        + "Total Questions: " + questions.length
        + "<br><br>Time: 15 seconds per question"
        + "<br><br>Choose the correct option and finish the quiz!"
        + "</center></html>",
        JLabel.CENTER
    );
    instructions.setFont(new Font("Arial", Font.PLAIN, 15));
    instructions.setForeground(new Color(71, 85, 105));

    JButton startButton = new JButton("START QUIZ");
    startButton.setFont(new Font("Arial", Font.BOLD, 17));
    startButton.setForeground(Color.WHITE);
    startButton.setBackground(blue);
    startButton.setFocusPainted(false);
    startButton.setOpaque(true);
    startButton.setBorderPainted(false);
    startButton.setBorder(
        BorderFactory.createEmptyBorder(14, 35, 14, 35)
    );

    
startButton.addActionListener(e -> {
    index = 0;
    score = 0;

    getContentPane().removeAll();
    buildQuizScreen();
    showQuestion();
});


    JPanel center = new JPanel(new GridLayout(2, 1, 10, 10));
    center.setBackground(background);
    center.setBorder(
        BorderFactory.createEmptyBorder(25, 20, 25, 20)
    );
    center.add(welcome);
    center.add(instructions);

    JPanel bottom = new JPanel();
    bottom.setBackground(Color.WHITE);
    bottom.setBorder(
        BorderFactory.createEmptyBorder(15, 10, 15, 10)
    );
    bottom.add(startButton);

    add(title, BorderLayout.NORTH);
    add(center, BorderLayout.CENTER);
    add(bottom, BorderLayout.SOUTH);

    revalidate();
    repaint();
}


void buildQuizScreen() {
    setLayout(new BorderLayout());

    JLabel header = new JLabel(
        "ONLINE QUIZ APPLICATION", JLabel.CENTER
    );
    header.setFont(new Font("Arial", Font.BOLD, 21));
    header.setForeground(Color.WHITE);
    header.setOpaque(true);
    header.setBackground(blue);
    header.setBorder(
        BorderFactory.createEmptyBorder(20, 10, 20, 10)
    );

    JPanel questionPanel = new JPanel(
        new GridLayout(2, 1, 5, 12)
    );
    questionPanel.setBackground(background);
    questionPanel.setBorder(
        BorderFactory.createEmptyBorder(20, 25, 10, 25)
    );
    questionPanel.add(progressLabel);
    questionPanel.add(questionLabel);

    JPanel optionsPanel = new JPanel(
        new GridLayout(4, 1, 8, 8)
    );
    optionsPanel.setBackground(background);
    optionsPanel.setBorder(
        BorderFactory.createEmptyBorder(5, 25, 15, 25)
    );

    
for (int i = 0; i < 4; i++) {
    buttons[i].setBackground(Color.WHITE);
    buttons[i].setForeground(new Color(40, 60, 90));
    buttons[i].setFocusPainted(false);
    buttons[i].setBorder(
        BorderFactory.createLineBorder(new Color(37, 99, 235), 2)
    );

    buttons[i].setHorizontalAlignment(SwingConstants.LEFT);
buttons[i].setMargin(new Insets(12, 15, 12, 15));
buttons[i].setOpaque(true);

    optionsPanel.add(buttons[i]);
}


    JPanel centerPanel = new JPanel(new BorderLayout());
    centerPanel.setBackground(background);
    centerPanel.add(questionPanel, BorderLayout.NORTH);
    centerPanel.add(optionsPanel, BorderLayout.CENTER);

    JPanel statusPanel = new JPanel(
        new GridLayout(2, 1, 5, 5)
    );
    statusPanel.setBackground(Color.WHITE);
    statusPanel.setBorder(
        BorderFactory.createEmptyBorder(10, 25, 10, 25)
    );
    statusPanel.add(timerLabel);
    statusPanel.add(progressBar);

    centerPanel.add(statusPanel, BorderLayout.SOUTH);

    JPanel bottomPanel = new JPanel();
    bottomPanel.setBackground(Color.WHITE);
    bottomPanel.add(nextButton);

    add(header, BorderLayout.NORTH);
    add(centerPanel, BorderLayout.CENTER);
    add(bottomPanel, BorderLayout.SOUTH);

    revalidate();
    repaint();
}



    void showQuestion() {
        progressLabel.setText(
            "QUESTION " + (index + 1) + " OF " + questions.length
        );

        progressBar.setValue(index + 1);
        progressBar.setString(
    "Progress: " + (index + 1) + "/" + questions.length
);

        questionLabel.setText(questions[index]);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 20));

        for (int i = 0; i < 4; i++) {
            buttons[i].setText(options[index][i]);
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 16));
        }

        group.clearSelection();

        
timeLeft = 15;
timerLabel.setText("Time left: " + timeLeft + " seconds");
progressBar.setValue(index);
progressBar.setString(
    "Progress: " + index + "/" + questions.length
);

if (timer != null) {
    timer.stop();
}

timer = new Timer(1000, e -> {
    timeLeft--;

    timerLabel.setText("Time left: " + timeLeft + " seconds");

    if (timeLeft <= 0) {
        timer.stop();

        JOptionPane.showMessageDialog(
            this,
            "Time is up!",
            "Timer",
            JOptionPane.INFORMATION_MESSAGE
        );

        index++;

        if (index < questions.length) {
            showQuestion();
        } else {
            showResult();
        }
    }
});

timer.start();


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
    if (buttons[i].isSelected()) {
        if (i == answers[index]) {
            score++;
            JOptionPane.showMessageDialog(
                this, "Correct Answer! 🎉");
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Incorrect Answer!\nCorrect answer: "
                + options[index][answers[index]]);
        }
        break;
    }
}


        timer.stop();

        index++;

        if (index < questions.length) {
            showQuestion();
        } else {
            showResult();
        }
    }

    
int loadHighScore() {
    try {
        File file = new File("highscore.txt");

        if (file.exists()) {
            Scanner sc = new Scanner(file);
            int savedScore = sc.nextInt();
            sc.close();
            return savedScore;
        }
    } catch (IOException e) {
        System.out.println("Error loading high score.");
    }

    return 0;
}



void saveHighScore() {
    try {
        FileWriter writer = new FileWriter("highscore.txt");
        writer.write(String.valueOf(highScore));
        writer.close();
    } catch (IOException e) {
        System.out.println("Error saving high score.");
    }
}


    void showResult() {
        if (timer != null) {
    timer.stop();
}
        int percentage = score * 100 / questions.length;
        if (score > highScore) {
    highScore = score;
    saveHighScore();
}

        String message;

if (percentage >= 80) {
    message = "Excellent! Great job!";
} else if (percentage >= 60) {
    message = "Good job! Keep it up!";
} else if (percentage >= 40) {
    message = "Nice try! Keep practicing!";
} else {
    message = "Keep learning! Try again!";
}

       
JOptionPane.showMessageDialog(
    this,
    "🏆 Quiz Completed!\n\n"
    + "Your Score: " + score + "/" + questions.length
   
+ "\nPercentage: " + percentage + "%"
+ "\nHigh Score: " + highScore + "/" + questions.length
+ "\n\n" + message,

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
