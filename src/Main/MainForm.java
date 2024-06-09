package Main;

import Time.FPSCounter;
import Graphics2d.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Graphics3d.Vec3D;
import SolarSystem.AstronomicBodies.AstronomicBody;
import SolarSystem.SolarSystem;

public class MainForm {
    private JPanel root;
    private JPanel draw;
    private DrawPanel canvas;
    private JRadioButton animation;
    private JSlider speedSlider;
    private JButton generateNewButton;
    private JButton removeButton;
    private JComboBox<AstronomicBody> bodies;
    private JSlider trajectoryLen;
    long prevTime;

    final SolarSystem world = new SolarSystem();

    private Vec2<Integer> currentPos = new Vec2<>(0, 0);
    private Vec2<Integer> oldMousePos = new Vec2<>(0, 0);

    private FPSCounter fpsCounter = new FPSCounter();

    private int minDelta = 5;

    public enum Direction{
        FORWARD,
        BACK,
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    protected Vec3D getVectorByDir(Direction d, double module){
        Vec3D forward = world.camera.lookAt().normalized().multiple(module);
        switch (d){
            case BACK -> forward = forward.rotate(new Vec3D(-Math.PI, 0.0, 0.0));
            case LEFT -> forward = forward.rotate(new Vec3D(0.0, Math.PI/2, 0.0));
            case RIGHT -> forward = forward.rotate(new Vec3D(0.0, -Math.PI/2, 0.0));
            case UP -> forward = forward.rotate(new Vec3D(-Math.PI/2, 0.0, 0.0));
            case DOWN -> forward = forward.rotate(new Vec3D(Math.PI/2, 0.0, 0.0));
        }
        return forward;
    }


    public MainForm() {
        prevTime = System.currentTimeMillis();
        updateListBodies();

        root.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                double d = 0.7;
                if(e.getKeyChar() == 'w') {
                    world.camera.translate(getVectorByDir(Direction.FORWARD, d));
                }
                if(e.getKeyChar() == 's') {
                    world.camera.translate(getVectorByDir(Direction.BACK, d));
                }
                if(e.getKeyChar() == 'a') {
                    world.camera.translate(getVectorByDir(Direction.LEFT, d));
                }
                if(e.getKeyChar() == 'd') {
                    world.camera.translate(getVectorByDir(Direction.RIGHT, d));
                }
                if(e.getKeyChar() == 'q') {
                    world.camera.translate(getVectorByDir(Direction.UP, d));
                }
                if(e.getKeyChar() == 'z') {
                    world.camera.translate(getVectorByDir(Direction.DOWN, d));
                }

            }
        });

        root.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                oldMousePos = getMouseVector(e);
                update();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                root.requestFocus();
                update();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                update();
            }
        });
        root.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Vec2<Integer> now = getMouseVector(e);

                // Vec2<Integer> delta = new Vec2<>(now.x-oldMousePos.x, now.y-oldMousePos.y);

                Vec3D newPos = new Vec3D(-now.y.doubleValue(), now.x.doubleValue(), 0.0);
                Vec3D oldPos = new Vec3D(-oldMousePos.y.doubleValue(), oldMousePos.x.doubleValue(), 0.0);


                world.camera.rotate(newPos.minus(oldPos).multiple(0.007));

                oldMousePos = now;
                currentPos = now;
                update();
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                update();
            }
        });
        root.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double d = e.getWheelRotation() * 0.7;
                world.camera.translate(getVectorByDir(Direction.FORWARD, d));
                update();
            }
        });


        canvas.setImgDrawer(image -> {
            Graphics2D graphics2D = image.createGraphics();
            graphics2D.setClip(0, 0, image.getWidth(), image.getHeight());

            world.updateandDrawOnCanvas(graphics2D, true);
            fpsCounter.tick();
            graphics2D.setColor(Color.WHITE);
            graphics2D.setFont(new Font("Monospaced", Font.PLAIN, 16)); ;
            graphics2D.drawString("FPS: " + fpsCounter.getFPS(), 10, 30);
            graphics2D.drawString("projected: " + world.engine.getTrianglesAmount(), 10, 70);
        });


        root.setFocusable(true);
        root.requestFocus();

        removeButton.addActionListener(e -> {
            AstronomicBody toRemove = (AstronomicBody) bodies.getSelectedItem();
            world.bodies.remove(toRemove);
            updateListBodies();
            System.out.println(toRemove);
        });
        generateNewButton.addActionListener(e -> {
            BodyCreator bodyCreator = new BodyCreator(world.bodies);
            bodyCreator.pack();
            bodyCreator.setVisible(true);

            AstronomicBody body = bodyCreator.body;
            if(body != null) {
                world.bodies.add(body);
            }
            updateListBodies();
        });
    }

    public Vec2<Integer> getMouseVector(MouseEvent e) {
        return new Vec2<>(e.getX(), e.getY());
    }

    public void update(){
        canvas.setFocusable(true);
        //root.requestFocus();
        long currTime = System.currentTimeMillis();

        if(currTime - prevTime > minDelta) {
            int millisLasted = (int) (currTime - prevTime);
            prevTime = currTime;


            if(animation.isSelected()){
                world.nextStep(millisLasted / 10.0 * speedSlider.getValue() / 700.0, trajectoryLen.getValue() / 180.0 * Math.PI);
            }


            SwingUtilities.updateComponentTreeUI(canvas);
        }
    }

    public void updateListBodies(){
        bodies.setModel(new DefaultComboBoxModel<>(world.bodies.toArray(new AstronomicBody[0])));
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("MainForm");
        MainForm form = new MainForm();
        frame.setContentPane(form.root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        while (true) {
            form.update();
        }
        //ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        //executor.scheduleAtFixedRate(form::update, 0, 10, TimeUnit.MILLISECONDS);
    }

    private void createUIComponents() {
        canvas = new DrawPanel();


        draw = canvas;

        bodies = new JComboBox<>();
    }
}
