package Checkpoint4;

import java.awt.*;

public class Menu extends GameObject
{
    private GameState state;
    private Control control;

    private double totalTimeElapsed;

    public Menu(Control control, GameState state)
    {
        super();
        this.control = control;
        this.state = state;

        totalTimeElapsed = 0.0;
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.darkGray);
        g.fillRect(600,0, 200, 600);

        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.BOLD, 24));
        g.drawString("The Grid", 650, 30);

        g.setFont(new Font("arial", Font.PLAIN, 16));
        g.drawString("Lives: " + state.getLives(), 610, 60);
        g.drawString("Coins: " + state.getCoins(), 715, 60);
        g.drawString("Cost: 350", 730, 500);
        g.drawString("Cost: 200", 710, 420);

        g.setColor(new Color(0.5f, 0.8f, 1.0f));
        g.drawRoundRect(645,390, 60,70, 10,10);

        g.setColor(Color.GRAY);
        g.drawRoundRect(645, 470, 80,83, 10, 10);

        if (state.lives <= 0)
        {
            g.setColor(Color.WHITE);
            g.setFont(new Font("arial", Font.BOLD, 36));
            g.drawString("Game Over", 605, 300);
        }

        if (totalTimeElapsed > 110.0)
        {
            g.setColor(Color.WHITE);
            g.setFont(new Font("arial", Font.BOLD, 36));
            g.drawString("Congrats!", 605, 300);
            g.drawString("You Won", 610,350);
        }
    }

    public void update (double timeElapsed)
    {
        totalTimeElapsed += timeElapsed;
    }
}
