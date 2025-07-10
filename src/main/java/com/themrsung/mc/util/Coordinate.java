package com.themrsung.mc.util;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Coordinate data class.
 */
public class Coordinate {
    /**
     * Creates a new coordinate.
     *
     * @param worldName The world name
     * @param x         The X coordinate
     * @param y         The Y coordinate
     * @param z         The Z coordinate
     */
    public Coordinate(String worldName, double x, double y, double z) {
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public final String worldName;
    public final double x;
    public final double y;
    public final double z;

    /**
     * Returns the rounded coordinates.
     *
     * @return The rounded coordinates
     */
    public Coordinate rounded() {
        return new Coordinate(
                worldName,
                Math.round(x),
                Math.round(y),
                Math.round(z)
        );
    }

    /**
     * Returns the area of a square drawn between this and the other coordinate.
     *
     * @param other The other coordinate
     * @return The area
     */
    public double areaBetween(Coordinate other) {
        var dx = Math.abs(x - other.x);
        var dz = Math.abs(z - other.z);
        return dx * dz;
    }

    /**
     * Returns the volume of a cube drawn between this and the other coordinate.
     *
     * @param other The other coordinate
     * @return The volume
     */
    public double volumeBetween(Coordinate other) {
        var dx = Math.abs(x - other.x);
        var dy = Math.abs(y - other.y);
        var dz = Math.abs(z - other.z);
        return dx * dy * dz;
    }

    /**
     * Checks if the given coordinate {@code c} is within the bounds of {@code pos1} and {@code pos2}.
     *
     * @param pos1 The first position
     * @param pos2 The second position
     * @param c    The coordinate to check
     * @return {@code true} if the coordinate {@code c} is within the bounds
     */
    public static boolean contains(Coordinate pos1, Coordinate pos2, Coordinate c) {
        if (!pos1.worldName.equalsIgnoreCase(pos2.worldName) || !pos2.worldName.equalsIgnoreCase(c.worldName))
            return false;

        var containsX = (c.x <= Math.max(pos1.x, pos2.x)) && (c.x >= Math.min(pos1.x, pos2.x));
        var containsY = (c.y <= Math.max(pos1.y, pos2.y)) && (c.y >= Math.min(pos1.y, pos2.y));
        var containsZ = (c.z <= Math.max(pos1.z, pos2.z)) && (c.z >= Math.min(pos1.z, pos2.z));

        return containsX && containsY && containsZ;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Coordinate c)) return false;

        return Objects.equals(worldName, c.worldName)
                && x == c.x && y == c.y && z == c.z;
    }

    /**
     * Parses the given coordinate.
     *
     * @param s The string
     * @return The parsed coordinate
     * @throws IllegalArgumentException When the format is invalid
     */
    public static @NotNull Coordinate parseCoordinate(String s) throws IllegalArgumentException {
        if (s == null) throw new IllegalArgumentException("Invalid input string");

        var parts = s
                .replace("{", "")
                .replace("}", "")
                .replace("[", "")
                .replace("]", "")
                .replace(" ", "")
                .split(",");

        if (parts.length != 4) throw new IllegalArgumentException("Invalid coordinate format");
        String world = parts[0];
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);
        return new Coordinate(world, x, y, z);
    }

    @Override
    public String toString() {
        return "{" + worldName + ", [" + x + ", " + y + ", " + z + "]}";
    }
}
