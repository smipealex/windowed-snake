package ru.smipealex;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.Vector;

public class Game {
    private final int                     FIELD_WIDTH;
    private final int                     FIELD_HEIGHT;
    private final int                     WINDOW_WIDTH; // 122
    private final int                     WINDOW_HEIGHT;
    private int                           _score               = 0;

    private MovementTo                    _vector              = MovementTo.STAY;
    private final Vector<GameObject>      _objects             = new Vector<>();

    private boolean                       _gameIsPlaying       = true;

    private final KeyHandler              _keyHandler          = new KeyHandler();

    private final JFrame                  _main;

    public Game() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Window Size(normal: 122,32 ; 20,20 - optimized): ");
        String data = scanner.nextLine();
        WINDOW_WIDTH = Integer.parseInt(data.split(",")[0]);
        WINDOW_HEIGHT = Integer.parseInt(data.split(",")[1]);
        System.out.print("Field Size(normal: 10,10 ; " + (Toolkit.getDefaultToolkit().getScreenSize().width / WINDOW_WIDTH) +
                "," + (Toolkit.getDefaultToolkit().getScreenSize().height / WINDOW_HEIGHT)+ " - optimized): ");
        data = scanner.nextLine();
        FIELD_WIDTH = Integer.parseInt(data.split(",")[0]);
        FIELD_HEIGHT = Integer.parseInt(data.split(",")[1]);
        System.out.print("Game speed(100): ");
        int _speed = scanner.nextInt();
        _main = new JFrame();
        JComponent _component = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setFont(new Font("Arial", Font.BOLD, 40));
                g.drawString("Score: " + _score, 10,50);
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.drawString("Field Size: " + FIELD_WIDTH + " " + FIELD_HEIGHT, 10, 100);
            }
        };
        _main.add(_component);
        _main.addKeyListener(_keyHandler);
        _main.setSize(200, 200);
        _main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        _main.setLocation(1000,500);
        _main.setVisible(true);

        int newApplePositionX = (int) (Math.random() * FIELD_WIDTH-1);
        int newApplePositionY = (int) (Math.random() * FIELD_HEIGHT-1);

        if(newApplePositionX == (FIELD_WIDTH/2-1)) newApplePositionX -= 1;
        if(newApplePositionY == (FIELD_HEIGHT/2-1)) newApplePositionY -= 1;

        Vector2 newApplePosition = new Vector2(newApplePositionX, newApplePositionY);

        Apple _apple = new Apple();
        _apple.initializeWindow(WINDOW_WIDTH, WINDOW_HEIGHT);
        _apple.set_position(newApplePosition);
        _objects.add(_apple);

        int newHeadPositionX = FIELD_WIDTH / 2;
        int newHeadPositionY = FIELD_HEIGHT / 2;

        Vector2 newHeadPosition = new Vector2(newHeadPositionX, newHeadPositionY);

        Tail head = new Tail(TailType.HEAD,null);
        head.initializeWindow(WINDOW_WIDTH, WINDOW_HEIGHT);
        head.set_position(newHeadPosition);

        _objects.add(head);

        while(_gameIsPlaying){
            update();
            Thread.sleep(_speed);
        }

        Thread.sleep(4000);
        for(GameObject obj : _objects){
            obj.closeWindow();
        }
    }

    private void checkKeys(){
        _vector = _keyHandler.getVector();
    }

    private void update(){
        checkKeys();
        logicUpdate();
    }

    private void logicUpdate(){
        Vector2 headPosition = new Vector2(0,0);
        for(int i = 0;i < _objects.size();i++){
            GameObject current = _objects.get(i);
            if(current.get_type() == GameObjectType.TAIL){
                Tail currentTail = (Tail) current;
                if(currentTail.getTailType() == TailType.HEAD){
                    /*
                    if _vector is LEFT or RIGHT, then if _vector is LEFT then movementX = -1 else 1, else  movementY = 0
                     */
                    if(currentTail.get_position().getX() == -1 || currentTail.get_position().getX() == FIELD_WIDTH){
                        _gameIsPlaying = false;
                        return;
                    }
                    if(currentTail.get_position().getY() == -1 || currentTail.get_position().getY() == FIELD_HEIGHT){
                        _gameIsPlaying = false;
                        return;
                    }
                    int movementX = _vector == MovementTo.LEFT || _vector == MovementTo.RIGHT ?
                            _vector == MovementTo.LEFT ? -1 : 1: 0;
                    int movementY = _vector == MovementTo.FORWARD || _vector == MovementTo.BACK ?
                            _vector == MovementTo.FORWARD ? -1 : 1 : 0;
                    int newPositionX = currentTail.get_position().getX() + movementX;
                    int newPositionY = currentTail.get_position().getY() + movementY;
                    currentTail.set_position(new Vector2(newPositionX, newPositionY));
                    headPosition = currentTail.get_position();

                    if(_objects.get(0).get_position().equals(currentTail.get_position())){
                        _score++;
                        _main.update(_main.getGraphics());
                        Tail tail = new Tail(TailType.TAIL, (Tail) _objects.get(_objects.size()-1));
                        tail.initializeWindow(WINDOW_WIDTH, WINDOW_HEIGHT);
                        _objects.add(tail);

                        int newApplePositionX = (int) (Math.random() * FIELD_WIDTH);
                        int newApplePositionY = (int) (Math.random() * FIELD_HEIGHT);

                        Vector2 newApplePosition = new Vector2(newApplePositionX, newApplePositionY);

                        _objects.get(0).set_position(newApplePosition);
                    }
                } else{
                    if(headPosition.equals(currentTail.get_position())){
                        _gameIsPlaying = false;
                        break;
                    }
                    currentTail.set_position(currentTail.getNextTail().get_lastPosition());
                }
                _main.toFront();
            }
        }
    }
}
