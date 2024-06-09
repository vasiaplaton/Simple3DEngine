## Простой 3d движок, использующий только стандартную библиотеку java
# Проект 3D Движка
Проект 3D Движка - это простой и легкий в использовании движок для создания и визуализации трехмерных моделей.
Он разработан с использованием языка программирования Java. Использует только стандартную библиотеку.

![Screenshot 2024-06-09 at 22.16.51.png](readme_imgs%2FScreenshot%202024-06-09%20at%2022.16.51.png)

Демо работы https://youtu.be/dhZUX9ajUoo

## Простота использования

Проект предоставляет простой и интуитивно понятный API для создания и управления трехмерными моделями. Движок основан на языке Java, что делает его доступным для широкого круга разработчиков.

Пример использования:

```java
import Engine.Camera;
import Engine.Engine;
import Engine.ThreadEngine;
import Graphics3d.IModel;
import SolarSystem.SolarSystem;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainForm1 {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("MainForm");
        MainForm1 form = new MainForm1();
        frame.setContentPane(form.root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public MainForm1() throws IOException {
        // Создание камеры
        Camera camera = new Camera(900, 900, 90, 0.1, 10000000);

        // Создание движка
        Engine engine = new ThreadEngine(camera);

        // Создание модели
        IModel model = new IModel("models/monkey.obj", Color.GREEN);

        // Добавление модели к движку
        List<IModel> models = new ArrayList<>();
        models.add(model);
        engine.update(models);

        // Установка рисовальщика
        canvas.setImgDrawer(image -> {
            Graphics2D graphics2D = image.createGraphics();
            graphics2D.setClip(0, 0, image.getWidth(), image.getHeight());

            // Рендеринг моделей
            engine.drawOnCanvas(graphics2D);
        });

        // Настройка окна
        frame.setFocusable(true);
        frame.requestFocus();
    }

    // Дополнительные поля и методы опущены для краткости
}
```

![Screenshot 2024-06-09 at 22.15.58.png](readme_imgs%2FScreenshot%202024-06-09%20at%2022.15.58.png)

## Возможности

- Создание и управление трехмерными моделями.
- Визуализация моделей с помощью камеры и движка.
- Простота интеграции с существующими приложениями.

## Установка и использование

1. Клонируйте репозиторий или скопируйте исходный код.
2. Импортируйте проект в вашу любимую интегрированную среду разработки (IDE).
3. Соберите и запустите проект.

## Лицензия

Этот проект лицензируется под [лицензией MIT](LICENSE).

## Все диаграмы находятся в папке doc
![Engine.png](doc1%2FEngine.png)
