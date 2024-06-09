package Math;

import Graphics3d.Vec3D;
import Graphics3d.Vec4D;

/**
 * The {@code Matrix4} class represents a 4x4 matrix and provides methods for common matrix operations.
 * It is used in 3D graphics transformations, including scaling, translation, and rotation.
 */
public class Matrix4 {

    /**
     * The 4x4 matrix represented as a 2D array.
     */
    public final double[][] arr = new double[4][4];

    /**
     * Constructs a {@code Matrix4} with all elements initialized to the given value.
     *
     * @param val the value to initialize all elements of the matrix.
     */
    public Matrix4(double val){
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                arr[x][y] = val;
            }
        }
    }

    /**
     * Returns a zero matrix.
     *
     * @return a new {@code Matrix4} initialized to zero.
     */
    public static Matrix4 zero(){
        return new Matrix4(0);
    }

    /**
     * Returns an identity matrix.
     *
     * @return a new {@code Matrix4} representing the identity matrix.
     */
    public static Matrix4 identity() {
        Matrix4 result = Matrix4.zero();

        result.arr[0][0] = 1.0;
        result.arr[1][1] = 1.0;
        result.arr[2][2] = 1.0;
        result.arr[3][3] = 1.0;

        return result;
    }

    /**
     * Multiplies this matrix by another matrix.
     *
     * @param matrix the matrix to multiply with.
     * @return the result of the matrix multiplication.
     */
    public Matrix4 multiple(Matrix4 matrix){
        Matrix4 result = Matrix4.zero();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    result.arr[i][j] += arr[i][k] * matrix.arr[k][j];
                }
            }
        }
        return result;
    }

    /**
     * Multiplies this matrix by a {@code Vec3D} vector.
     *
     * @param vec the vector to multiply with.
     * @return the result of the matrix-vector multiplication.
     */
    public Vec3D multiple(Vec3D vec) {
        return new Vec3D(
                arr[0][0] * vec.x + arr[0][1] * vec.y + arr[0][2] * vec.z,
                arr[1][0] * vec.x + arr[1][1] * vec.y + arr[1][2] * vec.z,
                arr[2][0] * vec.x + arr[2][1] * vec.y + arr[2][2] * vec.z
        );
    }

    /**
     * Multiplies this matrix by a {@code Vec4D} point.
     *
     * @param point4D the point to multiply with.
     * @return the result of the matrix-point multiplication.
     */
    public Vec4D multiple(Vec4D point4D) {
        return new Vec4D(
                arr[0][0] * point4D.x + arr[0][1] * point4D.y + arr[0][2] * point4D.z + arr[0][3] * point4D.w,
                arr[1][0] * point4D.x + arr[1][1] * point4D.y + arr[1][2] * point4D.z + arr[1][3] * point4D.w,
                arr[2][0] * point4D.x + arr[2][1] * point4D.y + arr[2][2] * point4D.z + arr[2][3] * point4D.w,
                arr[3][0] * point4D.x + arr[3][1] * point4D.y + arr[3][2] * point4D.z + arr[3][3] * point4D.w
        );
    }

    /**
     * Creates a scaling matrix.
     *
     * @param factor the vector representing the scaling factors for each axis.
     * @return a new {@code Matrix4} representing the scaling transformation.
     */
    public static Matrix4 scale(Vec3D factor){
        Matrix4 result = Matrix4.zero();
        result.arr[0][0] = factor.x;
        result.arr[1][1] = factor.y;
        result.arr[2][2] = factor.z;
        result.arr[3][3] = 1;
        return result;
    }

    /**
     * Creates a translation matrix.
     *
     * @param v the vector representing the translation for each axis.
     * @return a new {@code Matrix4} representing the translation transformation.
     */
    public static Matrix4 translation(Vec3D v) {
        Matrix4 t = Matrix4.zero();
        t.arr[0][0] = 1.0;
        t.arr[1][1] = 1.0;
        t.arr[2][2] = 1.0;
        t.arr[3][3] = 1.0;

        t.arr[0][3] = v.x;
        t.arr[1][3] = v.y;
        t.arr[2][3] = v.z;

        return t;
    }

    /**
     * Creates a rotation matrix around the X-axis.
     *
     * @param rx the angle of rotation in radians.
     * @return a new {@code Matrix4} representing the rotation transformation.
     */
    public static Matrix4 rotationX(double rx){
        Matrix4 Rx = Matrix4.zero();

        double c = Math.cos(rx), s = Math.sin(rx);

        Rx.arr[0][0] = 1.0;

        Rx.arr[1][1] = c;
        Rx.arr[1][2] = -s;
        Rx.arr[2][1] = s;
        Rx.arr[2][2] = c;

        Rx.arr[3][3] = 1.0;

        return Rx;
    }

    /**
     * Creates a rotation matrix around the Y-axis.
     *
     * @param ry the angle of rotation in radians.
     * @return a new {@code Matrix4} representing the rotation transformation.
     */
    public static Matrix4 rotationY(double ry){
        Matrix4 Ry = Matrix4.zero();

        double c = Math.cos(ry), s = Math.sin(ry);

        Ry.arr[1][1] = 1.0;

        Ry.arr[0][0] = c;
        Ry.arr[0][2] = s;
        Ry.arr[2][0] = -s;
        Ry.arr[2][2] = c;

        Ry.arr[3][3] = 1.0;

        return Ry;
    }

    /**
     * Creates a rotation matrix around the Z-axis.
     *
     * @param rz the angle of rotation in radians.
     * @return a new {@code Matrix4} representing the rotation transformation.
     */
    public static Matrix4 rotationZ(double rz){
        Matrix4 Rz = Matrix4.zero();

        double c = Math.cos(rz), s = Math.sin(rz);

        Rz.arr[2][2] = 1.0;

        Rz.arr[0][0] = c;
        Rz.arr[0][1] = -s;
        Rz.arr[1][0] = s;
        Rz.arr[1][1] = c;

        Rz.arr[3][3] = 1.0;

        return Rz;
    }

    /**
     * Creates a rotation matrix for arbitrary axis rotation.
     *
     * @param r the vector representing the axis of rotation.
     * @return a new {@code Matrix4} representing the rotation transformation.
     */
    public static Matrix4 rotation(Vec3D r) {
        return rotationX(r.x).multiple(rotationY(r.y)).multiple(rotationZ(r.z));
    }

    /**
     * Creates a rotation matrix around an arbitrary axis.
     *
     * @param v  the vector representing the axis of rotation.
     * @param rv the angle of rotation in radians.
     * @return a new {@code Matrix4} representing the rotation transformation.
     */
    public static Matrix4 rotation(Vec3D v, double rv) {
        Matrix4 Rv = Matrix4.zero();
        Vec3D nv = v.normalized();

        double c = Math.cos(rv), s = Math.sin(rv);

        Rv.arr[0][0] = c + (1.0 - c) * nv.x * nv.x;
        Rv.arr[0][1] = (1.0 - c) * nv.x * nv.y - s * nv.z;
        Rv.arr[0][2] = (1.0 - c) * nv.x * nv.z + s * nv.y;

        Rv.arr[1][0] = (1.0 - c) * nv.x * nv.y + s * nv.z;
        Rv.arr[1][1] = c + (1.0 - c) * nv.y * nv.y;
        Rv.arr[1][2] = (1.0 - c) * nv.y * nv.z - s * nv.x;

        Rv.arr[2][0] = (1.0 - c) * nv.z * nv.x - s * nv.y;
        Rv.arr[2][1] = (1.0 - c) * nv.z * nv.y + s * nv.x;
        Rv.arr[2][2] = c + (1.0 - c) * nv.z * nv.z;

        Rv.arr[3][3] = 1.0;

        return Rv;
    }

    /**
     * Creates a projection matrix for a perspective projection.
     *
     * @param fov    the field of view angle in degrees.
     * @param aspect the aspect ratio of the viewport.
     * @param ZNear  the distance to the near clipping plane.
     * @param ZFar   the distance to the far clipping plane.
     * @return a new {@code Matrix4} representing the projection transformation.
     */
    public static Matrix4 projection(double fov, double aspect, double ZNear, double ZFar) {
        Matrix4 p = Matrix4.zero();

        p.arr[0][0] = 1.0 / (Math.tan(Const.PI * fov * 0.5 / 180.0) * aspect);
        p.arr[1][1] = 1.0 / Math.tan(Const.PI * fov * 0.5 / 180.0);
        p.arr[2][2] = ZFar / (ZFar - ZNear);
        p.arr[2][3] = -ZFar * ZNear / (ZFar - ZNear);
        p.arr[3][2] = 1.0;

        return p;
    }

    /**
     * Creates a screen space matrix for transforming normalized device coordinates to screen coordinates.
     *
     * @param width  the width of the viewport.
     * @param height the height of the viewport.
     * @return a new {@code Matrix4} representing the screen space transformation.
     */
    public static Matrix4 screenSpace(int width, int height) {
        Matrix4 s = Matrix4.zero();

        s.arr[0][0] = -0.5 * width;
        s.arr[1][1] = -0.5 * height;
        s.arr[2][2] = 1.0;

        s.arr[0][3] = 0.5 * width;
        s.arr[1][3] = 0.5 * height;

        s.arr[3][3] = 1.0;

        return s;
    }

    /**
     * Creates a view matrix from a given transformation matrix.
     *
     * @param transformMatrix the transformation matrix representing the view.
     * @return a new {@code Matrix4} representing the view transformation.
     */
    public static Matrix4 view(Matrix4 transformMatrix){
        Matrix4 V = Matrix4.zero();

        Vec3D left      = transformMatrix.x();
        Vec3D up        = transformMatrix.y();
        Vec3D lookAt    = transformMatrix.z();
        Vec3D eye       = transformMatrix.w();

        double left_sqrAbs      = left.sqrAbs();
        double up_sqrAbs        = up.sqrAbs();
        double lookAt_sqrAbs    = lookAt.sqrAbs();

        V.arr[0][0] = left.x/left_sqrAbs;
        V.arr[0][1] = left.y/left_sqrAbs;
        V.arr[0][2] = left.z/left_sqrAbs;
        V.arr[0][3] = -eye.dot(left)/left_sqrAbs;

        V.arr[1][0] = up.x/up_sqrAbs;
        V.arr[1][1] = up.y/up_sqrAbs;
        V.arr[1][2] = up.z/up_sqrAbs;
        V.arr[1][3] = -eye.dot(up)/up_sqrAbs;

        V.arr[2][0] = lookAt.x/lookAt_sqrAbs;
        V.arr[2][1] = lookAt.y/lookAt_sqrAbs;
        V.arr[2][2] = lookAt.z/lookAt_sqrAbs;
        V.arr[2][3] = -eye.dot(lookAt)/lookAt_sqrAbs;

        V.arr[3][3] = 1.0;

        return V;
    }

    /**
     * Gets the X-axis vector of this matrix.
     *
     * @return the X-axis vector represented as a {@code Vec3D}.
     */
    public Vec3D x(){
        return new Vec3D(arr[0][0], arr[1][0], arr[2][0]);
    }

    /**
     * Gets the Y-axis vector of this matrix.
     *
     * @return the Y-axis vector represented as a {@code Vec3D}.
     */
    public Vec3D y(){
        return new Vec3D(arr[0][1], arr[1][1], arr[2][1]);
    }

    /**
     * Gets the Z-axis vector of this matrix.
     *
     * @return the Z-axis vector represented as a {@code Vec3D}.
     */
    public Vec3D z(){
        return new Vec3D(arr[0][2], arr[1][2], arr[2][2]);
    }

    /**
     * Gets the translation vector of this matrix.
     *
     * @return the translation vector represented as a {@code Vec3D}.
     */
    public Vec3D w(){
        return new Vec3D(arr[0][3], arr[1][3], arr[2][3]);
    }

}
