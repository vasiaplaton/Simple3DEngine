package Graphics3d;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The {@code IModel} class represents a 3D model consisting of a list of triangles.
 * It provides methods to load a model from an OBJ file and to set the color of the model.
 */
public class IModel extends Object {
    /**
     * List of triangles that make up the 3D model.
     */
    public List<Triangle> list = new ArrayList<>();

    /**
     * Constructs an empty {@code IModel}.
     */
    public IModel(){}

    /**
     * Constructs an {@code IModel} from an OBJ file.
     * The model is loaded from the specified file path, and each triangle is assigned the specified color.
     *
     * @param objPath the path to the OBJ file
     * @param color the color to assign to each triangle
     * @throws IOException if an I/O error occurs
     */
    public IModel(String objPath, Color color) throws IOException {
        BufferedReader scanner = new BufferedReader(new FileReader(objPath));

        String stringLine = scanner.readLine();

        List<Vec4D> vertexes = new ArrayList<>();

        while (stringLine != null) {
            String[] line = stringLine.split(" ");

            if (Objects.equals(line[0], "v")) {
                double x = Double.parseDouble(line[1]);
                double y = Double.parseDouble(line[2]);
                double z = Double.parseDouble(line[3]);
                vertexes.add(new Vec4D(x, y, z, 1.0));
            }

            if (Objects.equals(line[0], "f")) {
                int a = Integer.parseInt(line[1]);
                int b = Integer.parseInt(line[2]);
                int c = Integer.parseInt(line[3]);
                list.add(new Triangle(vertexes.get(a - 1), vertexes.get(b - 1), vertexes.get(c - 1), color));
            }

            // read next line
            stringLine = scanner.readLine();
        }
        scanner.close();
    }

    /**
     * Sets the color of all triangles in the model.
     *
     * @param color the new color to assign to each triangle
     */
    public void setColor(Color color){
        List<Triangle> newList = new ArrayList<>();
        for (Triangle tri : list) {
            newList.add(new Triangle(tri.getPoint(0), tri.getPoint(1), tri.getPoint(2), color));
        }
        list = newList;
    }
}
