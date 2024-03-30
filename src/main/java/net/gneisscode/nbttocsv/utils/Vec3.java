package net.gneisscode.nbttocsv.utils;

import com.almasb.fxgl.core.math.Vec2;
import com.mojang.serialization.Codec;

import java.util.EnumSet;
import java.util.List;

public class Vec3 implements Position{
    public static final Codec<Vec3> CODEC = Codec.DOUBLE.listOf().comapFlatMap((p_231079_) -> {
        return Util.fixedSize(p_231079_, 3).map((p_231081_) -> {
            return new Vec3(p_231081_.get(0), p_231081_.get(1), p_231081_.get(2));
        });
    }, (p_231083_) -> {
        return List.of(p_231083_.x(), p_231083_.y(), p_231083_.z());
    });
    public static final Vec3 ZERO = new Vec3(0.0D, 0.0D, 0.0D);
    public final double x;
    public final double y;
    public final double z;

    public static Vec3 fromRGB24(int pPacked) {
        double d0 = (double)(pPacked >> 16 & 255) / 255.0D;
        double d1 = (double)(pPacked >> 8 & 255) / 255.0D;
        double d2 = (double)(pPacked & 255) / 255.0D;
        return new Vec3(d0, d1, d2);
    }

    /**
     * Copies the coordinates of an Int vector and centers them.
     */
    public static Vec3 atCenterOf(Vec3i pToCopy) {
        return new Vec3((double)pToCopy.getX() + 0.5D, (double)pToCopy.getY() + 0.5D, (double)pToCopy.getZ() + 0.5D);
    }

    /**
     * Copies the coordinates of an int vector exactly.
     */
    public static Vec3 atLowerCornerOf(Vec3i pToCopy) {
        return new Vec3((double)pToCopy.getX(), (double)pToCopy.getY(), (double)pToCopy.getZ());
    }

    /**
     * Copies the coordinates of an int vector and centers them horizontally (x and z)
     */
    public static Vec3 atBottomCenterOf(Vec3i pToCopy) {
        return new Vec3((double)pToCopy.getX() + 0.5D, (double)pToCopy.getY(), (double)pToCopy.getZ() + 0.5D);
    }

    /**
     * Copies the coordinates of an int vector and centers them horizontally and applies a vertical offset.
     */
    public static Vec3 upFromBottomCenterOf(Vec3i pToCopy, double pVerticalOffset) {
        return new Vec3((double)pToCopy.getX() + 0.5D, (double)pToCopy.getY() + pVerticalOffset, (double)pToCopy.getZ() + 0.5D);
    }

    public Vec3(double pX, double pY, double pZ) {
        this.x = pX;
        this.y = pY;
        this.z = pZ;
    }

//    public Vec3(Vector3f pFloatVector) {
//        this((double)pFloatVector.x(), (double)pFloatVector.y(), (double)pFloatVector.z());
//    }

    /**
     * Returns a new vector with the result of the specified vector minus this.
     */
    public Vec3 vectorTo(Vec3 pVec) {
        return new Vec3(pVec.x - this.x, pVec.y - this.y, pVec.z - this.z);
    }

    /**
     * Normalizes the vector to a length of 1 (except if it is the zero vector)
     */
    public Vec3 normalize() {
        double d0 = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        return d0 < 1.0E-4D ? ZERO : new Vec3(this.x / d0, this.y / d0, this.z / d0);
    }

    public double dot(Vec3 pVec) {
        return this.x * pVec.x + this.y * pVec.y + this.z * pVec.z;
    }

    /**
     * Returns a new vector with the result of this vector x the specified vector.
     */
    public Vec3 cross(Vec3 pVec) {
        return new Vec3(this.y * pVec.z - this.z * pVec.y, this.z * pVec.x - this.x * pVec.z, this.x * pVec.y - this.y * pVec.x);
    }

    public Vec3 subtract(Vec3 pVec) {
        return this.subtract(pVec.x, pVec.y, pVec.z);
    }

    public Vec3 subtract(double pX, double pY, double pZ) {
        return this.add(-pX, -pY, -pZ);
    }

    public Vec3 add(Vec3 pVec) {
        return this.add(pVec.x, pVec.y, pVec.z);
    }

    /**
     * Adds the specified x,y,z vector components to this vector and returns the resulting vector. Does not change this
     * vector.
     */
    public Vec3 add(double pX, double pY, double pZ) {
        return new Vec3(this.x + pX, this.y + pY, this.z + pZ);
    }

    /**
     * Checks if a position is within a certain distance of the coordinates.
     */
    public boolean closerThan(Position pPos, double pDistance) {
        return this.distanceToSqr(pPos.x(), pPos.y(), pPos.z()) < pDistance * pDistance;
    }

    /**
     * Euclidean distance between this and the specified vector, returned as double.
     */
    public double distanceTo(Vec3 pVec) {
        double d0 = pVec.x - this.x;
        double d1 = pVec.y - this.y;
        double d2 = pVec.z - this.z;
        return Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }

    /**
     * The square of the Euclidean distance between this and the specified vector.
     */
    public double distanceToSqr(Vec3 pVec) {
        double d0 = pVec.x - this.x;
        double d1 = pVec.y - this.y;
        double d2 = pVec.z - this.z;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public double distanceToSqr(double pX, double pY, double pZ) {
        double d0 = pX - this.x;
        double d1 = pY - this.y;
        double d2 = pZ - this.z;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public Vec3 scale(double pFactor) {
        return this.multiply(pFactor, pFactor, pFactor);
    }

    public Vec3 reverse() {
        return this.scale(-1.0D);
    }

    public Vec3 multiply(Vec3 pVec) {
        return this.multiply(pVec.x, pVec.y, pVec.z);
    }

    public Vec3 multiply(double pFactorX, double pFactorY, double pFactorZ) {
        return new Vec3(this.x * pFactorX, this.y * pFactorY, this.z * pFactorZ);
    }

    /**
     * Returns the length of the vector.
     */
    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public double lengthSqr() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public double horizontalDistance() {
        return Math.sqrt(this.x * this.x + this.z * this.z);
    }

    public double horizontalDistanceSqr() {
        return this.x * this.x + this.z * this.z;
    }

    public boolean equals(Object pOther) {
        if (this == pOther) {
            return true;
        } else if (!(pOther instanceof Vec3)) {
            return false;
        } else {
            Vec3 vec3 = (Vec3)pOther;
            if (Double.compare(vec3.x, this.x) != 0) {
                return false;
            } else if (Double.compare(vec3.y, this.y) != 0) {
                return false;
            } else {
                return Double.compare(vec3.z, this.z) == 0;
            }
        }
    }

    public int hashCode() {
        long j = Double.doubleToLongBits(this.x);
        int i = (int)(j ^ j >>> 32);
        j = Double.doubleToLongBits(this.y);
        i = 31 * i + (int)(j ^ j >>> 32);
        j = Double.doubleToLongBits(this.z);
        return 31 * i + (int)(j ^ j >>> 32);
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }


    public Vec3 lerp(Vec3 pTo, double pDelta) {
        return new Vec3(Util.lerp(pDelta, this.x, pTo.x), Util.lerp(pDelta, this.y, pTo.y), Util.lerp(pDelta, this.z, pTo.z));
    }

    public Vec3 xRot(float pPitch) {
        double f = Math.cos(pPitch);
        double f1 = Math.sin(pPitch);
        double d0 = this.x;
        double d1 = this.y * f + this.z * f1;
        double d2 = this.z * f - this.y * f1;
        return new Vec3(d0, d1, d2);
    }

    public Vec3 yRot(float pYaw) {
        double f = Math.cos(pYaw);
        double f1 = Math.sin(pYaw);
        double d0 = this.x * f + this.z * f1;
        double d1 = this.y;
        double d2 = this.z * f - this.x * f1;
        return new Vec3(d0, d1, d2);
    }

    public Vec3 zRot(float pRoll) {
        double f = Math.cos(pRoll);
        double f1 = Math.sin(pRoll);
        double d0 = this.x * f + this.y * f1;
        double d1 = this.y * f - this.x * f1;
        double d2 = this.z;
        return new Vec3(d0, d1, d2);
    }

    /**
     * returns a Vec3d from given pitch and yaw degrees as Vec2f
     */
    public static Vec3 directionFromRotation(Vec2 pVec) {
        return directionFromRotation(pVec.x, pVec.y);
    }

    /**
     * returns a Vec3d from given pitch and yaw degrees
     */
    public static Vec3 directionFromRotation(float pPitch, float pYaw) {
        double f =  Math.cos(-pYaw * ((float)Math.PI / 180F) - (float)Math.PI);
        double f1 =  Math.sin(-pYaw * ((float)Math.PI / 180F) - (float)Math.PI);
        double f2 =  -Math.cos(-pPitch * ((float)Math.PI / 180F));
        double f3 =  Math.sin(-pPitch * ((float)Math.PI / 180F));
        return new Vec3((f1 * f2), f3, (f * f2));
    }

    public Vec3 align(EnumSet<Direction.Axis> pAxes) {
        double d0 = pAxes.contains(Direction.Axis.X) ? Math.floor(this.x) : this.x;
        double d1 = pAxes.contains(Direction.Axis.Y) ? Math.floor(this.y) : this.y;
        double d2 = pAxes.contains(Direction.Axis.Z) ? Math.floor(this.z) : this.z;
        return new Vec3(d0, d1, d2);
    }

    public double get(Direction.Axis pAxis) {
        return pAxis.choose(this.x, this.y, this.z);
    }

    public Vec3 with(Direction.Axis pAxis, double pLength) {
        double d0 = pAxis == Direction.Axis.X ? pLength : this.x;
        double d1 = pAxis == Direction.Axis.Y ? pLength : this.y;
        double d2 = pAxis == Direction.Axis.Z ? pLength : this.z;
        return new Vec3(d0, d1, d2);
    }

    public Vec3 relative(Direction p_231076_, double p_231077_) {
        Vec3i vec3i = p_231076_.getNormal();
        return new Vec3(this.x + p_231077_ * (double)vec3i.getX(), this.y + p_231077_ * (double)vec3i.getY(), this.z + p_231077_ * (double)vec3i.getZ());
    }

    public final double x() {
        return this.x;
    }

    public final double y() {
        return this.y;
    }

    public final double z() {
        return this.z;
    }
}

